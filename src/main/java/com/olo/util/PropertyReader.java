package com.olo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PropertyReader {
	
	private static final Logger logger = LogManager.getLogger(PropertyReader.class.getName());
	
	public static HashMap<String, Properties> webElements = new HashMap<String, Properties>();
	public static HashMap<String, Properties> app = new HashMap<String, Properties>();
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
								String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
								String propName= fileName.substring(0,fileName.lastIndexOf("."));
								
								if(extension.equals("properties")){
									logger.info("Loading "+nextFile.getAbsolutePath());
									Properties temp = new Properties();
									temp.load(new FileInputStream(nextFile));
									webElements.put(propName, temp);
								}
								
							}
					    }
					} catch (URISyntaxException e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					}
				}
			}
			
			if(configProp.containsKey("app") && configProp.getProperty("app").equals("true")){
				URL messageFilesUrl = PropertyReader.class.getResource("/app");
				if(messageFilesUrl!=null){
					try {
						File messageFolder = new File(messageFilesUrl.toURI());
						for (File nextFile : messageFolder.listFiles()) {
							if (nextFile.isFile() && !nextFile.isHidden()) {
								String fileName=nextFile.getName();
								String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
								String propFileName= fileName.substring(0,fileName.lastIndexOf("."));
								
								if(extension.equals("properties")){
									logger.info("Loading "+nextFile.getAbsolutePath());
									Properties temp = new Properties();
									temp.load(new FileInputStream(nextFile));
									app.put(propFileName, temp);
								}
								
							}
					    }
					} catch (URISyntaxException e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			logger.error("File Not Found in the specified location "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Could not able to open file "+e.getMessage());
			e.printStackTrace();
		}
	}
}


