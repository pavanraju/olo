package com.olo.initiator;

import static com.olo.util.PropertyReader.configProp;

import com.olo.annotations.Reporter;
import com.olo.bot.OloBrowserBot;
import com.olo.util.OSUtil;
import com.opera.core.systems.OperaDriver;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.iphone.IPhoneDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class WebDriverInitiator {
	
	private static final Logger logger = LogManager.getLogger(WebDriverInitiator.class.getName());
	
	public OloBrowserBot browser = null;
	
	@BeforeMethod
	public void driverStart(ITestContext ctx) throws Exception{
		try{
			try {
				WebDriver driver = null;
				String openBrowser = configProp.getProperty("browser");
				logger.info("Trying to Start WebDriver");
				DesiredCapabilities capabilities = null;
				if(openBrowser.equals("Firefox")){
					capabilities = DesiredCapabilities.firefox();
				}else if(openBrowser.equals("Explorer")){
					capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				}else if(openBrowser.equals("Chrome")){
					capabilities = DesiredCapabilities.chrome();
				}else if(openBrowser.equals("Opera")){
					capabilities = DesiredCapabilities.opera();
				}else if(openBrowser.equals("Android")){
					capabilities = DesiredCapabilities.android();
				}else if(openBrowser.equals("Iphone")){
					capabilities = DesiredCapabilities.iphone();
				}else if(openBrowser.equals("Ipad")){
					capabilities = DesiredCapabilities.ipad();
				}else{
					throw new Exception("Un Supported Browser");
				}
				
				Class<?> webPageBotClass = Class.forName(configProp.getProperty("browserBot"));
				if(webPageBotClass==null){
					throw new Exception("Could Not found Web Page Bot");
				}
				
				if(!ctx.getSuite().getParallel().equals("false")){
					String hubURL = configProp.getProperty("hubURL");
					driver = new RemoteWebDriver(new URL(hubURL),capabilities);
				}else{
					if(openBrowser.equals("Firefox")){
						FirefoxBinary binary = null;
						if(!configProp.contains("webdriver.firefox.bin")){
							binary = new FirefoxBinary();
						}else{
							binary = new FirefoxBinary(new File(configProp.getProperty("webdriver.firefox.bin")));
						}
						FirefoxProfile profile = new FirefoxProfile();
						profile.setAcceptUntrustedCertificates(true);
						driver = new FirefoxDriver(binary,profile,capabilities);
						logger.info("Firefox Driver Initiated");
					}else if(openBrowser.equals("Explorer")){
						System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/win+"+OSUtil.getJavaBitVersion()+"/IEDriverServer.exe");
						driver = new InternetExplorerDriver(capabilities);
						logger.info("Internet Explorer Driver Initiated");
					}else if(openBrowser.equals("Chrome")){
						String driverFolder=null;
						String javaBitVersion = OSUtil.getJavaBitVersion();
						if(Platform.getCurrent().is(Platform.WINDOWS)){
							driverFolder="win"+javaBitVersion;
						}else if(Platform.getCurrent().is(Platform.MAC)){
							driverFolder="mac"+javaBitVersion;
						}else{
							driverFolder="linux"+javaBitVersion;
						}
						System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/"+driverFolder+"/chromedriver.exe");
						if(configProp.contains("chrome.binary")){
							capabilities.setCapability("chrome.binary", configProp.getProperty("chrome.binary"));
						}
						driver = new ChromeDriver(capabilities);
						logger.info("Google Chrome Driver Initiated");
					}else if(openBrowser.equals("Opera")){
						driver = new OperaDriver(capabilities);
						logger.info("Opera Driver Initiated");
					}else if(openBrowser.equals("Android")){
						driver = new AndroidDriver(capabilities);
						logger.info("Android Driver Initiated");
					}else if(openBrowser.equals("Iphone") || openBrowser.equals("Ipad")){
						driver = new IPhoneDriver(capabilities);
						logger.info("IPhoneDriver Initiated");
					}
				}
				
				Constructor<?> botConstructor = webPageBotClass.getConstructor(new Class[]{WebDriver.class});
				browser = (OloBrowserBot) botConstructor.newInstance(driver);
				if(configProp.containsKey("url")){
					browser.get(configProp.getProperty("url"));
					browser.waitForPageToLoad();
					browser.deleteAllVisibleCookies();
				}
				browser.implicitWait();
				browser.windowMaximize();
				browser.windowFocus();
				Thread.sleep(1000);
				logger.info("WebDriver Started");
			} catch (Exception e) {
				throw e;
			} catch (Throwable e) {
				e.printStackTrace();
				throw new Exception(e.getCause().getMessage());
			}
			
		}catch (Exception e) {
			logger.error("WebDriver Failed To Start "+e.getMessage());
			throw e;
		}
	}
	
	@AfterMethod
	public void driverStop(ITestResult result){
		try{
			Reporter reporter = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(com.olo.annotations.Reporter.class);
			if(reporter == null || (reporter!=null && !reporter.screenShotHandled())){
				switch(result.getStatus()){
				case ITestResult.FAILURE:
					try {
						String screenShotFileName=System.currentTimeMillis()+".png";
						String screenShotPath=result.getTestContext().getOutputDirectory()+File.separator+"screenshots"+File.separator+screenShotFileName;
						browser.captureScreenshot(screenShotPath);
						result.setAttribute("screenshot", screenShotFileName);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					break;
				}
			}
			logger.info("Trying to Stop WebDriver");
			browser.getDriver().quit();
			logger.info("WebDriver Stopped");
		}catch(Exception e){
			logger.error("Error in stopping WebDriver "+e.getMessage());
		}
	}
	
}
