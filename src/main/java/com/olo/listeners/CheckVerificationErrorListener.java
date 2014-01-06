package com.olo.listeners;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import com.olo.reporter.Utility;
import com.olo.util.Commons;
import com.olo.util.VerificationErrorsInTest;

public class CheckVerificationErrorListener implements IInvokedMethodListener{
	
	private static final Logger logger = LogManager.getLogger(CheckVerificationErrorListener.class.getName());

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if(method.isTestMethod()){
			logger.info("Test execution started : "+testResult.getName());
		}
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if(method.isTestMethod()){
			logger.info("Test execution completed : "+testResult.getName());
			if(testResult.getStatus() == ITestResult.SUCCESS){
				if(VerificationErrorsInTest.hasVerificationErrors(testResult)){
					testResult.setThrowable(new Throwable(Commons.verificationFailuresMessage));
					testResult.setStatus(ITestResult.FAILURE);
				}
			}
			logger.info("Test status : "+testResult.getName()+" : "+Utility.getStatusString(testResult.getStatus()));
		}
	}

}
