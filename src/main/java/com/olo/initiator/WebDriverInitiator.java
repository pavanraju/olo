package com.olo.initiator;

import static com.olo.util.PropertyReader.configProp;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class WebDriverInitiator extends Configuration{
	
	private static final Logger logger = LogManager.getLogger(WebDriverInitiator.class.getName());
	
	public WebDriver driver = null;
	
	@BeforeMethod
	public void driverStart(ITestContext ctx) throws Exception{
		try{
			try {
				String openBrowser = configProp.getProperty("browser");
				logger.info("Trying to Start WebDriver");
				DesiredCapabilities capabilities = getCapabilities(openBrowser);
				
				if(!ctx.getSuite().getParallel().equals("false")){
					String hubURL = configProp.getProperty("hubURL");
					driver = getRemoteWebDriverDriver(hubURL, capabilities);
				}else{
					driver = getDriver(openBrowser, capabilities);
				}
				Thread.sleep(1000);
				logger.info("WebDriver Started");
				
				if(configProp.containsKey("pageWaitAndWaitTimeOut")){
					int timeout = Integer.parseInt(configProp.getProperty("pageWaitAndWaitTimeOut"));
					logger.info("Setting pageloadtimeout");
					setWaitForPageToLoadInSec(driver, timeout);
				}
				if(configProp.containsKey("implicitWait")){
					int implicitWait = Integer.parseInt(configProp.getProperty("implicitWait"));
					logger.info("Setting implicit wait");
					setImplicitWaitInSec(driver, implicitWait);
				}
				if(configProp.containsKey("url")){
					logger.info("Trying to open url");
					openUrl(driver, configProp.getProperty("url"));
					logger.info("Trying to delete cookies");
					deleteCookies(driver);
				}
				logger.info("Trying to maximize and focus the window");
				windowMaximizeAndWindowFocus(driver);
				logger.info("setting up browser preferences completed");
			} catch (Exception e) {
				throw e;
			} catch (Throwable e) {
				throw new Exception(e.getCause().getMessage());
			}
		}catch (Exception e) {
			logger.error("WebDriver Failed To Start "+e.getMessage());
			throw e;
		}
	}
	
	@AfterMethod(alwaysRun=true)
	public void driverStop(ITestResult result){
		handleAfterMethod(driver, result);
	}
	
}
