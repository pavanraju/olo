package com.olo.initiator;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;

import com.opera.core.systems.OperaDriver;

public class WebDriverConfiguration {
	
	protected DesiredCapabilities getInternetExplorerCapabilities(){
		DesiredCapabilities capabilities = null;
		capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		return capabilities;
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
		}else{
			throw new Exception("Un Supported Browser");
		}
		return capabilities;
	}
	
	protected WebDriver getInternetExplorerDriver(DesiredCapabilities capabilities){
		return new InternetExplorerDriver(capabilities);
	}
	
	protected WebDriver getFirefoxDriver(DesiredCapabilities capabilities){
		return new FirefoxDriver(capabilities);
	}
	
	protected WebDriver getChromeDriver(DesiredCapabilities capabilities){
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
	
	protected WebDriver getRemoteWebDriverDriver(String hubURL, DesiredCapabilities capabilities) throws Exception{
		return new RemoteWebDriver(new URL(hubURL),capabilities);
	}
	
	protected WebDriver getDriver(String browser, DesiredCapabilities capabilities) throws Exception{
		if(browser.equals("Firefox")){
			return getFirefoxDriver(capabilities);
		}else if(browser.equals("Explorer")){
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/win/IEDriverServer.exe");
			return getInternetExplorerDriver(capabilities);
		}else if(browser.equals("Chrome")){
			String driverFolder=null;
			if(Platform.getCurrent().is(Platform.WINDOWS)){
				driverFolder="win";
			}else if(Platform.getCurrent().is(Platform.MAC)){
				driverFolder="mac";
			}else{
				driverFolder="linux";
			}
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/"+driverFolder+"/chromedriver.exe");
			return getChromeDriver(capabilities);
		}else if(browser.equals("Opera")){
			return getOperaDriver(capabilities);
		}else if(browser.equals("Safari")){
			return getSafariDriver(capabilities);
		}else if(browser.equals("Android")){
			return getAndroidDriver(capabilities);
		}else{
			throw new Exception("Unsupported Browser");
		}
	}
	
	protected void takeScreenShotForTest(ITestResult result,WebDriver driver) throws Exception{
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
	
	protected void openUrlAndDeleteCookies(WebDriver driver,String url){
		driver.get(url);
		driver.manage().deleteAllCookies();
	}
	
}
