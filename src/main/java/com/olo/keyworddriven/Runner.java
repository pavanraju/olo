package com.olo.keyworddriven;

import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.olo.annotations.Reporter;
import com.olo.bot.BrowserBot;
import com.olo.initiator.WebDriverInitiator;
import com.olo.keyworddriven.Keywords;
import com.olo.propobject.KeywordPropObject;


public class Runner extends WebDriverInitiator implements ITest{
	
	private static final Logger logger = LogManager.getLogger(Runner.class.getName());
	private String testFilePath;
	private String testName;
	
	public Runner(String testFileName,String testFilePath){
		this.testFilePath = testFilePath;
		testName = testFileName;
	}
	
	public String getTestName() {
		return testName;
	}
	
	@Reporter(name=com.olo.annotations.KeywordDriven.class)
	@Test
	public void keywordTest(ITestContext ctx) throws Exception{
		BrowserBot browser = new BrowserBot(driver);
		ArrayList<KeywordPropObject> excelSteps = new KeywordUtility().getExcelSteps(testFilePath);
		new KeywordUtility().validateSteps(excelSteps);
		logger.info("Executing Test File "+testFilePath);
		new Execution(browser, new Keywords(browser)).run(ctx, excelSteps, testFilePath, testName);
		logger.info("##### Test Case Completed "+testFilePath+" #####");
	}

}
