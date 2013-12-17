package com.olo.initiator;

import static com.olo.util.PropertyReader.configProp;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.olo.util.Commons;
import com.olo.util.TestProp;

public class InitiatorUtil {
	
	private static final Logger logger = LogManager.getLogger(InitiatorUtil.class.getName());
	public DriverConfiguration driverConfig = new DriverConfiguration();
	
	public void handleAfterMethod(WebDriver driver, ITestResult result){
		if(result.getStatus()==ITestResult.FAILURE && result.getThrowable().getMessage()!=Commons.verificationFailuresMessage){
			takeScreenShotForTest(driver, result);
		}
		driverConfig.closeDriver(driver);
	}
	
	public void setPropertyForTest(ITestResult result, String propertyName, String propertyValue){
		result.setAttribute(propertyName, propertyValue);
	}
	
	public void setPropertyForTest(String propertyName, String propertyValue){
		Reporter.getCurrentTestResult().setAttribute(propertyName, propertyValue);
	}
	
	public void takeScreenShotForTest(WebDriver driver) {
		ITestResult result = org.testng.Reporter.getCurrentTestResult();
		takeScreenShotForTest(driver, result);
	}
	
	public void takeScreenShotForTest(WebDriver driver, ITestResult result){
		boolean takeScreenshot = true;
		if(result.getTestContext().getSuite().getParallel().equals("true") && configProp.containsKey("remoteExecution") && configProp.getProperty("remoteExecution").equals("true")){
			takeScreenshot = false;
		}
		if(takeScreenshot){
			try {
				String screenShotFileName=System.currentTimeMillis()+".png";
				String screenShotPath=result.getTestContext().getOutputDirectory()+File.separator+"screenshots"+File.separator+screenShotFileName;
				File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile, new File(screenShotPath));
				result.setAttribute(TestProp.SCREENSHOT, screenShotFileName);
			} catch (Exception e) {
				if(e != null){
					logger.warn("Could not take screenshot "+e.getMessage());
				}
			}
		}
	}
	
	public void openUrl(WebDriver driver,String url){
		logger.info("Trying to open url "+url);
		driver.get(url);
		logger.info("current url is "+driver.getCurrentUrl());
	}
	
	public void deleteCookies(WebDriver driver){
		driver.manage().deleteAllCookies();
	}
	
}
