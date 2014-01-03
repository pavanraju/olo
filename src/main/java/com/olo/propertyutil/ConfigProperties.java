package com.olo.propertyutil;

import static com.olo.propertyutil.WebElementsProperties.webElementsProp;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ConfigProperties {
	
	private static final Logger logger = LogManager.getLogger(ConfigProperties.class.getName());
	protected static Properties configProp = new Properties();
	
	static{
		loadProperties();
	}
	
	private static void loadProperties() {
		try {
			if(configProp.isEmpty()){
				configProp.load(ConfigProperties.class.getResourceAsStream("/config/config.properties"));
			}
			if(getWebElements()){
				URL propertyFilesUrl = ConfigProperties.class.getResource("/webElements");
				if(propertyFilesUrl!=null){
						File propFolder = new File(propertyFilesUrl.toURI());
						for (File nextFile : propFolder.listFiles()) {
							if (nextFile.isFile() && !nextFile.isHidden()) {
								String fileName=nextFile.getName();
								if(FilenameUtils.getExtension(fileName).equals("properties")){
									logger.info("Loading /webElements/"+fileName);
									Properties temp = new Properties();
									temp.load(new FileInputStream(nextFile));
									webElementsProp.put(FilenameUtils.getBaseName(fileName), temp);
								}
							}
					    }
				}
			}
		} catch (Exception e) {
			logger.error("An error occured while loading configuration properties "+e.getMessage());
			System.exit(1);
		}
	}
	
	public static String getApplicationUrl(){
		return configProp.getProperty("applicationUrl");
	}
	
	public static String getBrowser(){
		return configProp.getProperty("browser");
	}
	
	public static int getImplicitWait(){
		try {
			return Integer.parseInt(configProp.getProperty("implicitWait"));
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static int getPageWaitAndWaitTimeOut(){
		try {
			return Integer.parseInt(configProp.getProperty("pageWaitAndWaitTimeOut"));
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static boolean getRemoteExecution(){
		try {
			return Boolean.parseBoolean(configProp.getProperty("remoteExecution"));
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getHubUrl(){
		return configProp.getProperty("hubURL");
	}
	
	public static boolean getWebElements(){
		try {
			return Boolean.parseBoolean(configProp.getProperty("webElements"));
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getDateFormat(){
		return configProp.getProperty("dateFormat");
	}
	
	public static void setBrowser(String browser){
		configProp.setProperty("browser", browser);
	}
	
}
