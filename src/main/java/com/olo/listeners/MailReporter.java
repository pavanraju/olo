package com.olo.listeners;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.internal.Utils;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlSuite;

import com.olo.mailer.MailClient;
import com.olo.propertyutil.MailProperties;
import com.olo.reporter.Utility;
import com.olo.util.Commons;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MailReporter implements IReporter{
	
	private static final Logger LOGGER = Logger.getLogger(MailReporter.class);
	
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		try {
			generateNewIndex(suites,outputDirectory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void generateNewIndex(List<ISuite> suites,String outputDirectory){
	    
		int totalFailedTests = 0;
		int totalPassedTests = 0;
		int totalSkippedTests = 0;
	    long startTimeOfSuites = 0;
	    long endTimeOfSuites = 0;
	    String title="Test Results";
	    
	    /*finding the start time to sort the list
	     */
	    for (ISuite suite : suites) {
	    	long suiteStartTime=0;
	    	long temp = 0;
	    	int ctr = 0;
	    	Map<String, ISuiteResult> results = suite.getResults();
	    	for (ISuiteResult suiteResult : results.values()) {
	    		ITestContext suiteTestContext = suiteResult.getTestContext();
	    		if(ctr == 0){
	    			suiteStartTime=suiteTestContext.getStartDate().getTime();
	    			ctr++;
	    		}else{
	    			temp = suiteTestContext.getStartDate().getTime();
	    			if(temp < suiteStartTime)
	    				suiteStartTime = temp;
				}
	    	}
	    	suite.setAttribute("suiteStartTime_sort", suiteStartTime);
	    }
	    
	    Collections.sort(suites, Utility.suiteStartComp);
	    
	    StringBuffer suiteListDetails = new StringBuffer();
	    
	    int totalSuitesSize = suites.size();
	    int suiteCounter = 1;
	    for (ISuite suite : suites) {
	    	if (suite.getResults().size() == 0) {
				continue;
			}
	    	
			
			int suiteFailedTests=0;
			int suitePassedTests=0;
			int suiteSkippedTests=0;
			long suiteStartTime=0;
			long suiteEndTime=0;
			long temp = 0;
			int ctr = 0;
			
			Map<String, ISuiteResult> results = suite.getResults();
			for (ISuiteResult suiteResult : results.values()) {
				ITestContext suiteTestContext = suiteResult.getTestContext();
				suiteFailedTests+= suiteTestContext.getFailedTests().size();
				suitePassedTests+= suiteTestContext.getPassedTests().size();
				suiteSkippedTests+=suiteTestContext.getSkippedTests().size();
				if(ctr == 0){
					suiteStartTime=suiteTestContext.getStartDate().getTime();
					suiteEndTime=suiteTestContext.getEndDate().getTime();
					ctr++;
				}else{
					temp = suiteTestContext.getStartDate().getTime();
					if(temp < suiteStartTime)
						suiteStartTime = temp;
					temp = suiteTestContext.getEndDate().getTime();
					if(temp > suiteEndTime)
						suiteEndTime = temp;
				}
			}
			if(suiteCounter == 1){
				startTimeOfSuites=suiteStartTime;
	    	}
			if(suiteCounter == totalSuitesSize){
				endTimeOfSuites = suiteEndTime;
			}
			suiteListDetails.append(Utility.suiteListTableDetailsRow(true,suite.getName(),suiteStartTime,suiteEndTime,suitePassedTests,suiteFailedTests,suiteSkippedTests));
			ctr=0;
			totalFailedTests+=suiteFailedTests;
			totalPassedTests+=suitePassedTests;
			totalSkippedTests+=suiteSkippedTests;
			suiteCounter++;
	    }
	    
	    StringBuffer suitesSummaryHtml = new StringBuffer();
	    suitesSummaryHtml.append(Utility.getHtmlToHead());
	    suitesSummaryHtml.append(Utility.suitesSummaryHead(title, totalPassedTests, totalFailedTests, totalSkippedTests));
	    suitesSummaryHtml.append(Utility.endHeadAndStartBody());
	    suitesSummaryHtml.append(Utility.startContainer());
	    suitesSummaryHtml.append(Utility.headerTitle(title));
	    suitesSummaryHtml.append(Utility.startRow());
	    suitesSummaryHtml.append(Utility.configTableDiv());
	    suitesSummaryHtml.append(Utility.chartDiv());
	    suitesSummaryHtml.append(Utility.endRow());
	    suitesSummaryHtml.append(Utility.startResponsiveTableDiv());
	    suitesSummaryHtml.append(Utility.startTableWithHover());
	    suitesSummaryHtml.append(Utility.suiteListTableHeaderRow());
	    suitesSummaryHtml.append(suiteListDetails);
	    suitesSummaryHtml.append(Utility.suitesSummaryRow(startTimeOfSuites, endTimeOfSuites, totalPassedTests, totalFailedTests, totalSkippedTests));
	    suitesSummaryHtml.append(Utility.endTable());
	    suitesSummaryHtml.append(Utility.endResponsiveTableDiv());
	    suitesSummaryHtml.append(Utility.endContainerToHtml());
	    
	    String fileName = "mail-suites-summary-index.html";
	    Utils.writeFile(outputDirectory, fileName, suitesSummaryHtml.toString());
	    
	    try{
    		String cc = MailProperties.getMailCC();
	    	String to = MailProperties.getMailTo();
	    	String subject = "Suites Summary Report - Total : "+(totalPassedTests+totalFailedTests+totalSkippedTests)+"; Passed : "+totalPassedTests+"; Failed : "+totalFailedTests+"; Skipped : "+totalSkippedTests;
	    	StringBuffer body = new StringBuffer();
	    	body.append("Execution Summary Report is in below mentioned location.<br/><br/>Ip Address : "+Commons.getSystemIpAddress()+"<br/>Folder Path : "+outputDirectory);
	    	MailClient mail = new MailClient();
	    	if(cc==null || cc.equals("")){
	    		mail.sendMail(to.split(","), subject, body,outputDirectory+File.separator+fileName);
	    	}else{
	    		mail.sendMail(to.split(","), subject, body, cc.split(","), outputDirectory+File.separator+fileName);
	    	}
	    	LOGGER.info("Suites Summary mail sent");
	    }catch(Exception e){
	    	LOGGER.error("Mail sending failed!!");
	    	e.printStackTrace();
	    }
	}
	
}
