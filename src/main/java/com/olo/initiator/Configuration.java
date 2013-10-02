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
import org.testng.SkipException;

import com.opera.core.systems.OperaDriver;

public class Configuration {
	
	private static final Logger logger = LogManager.getLogger(Configuration.class.getName());
	
	protected DesiredCapabilities getInternetExplorerCapabilities(){
		return DesiredCapabilities.internetExplorer();
	}
	
	protected DesiredCapabilities getFirefoxCapabilities(){
		return DesiredCapabilities.firefox();
	}
	
	protected DesiredCapabilities getChromeCapabilities(){
		return DesiredCapabilities.chrome();
	}
	
	protected DesiredCapabilities getOperaCapabilities(){
		return DesiredCapabilities.opera();
	}
	
	protected DesiredCapabilities getSafariCapabilities(){
		return DesiredCapabilities.safari();
	}
	
	protected DesiredCapabilities getAndroidCapabilities(){
		return DesiredCapabilities.android();
	}
	
	protected DesiredCapabilities getIphoneCapabilities(){
		return DesiredCapabilities.iphone();
	}
	
	protected DesiredCapabilities getIpadCapabilities(){
		return DesiredCapabilities.ipad();
	}
	
	protected DesiredCapabilities getHtmlUnitCapabilities(){
		return DesiredCapabilities.htmlUnit();
	}
	
	protected DesiredCapabilities getCapabilities(String browser) throws Exception{
		DesiredCapabilities capabilities = null;
		if(browser.equals("Firefox")){
			capabilities = getFirefoxCapabilities();
		}else if(browser.equals("Explorer")){
			capabilities = getInternetExplorerCapabilities();
		}else if(browser.equals("Chrome")){
			capabilities = getChromeCapabilities();
		}else if(browser.equals("Opera")){
			capabilities = getOperaCapabilities();
		}else if(browser.equals("Safari")){
			capabilities = getSafariCapabilities();
		}else if(browser.equals("Android")){
			capabilities = getAndroidCapabilities();
		}else if(browser.equals("HtmlUnit")){
			capabilities = getHtmlUnitCapabilities();
		}else{
			throw new Exception("Un Supported Browser");
		}
		return capabilities;
	}
	
	protected WebDriver getInternetExplorerDriver(DesiredCapabilities capabilities){
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/win/IEDriverServer.exe");
		return new InternetExplorerDriver(capabilities);
	}
	
	protected WebDriver getFirefoxDriver(DesiredCapabilities capabilities){
		return new FirefoxDriver(capabilities);
	}
	
