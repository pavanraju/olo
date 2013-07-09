package com.olo.listeners;

import java.io.File;
import java.io.InputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.IExecutionListener;
import org.testng.reporters.Files;

public class ExecutionListener implements IExecutionListener{
	
	private static final Logger logger = LogManager.getLogger(ExecutionListener.class.getName());
	
	public void onExecutionFinish() {
		
	}

	public void onExecutionStart() {
		
			try {
				String oloCssFile = "olostyles.css";
				InputStream olocss = getClass().getResourceAsStream("/css/"+ oloCssFile);
		        
		        if(olocss!=null){
		        	Files.copyFile(olocss, new File(System.getProperty("reportsDirectory"), oloCssFile));
		        }
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			
	}

}
