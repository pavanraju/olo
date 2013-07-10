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
import org.apache.log4j.PropertyConfigurator;

public class PropertyReader {
	
	private static final Logger logger = LogManager.getLogger(PropertyReader.class.getName());
	
	public static HashMap<String, Properties> allProp = new HashMap<String, Properties>();
	public static HashMap<String, Properties> messages = new HashMap<String, Properties>();
	public static Properties configProp = new Properties();
	public static Properties mailProp = new Properties();
	
	static{
		PropertyConfigurator.configure(PropertyReader.class.getResource("/properties/config/log4j.properties"));
		try {
			
			if(configProp.isEmpty()){
				logger.info("Loading config.properties");
				configProp.load(PropertyReader.class.getResourceAsStream("/properties/config/config.properties"));
			}
			if(mailProp.isEmpty()){
				logger.info("Loading mail.properties");
				mailProp.load(PropertyReader.class.getResourceAsStream("/properties/config/mail.properties"));
			}
			/*
			Enumeration<URL> propFolder = PropertyReader.class.getClassLoader().getResources("/properties");
			
			if (propFolder.hasMoreElements()) {
				URL fileURL=propFolder.nextElement();
				try {
					File file=new File(fileURL.toURI());
					if (file.isFile()) {
						String fileName=file.getName();
						String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
						String propName= fileName.substring(0,fileName.lastIndexOf("."));
						
						if(extension.equals("properties")){
							System.out.println("Loading "+fileName);
							Properties temp = new Properties();
							temp.load(new FileInputStream(file));
							allProp.put(propName, temp);
						}
						
					}
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
			*/
			
			
			URL propertyFilesUrl = PropertyReader.class.getResource("/properties/webelements");
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
								allProp.put(propName, temp);
							}
							
						}
				    }
					
				} catch (URISyntaxException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
			
			
			URL messageFilesUrl = PropertyReader.class.getResource("/properties/messages");
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
								messages.put(propFileName, temp);
							}
							
						}
				    }
					
				} catch (URISyntaxException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
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