	protected WebDriver getChromeDriver(DesiredCapabilities capabilities){
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
	
	protected WebDriver getOperaDriver(DesiredCapabilities capabilities){
		return new OperaDriver(capabilities);
	}
	
	protected WebDriver getSafariDriver(DesiredCapabilities capabilities){
		return new SafariDriver(capabilities);
	}
	
	protected WebDriver getAndroidDriver(DesiredCapabilities capabilities){
		return new AndroidDriver(capabilities);
	}
	
	protected WebDriver getHtmlUnitDriver(DesiredCapabilities capabilities){
		capabilities.setJavascriptEnabled(true);
		return new HtmlUnitDriver(capabilities);
	}
	
	protected WebDriver getRemoteWebDriverDriver(String hubURL, DesiredCapabilities capabilities) throws Exception{
		return new RemoteWebDriver(new URL(hubURL),capabilities);
	}
	
	protected WebDriver getDriver(ITestContext ctx) throws Exception{
		String browser = configProp.getProperty("browser");
		DesiredCapabilities capabilities =  getCapabilities(browser);
		if(!ctx.getSuite().getParallel().equals("false") && configProp.containsKey("parallelExecution") && configProp.getProperty("parallelExecution").equals("remote")){
			String hubURL = configProp.getProperty("hubURL");
			return getRemoteWebDriverDriver(hubURL, capabilities);
		}else{
			if(browser.equals("Firefox")){
				return getFirefoxDriver(capabilities);
			}else if(browser.equals("Explorer")){
				return getInternetExplorerDriver(capabilities);
			}else if(browser.equals("Chrome")){
				return getChromeDriver(capabilities);
			}else if(browser.equals("Opera")){
				return getOperaDriver(capabilities);
			}else if(browser.equals("Safari")){
				return getSafariDriver(capabilities);
			}else if(browser.equals("Android")){
				return getAndroidDriver(capabilities);
			}else if(browser.equals("HtmlUnit")){
				return getHtmlUnitDriver(capabilities);
			}else{
				throw new Exception("Unsupported Browser");
			}
		}
	}
	
	protected WebDriver getDriverInstanceByOpeningUrlAndSetTimeOuts(ITestContext ctx) throws Exception{
		return getDriverInstanceByOpeningUrlAndSetTimeOuts(ctx,configProp.getProperty("url"));
	}
	
	protected WebDriver getDriverInstanceByOpeningUrlAndSetTimeOuts(ITestContext ctx,String url) throws Exception{
		try {
			WebDriver driver = getDriver(ctx);
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
			logger.info("Trying to open url "+url);
			openUrl(driver, url);
			logger.info("current url is "+driver.getCurrentUrl());
			logger.info("Trying to delete cookies");
			deleteCookies(driver);
			logger.info("Trying to maximize and focus the window");
			windowMaximizeAndWindowFocus(driver);
			logger.info("setting up browser preferences completed");
			return driver;
		} catch (Exception e) {
			throw new SkipException(e.getMessage());
		} catch (Throwable e) {
			throw new SkipException(e.getCause().getMessage());
		}
	}
	/*
	protected void takeScreenShotForTest(ITestResult result,WebDriver driver) throws Exception{
		String screenShotFileName=System.currentTimeMillis()+".png";
		String screenShotPath=result.getTestContext().getOutputDirectory()+"/"+"screenshots"+"/"+screenShotFileName;
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(screenShotPath));
		result.setAttribute("screenshot", screenShotFileName);
	}
	*/
	protected void takeScreenShotForTest(WebDriver driver) throws Exception{
		ITestResult result = org.testng.Reporter.getCurrentTestResult();
		String screenShotFileName=System.currentTimeMillis()+".png";
		String screenShotPath=result.getTestContext().getOutputDirectory()+"/"+"screenshots"+"/"+screenShotFileName;
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(screenShotPath));
		result.setAttribute("screenshot", screenShotFileName);
	}
	
	protected void setWaitForPageToLoadInSec(WebDriver driver,long sec){
		driver.manage().timeouts().pageLoadTimeout(sec, TimeUnit.SECONDS);
	}
	
	protected void setImplicitWaitInSec(WebDriver driver,long sec){
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}
	
	protected void windowMaximizeAndWindowFocus(WebDriver driver){
		driver.manage().window().maximize();
		driver.switchTo().window(driver.getWindowHandle());
	}
	
	protected void openUrl(WebDriver driver,String url){
		driver.get(url);
	}
	
	protected void deleteCookies(WebDriver driver){
		driver.manage().deleteAllCookies();
	}
	/*
	protected void handleAfterMethod(WebDriver driver, ITestResult result){
		if(driver!=null){
			try{
				if(result.getStatus() == ITestResult.FAILURE){
					Reporter reporter = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(com.olo.annotations.Reporter.class);
					boolean verificationFailuresOnly = false;
					if(result.getThrowable()!=null){
						if(result.getThrowable().getMessage().equals(Commons.verificationFailuresMessage)){
							verificationFailuresOnly = true;
						}
					}
					if(reporter == null && verificationFailuresOnly == false){
						try {
							takeScreenShotForTest(result,driver);
						} catch (Exception e2) {
							logger.error(e2.getMessage());
						}
					}
				}
				logger.info("Trying to Stop WebDriver");
				driver.quit();
				logger.info("WebDriver Stopped");
			}catch(Exception e){
				logger.error("Error in stopping WebDriver "+e.getMessage());
			}
		}
	}
	*/
	protected void closeDriver(WebDriver driver){
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
	
}
