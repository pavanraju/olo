package com.olo.initiator;

import static com.olo.util.PropertyReader.configProp;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.olo.util.TestProp;
import com.opera.core.systems.OperaDriver;

public class Configuration {
	
	private static final Logger logger = LogManager.getLogger(Configuration.class.getName());
	
	public DesiredCapabilities getInternetExplorerCapabilities(){
		return DesiredCapabilities.internetExplorer();
	}
	
	public DesiredCapabilities getFirefoxCapabilities(){
		return DesiredCapabilities.firefox();
	}
	
	public DesiredCapabilities getChromeCapabilities(){
		return DesiredCapabilities.chrome();
	}
	
	public DesiredCapabilities getOperaCapabilities(){
		return DesiredCapabilities.opera();
	}
	
	public DesiredCapabilities getSafariCapabilities(){
		return DesiredCapabilities.safari();
	}
	
	public DesiredCapabilities getAndroidCapabilities(){
		return DesiredCapabilities.android();
	}
	
	public DesiredCapabilities getHtmlUnitCapabilities(){
		return DesiredCapabilities.htmlUnit();
	}
	
	public DesiredCapabilities getCapabilities(String browser) throws Exception{
		DesiredCapabilities capabilities = null;
		if(browser.equals("firefox")){
			capabilities = getFirefoxCapabilities();
		}else if(browser.equals("internet explorer")){
			capabilities = getInternetExplorerCapabilities();
		}else if(browser.equals("chrome")){
			capabilities = getChromeCapabilities();
		}else if(browser.equals("opera")){
			capabilities = getOperaCapabilities();
		}else if(browser.equals("safari")){
			capabilities = getSafariCapabilities();
		}else if(browser.equals("android")){
			capabilities = getAndroidCapabilities();
		}else if(browser.equals("htmlunit")){
			capabilities = getHtmlUnitCapabilities();
		}else{
			throw new Exception("Un Supported Browser");
		}
		return capabilities;
	}
	
	public WebDriver getInternetExplorerDriver(DesiredCapabilities capabilities){
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/win/IEDriverServer.exe");
		return new InternetExplorerDriver(capabilities);
	}
	
	public WebDriver getFirefoxDriver(DesiredCapabilities capabilities){
		return new FirefoxDriver(capabilities);
	}
	
	public WebDriver getChromeDriver(DesiredCapabilities capabilities){
		String driverFolder=null;
		if(Platform.getCurrent().is(Platform.WINDOWS)){
			driverFolder="win";
		}else if(Platform.getCurrent().is(Platform.MAC)){
			driverFolder="mac";
		}else{
			driverFolder="linux";
		}
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/"+driverFolder+"/chromedriver.exe");
		return new ChromeDriver(capabilities);
	}
	
	public WebDriver getOperaDriver(DesiredCapabilities capabilities){
		return new OperaDriver(capabilities);
	}
	
	public WebDriver getSafariDriver(DesiredCapabilities capabilities){
		return new SafariDriver(capabilities);
	}
	
	public WebDriver getAndroidDriver(DesiredCapabilities capabilities){
		return new AndroidDriver(capabilities);
	}
	
	public WebDriver getHtmlUnitDriver(DesiredCapabilities capabilities){
		capabilities.setJavascriptEnabled(true);
		return new HtmlUnitDriver(capabilities);
	}
	
	public WebDriver getRemoteWebDriverDriver(String hubURL, DesiredCapabilities capabilities) throws Exception{
		return new RemoteWebDriver(new URL(hubURL),capabilities);
	}
	
