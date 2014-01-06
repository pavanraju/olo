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
	
	public static String getSmtpHost(){
		return mailProp.getProperty("mail.smtp.host");
	}
	
	public static int getSmtpPort(){
		return Integer.parseInt(mailProp.getProperty("mail.smtp.port"));
	}
	
	public static boolean getSmtpStartTlsEnable(){
		return Boolean.parseBoolean(mailProp.getProperty("mail.smtp.starttls.enable"));
	}
	
	public static String getSmtpFrom(){
		return mailProp.getProperty("mail.smtp.from");
	}
	
	public static String getSmtpUser(){
		return mailProp.getProperty("mail.smtp.user");
	}
	
	public static String getSmtpPassword(){
		return mailProp.getProperty("mail.smtp.password");
	}
	
	public static String getMailTo(){
		return mailProp.getProperty("mail.to");
	}
	
	public static String getMailCC(){
		return mailProp.getProperty("mail.cc");
	}
	
}
