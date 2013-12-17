package com.olo.initiator;

import static com.olo.util.PropertyReader.configProp;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class ApplicationInitiator extends InitiatorUtil{
	
	protected ThreadLocal<WebDriver> threadDriver = new InheritableThreadLocal<WebDriver>();
	
	@BeforeMethod
	public void beforeTestMethod(ITestContext ctx) throws Exception{
		threadDriver.set(driverConfig.getDriverBySetTimeOuts(ctx));
		openUrl(getDriver(), configProp.getProperty("applicationUrl"));
	}
	
	public WebDriver getDriver() {
        return threadDriver.get();
    }
	
	@AfterMethod(alwaysRun=true)
	public void afterTestMethod(ITestResult result){
		handleAfterMethod(getDriver(), result);
	}

}
