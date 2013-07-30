package com.olo.keyworddriven;

import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.olo.annotations.Reporter;
import com.olo.bot.BrowserBot;
import com.olo.initiator.WebDriverInitiator;
import com.olo.keyworddriven.Keywords;


public class Runner extends WebDriverInitiator implements ITest{
	
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
		Execution execution = new Execution(testFilePath, browser, new Keywords(browser));
		execution.run(ctx);
	}

}
