package com.olo.listeners;

import static com.olo.util.PropertyReader.mailProp;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.collections.Lists;
import org.testng.collections.Maps;
import org.testng.collections.Sets;
import org.testng.internal.MethodHelper;
import org.testng.internal.Utils;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.olo.util.Commons;
import com.olo.util.MailClient;

public class SuiteListener implements ISuiteListener{
	
	private static final Logger logger = LogManager.getLogger(SuiteListener.class.getName());
	
	private XmlSuite m_xmlSuite;
	
	public void onFinish(ISuite suite) {
		try {
			Map<String, ISuiteResult> results=suite.getResults();
			String suiteName= suite.getName();
			int suiteFailedTests=0;
			int suitePassedTests=0;
			int suiteSkippedTests=0;
			int suiteTotalTests=0;
			int ctr=0;
			long suiteStartTime=0;
			long suiteEndTime=0;
			String suiteTimeTaken=null;
			long temp=0;
			StringBuffer textContextReport = new StringBuffer();
			StringBuffer textContextSummaryReport = new StringBuffer();
			StringBuffer passedtextContextSummaryReport = new StringBuffer();
			StringBuffer failedtextContextSummaryReport = new StringBuffer();
			StringBuffer skippedtextContextSummaryReport = new StringBuffer();
			
			StringBuffer passedTextContextReport = new StringBuffer();
			StringBuffer failedTextContextReport = new StringBuffer();
			StringBuffer skippedTextContextReport = new StringBuffer();
			
			StringBuffer testResultsHeader = new StringBuffer();
			testResultsHeader.append("<tr>");
			testResultsHeader.append("<th>S.No</th>");
			testResultsHeader.append("<th>Test Case</th>");
			testResultsHeader.append("<th>Start Time</th>");
			testResultsHeader.append("<th>End Time</th>");
			testResultsHeader.append("<th>Time Taken</th>");
			testResultsHeader.append("<th>Status</th>");
			testResultsHeader.append("</tr>");
			
			StringBuffer errorModelWindow = new StringBuffer();
			errorModelWindow.append("<div id='myModal' class='modal hide fade' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>  <div class='modal-header'>   <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>   <h4 id='myModalLabel'>Stack Trace</h4>  </div>  <div class='modal-body'>   <p id='modelbodyerror'></p>  </div>	</div>");
			
			
			
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
				String contextStartTime=Commons.sdf.format(suiteTestContext.getStartDate().getTime());
				String contextEndTime=Commons.sdf.format(suiteTestContext.getEndDate().getTime());
				String contextTimeTaken=Commons.timeTaken(suiteTestContext.getEndDate().getTime()-suiteTestContext.getStartDate().getTime());
				int contextTotalTests=contextPassedTests+contextFailedTests+contextSkippedTests;
				
			    List<ITestResult> testResultsChronological = new ArrayList<ITestResult>();
			    testResultsChronological.addAll(suiteTestContext.getPassedTests().getAllResults());
			    testResultsChronological.addAll(suiteTestContext.getFailedTests().getAllResults());
			    testResultsChronological.addAll(suiteTestContext.getSkippedTests().getAllResults());
			    Collections.sort(testResultsChronological, TIME_COMPARATOR);
			    
			    List<ITestResult> failedSuiteExcel = new ArrayList<ITestResult>();
			    failedSuiteExcel.addAll(suiteTestContext.getFailedTests().getAllResults());
			    failedSuiteExcel.addAll(suiteTestContext.getSkippedTests().getAllResults());
			    Collections.sort(failedSuiteExcel, TIME_COMPARATOR);
			    
			    StringBuffer suiteResultSummaryReport = new StringBuffer();
			    suiteResultSummaryReport.append("<div class='span5'><table class='table table-bordered' id='"+suiteTestContext.getName()+"'><tr><th colspan='2'>Summary of "+suiteTestContext.getName()+" Tests</th></tr>");
			    suiteResultSummaryReport.append("<tr><th>Total Tests</th><td>"+contextTotalTests+"</td></tr>\n");
			    suiteResultSummaryReport.append("<tr "+(contextPassedTests!=0 ? "class='success'": "")+"><th>Passed</th><td>"+contextPassedTests+"</td></tr>\n");
			    suiteResultSummaryReport.append("<tr "+(contextFailedTests!=0 ? "class='error'" : "")+" ><th>Failed</th><td>"+contextFailedTests+"</td></tr>\n");
			    suiteResultSummaryReport.append("<tr "+(contextSkippedTests!=0 ? "class='warning'" : "")+" ><th>Skipped</th><td>"+contextSkippedTests+"</td></tr>\n");
			    suiteResultSummaryReport.append("<tr><th>Start Time</th><td>"+contextStartTime+"</td></tr>\n");
			    suiteResultSummaryReport.append("<tr><th>End Time</th><td>"+contextEndTime+"</td></tr>\n");
			    suiteResultSummaryReport.append("<tr><th>Time Taken</th><td>"+contextTimeTaken+"</td></tr>\n");
			    suiteResultSummaryReport.append("</table></div>");
			    
			    StringBuffer suiteResultBody = new StringBuffer();
			    suiteResultBody.append(testDetailReport(testResultsChronological));
			    
			    textContextReport.append(suiteResultSummaryReport);
			    textContextReport.append("<table class='table table-bordered'>");
			    textContextReport.append("<caption>Detailed report of "+suiteTestContext.getName()+" Tests</caption>");
			    textContextReport.append(testResultsHeader);
			   
			    textContextReport.append(suiteResultBody);
			    textContextReport.append("</table><hr/>");
			    textContextSummaryReport.append("<tr><td><a href=#"+suiteTestContext.getName()+">"+suiteTestContext.getName()+"</a></td><td class='success'>"+contextPassedTests+"</td><td class='error'>"+contextFailedTests+"</td><td class='warning'>"+contextSkippedTests+"</td></tr>");
			    
			    
			    /**
			     * Passed Context
			     */
			    
			    StringBuffer suiteReportPassedTestDetails = new StringBuffer();
			    if(contextPassedTests>0){
			    	
			    	passedtextContextSummaryReport.append("<tr><td><a href=#"+suiteTestContext.getName()+">"+suiteTestContext.getName()+"</a></td><td class='success'>"+contextPassedTests+"</td><td>"+contextTotalTests+"</td></tr>");
			    	
				    suiteReportPassedTestDetails.append("<table class='table table-bordered'>");
				    suiteReportPassedTestDetails.append("<caption>Detailed report of "+suiteTestContext.getName()+" Tests</caption>");
				    suiteReportPassedTestDetails.append(testResultsHeader);
				    List<ITestResult> passedTests = new ArrayList<ITestResult>();
				    passedTests.addAll(suiteTestContext.getPassedTests().getAllResults());
				    Collections.sort(passedTests, TIME_COMPARATOR);
				    suiteReportPassedTestDetails.append(testDetailReport(passedTests));
				    suiteReportPassedTestDetails.append("</table><hr/>");
			    }
			    
			    passedTextContextReport.append(suiteReportPassedTestDetails);
			    
			    /**
			     * Failed Context
			     */
			    
			    StringBuffer suiteReportFailedDetailReport = new StringBuffer();
			    if(contextFailedTests>0){
			    	
			    	failedtextContextSummaryReport.append("<tr><td><a href=#"+suiteTestContext.getName()+">"+suiteTestContext.getName()+"</a></td><td class='error'>"+contextFailedTests+"</td><td>"+contextTotalTests+"</td></tr>");
			    	
			    	suiteReportFailedDetailReport.append("<table class='table table-bordered'>");
			    	suiteReportFailedDetailReport.append("<caption>Detailed report of "+suiteTestContext.getName()+" Tests</caption>");
			    	suiteReportFailedDetailReport.append(testResultsHeader);
			    	List<ITestResult> failedTests = new ArrayList<ITestResult>();
				    failedTests.addAll(suiteTestContext.getFailedTests().getAllResults());
				    Collections.sort(failedTests, TIME_COMPARATOR);
				    suiteReportFailedDetailReport.append(testDetailReport(failedTests));
				    suiteReportFailedDetailReport.append("</table>");
			    }
			    failedTextContextReport.append(suiteReportFailedDetailReport);
			    
			    /**
			     * Skipped Context
			     */
			    
			    StringBuffer suiteReportSkippedDetailReport = new StringBuffer();
			    if(contextSkippedTests>0){
			    	
			    	skippedtextContextSummaryReport.append("<tr><td><a href=#"+suiteTestContext.getName()+">"+suiteTestContext.getName()+"</a></td><td class='warning'>"+contextSkippedTests+"</td><td>"+contextTotalTests+"</td></tr>");
			    	
			    	suiteReportSkippedDetailReport.append("<table class='table table-bordered'>");
			    	suiteReportSkippedDetailReport.append("<caption>Detailed report of "+suiteTestContext.getName()+" Tests</caption>");
			    	suiteReportSkippedDetailReport.append(testResultsHeader);
			    	List<ITestResult> skippedTests = new ArrayList<ITestResult>();
			    	skippedTests.addAll(suiteTestContext.getSkippedTests().getAllResults());
				    Collections.sort(skippedTests, TIME_COMPARATOR);
				    suiteReportSkippedDetailReport.append(testDetailReport(skippedTests));
				    suiteReportSkippedDetailReport.append("</table>");
			    }
			    skippedTextContextReport.append(suiteReportSkippedDetailReport);
			    
			}
			suiteTimeTaken=Commons.timeTaken(suiteEndTime-suiteStartTime);
			
			suiteTotalTests=suiteFailedTests+suitePassedTests+suiteSkippedTests;
			StringBuffer suiteReport = new StringBuffer();
			suiteReport.append("<!DOCTYPE html><html><head><title>"+suiteName+" Suite Results</title><meta name='viewport' content='width=device-width, initial-scale=1.0'><link href='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css' rel='stylesheet'>"+Commons.customStyle+"<script src='http://code.jquery.com/jquery-1.10.1.min.js'></script><script src='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js'></script><script type='text/javascript'>$( document ).ready(function() {    $(document).on('click', '.openDialog', function () {  var myBookId = $(this).data('showthismessage');   $('.modal-body #modelbodyerror').html( myBookId ); });  });</script></head><body>");
			suiteReport.append("<div class='container-fluid' style='margin-top:20px;'>");
			suiteReport.append("<ul class='breadcrumb'>  <li><a href='../suites-summary-index.html'>Suite Summary</a> <span class='divider'>/</span></li>");
			
			suiteReport.append("<div class='btn-group'>");
			suiteReport.append("<button class='btn dropdown-toggle' data-toggle='dropdown'>"+suiteName+" Results <span class='caret'></span></button>");
            
			suiteReport.append("<ul class='dropdown-menu'>");
            if(suitePassedTests>0){
            	suiteReport.append("<li><a href='suite-"+suiteName+"-passed.html'>Passed</a></li>");
            }
            if(suiteFailedTests>0){
            	suiteReport.append("<li><a href='suite-"+suiteName+"-failed.html'>Failed</a></li>");
            }
            if(suiteSkippedTests>0){
            	suiteReport.append("<li><a href='suite-"+suiteName+"-skipped.html'>Skipped</a></li>");
            }
            suiteReport.append("</ul>");
            suiteReport.append("</div>");
            suiteReport.append("</ul>");
            
			suiteReport.append("<div class='row-fluid'>");
			suiteReport.append("<div class='span3'><div class='affix span3'>");
			suiteReport.append("<table class='table table-bordered'>");
			suiteReport.append("<tr><th>Suite</th><td>"+suiteName+"</td></tr>");
			suiteReport.append("<tr><th>Start Time</th><td>"+Commons.sdfTests.format(suiteStartTime)+"</td></tr>");
			suiteReport.append("<tr><th>End Time</th><td>"+Commons.sdfTests.format(suiteEndTime)+"</td></tr>");
			suiteReport.append("<tr><th>Time Taken</th><td>"+suiteTimeTaken+"</td></tr>");
			suiteReport.append("</table>");
			
			suiteReport.append("<table class='table table-bordered'>");
			suiteReport.append("<tr><th>Test Name</th><th>Passed</th><th>Failed</th><th>Skipped</th></tr>");
			suiteReport.append(textContextSummaryReport);
			suiteReport.append("<tr><th>Total</th><th class='success'>"+suitePassedTests+"</th><th class='error'>"+suiteFailedTests+"</th><th class='warning'>"+suiteSkippedTests+"</th></tr>");
			suiteReport.append("</table>");
			
			suiteReport.append("</div></div>");
			suiteReport.append("<div class='span9'>");
			suiteReport.append(textContextReport);
			suiteReport.append("</div>");
			suiteReport.append(errorModelWindow);
		    suiteReport.append("</div></div></body></html>");
		    
		    
		    /**
		     * Writing all results
		     */

		    Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-index.html", suiteReport.toString());
		    
		    
		    /**
		     * Writing only suite passed results
		     */
		    if(suitePassedTests>0){
		    	StringBuffer suiteReportPassed = new StringBuffer();
		    	suiteReportPassed.append("<!DOCTYPE html><html><head><title>"+suiteName+" Passed Results</title><meta name='viewport' content='width=device-width, initial-scale=1.0'><link href='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css' rel='stylesheet'>"+Commons.customStyle+"<script src='http://code.jquery.com/jquery-1.10.1.min.js'></script><script src='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js'></script><script type='text/javascript'>$( document ).ready(function() { $(document).on('click', '.openDialog', function () {  var myBookId = $(this).data('showthismessage');   $('.modal-body #modelbodyerror').html( myBookId ); });  });</script></head><body>");
		    	suiteReportPassed.append("<div class='container-fluid' style='margin-top:20px;'>");
		    	suiteReportPassed.append("<ul class='breadcrumb'>  <li><a href='../suites-summary-index.html'>Suite Summary</a> <span class='divider'>/</span></li>");
				
		    	suiteReportPassed.append("<div class='btn-group'>");
		    	suiteReportPassed.append("<button class='btn dropdown-toggle' data-toggle='dropdown'>"+suiteName+" Passed Results <span class='caret'></span></button>");
	            
		    	suiteReportPassed.append("<ul class='dropdown-menu'>");
	            
		    	suiteReportPassed.append("<li><a href='suite-"+suiteName+"-index.html'>All</a></li>");

	            if(suiteFailedTests>0){
	            	suiteReportPassed.append("<li><a href='suite-"+suiteName+"-failed.html'>Failed</a></li>");
	            }
	            if(suiteSkippedTests>0){
	            	suiteReportPassed.append("<li><a href='suite-"+suiteName+"-skipped.html'>Skipped</a></li>");
	            }
	            suiteReportPassed.append("</ul>");
	            suiteReportPassed.append("</div>");
	            suiteReportPassed.append("</ul>");
	            
	            suiteReportPassed.append("<div class='row-fluid'>");
		    	suiteReportPassed.append("<div class='span3'><div class='affix span3'>");
		    	suiteReportPassed.append("<table class='table table-bordered'>");
		    	suiteReportPassed.append("<tr><th colspan='2'>Passed Results of "+suiteName+" Suite</th></tr>");
				suiteReportPassed.append("<tr><th>Total Tests</th><td>"+suiteTotalTests+"</td></tr>");
				suiteReportPassed.append("<tr><th>Passed</th><td>"+suitePassedTests+"</td></tr>");
				suiteReportPassed.append("</table>");
				suiteReportPassed.append("<table class='table table-bordered'>");
				suiteReportPassed.append("<tr><th>Test Name</th><th>Passed</th><th>Total Tests</th></tr>");
				suiteReportPassed.append(passedtextContextSummaryReport);
				suiteReportPassed.append("</table>");
				
				suiteReportPassed.append("</div></div>");
				suiteReportPassed.append("<div class='span9'>");
				suiteReportPassed.append(passedTextContextReport);
				suiteReportPassed.append("</div>");
				suiteReportPassed.append(errorModelWindow);
				suiteReportPassed.append("</div></div></body></html>");
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-passed.html", suiteReportPassed.toString());
		    }
		    
		    /**
		     * Writing only suite failed results
		     */
		    if(suiteFailedTests>0){
		    	StringBuffer suiteReportFailed = new StringBuffer();
		    	suiteReportFailed.append("<!DOCTYPE html><html><head><title>"+suiteName+" Failed Results</title><meta name='viewport' content='width=device-width, initial-scale=1.0'><link href='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css' rel='stylesheet'>"+Commons.customStyle+"<script src='http://code.jquery.com/jquery-1.10.1.min.js'></script><script src='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js'></script><script type='text/javascript'>$( document ).ready(function() { $(document).on('click', '.openDialog', function () {  var myBookId = $(this).data('showthismessage');   $('.modal-body #modelbodyerror').html( myBookId ); });  });</script></head><body>");
		    	suiteReportFailed.append("<div class='container-fluid' style='margin-top:20px;'>");
		    	suiteReportFailed.append("<ul class='breadcrumb'>  <li><a href='../suites-summary-index.html'>Suite Summary</a> <span class='divider'>/</span></li>");
				
		    	suiteReportFailed.append("<div class='btn-group'>");
		    	suiteReportFailed.append("<button class='btn dropdown-toggle' data-toggle='dropdown'>"+suiteName+" Failed Results <span class='caret'></span></button>");
	            
		    	suiteReportFailed.append("<ul class='dropdown-menu'>");
	            
		    	suiteReportFailed.append("<li><a href='suite-"+suiteName+"-index.html'>All</a></li>");

	            if(suitePassedTests>0){
	            	suiteReportFailed.append("<li><a href='suite-"+suiteName+"-passed.html'>Passed</a></li>");
	            }
	            if(suiteSkippedTests>0){
	            	suiteReportFailed.append("<li><a href='suite-"+suiteName+"-skipped.html'>Skipped</a></li>");
	            }
	            suiteReportFailed.append("</ul>");
	            suiteReportFailed.append("</div>");
	            suiteReportFailed.append("</ul>");
	            
	            suiteReportFailed.append("<div class='row-fluid'>");
		    	suiteReportFailed.append("<div class='span3'><div class='affix span3'>");
		    	suiteReportFailed.append("<table class='table table-bordered'>");
		    	suiteReportFailed.append("<tr><th colspan='2'>Failed Results of "+suiteName+" Suite</th></tr>");
		    	suiteReportFailed.append("<tr><th>Total Tests</th><td>"+suiteTotalTests+"</td></tr>");
		    	suiteReportFailed.append("<tr><th>Failed</th><td>"+suiteFailedTests+"</td></tr>");
		    	suiteReportFailed.append("</table>");
		    	suiteReportFailed.append("<table class='table table-bordered'>");
		    	suiteReportFailed.append("<tr><th>Test Name</th><th>Failed</th><th>Total Tests</th></tr>");
		    	suiteReportFailed.append(failedtextContextSummaryReport);
		    	suiteReportFailed.append("</table>");
		    	
		    	suiteReportFailed.append("</div></div>");
		    	suiteReportFailed.append("<div class='span9'>");
		    	suiteReportFailed.append(failedTextContextReport);
		    	suiteReportFailed.append("</div>");
		    	suiteReportFailed.append(errorModelWindow);
		    	suiteReportFailed.append("</div></div></body></html>");
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-failed.html", suiteReportFailed.toString());
		    }
		    
		    /**
		     * Writing only suite skipped results
		     */
		    if(suiteSkippedTests>0){
		    	StringBuffer suiteReportSkipped = new StringBuffer();
		    	suiteReportSkipped.append("<!DOCTYPE html><html><head><title>"+suiteName+" Skipped Results</title><meta name='viewport' content='width=device-width, initial-scale=1.0'><link href='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css' rel='stylesheet'>"+Commons.customStyle+"<script src='http://code.jquery.com/jquery-1.10.1.min.js'></script><script src='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js'></script><script type='text/javascript'>$( document ).ready(function() { $(document).on('click', '.openDialog', function () {  var myBookId = $(this).data('showthismessage');   $('.modal-body #modelbodyerror').html( myBookId ); });  });</script></head><body>");
		    	suiteReportSkipped.append("<div class='container-fluid' style='margin-top:20px;'>");
		    	suiteReportSkipped.append("<ul class='breadcrumb'>  <li><a href='../suites-summary-index.html'>Suite Summary</a> <span class='divider'>/</span></li>");
				
		    	suiteReportSkipped.append("<div class='btn-group'>");
				suiteReportSkipped.append("<button class='btn dropdown-toggle' data-toggle='dropdown'>"+suiteName+" Skipped Results <span class='caret'></span></button>");
	            
				suiteReportSkipped.append("<ul class='dropdown-menu'>");
	            
				suiteReportSkipped.append("<li><a href='suite-"+suiteName+"-index.html'>All</a></li>");

	            if(suitePassedTests>0){
	            	suiteReportSkipped.append("<li><a href='suite-"+suiteName+"-passed.html'>Failed</a></li>");
	            }
	            if(suiteFailedTests>0){
	            	suiteReportSkipped.append("<li><a href='suite-"+suiteName+"-failed.html'>Skipped</a></li>");
	            }
	            suiteReportSkipped.append("</ul>");
	            suiteReportSkipped.append("</div>");
	            suiteReportSkipped.append("</ul>");
	            
	            suiteReportSkipped.append("<div class='row-fluid'>");
		    	suiteReportSkipped.append("<div class='span3'><div class='affix span3'>");
		    	suiteReportSkipped.append("<table class='table table-bordered'>");
		    	suiteReportSkipped.append("<tr><th colspan='2'>Skipped Results of "+suiteName+" Suite</th></tr>");
		    	suiteReportSkipped.append("<tr><th>Total Tests</th><td>"+suiteTotalTests+"</td></tr>");
		    	suiteReportSkipped.append("<tr><th>Skipped</th><td>"+suiteSkippedTests+"</td></tr>");
		    	suiteReportSkipped.append("</table>");
		    	suiteReportSkipped.append("<table class='table table-bordered'>");
		    	suiteReportSkipped.append("<tr><th>Test Name</th><th>Skipped</th><th>Total Tests</th></tr>");
		    	suiteReportSkipped.append(skippedtextContextSummaryReport);
		    	suiteReportSkipped.append("</table>");
		    	
		    	suiteReportSkipped.append("</div></div>");
		    	suiteReportSkipped.append("<div class='span9'>");
		    	suiteReportSkipped.append(skippedTextContextReport);
		    	suiteReportSkipped.append("</div>");
		    	suiteReportSkipped.append(errorModelWindow);
		    	suiteReportSkipped.append("</div></div></body></html>");
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-skipped.html", suiteReportSkipped.toString());
		    }
		    
		    
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void onStart(ISuite arg0) {
		try{
		    if(mailProp.containsKey("mail.SendMail") && mailProp.getProperty("mail.SendMail").equalsIgnoreCase("ON")){
	    		String suiteName=arg0.getName();
	    		
	    		String ToMail=mailProp.getProperty("mail.to");
    			String CCMail=mailProp.getProperty("mail.cc");
	    		
	    		if(mailProp.containsKey("mail."+arg0.getName()+".to")){
	    			ToMail=mailProp.getProperty("mail."+arg0.getName()+".to");
	    		}
	    		
	    		if(mailProp.containsKey("mail."+arg0.getName()+".cc")){
	    			CCMail=mailProp.getProperty("mail."+arg0.getName()+".cc");
	    		}
    			
	    		StringBuffer subject = new StringBuffer();
	    		subject.append("Suite '"+suiteName+"' Execution Started");
	    		
	    		StringBuffer body = new StringBuffer();
	    		
		    	MailClient mail = new MailClient();
		    	if(CCMail==null || CCMail.equals("")){
		    		mail.sendMail(ToMail.split(","), subject.toString(), body);
		    	}else{
		    		mail.sendMail(ToMail.split(","), subject.toString(), body, CCMail.split(","));
		    	}
		    }
	    }catch(Exception e){
	    	logger.error("Mail sending failed!!");
	    	e.printStackTrace();
	    }
	}
	
	public void zipDir(String dir2zip, ZipOutputStream zos, String dir) 
    {
        try 
        {
           File zipDir = new File(dir2zip); 	           
           String[] dirList = zipDir.list();
           byte[] readBuffer = new byte[2156]; 
           int bytesIn = 0; 
           //System.out.println("dir2zip "+dir2zip);
           //System.out.println("size "+dirList.length);
           for(int i=0; i<dirList.length; i++) 
           {
        	   //System.out.println(dirList[i]);
               File f = new File(zipDir, dirList[i]); 
               if(f.isDirectory()) 
               {	                    
            	   String filePath = f.getPath(); 
            	   zipDir(filePath, zos, dir);
            	   continue; 
               } 
               //System.out.println("File Path "+f.getPath().substring(47,f.getPath().length()));
               String path=f.getPath();
               StringTokenizer str = new StringTokenizer(path, "\\");
               path="";
               int ctr=0;
               while(str.hasMoreTokens()){
            	   if(ctr < 3){
	            	  str.nextToken();
	            	   ctr++;
            	   }else
            		   path+="\\"+str.nextToken();
               }
               //System.out.println(path);
               FileInputStream fis = new FileInputStream(f); 	           
               ZipEntry anEntry = new ZipEntry(path);
               zos.putNextEntry(anEntry);
               while((bytesIn = fis.read(readBuffer)) != -1) 
               { 
            	   zos.write(readBuffer, 0, bytesIn); 
               } 
               
               fis.close();
           }  
       }catch(Exception e){ 
    	   System.out.println("Error while zipping the files!!");
       }
    }
	
	private static final Comparator<ITestResult> TIME_COMPARATOR= new TimeComparator();
	  
	private static class TimeComparator implements Comparator<ITestResult>, Serializable {
		private static final long serialVersionUID = 381775815838366907L;
		public int compare(ITestResult o1, ITestResult o2) {
		  return (int) (o1.getStartMillis() - o2.getStartMillis());
		}
	} 
	
	
	private StringBuffer testDetailReport(List<ITestResult> testResults){
		StringBuffer resultsStringBuffer = new StringBuffer();
		int i=1;
		for (ITestResult eachTestResult : testResults) {
			
			String testNamePrint=null;
	    	String testCasePath=null;
	    	if(eachTestResult.getAttribute("reporterFileName")==null){
	    		testNamePrint=eachTestResult.getName();
	    	}else{
	    		testNamePrint=eachTestResult.getAttribute("reporterFileName").toString();
	    	}
	    	
	    	if(eachTestResult.getAttribute("reporterFilePath")!=null){
	    		testCasePath=eachTestResult.getAttribute("reporterFilePath").toString();
	    	}
			
	    	resultsStringBuffer.append("<tr class='"+((eachTestResult.getStatus()==ITestResult.SUCCESS) ? "success" : (eachTestResult.getStatus()==ITestResult.FAILURE ? "error" : "warning") )+"'>");
	    	
	    	resultsStringBuffer.append("<td>"+i+"</td>");
	    	if(testCasePath==null){
	    		resultsStringBuffer.append("<td>"+testNamePrint+"</td>");
	    	}else{
	    		resultsStringBuffer.append("<td><a href='"+testCasePath+"'>"+testNamePrint+"</a></td>");
	    	}
	    	
	    	resultsStringBuffer.append("<td>"+startTimeForResult(eachTestResult)+"</td>");
	    	resultsStringBuffer.append("<td>"+endTimeForResult(eachTestResult)+"</td>");
	    	resultsStringBuffer.append("<td>"+tikeTakenForResult(eachTestResult)+"</td>");
	    	String testStatus=statusForResult(eachTestResult);
	    	String errorMessage=null;
	    	
	    	if(eachTestResult.getStatus() != ITestResult.SUCCESS){
	    		
	    		if(eachTestResult.getThrowable()!= null) {
					String[] stackTraces = Utils.stackTrace(eachTestResult.getThrowable(), true);
			        errorMessage = stackTraces[1];
				}
	    		
	    	}
	    	resultsStringBuffer.append("<td>"+(eachTestResult.getStatus()== ITestResult.SUCCESS ? testStatus : "<a href='#myModal' role='button' class='openDialog btn' data-toggle='modal' data-showthismessage='"+(errorMessage!=null ? errorMessage : "")+" "+(eachTestResult.getAttribute("screenshot")!=null? "<a href=\"screenshots"+File.separator+eachTestResult.getAttribute("screenshot").toString()+"\">Screenshot</a>" : "")+" '>"+testStatus+"</a>") +"</td>");
	    	
	    	
	    	resultsStringBuffer.append("</tr>");
    		i++;
    	}
		return resultsStringBuffer;
	}
	
	private static String startTimeForResult(ITestResult eachTestResult){
		return Commons.sdfTests.format(eachTestResult.getStartMillis());
	}
	
	private static String endTimeForResult(ITestResult eachTestResult){
		return Commons.sdfTests.format(eachTestResult.getEndMillis());
	}
	
	private static String tikeTakenForResult(ITestResult eachTestResult){
		return Commons.timeTaken(eachTestResult.getEndMillis()-eachTestResult.getStartMillis());
	}
	
	private static String statusForResult(ITestResult eachTestResult){
		return Commons.getStatusString(eachTestResult.getStatus());
	}
	
	protected void generateFailureSuite(XmlSuite xmlSuite, ISuite suite, String outputDir) {
	    XmlSuite failedSuite = (XmlSuite) xmlSuite.clone();
	    failedSuite.setName(xmlSuite.getName());
	    m_xmlSuite= failedSuite;
	    Map<String, XmlTest> xmlTests= Maps.newHashMap();
	    for(XmlTest xmlT: xmlSuite.getTests()) {
	      xmlTests.put(xmlT.getName(), xmlT);
	    }
	    
	    Map<String, ISuiteResult> results = suite.getResults();

	    for(Map.Entry<String, ISuiteResult> entry : results.entrySet()) {
	      ISuiteResult suiteResult = entry.getValue();
	      ITestContext testContext = suiteResult.getTestContext();

	      generateXmlTest(suite,
	                      xmlTests.get(testContext.getName()),
	                      testContext,
	                      testContext.getFailedTests().getAllResults(),
	                      testContext.getSkippedTests().getAllResults());
	    }

	    if(null != failedSuite.getTests() && failedSuite.getTests().size() > 0) {
	      Utils.writeUtf8File(outputDir, suite.getName()+"-failed.xml", failedSuite.toXml());
	    }
	}
	

	
	private void generateXmlTest(ISuite suite,  XmlTest xmlTest, ITestContext context, Collection<ITestResult> failedTests, Collection<ITestResult> skippedTests) {
		// Note:  we can have skipped tests and no failed tests
		// if a method depends on nonexistent groups
		if (skippedTests.size() > 0 || failedTests.size() > 0) {
			Set<ITestNGMethod> methodsToReRun = Sets.newHashSet();
			
			// Get the transitive closure of all the failed methods and the methods
			// they depend on
			Collection[] allTests = new Collection[] {failedTests, skippedTests};
			
			for (Collection<ITestResult> tests : allTests) {
				for (ITestResult failedTest : tests) {
					ITestNGMethod current = failedTest.getMethod();
					if (current.isTest()) {
						methodsToReRun.add(current);
						ITestNGMethod method = failedTest.getMethod();
						// Don't count configuration methods
						if (method.isTest()) {
							List<ITestNGMethod> methodsDependedUpon =MethodHelper.getMethodsDependedUpon(method,
							context.getAllTestMethods());
				
							for (ITestNGMethod m : methodsDependedUpon) {
								if (m.isTest()) {
									methodsToReRun.add(m);
								}
							}
						}
					}
				}
			}
			List<ITestNGMethod> result = Lists.newArrayList();
			for (ITestNGMethod m : context.getAllTestMethods()) {
				if (methodsToReRun.contains(m)) {
					result.add(m);
				}
			}
			
			methodsToReRun.clear();
			Collection<ITestNGMethod> invoked= suite.getInvokedMethods();
			for(ITestNGMethod tm: invoked) {
				if(!tm.isTest()) {
					methodsToReRun.add(tm);
				}
			}				
			result.addAll(methodsToReRun);
			createXmlTest(context, result, xmlTest);
		}
	}
	
	 private void createXmlTest(ITestContext context, List<ITestNGMethod> methods, XmlTest srcXmlTest) {
		    XmlTest xmlTest = new XmlTest(m_xmlSuite);
		    xmlTest.setName(context.getName());
		    xmlTest.setBeanShellExpression(srcXmlTest.getExpression());
		    xmlTest.setIncludedGroups(srcXmlTest.getIncludedGroups());
		    xmlTest.setExcludedGroups(srcXmlTest.getExcludedGroups());
		    xmlTest.setParallel(srcXmlTest.getParallel());
		    xmlTest.setJUnit(srcXmlTest.isJUnit());
		    List<XmlClass> xmlClasses = createXmlClasses(methods, srcXmlTest);
		    xmlTest.setXmlClasses(xmlClasses);
	}

	private List<XmlClass> createXmlClasses(List<ITestNGMethod> methods, XmlTest srcXmlTest) {
	    List<XmlClass> result = Lists.newArrayList();
	    Map<Class, Set<ITestNGMethod>> methodsMap= Maps.newHashMap();

	    for (ITestNGMethod m : methods) {
	      Object[] instances= m.getInstances();
	      Class clazz= instances == null || instances.length == 0 || instances[0] == null
	          ? m.getRealClass()
	          : instances[0].getClass();
	      Set<ITestNGMethod> methodList= methodsMap.get(clazz);
	      if(null == methodList) {
	        methodList= new HashSet<ITestNGMethod>();
	        methodsMap.put(clazz, methodList);
	      }
	      methodList.add(m);
	    }

	    // Ideally, we should preserve each parameter in each class but putting them
	    // all in the same bag for now
	    Map<String, String> parameters = Maps.newHashMap();
	    for (XmlClass c : srcXmlTest.getClasses()) {
	      parameters.putAll(c.getLocalParameters());
	    }

	    int index = 0;
	    for(Map.Entry<Class, Set<ITestNGMethod>> entry: methodsMap.entrySet()) {
	      Class clazz= entry.getKey();
	      Set<ITestNGMethod> methodList= entry.getValue();
	      XmlClass xmlClass= new XmlClass(clazz.getName(), index++, false /* don't load classes */);
	      List<XmlInclude> methodNames= Lists.newArrayList(methodList.size());
	      int ind = 0;
	      for(ITestNGMethod m: methodList) {
	        methodNames.add(new XmlInclude(m.getMethod().getName(), m.getFailedInvocationNumbers(),ind++));
	      }
	      
	      xmlClass.setIncludedMethods(methodNames);
	      
	      
	      
	      xmlClass.setParameters(parameters);
	      result.add(xmlClass);

	    }

	    return result;
	}
	
}
