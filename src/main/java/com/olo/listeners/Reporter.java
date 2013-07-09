package com.olo.listeners;

import static com.olo.util.PropertyReader.configProp;
import static com.olo.util.PropertyReader.mailProp;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;

//import com.igloor.WebDriverInitiator;
import com.olo.util.Commons;
import com.olo.util.MailClient;

import java.io.File;
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
	    
		int totalFailedTests=0;
		int totalPassedTests=0;
		int totalSkippedTests=0;
	    StringBuffer suitesSummaryReport = new StringBuffer();
	    StringBuffer suitesSummaryReportMailBody = new StringBuffer();
	    String title="Test Results";

	    suitesSummaryReport.append("<table class='table table-bordered'><tr>");
	    suitesSummaryReport.append("<th>Suite</th>");
	    suitesSummaryReport.append("<th>Start Time</th>");
	    suitesSummaryReport.append("<th>End Time</th>");
	    suitesSummaryReport.append("<th>Time Taken</th>");
	    suitesSummaryReport.append("<th>Total Tests</th>");
	    suitesSummaryReport.append("<th>Passed</th>");
	    suitesSummaryReport.append("<th>Failed</th>");
	    suitesSummaryReport.append("<th>Skipped</th>");
	    suitesSummaryReport.append("<th>Failed %</th>");
	    suitesSummaryReport.append("</tr>");
	    suitesSummaryReportMailBody.append(suitesSummaryReport);
	    
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
	    
	    Collections.sort(suites, Commons.suiteStartComp);
	    
	    for (ISuite suite : suites) {
	    	if (suite.getResults().size() == 0) {
				continue;
			}
			String suiteName = suite.getName();
			int suiteFailedTests=0;
			int suitePassedTests=0;
			int suiteSkippedTests=0;
			int suiteTotalTests=0;
			long suiteStartTime=0;
			long suiteEndTime=0;
			long temp = 0;
			int ctr = 0;
			String suiteTimeTaken=null;
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
				suiteTotalTests=suitePassedTests+suiteFailedTests+suiteSkippedTests;
			}
			suiteTimeTaken = Commons.timeTaken(suiteEndTime-suiteStartTime);
			String cls = suiteFailedTests > 0 ? "error" : ( (suitePassedTests > 0 && suiteSkippedTests==0 )  ? "success" :  "error"  );
			suitesSummaryReport.append("<tr class='"+cls+"'>");
			suitesSummaryReportMailBody.append("<tr class='"+cls+"'>");
			suitesSummaryReport.append("<td>"+suiteName+"</td>");
			suitesSummaryReportMailBody.append("<td>"+suiteName+"</td>");
			suitesSummaryReport.append("<td>"+Commons.sdf.format(suiteStartTime)+"</td>");
			suitesSummaryReportMailBody.append("<td>"+Commons.sdf.format(suiteStartTime)+"</td>");
			suitesSummaryReport.append("<td>"+Commons.sdf.format(suiteEndTime)+"</td>");
			suitesSummaryReportMailBody.append("<td>"+Commons.sdf.format(suiteEndTime)+"</td>");
			suitesSummaryReport.append("<td>"+suiteTimeTaken+"</td>");
			suitesSummaryReportMailBody.append("<td>"+suiteTimeTaken+"</td>");
			suitesSummaryReport.append("<td><a href='"+suiteName+File.separator+"suite-"+suiteName+"-index.html'>"+suiteTotalTests+"</a></td>");
			suitesSummaryReportMailBody.append("<td>"+suiteTotalTests+"</td>");
			suitesSummaryReport.append("<td>"+(suitePassedTests > 0 ? "<a href='"+suiteName+File.separator+"suite-"+suiteName+"-passed.html'>"+suitePassedTests+"</a>" : suitePassedTests)+"</td>");
			suitesSummaryReportMailBody.append("<td>"+suitePassedTests+"</td>");
			suitesSummaryReport.append("<td>"+(suiteFailedTests > 0 ? "<a href='"+suiteName+File.separator+"suite-"+suiteName+"-failed.html'>"+suiteFailedTests+"</a>" : suiteFailedTests)+"</td>");
			suitesSummaryReportMailBody.append("<td>"+suiteFailedTests+"</td>");
			suitesSummaryReport.append("<td>"+(suiteSkippedTests > 0 ? "<a href='"+suiteName+File.separator+"suite-"+suiteName+"-skipped.html'>"+suiteSkippedTests+"</a>" : suiteSkippedTests)+"</td>");
			suitesSummaryReportMailBody.append("<td>"+suiteSkippedTests+"</td>");
			suitesSummaryReport.append("<td>"+Commons.percentageCalculator(suiteTotalTests,suiteFailedTests)+" %</td>");
			suitesSummaryReportMailBody.append("<td>"+Commons.percentageCalculator(suiteTotalTests,suiteFailedTests)+" %</td>");
			suitesSummaryReport.append("</tr>");
			suitesSummaryReportMailBody.append("</tr>");
			ctr=0;
			totalFailedTests+=suiteFailedTests;
			totalPassedTests+=suitePassedTests;
			totalSkippedTests+=suiteSkippedTests;
	    }
	    int totalTests=totalPassedTests+totalFailedTests+totalSkippedTests;
	    
	    StringBuffer suitesSummaryStartHtml = new StringBuffer();
	    suitesSummaryStartHtml.append("<html><head><title>" + title + "</title><meta name='viewport' content='width=device-width, initial-scale=1.0'><link href='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css' rel='stylesheet'>");
	    suitesSummaryStartHtml.append("<script type='text/javascript' src='http://www.google.com/jsapi'></script>");
	    suitesSummaryStartHtml.append("<script type='text/javascript'>google.load('visualization', '1', {packages: ['corechart']}); </script>");
	    suitesSummaryStartHtml.append("<script type='text/javascript'>function drawVisualization() { var data = new google.visualization.DataTable(); data.addColumn('string', 'Topping'); data.addColumn('number', 'Slices'); data.addRows([['Passed', "+totalPassedTests+"],['Failed', "+totalFailedTests+"],['Skipped', "+totalSkippedTests+"]]); new google.visualization.PieChart(document.getElementById('visualization')).draw(data,{'width':400,'height':300,slices: [{color: '#109618'}, {color:'#dc3912'}, {color: '#ff9900'}]});} google.setOnLoadCallback(drawVisualization); </script> ");
	    
	    suitesSummaryStartHtml.append("</head><body>");
	    suitesSummaryStartHtml.append("<div class='container'>");
	    suitesSummaryStartHtml.append("<h3 align='center'>"+title+"</h3>");
	    suitesSummaryStartHtml.append("<div class='row'>");
	    suitesSummaryStartHtml.append("<div class='span4'>");
	    suitesSummaryStartHtml.append("<table align='center' class='table table-bordered span4'>");
	    suitesSummaryStartHtml.append("<tr><th colspan='2'><p class='text-center'>Summary</p></th></tr>");
	    if(configProp.containsKey("url")){
	    	suitesSummaryStartHtml.append("<tr><th>URL</th><td>"+configProp.getProperty("url")+"</td></tr>");
	    }
	    suitesSummaryStartHtml.append("<tr><th>Browser</th><td>"+configProp.getProperty("browser")+"</td></tr>");
	    suitesSummaryStartHtml.append("<tr><th>Total Tests</th><td>"+totalTests+"</td></tr>");
	    suitesSummaryStartHtml.append("<tr><th>Passed</th><td>"+totalPassedTests+"</td></tr>");
	    suitesSummaryStartHtml.append("<tr><th>Failed</th><td>"+totalFailedTests+"</td></tr>");
	    suitesSummaryStartHtml.append("<tr><th>Skipped</th><td>"+totalSkippedTests+"</td></tr>");
	    suitesSummaryStartHtml.append("</table>");
	    suitesSummaryStartHtml.append("</div>");
	    suitesSummaryStartHtml.append("<div id='visualization' class='span4'></div>");
	    
	    suitesSummaryStartHtml.append("</div>");
	    
	    
	    StringBuffer suitesSummaryEndHtml = new StringBuffer();
	    suitesSummaryEndHtml.append("</table>");
	    suitesSummaryEndHtml.append("</div></body></html>");
	    suitesSummaryReport.append(suitesSummaryEndHtml);
	    suitesSummaryReportMailBody.append("</table>");
	    
	    StringBuffer suitesFinalSummary = new StringBuffer();
	    suitesFinalSummary.append(suitesSummaryStartHtml);
	    suitesFinalSummary.append(suitesSummaryReport);
	    suitesFinalSummary.append(suitesSummaryEndHtml);
	    
	    suitesSummaryReportMailBody.append("</div></body></html>");
	    
	    StringBuffer suitesFinalSummaryMailBody = new StringBuffer();
	    suitesFinalSummaryMailBody.append(suitesSummaryStartHtml);
	    suitesFinalSummaryMailBody.append(suitesSummaryReportMailBody);
	    
	    Utils.writeFile(outputDirectory, "suites-summary-index.html", suitesFinalSummary.toString());
	    
	    try{
		    if(mailProp.containsKey("mail.SendMail") && mailProp.getProperty("mail.SendMail").equalsIgnoreCase("ON")){
	    		String cc=mailProp.getProperty("mail.cc");
		    	String to = mailProp.getProperty("mail.to");
		    	String subject = "Suites Summary Report";
		    	MailClient mail = new MailClient();
		    	if(cc==null || cc.equals("")){
		    		mail.sendMail(to.split(","), subject, suitesFinalSummaryMailBody);
		    	}else{
		    		mail.sendMail(to.split(","), subject, suitesFinalSummaryMailBody, cc.split(","));
		    	}
		    }
	    }catch(Exception e){
	    	logger.error("Mail sending failed!!");
	    	e.printStackTrace();
	    }
	    
	}
	
}
