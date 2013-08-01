package com.olo.keyworddriven;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.SkipException;

import com.olo.propobject.KeywordPropObject;
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
			logger.info("#### Reading "+testFile+" #####");
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
			if(excelSteps.get(i).getAction().startsWith("If")){
				ifConditionCount++;
			}
			if(excelSteps.get(i).getAction().equals("EndIf")){
				endIfcount++;
			}
			if(excelSteps.get(i).getAction().equals("StartDataTable")){
				startDataTableCount++;
			}
			if(excelSteps.get(i).getAction().equals("EndDataTable")){
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
	
}
