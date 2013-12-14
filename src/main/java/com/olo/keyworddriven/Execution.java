package com.olo.keyworddriven;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.olo.annotations.Keyword;
import com.olo.keyworddriven.Keywords;
import com.olo.keyworddriven.KeywordPropObject;
import com.olo.util.Commons;
import com.olo.util.VerificationErrorsInTest;

public class Execution {
	
	private static final Logger logger = LogManager.getLogger(Execution.class.getName());
	private Keywords keywords;
	
	public Execution(Keywords keywords){
		this.keywords=keywords;
	}
	
	public void run(ITestContext ctx, ArrayList<KeywordPropObject> excelSteps) throws Exception{
		ITestResult testResult = Reporter.getCurrentTestResult();
		Throwable throwableException = null;
		HashMap<String, String> storeData = new HashMap<String, String>();
		ArrayList<KeywordPropObject> keywordExecutionSteps = new ArrayList<KeywordPropObject>();
		int skipIf=0;
		int verificationErrorCount = 0;
		for(int i=0;i<excelSteps.size();i++){
			KeywordPropObject localStep = new KeywordPropObject();
			localStep= excelSteps.get(i);
			keywordExecutionSteps.add(localStep);
			
			if(skipIf!=0 && !localStep.getCommand().equals("EndIf") && !localStep.getCommand().equals("Else")){
				localStep.setConditionSkip(true);
				continue;
			}
			
			if(localStep.getCommand().equals("Else")){
				skipIf--;
			}
			
			if(localStep.getCommand().equals("EndIf")){
				if(skipIf>0){
					skipIf--;
				}else if(skipIf<0){
					skipIf++;
				}
			}
			
			if(!localStep.getSkip()){

				boolean foundKeyword=false;
				localStep.setStartTime(System.currentTimeMillis());
				try {
					for (final Method method : keywords.getClass().getDeclaredMethods()) {
						Keyword annotation = method.getAnnotation(com.olo.annotations.Keyword.class);
						if(annotation!=null){
							if(annotation.value().equals(localStep.getCommand())){
								foundKeyword=true;
								localStep.setActualValue(Commons.replaceDynamicValueMatchers(localStep.getActualValue(), storeData));
								logger.info(localStep);
								if(localStep.getCommand().startsWith("Verify")){
									localStep.setIsVerification(true);
								}
								if(localStep.getCommand().startsWith("Put")){
									HashMap<String, String> storedData =  (HashMap<String, String>)method.invoke(keywords,localStep);
									storeData.putAll(storedData);
								}else{
									method.invoke(keywords,localStep);
								}
								
								if(localStep.getCommand().startsWith("If") && localStep.getIfSkipped()){
									skipIf++;
								}
							}
						}
					}
				} catch (InvocationTargetException e) {
					localStep.setHasError(true);
					throwableException = e.getCause();
				} catch (Exception e) {
					localStep.setHasError(true);
					throwableException = e.getCause();
				}finally{
					if(localStep.getEndTime()==0){
						localStep.setEndTime(System.currentTimeMillis());
					}
				}
				
				if(!foundKeyword){
					localStep.setHasError(true);
					throwableException = new Exception("Invalied Action");
				}
				
			}
			
			if(verificationErrorCount!=VerificationErrorsInTest.verificationFailuresCount(testResult)){
				localStep.setHasError(true);
				localStep.setIsVerificationError(true);
				localStep.setVerificationIndex(verificationErrorCount);
				verificationErrorCount++;
			}
			if(localStep.getHasError() && !localStep.getIsVerificationError()){
				addVariables(testResult, keywordExecutionSteps);
				Assert.fail(throwableException.getMessage(), throwableException);
			}
		}
		
		addVariables(testResult, keywordExecutionSteps);
	}
	
	private void addVariables(ITestResult testResult, ArrayList<KeywordPropObject> keywordExecutionSteps){
		KeywordReporterData.addTestExecutionData(testResult, keywordExecutionSteps);
	}
	
}
