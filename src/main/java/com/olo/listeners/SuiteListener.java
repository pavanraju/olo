package com.olo.listeners;

import static com.olo.util.PropertyReader.mailProp;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.olo.mailer.MailClient;
import com.olo.reporter.Utility;

public class SuiteListener implements ISuiteListener{
	
	private static final Logger logger = LogManager.getLogger(SuiteListener.class.getName());
	
	private XmlSuite m_xmlSuite;
	
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
			StringBuffer textContextReport = new StringBuffer();
			StringBuffer mailTextContextReport = new StringBuffer();
			StringBuffer suiteContextSummaryReport = new StringBuffer();
			StringBuffer mailSuiteContextSummaryReport = new StringBuffer();
			StringBuffer passedtextContextSummaryReport = new StringBuffer();
			StringBuffer failedtextContextSummaryReport = new StringBuffer();
			StringBuffer skippedtextContextSummaryReport = new StringBuffer();
			
			StringBuffer passedTextContextReport = new StringBuffer();
			StringBuffer failedTextContextReport = new StringBuffer();
			StringBuffer skippedTextContextReport = new StringBuffer();
			
			StringBuffer errorModelWindow = new StringBuffer();
			//errorModelWindow.append("<div id='myModal' class='modal hide fade' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>  <div class='modal-header'>   <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>   <h4 id='myModalLabel'>Error Details</h4>  </div>  <div class='modal-body'>   <p id='modelbodyerror'></p>  </div>	</div>");
			errorModelWindow.append("<div class='modal fade' id='myModal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>");
			errorModelWindow.append("<div class='modal-dialog'>");
			errorModelWindow.append("<div class='modal-content'>");
			errorModelWindow.append("<div class='modal-header'><button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button><h4 class='modal-title'>Error Details</h4></div>");
			errorModelWindow.append("<div class='modal-body'><p id='modelbodyerror'></p></div>");
			errorModelWindow.append("</div>");
			errorModelWindow.append("</div>");
			errorModelWindow.append("</div>");
			
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
				String contextStartTime=Utility.sdf.format(suiteTestContext.getStartDate().getTime());
				String contextEndTime=Utility.sdf.format(suiteTestContext.getEndDate().getTime());
				String contextTimeTaken=Utility.timeTaken(suiteTestContext.getEndDate().getTime()-suiteTestContext.getStartDate().getTime());
				int contextTotalTests=contextPassedTests+contextFailedTests+contextSkippedTests;
			    
				suiteContextSummaryReport.append("<tr><td><a href=#"+suiteTestContext.getName()+">"+suiteTestContext.getName()+"</a></td><td class='success'>"+contextPassedTests+"</td><td class='danger'>"+contextFailedTests+"</td><td class='warning'>"+contextSkippedTests+"</td><th>"+contextTotalTests+"</th></tr>");
				mailSuiteContextSummaryReport.append("<tr><td>"+suiteTestContext.getName()+"</td><td class='success'>"+contextPassedTests+"</td><td class='danger'>"+contextFailedTests+"</td><td class='warning'>"+contextSkippedTests+"</td><th>"+contextTotalTests+"</th></tr>");
			    /**
			     * All Context
			     */
			    StringBuffer textContextSummary = new StringBuffer();
			    textContextSummary.append("<div class='col-md-6'><table class='table table-bordered' id='"+suiteTestContext.getName()+"'><tr><th>Test</th><td>"+suiteTestContext.getName()+"</td></tr>");
			    textContextSummary.append("<tr><th>Start Time</th><td>"+contextStartTime+"</td></tr>");
			    textContextSummary.append("<tr><th>End Time</th><td>"+contextEndTime+"</td></tr>");
			    textContextSummary.append("<tr><th>Time Taken</th><td>"+contextTimeTaken+"</td></tr>");
			    textContextSummary.append("</table></div>");
			    textContextReport.append(textContextSummary);
			    textContextReport.append(Utility.contextDetailedReport(suiteTestContext, false));
			    mailTextContextReport.append(textContextSummary);
			    mailTextContextReport.append(Utility.contextDetailedReport(suiteTestContext, true));
			    
			    /**
			     * Passed Context
			     */
			    
			    if(contextPassedTests>0){
			    	passedtextContextSummaryReport.append("<tr><td><a href=#"+suiteTestContext.getName()+">"+suiteTestContext.getName()+"</a></td><td class='success'>"+contextPassedTests+"</td></tr>");
			    	passedTextContextReport.append(Utility.passedContextDetailedReport(suiteTestContext));
			    }
			    
