package com.olo.keyworddriven;

import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.olo.annotations.Reporter;
import com.olo.initiator.ApplicationInitiator;
import com.olo.keyworddriven.Keywords;
import com.olo.keyworddriven.KeywordPropObject;


public class TestRunner extends ApplicationInitiator implements ITest{
	
	private String testFilePath;
	private String testName;
	
	public TestRunner(String testFilePath){
		this.testFilePath = testFilePath;
		testName = FilenameUtils.getName(testFilePath);
	}
	
	public String getTestName() {
		return testName;
	}
	
	@Reporter(com.olo.annotations.KeywordDriven.class)
	@Test
	public void keywordTest(ITestContext ctx) throws Exception{
		ArrayList<KeywordPropObject> excelSteps = new KeywordUtility().getExcelSteps(testFilePath);
		new KeywordUtility().validateSteps(excelSteps);
		new TestExecution(new Keywords(getDriver())).run(ctx, excelSteps);
	}

}