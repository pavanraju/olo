package com.olo.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

import com.olo.propertyutil.ConfigProperties;

public class CaptureScreenshot {
	
	private static final Logger LOGGER = Logger.getLogger(CaptureScreenshot.class);
	
	public static String takeScreenShotAndReturnFileName(WebDriver driver, ITestResult result){
		String screenShotFileName = null;
		if(ConfigProperties.getCaptureScreenshot()){
			try {
				screenShotFileName=System.currentTimeMillis()+".png";
				String screenShotPath=result.getTestContext().getOutputDirectory()+File.separator+"screenshots"+File.separator+screenShotFileName;
				File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile, new File(screenShotPath));
			} catch (Exception e) {
				screenShotFileName = null;
				if(e != null){
					LOGGER.warn("Could not take screenshot "+e.getMessage());
				}
			}
		}
		return screenShotFileName;
	}
	
}
