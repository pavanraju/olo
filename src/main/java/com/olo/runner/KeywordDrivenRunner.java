package com.olo.runner;

import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.olo.annotations.Reporter;
import com.olo.execution.KeywordDrivenExecution;
import com.olo.initiator.BrowserBotInitiator;
import com.olo.keywords.KeywordDrivenKeywords;


public class KeywordDrivenRunner extends BrowserBotInitiator implements ITest{
	
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
		KeywordDrivenExecution execution = new KeywordDrivenExecution(testFile, browser, new KeywordDrivenKeywords(browser));
		execution.run(ctx);
	}

}
