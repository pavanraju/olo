package com.olo.initiator;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.log4testng.Logger;

import com.olo.propertyutil.ConfigProperties;
import com.opera.core.systems.OperaDriver;

public class DriverConfiguration {
	
	private static final Logger LOGGER = Logger.getLogger(DriverConfiguration.class);
	
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
	
	public WebDriver getHtmlUnitDriver(DesiredCapabilities capabilities){
		capabilities.setJavascriptEnabled(true);
		return new HtmlUnitDriver(capabilities);
	}
	
	public WebDriver getRemoteWebDriverDriver(String hubURL, DesiredCapabilities capabilities) throws Exception{
		return new RemoteWebDriver(new URL(hubURL),capabilities);
	}
	
	public WebDriver getWebDriver(ITestContext ctx) throws Exception{
		String browser = ConfigProperties.getBrowser();
		DesiredCapabilities capabilities =  getCapabilities(browser);
		if(!ctx.getSuite().getParallel().equals("false") && ConfigProperties.getRemoteExecution()){
			String hubURL = ConfigProperties.getHubUrl();
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
			}else if(browser.equals("htmlunit")){
				return getHtmlUnitDriver(capabilities);
			}else{
				throw new Exception("Unsupported Browser");
			}
		}
	}
	
	public WebDriver getDriverBySetTimeOuts(ITestContext ctx){
		try {
			WebDriver driver = getWebDriver(ctx);
			setWaitForPageToLoadInSec(driver);
			setImplicitWaitInSec(driver);
			windowMaximize(driver);
			LOGGER.info("setting up browser preferences completed");
			return driver;
		} catch (Exception e) {
			throw new SkipException(e.getMessage());
		} catch (Throwable e) {
			throw new SkipException(e.getCause().getMessage());
		}
	}
	
	public void setWaitForPageToLoadInSec(WebDriver driver){
		if(ConfigProperties.getPageLoadTimeout()!=0){
			setWaitForPageToLoadInSec(driver, ConfigProperties.getPageLoadTimeout());
		}
	}
	
	public void setWaitForPageToLoadInSec(WebDriver driver,long sec){
		LOGGER.info("Setting pageLoadTimeout to "+sec+" seconds");
		driver.manage().timeouts().pageLoadTimeout(sec, TimeUnit.SECONDS);
	}
	
	public void setImplicitWaitInSec(WebDriver driver){
		setImplicitWaitInSec(driver, ConfigProperties.getImplicitWait());
	}
	
	public void setImplicitWaitInSec(WebDriver driver,long sec){
		LOGGER.info("Setting implicitlyWait to "+sec+" seconds");
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}
	
	public void windowMaximize(WebDriver driver){
		LOGGER.info("Trying to maximize window");
		driver.manage().window().maximize();
	}
	
}
