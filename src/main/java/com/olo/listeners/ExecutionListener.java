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
		        
		        String jqueryminFile = "jquery-1.10.1.min.js";
		        InputStream jqueryminjs = getClass().getResourceAsStream("/bootstrap/js/"+ jqueryminFile);
		        
		        if(jqueryminjs!=null){
		        	Files.copyFile(jqueryminjs, new File(System.getProperty("reportsDirectory")+"/bootstrap/js/", jqueryminFile));
		        }
		        
		        String bootstrapminFile = "bootstrap.min.js";
		        InputStream bootstrapminjs = getClass().getResourceAsStream("/bootstrap/js/"+ bootstrapminFile);
		        
		        if(bootstrapminjs!=null){
		        	Files.copyFile(bootstrapminjs, new File(System.getProperty("reportsDirectory")+"/bootstrap/js/", bootstrapminFile));
		        }
		        
		        String bootstrapminCssFile = "bootstrap.min.css";
		        InputStream bootstrapmincss = getClass().getResourceAsStream("/bootstrap/css/"+ bootstrapminCssFile);
		        
		        if(bootstrapmincss!=null){
		        	Files.copyFile(bootstrapmincss, new File(System.getProperty("reportsDirectory")+"/bootstrap/css/", bootstrapminCssFile));
		        }
		        
		        String bootstrapMinResponsiveCssFile = "bootstrap-responsive.min.css";
		        InputStream bootstrapminrespcss = getClass().getResourceAsStream("/bootstrap/css/"+ bootstrapMinResponsiveCssFile);
		        
		        if(bootstrapminrespcss!=null){
		        	Files.copyFile(bootstrapminrespcss, new File(System.getProperty("reportsDirectory")+"/bootstrap/css/", bootstrapMinResponsiveCssFile));
		        }
		        
		        String bootstrapIcons = "glyphicons-halflings.png";
		        InputStream bootstrapicons = getClass().getResourceAsStream("/bootstrap/img/"+ bootstrapIcons);
		        
		        if(bootstrapicons!=null){
		        	Files.copyFile(bootstrapicons, new File(System.getProperty("reportsDirectory")+"/bootstrap/img/", bootstrapIcons));
		        }
		        
		        String bootstrapIconsWhite = "glyphicons-halflings-white.png";
		        InputStream bootstrapiconswhite = getClass().getResourceAsStream("/bootstrap/img/"+ bootstrapIconsWhite);
		        
		        if(bootstrapiconswhite!=null){
		        	Files.copyFile(bootstrapiconswhite, new File(System.getProperty("reportsDirectory")+"/bootstrap/img/", bootstrapIconsWhite));
		        }
		        
		        //FileUtils.copyDirectory(new File(ExecutionListener.class.getResource("/bootstrap").toURI()),new File(System.getProperty("reportsDirectory")+"/bootstrap"));
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			
	}

}
