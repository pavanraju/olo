package com.olo.initiator;

import static com.olo.util.PropertyReader.configProp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.TestNG;

import com.olo.listeners.*;

public class ExecutionInitiator {
	
	private static final Logger logger = LogManager.getLogger(ExecutionInitiator.class.getName());
	
	public static void main(String[] args){
		
		try {
			
			if(args.length==0){
				logger.error("No Suite Files to Run");
				throw new Exception("No Suite Files specified");
			}
			
			String[] suitsToRun=args[0].split(",");
			
			if ( args.length > 1){
				configProp.setProperty("browser", args[1]);
			}
			
			List<String> suiteFiles = new ArrayList<String>();
			for(int i=0;i<suitsToRun.length;i++){
				File suiteFile = new File(suitsToRun[i]);
				if(suiteFile.exists()){
					suiteFiles.add(suiteFile.getPath());
				}
			}
			
			TestNG testng = new TestNG();
			TestListener testListener = new TestListener();
			SuiteListener suiteListner = new SuiteListener();
			Reporter reporter = new Reporter();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss");
			testng.setTestSuites(suiteFiles);
			testng.setUseDefaultListeners(false);
			testng.addListener(testListener);
			testng.addListener(suiteListner);
			testng.addListener(reporter);
			
			String testOutputDirectoryAppend="ExecutionReport-"+formatter.format(Calendar.getInstance().getTime());
			String testOutputDirectory="test-output/"+testOutputDirectoryAppend;
			testng.setOutputDirectory(testOutputDirectory);
			testng.run();
			logger.info("Execution Completed !!!");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
