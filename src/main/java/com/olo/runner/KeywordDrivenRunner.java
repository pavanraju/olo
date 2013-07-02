package com.olo.runner;

import static com.olo.util.PropertyReader.configProp;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.olo.annotations.Keyword;
import com.olo.annotations.Reporter;
import com.olo.bot.OloBrowserBot;
import com.olo.initiator.WebDriverInitiator;
import com.olo.propobject.KeywordPropObject;
import com.olo.util.Commons;


public class KeywordDrivenRunner extends WebDriverInitiator implements ITest{
	
	private static final Logger logger = LogManager.getLogger(KeywordDrivenRunner.class.getName());
	
	private String testFile;
	
	public KeywordDrivenRunner(String fileName){
		testFile=fileName;
	}
	
	public String getTestName() {
		return testFile;
	}
	
	@Reporter(name=com.olo.annotations.KeywordDriven.class)
	@Test
	public void keywordTest(ITestContext ctx) throws Exception{
		HashMap<String, String> storeData = new HashMap<String, String>();
		ArrayList<KeywordPropObject>  excelSteps=null;
		int totalVerification=0;
		int totalVerificationFailures=0;
		boolean verificationFailures=false;
		HashMap<String,Object> level3FinalReport = new HashMap<String,Object>();
		ArrayList<KeywordPropObject> level3ReportArrayLocal = new ArrayList<KeywordPropObject>();
		
		File xlsFile=null;
		
		Class<?> keywordClass=null;
		Constructor<?> keywordConstructor = null;
		try {
			logger.info("#### Test Started "+testFile+" #####");
			
			keywordClass = Class.forName(configProp.getProperty("keywords"));
			
			if(keywordClass==null){
				throw new Exception("Keywords Class Not Found");
			}
			
			try {
				keywordConstructor  = keywordClass.getConstructor(OloBrowserBot.class);
			} catch (NoSuchMethodException e) {
				throw new Exception("Could not found Matched Constructor");
			}
			
			try {
				xlsFile = new File(KeywordDrivenRunner.class.getResource(testFile).toURI());
			} catch (Exception e) {
				throw new SkipException("Test file not found "+testFile);
			}
			
			String fileExtension = xlsFile.getName().substring(
					xlsFile.getName().lastIndexOf("."),
					xlsFile.getName().length());
			if (!fileExtension.equalsIgnoreCase(".xls") && !fileExtension.equalsIgnoreCase(".xlsx")) {
				throw new SkipException("Invalied File "+testFile);
			}
			
			try {
				excelSteps = new Commons().getExcelSteps(xlsFile);
			} catch (Exception e) {
				throw new SkipException(e.getMessage());
			}
		
		} catch (SkipException skipError) {
			logger.error(skipError.getMessage());
			throw skipError;
		} catch (Exception e) {
			logger.error(e.getMessage());
			level3FinalReport.put("exception", e.getMessage());
			ctx.setAttribute(testFile, level3FinalReport);
			throw e;
		}
		
		int skipIf=0;
		
		for(int i=0;i<excelSteps.size();i++){
			KeywordPropObject localStep = new KeywordPropObject();
			localStep= excelSteps.get(i);
			level3ReportArrayLocal.add(localStep);
			
			if(skipIf!=0 && !localStep.getAction().equalsIgnoreCase("EndIf") && !localStep.getAction().equalsIgnoreCase("Else")){
				localStep.setConditionSkip(true);
				continue;
			}
			
			if(localStep.getAction().equalsIgnoreCase("Else")){
				skipIf--;
			}
			
			if(localStep.getAction().equalsIgnoreCase("Endif")){
				if(skipIf>0){
					skipIf--;
				}else if(skipIf<0){
					skipIf++;
				}
			}
			
			if(!localStep.getSkip()){

				boolean foundKeyword=true;
				
				try {
					localStep.setStartTime(System.currentTimeMillis());
					try {
						if(localStep.getAction().equalsIgnoreCase("StoreValueIn")){
							storeData.put(localStep.getValue(), browser.getValue(localStep.getPropertyValue()));
						}else if(localStep.getAction().equalsIgnoreCase("StoreTextIn")){
							storeData.put(localStep.getValue(), browser.getText(localStep.getPropertyValue()));
						}else{
							foundKeyword=false;
							
							for (final Method method : keywordClass.getDeclaredMethods()) {
								Keyword annotation = method.getAnnotation(com.olo.annotations.Keyword.class);
								if(annotation!=null){
									if(annotation.name().equals(localStep.getAction())){
										foundKeyword=true;
										logger.info(localStep);
										method.invoke(keywordConstructor.newInstance(browser),localStep);
										if(localStep.getAction().startsWith("If") && localStep.getIfSkipped()){
											skipIf++;
										}
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
						String screenShotFileName=System.currentTimeMillis()+".png";;
						String screenShotPath=ctx.getOutputDirectory()+File.separator+"screenshots"+File.separator+screenShotFileName;
						browser.captureScreenshot(screenShotPath);
						localStep.setScreenShotName(screenShotFileName);
						localStep.setScreenShotPath(screenShotPath);
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
						String screenShotPath=ctx.getOutputDirectory()+File.separator+"screenshots"+File.separator+screenShotFileName;
						browser.captureScreenshot(screenShotPath);
						localStep.setScreenShotName(screenShotFileName);
						localStep.setScreenShotPath(screenShotPath);
					} catch (Exception e1) {
						logger.error(e1.getMessage());
					}
					localStep.setHasError(true);
					
					if(localStep.getAction().startsWith("If")){
						browser.implicitWait();
					}
				}finally{
					if(localStep.getEndTime()==0){
						localStep.setEndTime(System.currentTimeMillis());
					}
				}
				
				if(!foundKeyword){
					localStep.setHasError(true);
					localStep.setErrorMessage("Invalied Action");
				}
				
			}
			if(localStep.getIsVerification()){
				totalVerification++;
			}
			if(localStep.getHasError()){
				if(localStep.getIsVerification()){
					if(localStep.getIsAssertionError()){
						verificationFailures=true;
						totalVerificationFailures++;
					}else{
						addVariables(ctx,level3FinalReport,totalVerification,totalVerificationFailures,level3ReportArrayLocal,testFile);
						Assert.fail(localStep.getErrorMessage());
					}
				}else{
					addVariables(ctx,level3FinalReport,totalVerification,totalVerificationFailures,level3ReportArrayLocal,testFile);
					Assert.fail(localStep.getErrorMessage());
				}
			}
		}
		
		addVariables(ctx,level3FinalReport,totalVerification,totalVerificationFailures,level3ReportArrayLocal,testFile);
		if(verificationFailures){
			Assert.fail("Verification Failures");
		}
		
		
	}
	
	
	private void addVariables(ITestContext ctx,HashMap<String,Object> level3FinalReport,int totalVerification,int totalVerificationFailures,ArrayList<KeywordPropObject> level3ReportArrayLocal,String xlsPath){
		level3FinalReport.put("totalVerifications", totalVerification);
		level3FinalReport.put("totalVerificationFailures", totalVerificationFailures);
		level3FinalReport.put("level3ReportArray", level3ReportArrayLocal);
		logger.info("##### Test Case Completed "+xlsPath+" #####");
		ctx.setAttribute(testFile, level3FinalReport);
	}
	

}
