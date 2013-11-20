package com.olo.initiator;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class WebDriverInitiator extends Configuration{
	
	protected ThreadLocal<WebDriver> threadDriver = new InheritableThreadLocal<WebDriver>();
	
	@BeforeMethod
	public void beforeTestMethod(ITestContext ctx) throws Exception{
		threadDriver.set(getDriverBySetTimeOutsAndOpenUrl(ctx));
	}
	
	public WebDriver getDriver() {
        return threadDriver.get();
    }
	
	@AfterMethod(alwaysRun=true)
	public void afterTestMethod(ITestResult result){
		handleAfterMethod(getDriver(), result);
	}

}
