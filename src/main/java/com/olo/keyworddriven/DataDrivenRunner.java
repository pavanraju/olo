package com.olo.keyworddriven;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.olo.annotations.Reporter;
import com.olo.bot.BrowserBot;
import com.olo.initiator.WebDriverInitiator;
import com.olo.propobject.KeywordPropObject;

public class DataDrivenRunner extends WebDriverInitiator implements ITest{

	private static final Logger logger = LogManager.getLogger(DataDrivenRunner.class.getName());
	private String testFilePath;
	private String testName;
	
	public DataDrivenRunner(String testFileName,String testFilePath){
		this.testFilePath = testFilePath;
		testName = testFileName;
	}
	
	public String getTestName() {
		return testName;
	}
	
	@DataProvider(name="getTestData")
	public Object[][] returnTestData() throws Exception{
		ArrayList<HashMap<String, String>> testData = new KeywordUtility().getDataProiderData(testFilePath);
		Object[][] result = new Object[testData.size()][2];
		for(int i=0;i<testData.size();i++){
			result[i][0] = i;
			result[i][1] = testData.get(i);
		}
		
		return result;
	}
	
	@Reporter(com.olo.annotations.KeywordDriven.class)
	@Test(dataProvider="getTestData")
	public void keywordTest(ITestContext ctx,int testCount,HashMap<String, String> testData) throws Exception{
		BrowserBot browser = new BrowserBot(driver);
		ArrayList<KeywordPropObject> excelSteps = new KeywordUtility().getExcelSteps(testFilePath);
		new KeywordUtility().replaceTestData(excelSteps, testData);
		new KeywordUtility().validateSteps(excelSteps);
		logger.info("Executing Test File "+testFilePath);
		new Execution(browser, new Keywords(browser)).run(ctx, testCount, excelSteps, testFilePath, testName);
	}

}