			    /**
			     * Failed Context
			     */
			    
			    if(contextFailedTests>0){
			    	failedtextContextSummaryReport.append("<tr><td><a href=#"+suiteTestContext.getName()+">"+suiteTestContext.getName()+"</a></td><td class='danger'>"+contextFailedTests+"</td></tr>");
			    	failedTextContextReport.append(Utility.failedContextDetailedReport(suiteTestContext));
			    }
			    
			    /**
			     * Skipped Context
			     */
			    
			    if(contextSkippedTests>0){
			    	skippedtextContextSummaryReport.append("<tr><td><a href=#"+suiteTestContext.getName()+">"+suiteTestContext.getName()+"</a></td><td class='warning'>"+contextSkippedTests+"</td></tr>");
			    	skippedTextContextReport.append(Utility.skippedContextDetailedReport(suiteTestContext));
			    }
			    
			}
			
			suiteTotalTests=suiteFailedTests+suitePassedTests+suiteSkippedTests;
			
			/**
		     * Writing suite results
		     */
			
			StringBuffer suiteReport = new StringBuffer();
			suiteReport.append("<!DOCTYPE html><html><head><title>"+suiteName+" Suite Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+"<script type='text/javascript'>$( document ).ready(function() {    $(document).on('click', '.openDialog', function () {  var myBookId = $(this).data('showthismessage');   $('.modal-body #modelbodyerror').html( myBookId ); });  });</script></head><body>");
			
			suiteReport.append("<div class='navbar navbar-fixed-top' role='navigation'>");
			suiteReport.append("<ol class='breadcrumb'>");
			suiteReport.append("<li><a href='../suites-summary-index.html'>Suite Summary</a></li>");
			suiteReport.append("<li><a href='suite-"+suiteName+"-index.html'>"+suiteName+"</a></li>");
			suiteReport.append("<li class='active'>All&nbsp;</li>");
			suiteReport.append("<div class='btn-group'>");
			suiteReport.append("<button type='button' class='btn btn-default btn-sm dropdown-toggle' data-toggle='dropdown'><span class='glyphicon glyphicon-filter'></span> <span class='caret'></span></button>");
			suiteReport.append("<ul class='dropdown-menu' role='menu'>");
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
            suiteReport.append("</ol>");
            suiteReport.append("</div>");
			
			suiteReport.append("<div class='container' style='margin-top:60px;'>");
			suiteReport.append("<div class='row'>");
			suiteReport.append("<div class='col-md-4'><div class='affix'>");
			suiteReport.append("<table class='table table-bordered table-condensed'>");
			suiteReport.append("<tr><th>Suite</th><td>"+suiteName+"</td></tr>");
			suiteReport.append("<tr><th>Start Time</th><td>"+Utility.sdf.format(suiteStartTime)+"</td></tr>");
			suiteReport.append("<tr><th>End Time</th><td>"+Utility.sdf.format(suiteEndTime)+"</td></tr>");
			suiteReport.append("<tr><th>Time Taken</th><td>"+Utility.timeTaken(suiteEndTime-suiteStartTime)+"</td></tr>");
			suiteReport.append("</table>");
			
			suiteReport.append("<table class='table table-bordered table-condensed'>");
			suiteReport.append(Utility.suiteContextSummaryHeader());
			suiteReport.append(suiteContextSummaryReport);
			suiteReport.append(Utility.suiteContextSummaryFooter(suiteTotalTests, suitePassedTests, suiteFailedTests, suiteSkippedTests));
			suiteReport.append("</table>");
			
			suiteReport.append("</div></div>");
			suiteReport.append("<div class='col-md-8'>");
			suiteReport.append(textContextReport);
			suiteReport.append("</div>");
			suiteReport.append(errorModelWindow);
			suiteReport.append("</div></div>"+Utility.getDescriptionTooltipJs()+"</body></html>");
		    Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-index.html", suiteReport.toString());
		    
		    try{
			    if(mailProp.containsKey("mail.SendMail") && mailProp.getProperty("mail.SendMail").equalsIgnoreCase("true")){
		    		
		    		String ToMail=mailProp.getProperty("mail.to");
	    			String CCMail=mailProp.getProperty("mail.cc");
		    		if(mailProp.containsKey("mail."+suiteName+".to")){
		    			ToMail=mailProp.getProperty("mail."+suiteName+".to");
		    		}
		    		if(mailProp.containsKey("mail."+suiteName+".cc")){
		    			CCMail=mailProp.getProperty("mail."+suiteName+".cc");
		    		}
	    			
		    		StringBuffer subject = new StringBuffer();
		    		subject.append("Suite '"+suiteName+"' Execution Completed - Total : "+suiteTotalTests+"; Passed : "+suitePassedTests+"; Failed : "+suiteFailedTests+"; Skipped : "+suiteSkippedTests);
		    		StringBuffer body = new StringBuffer();
		    		body.append(Utility.getHtmlToHead());
		    		body.append(Utility.mailSuiteSummaryHead());
		    		body.append(Utility.endHeadAndStartBody());
		    		body.append(Utility.startContainer());
		    		body.append(Utility.startRow());
		    		body.append("<div>");
		    		body.append(Utility.startTable());
		    		body.append("<tr><th>Suite</th><td>"+suiteName+"</td></tr>");
		    		body.append("<tr><th>Start Time</th><td>"+Utility.sdf.format(suiteStartTime)+"</td></tr>");
		    		body.append("<tr><th>End Time</th><td>"+Utility.sdf.format(suiteEndTime)+"</td></tr>");
		    		body.append("<tr><th>Time Taken</th><td>"+Utility.timeTaken(suiteEndTime-suiteStartTime)+"</td></tr>");
		    		body.append(Utility.endTable());
		    		body.append(Utility.startTable());
		    		body.append(Utility.suiteContextSummaryHeader());
		    		body.append(mailSuiteContextSummaryReport);
		    		body.append(Utility.suiteContextSummaryFooter(suiteTotalTests, suitePassedTests, suiteFailedTests, suiteSkippedTests));
		    		body.append(Utility.endTable());
		    		body.append("</div>");
		    		
		    		body.append("<div>");
		    		body.append(mailTextContextReport);
		    		body.append("</div>");
		    		
		    		body.append(Utility.endRow());
		    		body.append(Utility.endContainerToHtml());
		    		body.append("Execution Report for '"+suiteName+"' suite is in below mentioned location.<br/><br/>"+suite.getOutputDirectory());
			    	MailClient mail = new MailClient();
			    	if(CCMail==null || CCMail.equals("")){
			    		mail.sendMail(ToMail.split(","), subject.toString(), body);
			    	}else{
			    		mail.sendMail(ToMail.split(","), subject.toString(), body, CCMail.split(","));
			    	}
			    }
		    }catch(Exception e){
		    	logger.error("Mail sending failed!! "+e.getMessage());
		    }
		    
		    /**
		     * Writing suite passed results
		     */
		    if(suitePassedTests>0){
		    	StringBuffer suiteReportPassed = new StringBuffer();
		    	suiteReportPassed.append("<!DOCTYPE html><html><head><title>"+suiteName+" Passed Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+"<script type='text/javascript'>$( document ).ready(function() {    $(document).on('click', '.openDialog', function () {  var myBookId = $(this).data('showthismessage');   $('.modal-body #modelbodyerror').html( myBookId ); });  });</script></head><body>");
		    	
		    	suiteReportPassed.append("<div class='navbar navbar-fixed-top' role='navigation'>");
		    	suiteReportPassed.append("<ol class='breadcrumb'>");
		    	suiteReportPassed.append("<li><a href='../suites-summary-index.html'>Suite Summary</a></li>");
		    	suiteReportPassed.append("<li><a href='suite-"+suiteName+"-index.html'>"+suiteName+"</a></li>");
		    	suiteReportPassed.append("<li class='active'>Passed&nbsp;</li>");
		    	suiteReportPassed.append("<div class='btn-group'>");
		    	suiteReportPassed.append("<button type='button' class='btn btn-default btn-sm dropdown-toggle' data-toggle='dropdown'><span class='glyphicon glyphicon-filter'></span> <span class='caret'></span></button>");
		    	suiteReportPassed.append("<ul class='dropdown-menu' role='menu'>");
		    	suiteReportPassed.append("<li><a href='suite-"+suiteName+"-index.html'>All</a></li>");
	            if(suiteFailedTests>0){
	            	suiteReportPassed.append("<li><a href='suite-"+suiteName+"-failed.html'>Failed</a></li>");
	            }
	            if(suiteSkippedTests>0){
	            	suiteReportPassed.append("<li><a href='suite-"+suiteName+"-skipped.html'>Skipped</a></li>");
	            }
	            suiteReportPassed.append("</ul>");
	            suiteReportPassed.append("</div>");
	            suiteReportPassed.append("</ol>");
	            suiteReportPassed.append("</div>");
	            
		    	suiteReportPassed.append("<div class='container' style='margin-top:60px;'>");
	            suiteReportPassed.append("<div class='row'>");
	            suiteReportPassed.append("<div class='col-md-3'><div class='affix'>");
		    	suiteReportPassed.append("<table class='table table-bordered table-condensed'>");
		    	suiteReportPassed.append("<tr><th>Suite</th><td>"+suiteName+"</td></tr>");
				suiteReportPassed.append("</table>");
				suiteReportPassed.append("<table class='table table-bordered table-condensed'>");
				suiteReportPassed.append("<tr><th>Test</th><th>Passed</th></tr>");
				suiteReportPassed.append(passedtextContextSummaryReport);
				suiteReportPassed.append("<tr><th>Total</th><th class='success'>"+suitePassedTests+"</th></tr>");
				suiteReportPassed.append("</table>");
				
				suiteReportPassed.append("</div></div>");
				suiteReportPassed.append("<div class='col-md-9'>");
				suiteReportPassed.append(passedTextContextReport);
				suiteReportPassed.append("</div>");
				suiteReportPassed.append(errorModelWindow);
				suiteReportPassed.append("</div></div>"+Utility.getDescriptionTooltipJs()+"</body></html>");
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-passed.html", suiteReportPassed.toString());
		    }
		    
		    /**
		     * Writing suite failed results
		     */
		    if(suiteFailedTests>0){
		    	StringBuffer suiteReportFailed = new StringBuffer();
		    	suiteReportFailed.append("<!DOCTYPE html><html><head><title>"+suiteName+" Failed Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+"<script type='text/javascript'>$( document ).ready(function() {    $(document).on('click', '.openDialog', function () {  var myBookId = $(this).data('showthismessage');   $('.modal-body #modelbodyerror').html( myBookId ); });  });</script></head><body>");
		    	
		    	suiteReportFailed.append("<div class='navbar navbar-inverse navbar-fixed-top'>");
		    	suiteReportFailed.append("<ul class='breadcrumb'>");
		    	suiteReportFailed.append("<li><a href='../suites-summary-index.html'>Suite Summary</a> <span class='divider'>/</span></li>");
		    	suiteReportFailed.append("<li><a href='suite-"+suiteName+"-index.html'>"+suiteName+"</a> <span class='divider'>/</span></li>");
		    	suiteReportFailed.append("<li class='active'>Failed<span class='divider'></span></li>");
		    	suiteReportFailed.append("<div class='btn-group'>");
		    	suiteReportFailed.append("<button class='btn dropdown-toggle' data-toggle='dropdown'><i class='icon-filter'></i> <span class='caret'></span></button>");
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
		    	suiteReportFailed.append("</div>");
		    	
		    	suiteReportFailed.append("<div class='container-fluid' style='margin-top:60px;'>");
	            suiteReportFailed.append("<div class='row-fluid'>");
		    	suiteReportFailed.append("<div class='span3'><div class='affix span3'>");
		    	suiteReportFailed.append("<table class='table table-bordered table-condensed'>");
		    	suiteReportFailed.append("<tr><th>Suite</th><td>"+suiteName+"</td></tr>");
		    	suiteReportFailed.append("</table>");
		    	suiteReportFailed.append("<table class='table table-bordered table-condensed'>");
		    	suiteReportFailed.append("<tr><th>Test</th><th>Failed</th></tr>");
		    	suiteReportFailed.append(failedtextContextSummaryReport);
		    	suiteReportFailed.append("<tr><th>Total</th><th class='danger'>"+suiteFailedTests+"</th></tr>");
		    	suiteReportFailed.append("</table>");
		    	
		    	suiteReportFailed.append("</div></div>");
		    	suiteReportFailed.append("<div class='span9'>");
		    	suiteReportFailed.append(failedTextContextReport);
		    	suiteReportFailed.append("</div>");
		    	suiteReportFailed.append(errorModelWindow);
		    	suiteReportFailed.append("</div></div>"+Utility.getDescriptionTooltipJs()+"</body></html>");
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-failed.html", suiteReportFailed.toString());
		    }
		    
		    /**
		     * Writing suite skipped results
		     */
		    if(suiteSkippedTests>0){
		    	StringBuffer suiteReportSkipped = new StringBuffer();
		    	suiteReportSkipped.append("<!DOCTYPE html><html><head><title>"+suiteName+" Skipped Results</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+"<script type='text/javascript'>$( document ).ready(function() {    $(document).on('click', '.openDialog', function () {  var myBookId = $(this).data('showthismessage');   $('.modal-body #modelbodyerror').html( myBookId ); });  });</script></head><body>");
		    	
		    	suiteReportSkipped.append("<div class='navbar navbar-inverse navbar-fixed-top'>");
		    	suiteReportSkipped.append("<ul class='breadcrumb'>");
		    	suiteReportSkipped.append("<li><a href='../suites-summary-index.html'>Suite Summary</a> <span class='divider'>/</span></li>");
		    	suiteReportSkipped.append("<li><a href='suite-"+suiteName+"-index.html'>"+suiteName+"</a> <span class='divider'>/</span></li>");
		    	suiteReportSkipped.append("<li class='active'>Skipped<span class='divider'></span></li>");
		    	suiteReportSkipped.append("<div class='btn-group'>");
				suiteReportSkipped.append("<button class='btn dropdown-toggle' data-toggle='dropdown'><i class='icon-filter'></i> <span class='caret'></span></button>");
				suiteReportSkipped.append("<ul class='dropdown-menu'>");
				suiteReportSkipped.append("<li><a href='suite-"+suiteName+"-index.html'>All</a></li>");
	            if(suitePassedTests>0){
	            	suiteReportSkipped.append("<li><a href='suite-"+suiteName+"-passed.html'>Passed</a></li>");
	            }
	            if(suiteFailedTests>0){
	            	suiteReportSkipped.append("<li><a href='suite-"+suiteName+"-failed.html'>Failed</a></li>");
	            }
	            suiteReportSkipped.append("</ul>");
	            suiteReportSkipped.append("</div>");
	            suiteReportSkipped.append("</ul>");
		    	suiteReportSkipped.append("</div>");
		    	
		    	suiteReportSkipped.append("<div class='container-fluid' style='margin-top:60px;'>");
	            suiteReportSkipped.append("<div class='row-fluid'>");
		    	suiteReportSkipped.append("<div class='span3'><div class='affix span3'>");
		    	suiteReportSkipped.append("<table class='table table-bordered table-condensed'>");
		    	suiteReportSkipped.append("<tr><th>Suite</th><td>"+suiteName+"</td></tr>");
		    	suiteReportSkipped.append("</table>");
		    	suiteReportSkipped.append("<table class='table table-bordered table-condensed'>");
		    	suiteReportSkipped.append("<tr><th>Test</th><th>Skipped</th></tr>");
		    	suiteReportSkipped.append(skippedtextContextSummaryReport);
		    	suiteReportSkipped.append("<tr><th>Total</th><th class='warning'>"+suiteSkippedTests+"</th></tr>");
		    	suiteReportSkipped.append("</table>");
		    	
		    	suiteReportSkipped.append("</div></div>");
		    	suiteReportSkipped.append("<div class='span9'>");
		    	suiteReportSkipped.append(skippedTextContextReport);
		    	suiteReportSkipped.append("</div>");
		    	suiteReportSkipped.append(errorModelWindow);
		    	suiteReportSkipped.append("</div></div>"+Utility.getDescriptionTooltipJs()+"</body></html>");
				Utils.writeFile(suite.getOutputDirectory(), "suite-"+suiteName+"-skipped.html", suiteReportSkipped.toString());
		    }
		    
		    
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.getStackTrace());
		}
	}
	
	public void onStart(ISuite suite) {
		try{
		    if(mailProp.containsKey("mail.SendMail") && mailProp.getProperty("mail.SendMail").equalsIgnoreCase("true")){
	    		String suiteName=suite.getName();
	    		
	    		String ToMail=mailProp.getProperty("mail.to");
    			String CCMail=mailProp.getProperty("mail.cc");
	    		if(mailProp.containsKey("mail."+suiteName+".to")){
	    			ToMail=mailProp.getProperty("mail."+suiteName+".to");
	    		}
	    		if(mailProp.containsKey("mail."+suiteName+".cc")){
	    			CCMail=mailProp.getProperty("mail."+suiteName+".cc");
	    		}
    			
	    		StringBuffer subject = new StringBuffer();
	    		subject.append("Suite '"+suiteName+"' Execution Started");
	    		StringBuffer body = new StringBuffer();
	    		body.append("Execution Report for '"+suiteName+"' suite will be in below mentioned location.<br/><br/>"+suite.getOutputDirectory());
		    	MailClient mail = new MailClient();
		    	if(CCMail==null || CCMail.equals("")){
		    		mail.sendMail(ToMail.split(","), subject.toString(), body);
		    	}else{
		    		mail.sendMail(ToMail.split(","), subject.toString(), body, CCMail.split(","));
		    	}
		    }
	    }catch(Exception e){
	    	logger.error("Mail sending failed!! "+e.getMessage());
	    }
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
