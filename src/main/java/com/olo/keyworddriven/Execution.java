package com.olo.keyworddriven;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;

import com.olo.annotations.Keyword;
import com.olo.bot.BrowserBot;
import com.olo.keyworddriven.Keywords;
import com.olo.propobject.KeywordPropObject;
import com.olo.util.Commons;

public class Execution {
	
	private static final Logger logger = LogManager.getLogger(Execution.class.getName());
	private String testFile;
	private String testName;
	private BrowserBot browser;
	private Keywords keywords;
	private HashMap<String,Object> level3FinalReport = new HashMap<String,Object>();
	
	public Execution(String testFile,BrowserBot browser,Keywords keywords){
		this.testFile = testFile;
		this.browser = browser;
		this.keywords=keywords;
	}
	
	private ArrayList<KeywordPropObject> readAndValidate(ITestContext ctx) throws Exception{
		
		ArrayList<KeywordPropObject> excelSteps = null;
		try {
			logger.info("#### Test Started "+testFile+" #####");
			File xlsFile=null;
			try {
				xlsFile = new File(testFile);
			} catch (Exception e) {
				throw new SkipException("Test file not found "+testFile);
			}
			testName = xlsFile.getName();
			String fileExtension = testName.substring(testName.lastIndexOf("."),testName.length());
			if (!testName.startsWith("TC-") || (!fileExtension.equalsIgnoreCase(".xls") && !fileExtension.equalsIgnoreCase(".xlsx"))) {
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
			ctx.setAttribute(testName, level3FinalReport);
			throw e;
		}
		return excelSteps;
	}
	
	public void run(ITestContext ctx) throws Exception{
		
		ArrayList<KeywordPropObject>  excelSteps = readAndValidate(ctx);
		
		HashMap<String, String> storeData = new HashMap<String, String>();
		int totalVerification=0;
		int totalVerificationFailures=0;
		boolean verificationFailures=false;
		HashMap<String,Object> level3FinalReport = new HashMap<String,Object>();
		ArrayList<KeywordPropObject> level3ReportArrayLocal = new ArrayList<KeywordPropObject>();
		
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

				boolean foundKeyword=false;
				
				try {
					localStep.setStartTime(System.currentTimeMillis());
					try {
						
						for (final Method method : keywords.getClass().getDeclaredMethods()) {
							Keyword annotation = method.getAnnotation(com.olo.annotations.Keyword.class);
							if(annotation!=null){
								if(annotation.name().equals(localStep.getAction())){
									foundKeyword=true;
									logger.info(localStep);
									if(!localStep.getAction().startsWith("Put")){
										method.invoke(keywords,localStep);
									}else{
										HashMap<String, String> storedData =  (HashMap<String, String>)method.invoke(keywords,localStep);
										storeData.putAll(storedData);
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
						String screenShotFileName=System.currentTimeMillis()+".png";;
						String screenShotPath=ctx.getOutputDirectory()+"/"+"screenshots"+"/"+screenShotFileName;
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
						String screenShotPath=ctx.getOutputDirectory()+"/screenshots/"+screenShotFileName;
						browser.captureScreenshot(screenShotPath);
						localStep.setScreenShotName(screenShotFileName);
						localStep.setScreenShotPath(screenShotPath);
					} catch (Exception e1) {
						logger.error(e1.getMessage());
					}
					localStep.setHasError(true);
					
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
						addVariables(ctx,level3FinalReport,totalVerification,totalVerificationFailures,level3ReportArrayLocal);
						Assert.fail(localStep.getErrorMessage());
					}
				}else{
					addVariables(ctx,level3FinalReport,totalVerification,totalVerificationFailures,level3ReportArrayLocal);
					Assert.fail(localStep.getErrorMessage());
				}
			}
		}
		
		addVariables(ctx,level3FinalReport,totalVerification,totalVerificationFailures,level3ReportArrayLocal);
		if(verificationFailures){
			Assert.fail("Verification Failures");
		}
	}
	
	private void addVariables(ITestContext ctx,HashMap<String,Object> level3FinalReport,int totalVerification,int totalVerificationFailures,ArrayList<KeywordPropObject> level3ReportArrayLocal){
		level3FinalReport.put("totalVerifications", totalVerification);
		level3FinalReport.put("totalVerificationFailures", totalVerificationFailures);
		level3FinalReport.put("level3ReportArray", level3ReportArrayLocal);
		level3FinalReport.put("testPath", testFile);
		logger.info("##### Test Case Completed "+testFile+" #####");
		ctx.setAttribute(testName, level3FinalReport);
	}
	
}
