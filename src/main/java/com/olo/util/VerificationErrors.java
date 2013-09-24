package com.olo.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.ITestResult;

public class VerificationErrors {
	
	private static HashMap<ITestResult, ArrayList<HashMap<String, Object>>> testErrorDetails = new HashMap<ITestResult, ArrayList<HashMap<String,Object>>>();
	
	public static void addError(ITestResult result,HashMap<String, Object> errorDetails){
		ArrayList<HashMap<String, Object>> testerrorDetails = new  ArrayList<HashMap<String,Object>>();
		testerrorDetails.addAll(getTestErrors(result));
		testerrorDetails.add(errorDetails);
		testErrorDetails.put(result, testerrorDetails);
	}
	
	public static ArrayList<HashMap<String, Object>> getTestErrors(ITestResult result){
		if(testErrorDetails.containsKey(result)){
			return testErrorDetails.get(result);
		}else{
			return new ArrayList<HashMap<String,Object>>();
		}
	}
	
}
