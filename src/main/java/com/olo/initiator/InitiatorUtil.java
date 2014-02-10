package com.olo.initiator;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.log4testng.Logger;

import com.olo.propertyutil.ConfigProperties;
import com.olo.util.Commons;
import com.olo.util.TestProp;

public class InitiatorUtil {
	
	private static final Logger LOGGER = Logger.getLogger(InitiatorUtil.class);
	public DriverConfiguration driverConfig = new DriverConfiguration();
	protected ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>();
	
	public void captureScreenShotOnTestFailure(ITestResult result){
		if(result.getStatus()==ITestResult.FAILURE && result.getThrowable().getMessage()!=Commons.verificationFailuresMessage){
			takeScreenShotForTest(getDriver(), result);
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
		if(result.getTestContext().getSuite().getParallel().equals("true") && ConfigProperties.getRemoteExecution()){
			takeScreenshot = false;
		}
		if(takeScreenshot && ConfigProperties.getCaptureScreenshot()){
			try {
				String screenShotFileName=System.currentTimeMillis()+".png";
				String screenShotPath=result.getTestContext().getOutputDirectory()+File.separator+"screenshots"+File.separator+screenShotFileName;
				File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcFile, new File(screenShotPath));
				result.setAttribute(TestProp.SCREENSHOT, screenShotFileName);
			} catch (Exception e) {
				if(e != null){
					LOGGER.warn("Could not take screenshot "+e.getMessage());
				}
			}
		}
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
