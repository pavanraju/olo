package com.olo.listeners;

import java.lang.reflect.Method;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.olo.annotations.Reporter;
import com.olo.reporter.TestReporter;

public class TestListener implements ITestListener{
	
	private static final Logger logger = LogManager.getLogger(TestListener.class.getName());

	public void onTestStart(ITestResult result) {
		
		
	}

	public void onTestSuccess(ITestResult result) {
		generateReportForReporters(result);
	}

	public void onTestFailure(ITestResult result) {
		generateReportForReporters(result);
	}

	public void onTestSkipped(ITestResult result) {
		generateReportForReporters(result);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	public void onStart(ITestContext context) {
		
	}

	public void onFinish(ITestContext context) {
		
	}
	
	private void generateReportForReporters(ITestResult result){
		Reporter reporter = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(com.olo.annotations.Reporter.class);
		
		if(reporter!=null){
			
			result.getTestContext().getAttribute(result.getName());
			
			try {
				Class<TestReporter> testReporterClass = reporter.reporterClass();
				for (final Method reporterMethod : testReporterClass.getDeclaredMethods()) {
					Reporter annotation = reporterMethod.getAnnotation(com.olo.annotations.Reporter.class);
					if(annotation!=null){
						if(annotation.name().equals(reporter.name())){
							reporterMethod.invoke(testReporterClass.newInstance(),result);
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			
			result.getTestContext().removeAttribute(result.getName());
		}
	}

}
