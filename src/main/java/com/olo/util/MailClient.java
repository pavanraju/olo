package com.olo.util;

import static com.olo.util.PropertyReader.mailProp;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;
import java.util.Properties;

public class MailClient {

    private static Properties mailProperties(){
    	Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailProp.getProperty("mail.host"));
        props.setProperty("mail.from", mailProp.getProperty("mail.from"));
		return props;
    }
    
    public void sendMail(String [] to,String subject,StringBuffer body) {
    	try {
    		Properties props=mailProperties();
        	boolean debug = false;
        	body.append("<br /><h3>Thanks  & Regards<br />Automation Team</h3>");
       	  	String messageBody = body.toString();
        	Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(debug);
            Transport transport = mailSession.getTransport("smtp");
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subject);
            //message.setFrom(new InternetAddress(Commons.mailFrom));
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(messageBody, "text/html");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(textPart);
            message.setContent(mp);
            transport.connect();
            for(int i=0; i< to.length; i++)
          	  message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));
            for(int i = 0; i<message.getAllRecipients().length; i++)
            	System.out.println("Mail Sending to "+message.getAllRecipients()[i]);
            transport.sendMessage(message,message.getAllRecipients());
            transport.close();
            System.out.println("Mail sent succesfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    public void sendMail(String [] to,String subject,StringBuffer body,String attachment) {
    	try {
    		Properties props=mailProperties();
        	boolean debug = false;
        	body.append("<br /><h3>Thanks  & Regards<br />Automation Team</h3>");
       	  	String messageBody = body.toString();
        	Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(debug);
            Transport transport = mailSession.getTransport("smtp");
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subject);
            //message.setFrom(new InternetAddress(Commons.mailFrom));
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(messageBody, "text/html");
            MimeBodyPart attachFilePart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attachment);
            attachFilePart.setDataHandler(new DataHandler(fds));
            attachFilePart.setFileName(fds.getName());
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(textPart);
            mp.addBodyPart(attachFilePart);
            message.setContent(mp);
            transport.connect();
            for(int i=0; i< to.length; i++)
          	  message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));
            for(int i = 0; i<message.getAllRecipients().length; i++)
            	System.out.println("Mail Sending to "+message.getAllRecipients()[i]);
            transport.sendMessage(message,message.getAllRecipients());
            transport.close();
            System.out.println("Mail sent succesfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void sendMail(String [] to,String subject,StringBuffer body,String [] cc) {
    	try {
    		Properties props=mailProperties();
        	boolean debug = false;
        	body.append("<br /><h3>Thanks  & Regards<br />Automation Team</h3>");
       	  	String messageBody = body.toString();
        	Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(debug);
            Transport transport = mailSession.getTransport("smtp");
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subject);
            //message.setFrom(new InternetAddress(Commons.mailFrom));
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(messageBody, "text/html");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(textPart);
            message.setContent(mp);
            transport.connect();
            for(int i=0; i< to.length; i++)
          	  message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));
            for(int i=0; i< cc.length; i++)
          	  message.addRecipient(Message.RecipientType.CC,new InternetAddress(cc[i]));
            for(int i = 0; i<message.getAllRecipients().length; i++)
            	System.out.println("Mail Sending to "+message.getAllRecipients()[i]);
            transport.sendMessage(message,message.getAllRecipients());
            transport.close();
            System.out.println("Mail sent succesfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    public void sendMail(String [] to,String subject,StringBuffer body,String [] cc,String attachment) {
    	try{
    		Properties props=mailProperties();
        	boolean debug = false;
        	body.append("<br /><h3>Thanks  & Regards<br />Automation Team</h3>");
       	  	String messageBody = body.toString();
        	Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(debug);
            Transport transport = mailSession.getTransport("smtp");
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subject);
            //message.setFrom(new InternetAddress(Commons.mailFrom));
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(messageBody, "text/html");
            MimeBodyPart attachFilePart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attachment);
            attachFilePart.setDataHandler(new DataHandler(fds));
            attachFilePart.setFileName(fds.getName());
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(textPart);
            mp.addBodyPart(attachFilePart);
            message.setContent(mp);
            transport.connect();
            for(int i=0; i< to.length; i++)
          	  message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));
            for(int i=0; i< cc.length; i++)
          	  message.addRecipient(Message.RecipientType.CC,new InternetAddress(cc[i]));
            for(int i = 0; i<message.getAllRecipients().length; i++)
            	System.out.println("Mail Sending to "+message.getAllRecipients()[i]);
            transport.sendMessage(message,message.getAllRecipients());
            transport.close();
            System.out.println("Mail sent succesfully!!");
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
