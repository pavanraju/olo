package com.olo.initiator;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BrowserInitiator extends InitiatorUtil{
	
	@BeforeMethod
	public void beforeTestMethod(ITestContext ctx) throws Exception{
		configureDriver(ctx);
	}
	
	@AfterMethod(alwaysRun=true)
	public void afterTestMethod(ITestResult result){
		captureScreenShotOnTestFailure(result);
		closeDriver();
	}

}
