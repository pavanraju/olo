package com.olo.listeners;

import java.util.Map;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.internal.Utils;
import org.testng.log4testng.Logger;

import com.olo.reporter.Utility;

public class SuiteListener implements ISuiteListener{
	
	private static final Logger LOGGER = Logger.getLogger(SuiteListener.class);
	
	public void onFinish(ISuite suite) {
		try {
			Map<String, ISuiteResult> results = suite.getResults();
			String suiteName = suite.getName();
			int suiteFailedTests = 0;
			int suitePassedTests = 0;
			int suiteSkippedTests = 0;
			int suiteTotalTests = 0;
			int ctr = 0;
			long suiteStartTime = 0;
			long suiteEndTime = 0;
			long temp = 0;
			StringBuffer textContextSummaryAndDetailedReport = new StringBuffer();
			StringBuffer suiteContextSummaryReport = new StringBuffer();
			StringBuffer passedtextContextSummaryReport = new StringBuffer();
			StringBuffer failedtextContextSummaryReport = new StringBuffer();
			StringBuffer skippedtextContextSummaryReport = new StringBuffer();
			
			StringBuffer passedTextContextReport = new StringBuffer();
			StringBuffer failedTextContextReport = new StringBuffer();
			StringBuffer skippedTextContextReport = new StringBuffer();
			
			StringBuffer errorModelWindow = Utility.getErrorModelWindow();
			
			for (ISuiteResult suiteResult : results.values()) {
				ITestContext suiteTestContext = suiteResult.getTestContext();
				if(ctr == 0){
					suiteStartTime = suiteTestContext.getStartDate().getTime();
					suiteEndTime = suiteTestContext.getEndDate().getTime();
					ctr++;
				}else{
					temp = suiteTestContext.getStartDate().getTime();
					if(temp < suiteStartTime){
						suiteStartTime = temp;
					}
					temp = suiteTestContext.getEndDate().getTime();
					if(temp > suiteEndTime){
						suiteEndTime = temp;
					}
				}
				int contextFailedTests=suiteTestContext.getFailedTests().size();
				suiteFailedTests+= contextFailedTests;
				int contextPassedTests=suiteTestContext.getPassedTests().size();
				suitePassedTests+= contextPassedTests;
				int contextSkippedTests=suiteTestContext.getSkippedTests().size();
				suiteSkippedTests+=contextSkippedTests;
				int contextTotalTests=contextPassedTests+contextFailedTests+contextSkippedTests;
				String testContextAsId = suiteTestContext.getName().replaceAll(" ", "-");
				suiteContextSummaryReport.append("<tr><td><a href=#"+testContextAsId+">"+suiteTestContext.getName()+"</a></td><td class='success'>"+contextPassedTests+"</td><td class='danger'>"+contextFailedTests+"</td><td class='warning'>"+contextSkippedTests+"</td><th>"+contextTotalTests+"</th></tr>");
			    /**
			     * All Context
			     */
			    StringBuffer textContextSummary = Utility.contextSummaryReport(suiteTestContext);
			    textContextSummaryAndDetailedReport.append(textContextSummary);
			    StringBuffer currentTextContextDetailedReport = Utility.contextDetailedReport(suiteTestContext, false);
			    textContextSummaryAndDetailedReport.append(currentTextContextDetailedReport);
			    
			    /**
			     * Passed Context
			     */
			    
			    if(contextPassedTests>0){
			    	passedtextContextSummaryReport.append("<tr><td><a href=#"+testContextAsId+">"+suiteTestContext.getName()+"</a></td><td class='success'>"+contextPassedTests+"</td></tr>");
			    	passedTextContextReport.append(Utility.passedContextDetailedReport(suiteTestContext));
			    }
			    
			    /**
			     * Failed Context
			     */
			    
			    if(contextFailedTests>0){
			    	failedtextContextSummaryReport.append("<tr><td><a href=#"+testContextAsId+">"+suiteTestContext.getName()+"</a></td><td class='danger'>"+contextFailedTests+"</td></tr>");
			    	failedTextContextReport.append(Utility.failedContextDetailedReport(suiteTestContext));
			    }
			    
			    /**
			     * Skipped Context
			     */
			    
			    if(contextSkippedTests>0){
			    	skippedtextContextSummaryReport.append("<tr><td><a href=#"+testContextAsId+">"+suiteTestContext.getName()+"</a></td><td class='warning'>"+contextSkippedTests+"</td></tr>");
			    	skippedTextContextReport.append(Utility.skippedContextDetailedReport(suiteTestContext));
			    }
			    
			}
			
			suiteTotalTests=suiteFailedTests+suitePassedTests+suiteSkippedTests;
		    
			StringBuffer suiteReportDetailed = new StringBuffer();
			suiteReportDetailed.append(Utility.getHtmlToHead());
			suiteReportDetailed.append("<title>"+suiteName+" Suite Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+Utility.getModelJs());
			suiteReportDetailed.append(Utility.getGoogleChartsJs());
			suiteReportDetailed.append(Utility.getGooglePieChart());
			suiteReportDetailed.append(Utility.googleChartDraw(suitePassedTests, suiteFailedTests, suiteSkippedTests));
			suiteReportDetailed.append(Utility.endHeadAndStartBody());
			suiteReportDetailed.append(Utility.startNavigationBar());
			suiteReportDetailed.append(Utility.backToSuitesSummaryLink(false));
			suiteReportDetailed.append(Utility.suiteIndexLink(suiteName, false));
			suiteReportDetailed.append(Utility.suiteActiveAll());
			suiteReportDetailed.append(Utility.suiteAllDropDownMenu(suiteName, suitePassedTests, suiteFailedTests, suiteSkippedTests));
			suiteReportDetailed.append(Utility.endNavigationBar());
			
			suiteReportDetailed.append(Utility.startContainerWithMargin());
			suiteReportDetailed.append(Utility.startRow());
			suiteReportDetailed.append(Utility.startColumn(4));
			
			suiteReportDetailed.append(Utility.suiteSummaryAllInfo(suiteName, suiteStartTime, suiteEndTime));
			
			suiteReportDetailed.append(Utility.startRow());
			suiteReportDetailed.append(Utility.startColumn(12));
			suiteReportDetailed.append(Utility.chartDivID());
			suiteReportDetailed.append(Utility.endColumn());
			suiteReportDetailed.append(Utility.endRow());
			
			suiteReportDetailed.append(Utility.suiteContextSummaryAllInfo(suiteContextSummaryReport, suiteTotalTests, suitePassedTests, suiteFailedTests, suiteSkippedTests));
			
			suiteReportDetailed.append(Utility.endColumn());
			
			suiteReportDetailed.append(Utility.startColumn(8));
			suiteReportDetailed.append(textContextSummaryAndDetailedReport);
			suiteReportDetailed.append(Utility.endColumn());
			suiteReportDetailed.append(Utility.endRow());
			suiteReportDetailed.append(Utility.endContainer());
			suiteReportDetailed.append(errorModelWindow);
			suiteReportDetailed.append(Utility.getCustomJs());
			suiteReportDetailed.append(Utility.endBodyAndHtml());
			String fileName = "suite-"+suiteName+"-index.html";
		    Utils.writeFile(suite.getOutputDirectory(), fileName, suiteReportDetailed.toString());
		    
		    /**
		     * Writing suite passed results
		     */
		    if(suitePassedTests>0){
		    	StringBuffer suiteReportPassed = new StringBuffer();
		    	suiteReportPassed.append(Utility.getHtmlToHead());
		    	suiteReportPassed.append("<title>"+suiteName+" Passed Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getJqueryJs()+Utility.getBootstrapJs());
		    	suiteReportPassed.append(Utility.endHeadAndStartBody());
		    	suiteReportPassed.append(Utility.startNavigationBar());
		    	suiteReportPassed.append(Utility.backToSuitesSummaryLink(false));
		    	suiteReportPassed.append(Utility.suiteIndexLink(suiteName, false));
		    	suiteReportPassed.append(Utility.suiteActivePassed());
		    	suiteReportPassed.append(Utility.suitePassedDropDownMenu(suiteName, suiteFailedTests, suiteSkippedTests));
	            suiteReportPassed.append(Utility.endNavigationBar());
	            
	            suiteReportPassed.append(Utility.startContainerWithMargin());
	            suiteReportPassed.append(Utility.startRow());
	            suiteReportPassed.append(Utility.startColumn(3));
	            suiteReportPassed.append(Utility.suiteSummaryStatusInfo(suiteName));
		    	
		    	suiteReportPassed.append(Utility.suitePassedContextSummaryInfo(passedtextContextSummaryReport, suitePassedTests));
				suiteReportPassed.append(Utility.endColumn());
				
				suiteReportPassed.append(Utility.startColumn(9));
				suiteReportPassed.append(passedTextContextReport);
				suiteReportPassed.append(Utility.endColumn());
				suiteReportPassed.append(Utility.endRow());
				suiteReportPassed.append(Utility.endContainer());
				suiteReportPassed.append(Utility.getCustomJs());
				suiteReportPassed.append(Utility.endBodyAndHtml());
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-passed.html", suiteReportPassed.toString());
		    }
		    
		    /**
		     * Writing suite failed results
		     */
		    if(suiteFailedTests>0){
		    	StringBuffer suiteReportFailed = new StringBuffer();
		    	suiteReportFailed.append(Utility.getHtmlToHead());
		    	suiteReportFailed.append("<title>"+suiteName+" Failed Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+Utility.getModelJs());
		    	suiteReportFailed.append(Utility.endHeadAndStartBody());
		    	suiteReportFailed.append(Utility.startNavigationBar());
		    	suiteReportFailed.append(Utility.backToSuitesSummaryLink(false));
		    	suiteReportFailed.append(Utility.suiteIndexLink(suiteName, false));
		    	suiteReportFailed.append(Utility.suiteActiveFailed());
		    	suiteReportFailed.append(Utility.suiteFailedDropDownMenu(suiteName, suitePassedTests, suiteSkippedTests));
	            suiteReportFailed.append(Utility.endNavigationBar());
		    	
	            suiteReportFailed.append(Utility.startContainerWithMargin());
	            suiteReportFailed.append(Utility.startRow());
	            suiteReportFailed.append(Utility.startColumn(3));
	            suiteReportFailed.append(Utility.suiteSummaryStatusInfo(suiteName));
		    	
		    	suiteReportFailed.append(Utility.suiteFailedContextSummaryInfo(failedtextContextSummaryReport, suiteFailedTests));
		    	
		    	suiteReportFailed.append(Utility.endColumn());
		    	
				suiteReportFailed.append(Utility.startColumn(9));
		    	suiteReportFailed.append(failedTextContextReport);
		    	suiteReportFailed.append(Utility.endColumn());
		    	suiteReportFailed.append(Utility.endRow());
		    	suiteReportFailed.append(Utility.endContainer());
		    	suiteReportFailed.append(errorModelWindow);
		    	suiteReportFailed.append(Utility.getCustomJs());
		    	suiteReportFailed.append(Utility.endBodyAndHtml());
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-failed.html", suiteReportFailed.toString());
		    }
		    
		    /**
		     * Writing suite skipped results
		     */
		    if(suiteSkippedTests>0){
		    	StringBuffer suiteReportSkipped = new StringBuffer();
		    	suiteReportSkipped.append(Utility.getHtmlToHead());
		    	suiteReportSkipped.append("<title>"+suiteName+" Skipped Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+Utility.getModelJs());
		    	suiteReportSkipped.append(Utility.endHeadAndStartBody());
		    	suiteReportSkipped.append(Utility.startNavigationBar());
		    	suiteReportSkipped.append(Utility.backToSuitesSummaryLink(false));
		    	suiteReportSkipped.append(Utility.suiteIndexLink(suiteName, false));
		    	suiteReportSkipped.append(Utility.suiteActiveSkipped());
		    	suiteReportSkipped.append(Utility.suiteSkippedDropDownMenu(suiteName, suitePassedTests, suiteFailedTests));
		    	
	            suiteReportSkipped.append(Utility.endNavigationBar());
		    	
	            suiteReportSkipped.append(Utility.startContainerWithMargin());
	            suiteReportSkipped.append(Utility.startRow());
	            suiteReportSkipped.append(Utility.startColumn(3));
	            suiteReportSkipped.append(Utility.suiteSummaryStatusInfo(suiteName));
		    	
		    	suiteReportSkipped.append(Utility.suiteSkippedContextSummaryInfo(skippedtextContextSummaryReport, suiteSkippedTests));
		    	
		    	suiteReportSkipped.append(Utility.endColumn());
				suiteReportSkipped.append(Utility.startColumn(9));
		    	suiteReportSkipped.append(skippedTextContextReport);
		    	suiteReportSkipped.append(Utility.endColumn());
		    	suiteReportSkipped.append(Utility.endRow());
		    	suiteReportSkipped.append(Utility.endContainer());
		    	suiteReportSkipped.append(errorModelWindow);
		    	suiteReportSkipped.append(Utility.getCustomJs());
		    	suiteReportSkipped.append(Utility.endBodyAndHtml());
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-skipped.html", suiteReportSkipped.toString());
		    }
		    
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	public void onStart(ISuite suite) {
		
	}
	
}
