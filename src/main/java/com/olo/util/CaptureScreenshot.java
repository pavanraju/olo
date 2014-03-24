package com.olo.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

public class CaptureScreenshot {
	
	private static final Logger LOGGER = Logger.getLogger(CaptureScreenshot.class);
	
	public static String takeScreenShotAndReturnFileName(WebDriver driver, ITestResult result){
		String screenShotFileName = null;
		try {
			if(((RemoteWebDriver) driver).getCapabilities().is(CapabilityType.TAKES_SCREENSHOT)){
				byte[] screenshotBytes = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
				screenShotFileName=System.currentTimeMillis()+".png";
				String screenShotPath=result.getTestContext().getOutputDirectory()+File.separator+"screenshots"+File.separator+screenShotFileName;
				FileUtils.writeByteArrayToFile(new File(screenShotPath), screenshotBytes);
			}
		} catch (Exception e) {
			if(e != null){
				LOGGER.warn("Could not take screenshot "+e.getMessage());
			}
		}
		return screenShotFileName;
	}
	
}
