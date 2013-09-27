package com.olo.listeners;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.internal.Utils;

import com.olo.util.Commons;
import com.olo.util.VerificationErrors;

public class InvokedMethodListener implements IInvokedMethodListener{
	
	private static final Logger logger = LogManager.getLogger(InvokedMethodListener.class.getName());

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if(method.isTestMethod()){
			logger.info("Test execution started : "+testResult.getName());
		}else{
			logger.info("Test configurations started : "+testResult.getName());
		}
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		//List<HashMap<String, Object>> verificationErrors = VerificationErrors.getTestErrors(testResult);
		if(method.isTestMethod()){
			logger.info("Test execution completed : "+testResult.getName());
			/*
			if(testResult.getStatus() == ITestResult.FAILURE){
				if(!verificationErrors.isEmpty()){
					logger.error(testResult.getName()+" : Verification Failures");
					Iterator<HashMap<String, Object>> iter = verificationErrors.iterator();
					String errorMessage = "Verification Failures <br>";
					while(iter.hasNext()){
						HashMap<String, Object> errorDetails = iter.next();
						logger.error(testResult.getName()+" : Verification Failure Message: "+errorDetails.get("message"));
						errorMessage+="<div>"+errorDetails.get("stackTrace")+"</div><br>";
						errorMessage+="<a href=\"screenshots"+File.separator+errorDetails.get("screenshot")+"\">Screenshot</a><br>";
					}
					if(testResult.getThrowable()!=null){
						logger.error(testResult.getName()+" Failure Message: "+testResult.getThrowable().getMessage());
						errorMessage+="<hr>";
						String[] stackTraces = Utils.stackTrace(testResult.getThrowable(), true);
						errorMessage+="<div>"+stackTraces[1]+"</div><br>";
					}
					testResult.setThrowable(new Throwable(errorMessage));
				}else{
					if(testResult.getThrowable()!=null){
						String[] stackTraces = Utils.stackTrace(testResult.getThrowable(), true);
						logger.error(testResult.getName()+" Failure StackTrace : "+stackTraces[1]);
					}
				}
			}else if(testResult.getStatus() == ITestResult.SUCCESS){
				if(!verificationErrors.isEmpty()){
					logger.error(testResult.getName()+" : Verification Failures");
					Iterator<HashMap<String, Object>> iter = verificationErrors.iterator();
					String errorMessage = "Verification Failures <br>";
					while(iter.hasNext()){
						HashMap<String, Object> errorDetails = iter.next();
						logger.error(testResult.getName()+" : Verification Failure Message: "+errorDetails.get("message"));
						errorMessage+="<div>"+errorDetails.get("stackTrace")+"</div><br>";
						errorMessage+="<a href=\"screenshots"+File.separator+errorDetails.get("screenshot")+"\">Screenshot</a><br>";
					}
					testResult.setThrowable(new Throwable(errorMessage));
					testResult.setStatus(ITestResult.FAILURE);
					testResult.setAttribute("verificationFailures", true);
				}
			}
			*/
			if(testResult.getStatus() == ITestResult.SUCCESS){
				if(VerificationErrors.hasVerificationErrors(testResult)){
					testResult.setThrowable(new Throwable(Commons.verificationFailuresMessage));
					testResult.setStatus(ITestResult.FAILURE);
					//testResult.setAttribute("verificationFailuresOnly", true);
				}
			}
		}else{
			logger.info("Test configurations completed : "+testResult.getName());
		}
		
	}

}
