package com.olo.propertyutil;

import java.util.Properties;

public class MailProperties {
	
	public static Properties mailProp = new Properties();
	
	static{
		loadProperties();
	}
	
	private static void loadProperties() {
		try {
			if(mailProp.isEmpty()){
				mailProp.load(MailProperties.class.getResourceAsStream("/config/mail.properties"));
			}
		} catch (Exception e) {
			
		}
		
	}
	
}
