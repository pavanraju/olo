package com.olo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PropertyReader {
	
	private static final Logger logger = LogManager.getLogger(PropertyReader.class.getName());
	
	public static HashMap<String, Properties> webElements = new HashMap<String, Properties>();
	public static Properties configProp = new Properties();
	public static Properties mailProp = new Properties();
	
	static{
		try {
			
			if(configProp.isEmpty()){
				logger.info("Loading config.properties");
				configProp.load(PropertyReader.class.getResourceAsStream("/config/config.properties"));
			}
			if(mailProp.isEmpty()){
				logger.info("Loading mail.properties");
				mailProp.load(PropertyReader.class.getResourceAsStream("/config/mail.properties"));
			}
			
			if(configProp.containsKey("webElements") && configProp.getProperty("webElements").equals("true")){
				URL propertyFilesUrl = PropertyReader.class.getResource("/webElements");
				if(propertyFilesUrl!=null){
					try {
						File propFolder = new File(propertyFilesUrl.toURI());
						for (File nextFile : propFolder.listFiles()) {
							if (nextFile.isFile() && !nextFile.isHidden()) {
								String fileName=nextFile.getName();
								if(FilenameUtils.getExtension(fileName).equals("properties")){
									logger.info("Loading /webElements/"+fileName);
									Properties temp = new Properties();
									temp.load(new FileInputStream(nextFile));
									webElements.put(FilenameUtils.getBaseName(fileName), temp);
								}
							}
					    }
					} catch (URISyntaxException e) {
						logger.error(e.getMessage());
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			logger.error("File Not Found in the specified location "+e.getMessage());
		} catch (IOException e) {
			logger.error("Could not able to open file "+e.getMessage());
		}
	}
}
