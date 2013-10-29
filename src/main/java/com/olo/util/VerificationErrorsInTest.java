package com.olo.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.ITestResult;

public class VerificationErrorsInTest {
	
	private static HashMap<ITestResult, ArrayList<VerificationError>> testErrorDetails = new HashMap<ITestResult, ArrayList<VerificationError>>();
	
	public static void addError(ITestResult result,VerificationError errorDetails){
		ArrayList<VerificationError> testerrorDetails = new  ArrayList<VerificationError>();
		testerrorDetails.addAll(getTestErrors(result));
		testerrorDetails.add(errorDetails);
		testErrorDetails.put(result, testerrorDetails);
	}
	
	public static ArrayList<VerificationError> getTestErrors(ITestResult result){
		if(testErrorDetails.containsKey(result)){
			return testErrorDetails.get(result);
		}else{
			return new ArrayList<VerificationError>();
		}
	}
	
	public static boolean hasVerificationErrors(ITestResult result){
		if(testErrorDetails.containsKey(result)){
			return true;
		}else{
			return false;
		}
	}
	
}
