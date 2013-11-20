package com.olo.keyworddriven;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;

import com.olo.annotations.Keyword;
import com.olo.bot.BrowserBot;
import com.olo.keyworddriven.Keywords;
import com.olo.propobject.KeywordPropObject;
import com.olo.util.Commons;

public class Execution {
	
	private static final Logger logger = LogManager.getLogger(Execution.class.getName());
	private BrowserBot browser;
	private Keywords keywords;
	
	public Execution(BrowserBot browser,Keywords keywords){
		this.browser = browser;
		this.keywords=keywords;
	}
	
	public void run(ITestContext ctx, ArrayList<KeywordPropObject> excelSteps) throws Exception{
		
		HashMap<String, String> storeData = new HashMap<String, String>();
		//int totalVerification=0;
		//int totalVerificationFailures=0;
		//boolean verificationFailures=false;
		//HashMap<String,Object> level3FinalReport = new HashMap<String,Object>();
		ArrayList<KeywordPropObject> keywordExecutionSteps = new ArrayList<KeywordPropObject>();
		
		int skipIf=0;
		
		for(int i=0;i<excelSteps.size();i++){
			KeywordPropObject localStep = new KeywordPropObject();
			localStep= excelSteps.get(i);
			keywordExecutionSteps.add(localStep);
			
			if(skipIf!=0 && !localStep.getAction().equals("EndIf") && !localStep.getAction().equals("Else")){
				localStep.setConditionSkip(true);
				continue;
			}
			
			if(localStep.getAction().equals("Else")){
				skipIf--;
			}
			
			if(localStep.getAction().equals("Endif")){
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
							if(annotation.value().equals(localStep.getAction())){
								foundKeyword=true;
								localStep.setActualValue(Commons.replaceDynamicValueMatchers(localStep.getActualValue(), storeData));
								logger.info(localStep);
								if(localStep.getAction().startsWith("Verify")){
									localStep.setIsVerification(true);
								}
								if(localStep.getAction().equals("CaptureScreenshot")){
									String screenShotFileName=System.currentTimeMillis()+".png";
									String screenShotPath=ctx.getOutputDirectory()+"/"+"screenshots"+"/"+screenShotFileName;
									localStep.setScreenShotName(screenShotFileName);
									localStep.setScreenShotPath(screenShotPath);
									method.invoke(keywords,localStep);
								}else if(localStep.getAction().startsWith("Put")){
									HashMap<String, String> storedData =  (HashMap<String, String>)method.invoke(keywords,localStep);
									storeData.putAll(storedData);
								}else{
									method.invoke(keywords,localStep);
								}
								
								if(localStep.getAction().startsWith("If") && localStep.getIfSkipped()){
									skipIf++;
								}
							}
						}
					}
				} catch (InvocationTargetException e) {
					if(e.getCause() instanceof AssertionError){
						try {
							String screenShotFileName=System.currentTimeMillis()+".png";
							String screenShotPath=ctx.getOutputDirectory()+"/"+"screenshots"+"/"+screenShotFileName;
							browser.captureScreenshot(screenShotPath);
							localStep.setScreenShotName(screenShotFileName);
						} catch (Exception e1) {
							logger.error(e1.getMessage());
						}
						localStep.setHasError(true);
						localStep.setIsAssertionError(true);
						String errorMessage = e.getMessage();
						if(errorMessage!=null){
							localStep.setErrorMessage(errorMessage.replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br/>"));
						}else{
							localStep.setErrorMessage("NullPointerException");
						}
					}else{
						String errorMessage = e.getMessage();
						if(errorMessage!=null){
							errorMessage=errorMessage.replace("<", "&lt;").replace(">", "&gt;");
							localStep.setErrorMessage(errorMessage);
						}else{
							localStep.setErrorMessage("NullPointerException");
						}
						
						try {
							String screenShotFileName=System.currentTimeMillis()+".png";
							String screenShotPath=ctx.getOutputDirectory()+"/screenshots/"+screenShotFileName;
							browser.captureScreenshot(screenShotPath);
							localStep.setScreenShotName(screenShotFileName);
						} catch (Exception e1) {
							logger.error(e1.getMessage());
						}
						localStep.setHasError(true);
					}
				} catch (Exception e) {
					String errorMessage = e.getMessage();
					if(errorMessage!=null){
						errorMessage=errorMessage.replace("<", "&lt;").replace(">", "&gt;");
						localStep.setErrorMessage(errorMessage);
					}else{
						localStep.setErrorMessage("NullPointerException");
					}
					
					try {
						String screenShotFileName=System.currentTimeMillis()+".png";
						String screenShotPath=ctx.getOutputDirectory()+"/screenshots/"+screenShotFileName;
						browser.captureScreenshot(screenShotPath);
						localStep.setScreenShotName(screenShotFileName);
					} catch (Exception e1) {
						logger.error(e1.getMessage());
					}
					localStep.setHasError(true);
				}finally{
					if(localStep.getEndTime()==0){
						localStep.setEndTime(System.currentTimeMillis());
					}
				}
				
				/*
				
				
				
				
				try {
					
					try {
						
						for (final Method method : keywords.getClass().getDeclaredMethods()) {
							Keyword annotation = method.getAnnotation(com.olo.annotations.Keyword.class);
							if(annotation!=null){
								if(annotation.value().equals(localStep.getAction())){
									foundKeyword=true;
									localStep.setActualValue(Commons.replaceDynamicValueMatchers(localStep.getActualValue(), storeData));
									logger.info(localStep);
									if(localStep.getAction().equals("CaptureScreenshot")){
										String screenShotFileName=System.currentTimeMillis()+".png";
										String screenShotPath=ctx.getOutputDirectory()+"/"+"screenshots"+"/"+screenShotFileName;
										localStep.setScreenShotName(screenShotFileName);
										localStep.setScreenShotPath(screenShotPath);
										method.invoke(keywords,localStep);
									}else if(localStep.getAction().startsWith("Put")){
										HashMap<String, String> storedData =  (HashMap<String, String>)method.invoke(keywords,localStep);
										storeData.putAll(storedData);
									}else{
										method.invoke(keywords,localStep);
									}
									
									if(localStep.getAction().startsWith("If") && localStep.getIfSkipped()){
										skipIf++;
									}
								}
							}
						}
					} catch (InvocationTargetException e) {
						if(e.getCause() instanceof AssertionError){
							throw new AssertionError(e.getCause().getMessage());
						}else{
							throw new Exception(e.getCause().getMessage());
						}
					} catch (Exception e) {
						throw new Exception(e.getCause().getMessage());
					}
				} catch (AssertionError e) {
					
					try {
						String screenShotFileName=System.currentTimeMillis()+".png";
						String screenShotPath=ctx.getOutputDirectory()+"/"+"screenshots"+"/"+screenShotFileName;
						browser.captureScreenshot(screenShotPath);
						localStep.setScreenShotName(screenShotFileName);
					} catch (Exception e1) {
						logger.error(e1.getMessage());
					}
					localStep.setHasError(true);
					localStep.setIsAssertionError(true);
					String errorMessage = e.getMessage();
					if(errorMessage!=null){
						localStep.setErrorMessage(errorMessage.replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br/>"));
					}else{
						localStep.setErrorMessage("NullPointerException");
					}
				} catch (Exception e) {
					String errorMessage = e.getMessage();
					if(errorMessage!=null){
						errorMessage=errorMessage.replace("<", "&lt;").replace(">", "&gt;");
						localStep.setErrorMessage(errorMessage);
					}else{
						localStep.setErrorMessage("NullPointerException");
					}
					
					
					try {
						String screenShotFileName=System.currentTimeMillis()+".png";
						String screenShotPath=ctx.getOutputDirectory()+"/screenshots/"+screenShotFileName;
						browser.captureScreenshot(screenShotPath);
						localStep.setScreenShotName(screenShotFileName);
					} catch (Exception e1) {
						logger.error(e1.getMessage());
					}
					localStep.setHasError(true);
					
				}finally{
					if(localStep.getEndTime()==0){
						localStep.setEndTime(System.currentTimeMillis());
					}
				}
				*/
				if(!foundKeyword){
					localStep.setHasError(true);
					localStep.setErrorMessage("Invalied Action");
				}
				
			}
			/*
			if(localStep.getIsVerification()){
				totalVerification++;
			}
			*/
			if(localStep.getHasError()){
				if(localStep.getIsVerification()){
					if(localStep.getIsAssertionError()){
						//verificationFailures=true;
						//totalVerificationFailures++;
					}else{
						addVariables(keywordExecutionSteps);
						Assert.fail(localStep.getErrorMessage());
					}
				}else{
					addVariables(keywordExecutionSteps);
					Assert.fail(localStep.getErrorMessage());
				}
			}
		}
		
		addVariables(keywordExecutionSteps);
		/*
		if(verificationFailures){
			Assert.fail(Commons.verificationFailuresMessage);
		}
		*/
	}
	
	private void addVariables(ArrayList<KeywordPropObject> keywordExecutionSteps){
		//level3FinalReport.put("totalVerifications", totalVerification);
		//level3FinalReport.put("totalVerificationFailures", totalVerificationFailures);
		//level3FinalReport.put("keywordExecutionSteps", keywordExecutionSteps);
		//level3FinalReport.put("testPath", testPath);
		KeywordReporterData.addTestExecutionData(Reporter.getCurrentTestResult(), keywordExecutionSteps);
		//ctx.setAttribute(testName+"-"+testCount, level3FinalReport);
		//logger.info("##### Test Case Completed "+testPath+" #####");
	}
	
}
