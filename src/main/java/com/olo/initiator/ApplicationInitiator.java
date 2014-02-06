package com.olo.initiator;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.olo.propertyutil.ConfigProperties;

public class ApplicationInitiator extends InitiatorUtil{
	
	@BeforeMethod
	public void beforeTestMethod(ITestContext ctx) throws Exception{
		configureDriver(ctx);
		openUrl(ConfigProperties.getApplicationUrl());
	}
	
	@AfterMethod(alwaysRun=true)
	public void afterTestMethod(ITestResult result){
		handleAfterMethod(getDriver(), result);
	}

}
