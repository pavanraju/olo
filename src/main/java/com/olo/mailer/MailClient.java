package com.olo.mailer;

import static com.olo.propertyutil.MailProperties.mailProp;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MailClient {
	
	private static final Logger logger = LogManager.getLogger(MailClient.class.getName());
	
	private static Properties mailProperties(){
    	Properties props = new Properties();
        props.setProperty("mail.smtp.host", mailProp.getProperty("mail.smtp.host"));
        
        if(mailProp.containsKey("mail.smtp.port")){
        	props.setProperty("mail.smtp.port", mailProp.getProperty("mail.smtp.port"));
        }
        
        if(mailProp.containsKey("mail.smtp.starttls.enable")){
        	props.setProperty("mail.smtp.starttls.enable", mailProp.getProperty("mail.smtp.starttls.enable"));
        }
        
        if(mailProp.containsKey("mail.smtp.auth")){
        	props.setProperty("mail.smtp.auth", mailProp.getProperty("mail.smtp.auth"));
        	if(mailProp.getProperty("mail.smtp.auth").equals("true")){
        		props.setProperty("mail.smtp.user", mailProp.getProperty("mail.smtp.user"));
        		props.setProperty("mail.smtp.password", mailProp.getProperty("mail.smtp.password"));
        	}
        }else{
        	props.setProperty("mail.smtp.auth", "false");
        }
        
        props.setProperty("mail.smtp.from", mailProp.getProperty("mail.smtp.from"));
		return props;
    }
	
	public void sendMail(String [] to, String subject, StringBuffer body) {
		try {
			Properties props = mailProperties();
			Session session = Session.getDefaultInstance(props, null);
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(props.getProperty("mail.smtp.from")));
		    
		    for( int i=0; i < to.length; i++) {
		        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
		    }
		    message.setSubject(subject);
		    
		    MimeBodyPart textPart = new MimeBodyPart();
	        textPart.setContent(body.toString(), "text/html");
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(textPart);
	        message.setContent(mp);
	        Transport transport = session.getTransport("smtp");
	        if(props.getProperty("mail.smtp.auth").equals("false")){
	        	transport.connect();
	        }else{
	        	transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
	        }
	        transport.sendMessage(message,message.getAllRecipients());
	        transport.close();
	        logger.info("Mail sent succesfully !!!");
		} catch (Exception e) {
			logger.error("Could not send email" +e.getMessage());
		}
		
	}
	
	public void sendMail(String [] to, String subject, StringBuffer body, String attachment) {
		try {
			Properties props = mailProperties();
			Session session = Session.getDefaultInstance(props, null);
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(props.getProperty("mail.smtp.from")));
		    
		    for( int i=0; i < to.length; i++) {
		        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
		    }
		    message.setSubject(subject);
		    
		    MimeBodyPart textPart = new MimeBodyPart();
	        textPart.setContent(body.toString(), "text/html");
	        
	        MimeBodyPart attachFilePart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attachment);
            attachFilePart.setDataHandler(new DataHandler(fds));
            attachFilePart.setFileName(fds.getName());
	        
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(textPart);
	        mp.addBodyPart(attachFilePart);
	        
	        message.setContent(mp);
	        Transport transport = session.getTransport("smtp");
	        if(props.getProperty("mail.smtp.auth").equals("false")){
	        	transport.connect();
	        }else{
	        	transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
	        }
	        transport.sendMessage(message,message.getAllRecipients());
	        transport.close();
	        logger.info("Mail sent succesfully !!!");
		} catch (Exception e) {
			logger.error("Could not send email" +e.getMessage());
		}
		
	}
	
	public void sendMail(String [] to, String subject, StringBuffer body, String [] cc) {
		try {
			Properties props = mailProperties();
			Session session = Session.getDefaultInstance(props, null);
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(props.getProperty("mail.smtp.from")));
		    
		    for( int i=0; i < to.length; i++) {
		        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
		    }
		    
		    for(int i=0; i< cc.length; i++)
	          	  message.addRecipient(Message.RecipientType.CC,new InternetAddress(cc[i]));
		    
		    message.setSubject(subject);
		    
		    MimeBodyPart textPart = new MimeBodyPart();
	        textPart.setContent(body.toString(), "text/html");
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(textPart);
	        message.setContent(mp);
	        Transport transport = session.getTransport("smtp");
	        if(props.getProperty("mail.smtp.auth").equals("false")){
	        	transport.connect();
	        }else{
	        	transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
	        }
	        transport.sendMessage(message,message.getAllRecipients());
	        transport.close();
	        logger.info("Mail sent succesfully !!!");
		} catch (Exception e) {
			logger.error("Could not send email" +e.getMessage());
		}
		
	}
	
	public void sendMail(String [] to, String subject, StringBuffer body, String [] cc,String attachment) {
		try {
			Properties props = mailProperties();
			Session session = Session.getDefaultInstance(props, null);
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(props.getProperty("mail.smtp.from")));
		    
		    for( int i=0; i < to.length; i++)
		        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
		    
		    for(int i=0; i< cc.length; i++)
	          	  message.addRecipient(Message.RecipientType.CC,new InternetAddress(cc[i]));
		    
		    message.setSubject(subject);
		    
		    MimeBodyPart textPart = new MimeBodyPart();
	        textPart.setContent(body.toString(), "text/html");
	        
	        MimeBodyPart attachFilePart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attachment);
            attachFilePart.setDataHandler(new DataHandler(fds));
            attachFilePart.setFileName(fds.getName());
	        
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(textPart);
	        message.setContent(mp);
	        mp.addBodyPart(attachFilePart);
	        
	        Transport transport = session.getTransport("smtp");
	        if(props.getProperty("mail.smtp.auth").equals("false")){
	        	transport.connect();
	        }else{
	        	transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
	        }
	        transport.sendMessage(message,message.getAllRecipients());
	        transport.close();
	        logger.info("Mail sent succesfully !!!");
		} catch (Exception e) {
			logger.error("Could not send email" +e.getMessage());
		}
		
	}
	
}
