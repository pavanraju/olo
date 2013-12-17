package com.olo.keyworddriven;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.SkipException;

import com.olo.keyworddriven.KeywordPropObject;
import com.olo.util.Commons;

public class KeywordUtility {
	
	private static final Logger logger = LogManager.getLogger(KeywordUtility.class.getName());
	
	public ArrayList<KeywordPropObject> getExcelSteps(String testFile) throws Exception{
		ArrayList<KeywordPropObject> excelSteps = null;
		
		File xlsFile=null;
		try {
			xlsFile = new File(testFile);
		} catch (Exception e) {
			throw new SkipException("Test file not found "+testFile);
		}
		
		try {
			logger.info("#### Read and Get steps "+FilenameUtils.getName(testFile)+" #####");
			excelSteps = new Commons().getExcelSteps(xlsFile);
		} catch (Exception e) {
			logger.error("Skipping Test: Error occured while reading test file "+e.getMessage());
			throw new SkipException(e.getMessage());
		}
		
		return excelSteps;
	}
	
	public void validateSteps(ArrayList<KeywordPropObject> excelSteps) throws Exception{
		int ifConditionCount = 0;
		int endIfcount = 0;
		int startDataTableCount = 0;
		int endDataTableCount = 0;
		for(int i=0;i<excelSteps.size();i++){
			if(excelSteps.get(i).getCommand().startsWith("If")){
				ifConditionCount++;
			}
			if(excelSteps.get(i).getCommand().equals("EndIf")){
				endIfcount++;
			}
			if(excelSteps.get(i).getCommand().equals("StartDataTable")){
				startDataTableCount++;
			}
			if(excelSteps.get(i).getCommand().equals("EndDataTable")){
				endDataTableCount++;
			}
		}
		logger.info("validating If conditions matching to ElseIf");
		if(ifConditionCount != endIfcount){
			String message = "EndIf actions are not matching with If condition actions";
			logger.error("Skipping Test: "+ message);
			throw new SkipException(message);
		}
		
		if(startDataTableCount != endDataTableCount){
			String message = "StartDataTable action count is not matching to EndDataTable count";
			logger.error("Skipping Test: "+message);
			throw new SkipException(message);
		}
	}
	
	public void replaceTestData(ArrayList<KeywordPropObject> excelSteps, HashMap<String,String> testData) throws Exception{
		for(int i=0;i<excelSteps.size();i++){
			String replacedTestData = Commons.replaceTestData(excelSteps.get(i).getValue(),testData);
			excelSteps.get(i).setActualValue(replacedTestData);
		}
	}
	
	public ArrayList<HashMap<String, String>> getDataProiderData(String testFile){
		ArrayList<HashMap<String, String>> testData = new ArrayList<HashMap<String, String>>();
		try {
			logger.info("#### Reading Data from "+FilenameUtils.getName(testFile)+" #####");
			testData = Commons.getDataProviderSheetData(testFile);
		} catch (Exception e) {
			logger.error("Skipping Test: Error occured while reading test file data "+e.getMessage());
			throw new SkipException(e.getMessage());
		}
		return testData;
	}
	
}
