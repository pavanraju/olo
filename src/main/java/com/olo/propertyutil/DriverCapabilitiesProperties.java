package com.olo.propertyutil;

import java.util.Properties;

import org.testng.log4testng.Logger;

public class DriverCapabilitiesProperties {
	
	private static final Logger LOGGER = Logger.getLogger(DriverCapabilitiesProperties.class);
	private static Properties capProp = new Properties();
	
	static{
		loadProperties();
	}
	
	private static void loadProperties() {
		try {
			if(capProp.isEmpty()){
				capProp.load(DriverCapabilitiesProperties.class.getResourceAsStream("/config/capabilities/"+ConfigProperties.getCapabilitiesFile()));
			}
		} catch (Exception e) {
			LOGGER.error("An error occured while loading configuration properties "+e.getMessage());
			System.exit(1);
		}
	}
	
	public static String getProperty(String capability){
		return capProp.getProperty(capability);
	}
	
}