	public WebDriver getWebDriver(ITestContext ctx) throws Exception{
		String browser = configProp.getProperty("browser");
		DesiredCapabilities capabilities =  getCapabilities(browser);
		if(!ctx.getSuite().getParallel().equals("false") && configProp.containsKey("remoteExecution") && configProp.getProperty("remoteExecution").equals("true")){
			String hubURL = configProp.getProperty("hubURL");
			return getRemoteWebDriverDriver(hubURL, capabilities);
		}else{
			if(browser.equals("firefox")){
				return getFirefoxDriver(capabilities);
			}else if(browser.equals("internet explorer")){
				return getInternetExplorerDriver(capabilities);
			}else if(browser.equals("chrome")){
				return getChromeDriver(capabilities);
			}else if(browser.equals("opera")){
				return getOperaDriver(capabilities);
			}else if(browser.equals("safari")){
				return getSafariDriver(capabilities);
			}else if(browser.equals("android")){
				return getAndroidDriver(capabilities);
			}else if(browser.equals("htmlunit")){
				return getHtmlUnitDriver(capabilities);
			}else{
				throw new Exception("Unsupported Browser");
			}
		}
	}
	
	public WebDriver getDriverBySetTimeOuts(){
		return getDriverBySetTimeOuts(Reporter.getCurrentTestResult().getTestContext());
	}
	
	public WebDriver getDriverBySetTimeOuts(ITestContext ctx){
		try {
			WebDriver driver = getWebDriver(ctx);
			setWaitForPageToLoadInSec(driver);
			setImplicitWaitInSec(driver);
			windowMaximizeAndWindowFocus(driver);
			logger.info("setting up browser preferences completed");
			return driver;
		} catch (Exception e) {
			throw new SkipException(e.getMessage());
		} catch (Throwable e) {
			throw new SkipException(e.getCause().getMessage());
		}
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
	
	public void setWaitForPageToLoadInSec(WebDriver driver){
		if(configProp.containsKey("pageWaitAndWaitTimeOut")){
			int timeout = Integer.parseInt(configProp.getProperty("pageWaitAndWaitTimeOut"));
			setWaitForPageToLoadInSec(driver, timeout);
		}
	}
	
	public void setWaitForPageToLoadInSec(WebDriver driver,long sec){
		logger.info("Setting pageLoadTimeout to "+sec+" seconds");
		driver.manage().timeouts().pageLoadTimeout(sec, TimeUnit.SECONDS);
	}
	
	public void setImplicitWaitInSec(WebDriver driver){
		if(configProp.containsKey("implicitWait")){
			int implicitWait = Integer.parseInt(configProp.getProperty("implicitWait"));
			setImplicitWaitInSec(driver, implicitWait);
		}
	}
	
	public void setImplicitWaitInSec(WebDriver driver,long sec){
		logger.info("Setting implicitlyWait to "+sec+" seconds");
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}
	
	public void windowMaximizeAndWindowFocus(WebDriver driver){
		logger.info("Trying to maximize and focus the window");
		driver.manage().window().maximize();
		driver.switchTo().window(driver.getWindowHandle());
	}
	
	public void openUrl(WebDriver driver,String url){
		logger.info("Trying to open url "+url);
		driver.get(url);
		logger.info("current url is "+driver.getCurrentUrl());
	}
	
	public void deleteCookies(WebDriver driver){
		driver.manage().deleteAllCookies();
	}
	
	public void closeDriver(WebDriver driver){
		if(driver!=null){
			try {
				logger.info("Trying to Stop WebDriver");
				driver.quit();
				logger.info("WebDriver Stopped");
			} catch (Exception e) {
				logger.error("Error in stopping WebDriver "+e.getMessage());
			}
		}
	}
	
	public void handleAfterMethod(WebDriver driver, ITestResult result){
		if(result.getStatus()==ITestResult.FAILURE){
			takeScreenShotForTest(driver, result);
		}
		closeDriver(driver);
	}
	
	public void setPropertyForTest(ITestResult result, String propertyName, String propertyValue){
		result.setAttribute(propertyName, propertyValue);
	}
	
	public void setPropertyForTest(String propertyName, String propertyValue){
		Reporter.getCurrentTestResult().setAttribute(propertyName, propertyValue);
	}
	
}
