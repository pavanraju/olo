package com.olo.keyworddriven;

import java.util.HashMap;

import org.testng.ITestResult;

public class KeywordReporterData {
	
	private static HashMap<ITestResult, HashMap<String, Object>> testExecutionData = new HashMap<ITestResult, HashMap<String, Object>>();
	
	public static void addTestExecutionData(ITestResult result,HashMap<String, Object> testExecutionDetals){
		testExecutionData.put(result, testExecutionDetals);
	}
	
	public static HashMap<String, Object> getTestExecutionDetails(ITestResult result){
		if(testExecutionData.containsKey(result)){
			return testExecutionData.get(result);
		}else{
			return new HashMap<String, Object>();
		}
	}
	
}
