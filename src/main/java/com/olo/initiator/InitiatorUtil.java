package com.olo.initiator;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.log4testng.Logger;

import com.olo.util.CaptureScreenshot;
import com.olo.util.Commons;
import com.olo.util.TestProp;

public class InitiatorUtil {
	
	private static final Logger LOGGER = Logger.getLogger(InitiatorUtil.class);
	public DriverConfiguration driverConfig = new DriverConfiguration();
	protected ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>();
	
	public void captureScreenShotOnTestFailure(ITestResult result){
		if(result.getStatus()==ITestResult.FAILURE && result.getThrowable().getMessage()!=Commons.verificationFailuresMessage){
			String screenShotFileName = CaptureScreenshot.takeScreenShotAndReturnFileName(getDriver(), result);
			if(screenShotFileName!=null){
				result.setAttribute(TestProp.SCREENSHOT, screenShotFileName);
			}
		}
	}
	
	public void closeDriver(){
		if(getDriver()!=null){
			try {
				LOGGER.info("Trying to Stop WebDriver");
				getDriver().quit();
				LOGGER.info("WebDriver Stopped");
			} catch (Exception e) {
				LOGGER.error("Error in stopping WebDriver "+e.getMessage());
			}
		}
	}
	
	public void configureDriver(ITestContext ctx){
		threadDriver.set(driverConfig.getDriverBySetTimeOuts(ctx));
	}
	
	public WebDriver getDriver() {
        return threadDriver.get();
    }
	
	public void setPropertyForTest(String propertyName, String propertyValue){
		Reporter.getCurrentTestResult().setAttribute(propertyName, propertyValue);
	}
	
	public void openUrl(String url){
		LOGGER.info("Trying to open url "+url);
		getDriver().get(url);
		LOGGER.info("current url is "+getDriver().getCurrentUrl());
	}
	
	public void deleteCookies(WebDriver driver){
		driver.manage().deleteAllCookies();
	}
	
}
