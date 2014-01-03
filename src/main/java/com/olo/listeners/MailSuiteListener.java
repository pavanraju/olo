package com.olo.listeners;

import static com.olo.propertyutil.MailProperties.mailProp;

import java.io.File;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.internal.Utils;

import com.olo.mailer.MailClient;
import com.olo.reporter.Utility;
import com.olo.util.Commons;

public class MailSuiteListener implements ISuiteListener{
	
	private static final Logger logger = LogManager.getLogger(MailSuiteListener.class.getName());
	
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
			    StringBuffer currentTextContextDetailedReport = Utility.contextDetailedReport(suiteTestContext, true);
			    textContextSummaryAndDetailedReport.append(currentTextContextDetailedReport);
			    
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
			suiteReportDetailed.append(Utility.backToSuitesSummaryLink(true));
			suiteReportDetailed.append(Utility.suiteIndexLink(suiteName, true));
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
			suiteReportDetailed.append(errorModelWindow);
			suiteReportDetailed.append(Utility.endRow());
			suiteReportDetailed.append(Utility.endContainer());
			suiteReportDetailed.append(Utility.getCustomJs());
			suiteReportDetailed.append(Utility.endBodyAndHtml());
			String fileName = "mail-suite-"+suiteName+"-index.html";
		    Utils.writeFile(suite.getOutputDirectory(), fileName, suiteReportDetailed.toString());
		    
		    try{
	    		String toMail=mailProp.getProperty("mail.to");
    			String CCMail=mailProp.getProperty("mail.cc");
	    		if(mailProp.containsKey("mail."+suiteName+".to")){
	    			toMail=mailProp.getProperty("mail."+suiteName+".to");
	    		}
	    		if(mailProp.containsKey("mail."+suiteName+".cc")){
	    			CCMail=mailProp.getProperty("mail."+suiteName+".cc");
	    		}
    			
	    		StringBuffer subject = new StringBuffer();
	    		subject.append("Suite '"+suiteName+"' Execution Completed - Total : "+suiteTotalTests+"; Passed : "+suitePassedTests+"; Failed : "+suiteFailedTests+"; Skipped : "+suiteSkippedTests);
	    		StringBuffer body = new StringBuffer();
	    		body.append("Execution Report for '"+suiteName+"' suite is in below mentioned location.<br/><br/>Ip Address : "+Commons.getSystemIpAddress()+"<br/>Folder Path : "+suite.getOutputDirectory());
		    	MailClient mail = new MailClient();
		    	if(CCMail==null || CCMail.equals("")){
		    		mail.sendMail(toMail.split(","), subject.toString(), body,suite.getOutputDirectory()+File.separator+fileName);
		    	}else{
		    		mail.sendMail(toMail.split(","), subject.toString(), body, CCMail.split(","),suite.getOutputDirectory()+File.separator+fileName);
		    	}
		    	logger.info("Mail sent on suite completed '"+suiteName+"'");
		    }catch(Exception e){
		    	logger.error("Mail sending failed!! "+e.getMessage());
		    }
		    
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void onStart(ISuite suite) {
		try{
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
    		body.append("Execution Report for '"+suiteName+"' suite is in below mentioned location.<br/><br/>Ip Address : "+Commons.getSystemIpAddress()+"<br/>Folder Path : "+suite.getOutputDirectory());
	    	MailClient mail = new MailClient();
	    	if(CCMail==null || CCMail.equals("")){
	    		mail.sendMail(ToMail.split(","), subject.toString(), body);
	    	}else{
	    		mail.sendMail(ToMail.split(","), subject.toString(), body, CCMail.split(","));
	    	}
	    	logger.info("Mail sent on suite start '"+suiteName+"'");
	    }catch(Exception e){
	    	logger.error("Mail sending failed!! "+e.getMessage());
	    }
	}
	
}
