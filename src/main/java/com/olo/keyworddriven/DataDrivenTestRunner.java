package com.olo.keyworddriven;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.olo.annotations.Reporter;
import com.olo.initiator.ApplicationInitiator;
import com.olo.keyworddriven.KeywordPropObject;

public class DataDrivenTestRunner extends ApplicationInitiator implements ITest{
	
	private String testFilePath;
	private String testName;
	
	public DataDrivenTestRunner(String testFilePath){
		this.testFilePath = testFilePath;
		testName = FilenameUtils.getName(testFilePath);
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
		new TestExecution(new Keywords(getDriver())).run(ctx, excelSteps);
	}

}
