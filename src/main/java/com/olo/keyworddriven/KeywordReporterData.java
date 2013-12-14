package com.olo.keyworddriven;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.ITestResult;

import com.olo.keyworddriven.KeywordPropObject;

public class KeywordReporterData {
	
	private static HashMap<ITestResult, ArrayList<KeywordPropObject>> testExecutionData = new HashMap<ITestResult, ArrayList<KeywordPropObject>>();
	
	public static void addTestExecutionData(ITestResult result,ArrayList<KeywordPropObject> testExecutionSteps){
		testExecutionData.put(result, testExecutionSteps);
	}
	
	public static ArrayList<KeywordPropObject> getTestExecutionDetails(ITestResult result){
		if(testExecutionData.containsKey(result)){
			return testExecutionData.get(result);
		}else{
			return null;
		}
	}
	
}
