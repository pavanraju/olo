package com.olo.listeners;

import static com.olo.util.PropertyReader.mailProp;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;

import com.olo.mailer.MailClient;
import com.olo.reporter.Utility;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Reporter implements IReporter{
	
	private static final Logger logger = LogManager.getLogger(Reporter.class.getName());
	
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
	    StringBuffer suiteListDetailsMail = new StringBuffer();
	    
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
			suiteListDetails.append(Utility.suiteListTableDetailsRow(false,suite.getName(),suiteStartTime,suiteEndTime,suitePassedTests,suiteFailedTests,suiteSkippedTests));
			suiteListDetailsMail.append(Utility.suiteListTableDetailsRow(true,suite.getName(),suiteStartTime,suiteEndTime,suitePassedTests,suiteFailedTests,suiteSkippedTests));
			ctr=0;
			totalFailedTests+=suiteFailedTests;
			totalPassedTests+=suitePassedTests;
			totalSkippedTests+=suiteSkippedTests;
			suiteCounter++;
	    }
	    
	    StringBuffer suitesSummaryHtml = new StringBuffer();
	    suitesSummaryHtml.append(Utility.getHtmlToHead());
	    suitesSummaryHtml.append(Utility.getSuitesSummaryHead(title, totalPassedTests, totalFailedTests, totalSkippedTests));
	    suitesSummaryHtml.append(Utility.endHeadAndStartBody());
	    suitesSummaryHtml.append(Utility.startContainer());
	    suitesSummaryHtml.append(Utility.headerTitle(title));
	    suitesSummaryHtml.append(Utility.startRow());
	    suitesSummaryHtml.append(Utility.configTableDiv());
	    suitesSummaryHtml.append(Utility.spaceDiv());
	    suitesSummaryHtml.append(Utility.chartDiv());
	    suitesSummaryHtml.append(Utility.endRow());
	    suitesSummaryHtml.append(Utility.startTable());
	    suitesSummaryHtml.append(Utility.suiteListTableHeaderRow());
	    suitesSummaryHtml.append(suiteListDetails);
	    suitesSummaryHtml.append(Utility.suitesSummaryRow(startTimeOfSuites, endTimeOfSuites, totalPassedTests, totalFailedTests, totalSkippedTests));
	    suitesSummaryHtml.append(Utility.endTable());
	    suitesSummaryHtml.append(Utility.endContainerToHtml());
	    
	    StringBuffer suitesSummaryMailHtml = new StringBuffer();
	    suitesSummaryMailHtml.append(Utility.getHtmlToHead());
	    suitesSummaryMailHtml.append(Utility.getSuiteSummaryMailHead(title));
	    suitesSummaryMailHtml.append(Utility.endHeadAndStartBody());
	    suitesSummaryMailHtml.append(Utility.startContainer());
	    suitesSummaryMailHtml.append(Utility.headerTitle(title));
	    suitesSummaryMailHtml.append(Utility.startRow());
	    suitesSummaryMailHtml.append(Utility.configTableDiv());
	    suitesSummaryMailHtml.append(Utility.endRow());
	    suitesSummaryMailHtml.append(Utility.startTable());
	    suitesSummaryMailHtml.append(Utility.suiteListTableHeaderRow());
	    suitesSummaryMailHtml.append(suiteListDetailsMail);
	    suitesSummaryMailHtml.append(Utility.suitesSummaryRow(startTimeOfSuites, endTimeOfSuites, totalPassedTests, totalFailedTests, totalSkippedTests));
	    suitesSummaryMailHtml.append(Utility.endTable());
	    suitesSummaryMailHtml.append(Utility.endContainerToHtml());
	    
	    
	    Utils.writeFile(outputDirectory, "suites-summary-index.html", suitesSummaryHtml.toString());
	    
	    try{
		    if(mailProp.containsKey("mail.SendMail") && mailProp.getProperty("mail.SendMail").equalsIgnoreCase("ON")){
	    		String cc=mailProp.getProperty("mail.cc");
		    	String to = mailProp.getProperty("mail.to");
		    	String subject = "Suites Summary Report";
		    	MailClient mail = new MailClient();
		    	if(cc==null || cc.equals("")){
		    		mail.sendMail(to.split(","), subject, suitesSummaryMailHtml);
		    	}else{
		    		mail.sendMail(to.split(","), subject, suitesSummaryMailHtml, cc.split(","));
		    	}
		    	logger.info("Suites Summary mail sent");
		    }
	    }catch(Exception e){
	    	logger.error("Mail sending failed!!");
	    	e.printStackTrace();
	    }
	}
	
}
