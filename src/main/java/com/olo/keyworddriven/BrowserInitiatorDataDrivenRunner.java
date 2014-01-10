package com.olo.keyworddriven;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.olo.annotations.Reporter;
import com.olo.initiator.BrowserInitiator;
import com.olo.keyworddriven.KeywordPropObject;

public class BrowserInitiatorDataDrivenRunner extends BrowserInitiator implements ITest{
	
	private String testFilePath;
	private String testName;
	private Class<? extends Commands> keywords;
	
	public BrowserInitiatorDataDrivenRunner(String testFilePath, Class<? extends Commands> keywords){
		this.testFilePath = testFilePath;
		this.keywords = keywords;
		testName = FilenameUtils.getBaseName(testFilePath);
	}
	
	public String getTestName() {
		return testName;
	}
	
	@DataProvider(name="getTestData")
	public Object[][] returnTestData() throws Exception{
		ArrayList<HashMap<String, String>> testData = new KeywordUtility().getDataProiderData(testFilePath);
		Object[][] result = new Object[testData.size()][2];
		for(int i=0;i<testData.size();i++){
			result[i][0] = testData.get(i);
		}
		return result;
	}
	
	@Reporter(com.olo.annotations.KeywordDriven.class)
	@Test(dataProvider="getTestData")
	public void keywordTest(ITestContext ctx,HashMap<String, String> testData) throws Exception{
		ArrayList<KeywordPropObject> excelSteps = new KeywordUtility().getExcelSteps(testFilePath);
		new KeywordUtility().replaceTestData(excelSteps, testData);
		new KeywordUtility().validateSteps(excelSteps);
		new TestExecution().run(ctx, excelSteps, keywords.getConstructor(WebDriver.class).newInstance(getDriver()));
	}

}
