package com.olo.reporter;

import static com.olo.util.PropertyReader.configProp;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.Utils;

import com.olo.util.Commons;
import com.olo.util.VerificationError;
import com.olo.util.VerificationErrorsInTest;

public class Utility {
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss");
	public static final SimpleDateFormat sdfTests = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss.SSS");
	public static final NumberFormat formatter = new DecimalFormat("#,##0.0");
	
	public static final LinkedHashMap<String, String> testCaseReportColumns = new LinkedHashMap<String, String>(){
		private static final long serialVersionUID = 1L;

			{
				put("action", "Action");
		        put("propertyName", "Web Element");
		        put("propertyValue", "Element Locator");
		        put("value", "Value");
		        put("actualValue", "Actual Value");
		        put("options", "Options");
		        put("startTime", "Start Time");
		        put("endTime", "End Time");
		        put("timeTaken", "Time Taken");
		        put("status", "Status");
		        put("exception", "Exception");
		        put("screenShot", "ScreenShot");

			}
	};
	
	public static String getStatusString(int testResultStatus) {
		switch (testResultStatus) {
			case ITestResult.SUCCESS:
				return "PASS";
			case ITestResult.FAILURE:
				return "FAIL";
			case ITestResult.SKIP:
				return "SKIP";
			case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
				return "SUCCESS_PERCENTAGE_FAILURE";
		}
		return null;
	}
	
	public static Comparator<ISuite> suiteStartComp = new Comparator<ISuite>() {
		public int compare(ISuite o1, ISuite o2) {
			if (Long.valueOf(o1.getAttribute("suiteStartTime_sort").toString()) > Long.valueOf(o2.getAttribute("suiteStartTime_sort").toString()))
				return 1;
			else if (Long.valueOf(o1.getAttribute("suiteStartTime_sort").toString()) < Long.valueOf(o2.getAttribute("suiteStartTime_sort").toString()))
				return -1;
			else	
				return 0;
		}
	};
	
	public static String timeTaken(long timeDifference) {

		String timeTaken = "";
		if (timeDifference < 1000) {
			timeTaken = timeDifference + " ms";
		} else if (timeDifference < 60000) {
			timeTaken = formatter.format((double) timeDifference / 1000)
					+ " sec";
		} else if (timeDifference < 3600000) {
			timeTaken = timeDifference
					/ 60000
					+ " mins "
					+ formatter
							.format((double) (timeDifference % 60000) / 1000)
					+ " sec";
		} else {
			timeTaken = (timeDifference / 3600000)
					+ " hrs "
					+ ((timeDifference % 3600000) / 60000)
					+ " mins "
					+ formatter
							.format((double) (timeDifference % 60000) / 1000)
					+ " sec";
		}
		return timeTaken;
	}
	
	public static String getBootstrapCss(){
		return "<link rel=\"stylesheet\" href=\"http://netdna.bootstrapcdn.com/bootstrap/3.0.1/css/bootstrap.min.css\">";
	}
	
	public static String getBootstrapJs(){
		return "<script src=\"http://netdna.bootstrapcdn.com/bootstrap/3.0.1/js/bootstrap.min.js\"></script>";
	}
	
	public static String getJqueryJs(){
		return "<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>";
	}
	
	public static String getInlineCss(){
		return "<style type='text/css'>.ifskipped{background-color: #d6e1c9;}@media screen and (min-width: 768px) {.modal-dialog {width: 950px;}}@media(max-width:767px){.side-affix {position: static; width: auto; top: 0;}}</style>";
	}
	
	public static String getMailCss(){
		return "<style type='text/css'>table{border:1px solid #808080;border-collapse:collapse;} table td{border:1px solid #808080} table th{border:1px solid #808080;background-color:#C0C0C0;} tr.success{background-color: #dff0d8;} tr.error{background-color: #f2dede;} tr.warning{background-color: #fcf8e3;}</style>";
	}
	
	public static String getGoogleChartsJs(){
		return "<script type='text/javascript' src='http://www.google.com/jsapi'></script>";
	}
	
	public static String getGooglePieChart(){
		return "<script type='text/javascript'>google.load('visualization', '1', {packages: ['corechart']}); </script>";
	}
	
	public static String getMetaInfo(){
		return "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
	}
	
	public static String getDescriptionTooltipJs(){
		return "<script type='text/javascript'>$(document).ready(function() { $('.testNameToolTip').tooltip({html: true}); });</script>";
	}
	
	public static String getModelJs(){
		return "<script type='text/javascript'>$( document ).ready(function() {    $(document).on('click', '.openDialog', function () {  var errorDetails = $(this).data('showthismessage');   $('.modal-body').html( errorDetails ); });  });</script>";
	}
	
	public static StringBuffer getHtmlToHead(){
		return new StringBuffer().append("<!DOCTYPE html><html><head>");
	}
	
	public static StringBuffer suitesSummaryHead(String title,int totalPassedTests,int totalFailedTests, int totalSkippedTests){
		StringBuffer headPart = new StringBuffer();
		headPart.append("<meta charset='utf-8'><title>" + title + "</title><meta name='viewport' content='width=device-width, initial-scale=1.0'>"+Utility.getBootstrapCss());
		headPart.append(getGoogleChartsJs());
		headPart.append(getGooglePieChart());
		headPart.append(googleChartDraw(totalPassedTests, totalFailedTests, totalSkippedTests));
	    return headPart;
	}
	
	public static StringBuffer suiteHead(String title,int totalPassedTests,int totalFailedTests, int totalSkippedTests){
		StringBuffer headPart = new StringBuffer();
		headPart.append("<meta charset='utf-8'><title>" + title + "</title><meta name='viewport' content='width=device-width, initial-scale=1.0'>"+Utility.getBootstrapCss());
		headPart.append(getInlineCss());
		headPart.append(getJqueryJs());
		headPart.append(getBootstrapJs());
		headPart.append(getGoogleChartsJs());
		headPart.append(getGooglePieChart());
		headPart.append(googleChartDraw(totalPassedTests, totalFailedTests, totalSkippedTests));
	    return headPart;
	}
	
	public static StringBuffer googleChartDraw(int totalPassedTests,int totalFailedTests, int totalSkippedTests){
		return new StringBuffer().append("<script type='text/javascript'>function drawVisualization() { var data = new google.visualization.DataTable(); data.addColumn('string', 'Topping'); data.addColumn('number', 'Slices'); data.addRows([['Passed', "+totalPassedTests+"],['Failed', "+totalFailedTests+"],['Skipped', "+totalSkippedTests+"]]); new google.visualization.PieChart(document.getElementById('visualization')).draw(data,{'width':300,'height':200,slices: [{color: '#109618'}, {color:'#dc3912'}, {color: '#ff9900'}]});} google.setOnLoadCallback(drawVisualization); </script> ");
	}
	
	public static StringBuffer mailSuiteSummaryHead(){
		return new StringBuffer().append(getMailCss());
	}
	
	public static StringBuffer endHeadAndStartBody(){
		return new StringBuffer().append("</head><body>");
	}
	
	public static StringBuffer startNavigationBar(){
		return new StringBuffer().append("<div class='navbar navbar-fixed-top' role='navigation'>\n<ol class='breadcrumb'>");
	}
	
	public static StringBuffer endNavigationBar(){
		return new StringBuffer().append("</ol>").append(endDiv());
	}
	
	public static StringBuffer startContainerWithMargin(){
		return new StringBuffer().append("<div class='container' style='margin-top:60px;'>");
	}
	
	public static StringBuffer startContainer(){
		return new StringBuffer().append("<div class='container'>");
	}
	
	public static StringBuffer endContainer(){
		return endDiv();
	}
	
	public static StringBuffer headerTitle(String title){
		return new StringBuffer().append("<h3 class='text-center'>"+title+"</h3>");
	}
	
	public static StringBuffer startRow(){
		return new StringBuffer().append("<div class='row'>");
	}
	
	public static StringBuffer configTableDiv(){
		StringBuffer summaryTable = new StringBuffer();
		summaryTable.append("<div class='col-md-7'><div class='table-responsive'>");
		summaryTable.append("<table class='table table-bordered col-md-6'>");
		summaryTable.append("<thead><tr><th colspan='2'><p class='text-center'>Configuration</p></th></tr></thead>");
	    if(configProp.containsKey("url")){
	    	summaryTable.append("<tr><th>URL</th><td>"+configProp.getProperty("url")+"</td></tr>");
	    }
	    summaryTable.append("<tr><th>Browser</th><td>"+configProp.getProperty("browser")+"</td></tr>");
	    
	    summaryTable.append("</table>");
	    summaryTable.append("</div></div>");
	    return summaryTable;
	}
	
	public static StringBuffer chartDiv(){
		return new StringBuffer().append("<div class='col-md-4'><div id='visualization' ></div></div>");
	}
	
	public static StringBuffer startResponsiveTableDiv(){
		return new StringBuffer().append("<div class='table-responsive'>");
	}
	
	public static StringBuffer endResponsiveTableDiv(){
		return endDiv();
	}
	
	public static StringBuffer endDiv(){
		return new StringBuffer().append("</div>\n");
	}
	
	public static StringBuffer endRow(){
		return endDiv();
	}
	
	public static StringBuffer startTableWithHover(){
		return new StringBuffer().append("<table class='table table-bordered table-hover'>");
	}
	
	public static StringBuffer startCondensedTable(){
		return new StringBuffer().append("<table class='table table-bordered table-condensed'>");
	}
	
	public static StringBuffer startColumn(int colSize){
		return new StringBuffer().append("<div class='col-md-"+colSize+"'>");
	}
	
	public static StringBuffer endColumn(){
		return endDiv();
	}
	
	public static StringBuffer startAffix(){
		return new StringBuffer().append("<div class='affix side-affix'>\n");
	}
	
	public static StringBuffer endAffix(){
		return endDiv();
	}
	
	public static StringBuffer suiteSummaryAllInfo(String suiteName, long suiteStartTime, long suiteEndTime){
		StringBuffer suiteSummaryInfo = new StringBuffer();
		suiteSummaryInfo.append("<div class='row'><div class='table-responsive'>");
		suiteSummaryInfo.append(startCondensedTable());
		suiteSummaryInfo.append("<tr><th>Suite</th><td>"+suiteName+"</td></tr>");
		suiteSummaryInfo.append("<tr><th>Start Time</th><td>"+Utility.sdf.format(suiteStartTime)+"</td></tr>");
		suiteSummaryInfo.append("<tr><th>End Time</th><td>"+Utility.sdf.format(suiteEndTime)+"</td></tr>");
		suiteSummaryInfo.append("<tr><th>Time Taken</th><td>"+Utility.timeTaken(suiteEndTime-suiteStartTime)+"</td></tr>");
		suiteSummaryInfo.append(endTable());
		suiteSummaryInfo.append("</div></div>");
		return suiteSummaryInfo;
	}
	
	public static StringBuffer suiteSummaryStatusInfo(String suiteName){
		StringBuffer suiteSummaryInfo = new StringBuffer();
		suiteSummaryInfo.append(startCondensedTable());
		suiteSummaryInfo.append("<tr><th>Suite</th><td>"+suiteName+"</td></tr>");
		suiteSummaryInfo.append(endTable());
		return suiteSummaryInfo;
	}
	
	public static StringBuffer suiteContextSummaryAllInfo(StringBuffer suiteContextSummaryReport, int suiteTotalTests, int suitePassedTests, int suiteFailedTests, int suiteSkippedTests){
		StringBuffer suiteContextSummaryInfo = new StringBuffer();
		suiteContextSummaryInfo.append("<div class='row'><div class='table-responsive'>");
		suiteContextSummaryInfo.append(startCondensedTable());
		suiteContextSummaryInfo.append(suiteContextSummaryHeader());
		suiteContextSummaryInfo.append(suiteContextSummaryReport);
		suiteContextSummaryInfo.append(suiteContextSummaryFooter(suiteTotalTests, suitePassedTests, suiteFailedTests, suiteSkippedTests));
		suiteContextSummaryInfo.append(endTable());
		suiteContextSummaryInfo.append("</div></div>");
		return suiteContextSummaryInfo;
	}
	
	public static StringBuffer suitePassedContextSummaryInfo(StringBuffer passedtextContextSummaryReport, int suitePassedTests){
		StringBuffer suitePassedContextSummaryInfo = new StringBuffer();
		suitePassedContextSummaryInfo.append(startCondensedTable());
		suitePassedContextSummaryInfo.append("<tr><th>Test</th><th>Passed</th></tr>");
		suitePassedContextSummaryInfo.append(passedtextContextSummaryReport);
		suitePassedContextSummaryInfo.append("<tr><th>Total</th><th class='success'>"+suitePassedTests+"</th></tr>");
		suitePassedContextSummaryInfo.append(endTable());
		return suitePassedContextSummaryInfo;
	}
	
	public static StringBuffer suiteFailedContextSummaryInfo(StringBuffer failedtextContextSummaryReport, int suiteFailedTests){
		StringBuffer suiteFailedContextSummaryInfo = new StringBuffer();
		suiteFailedContextSummaryInfo.append(startCondensedTable());
		suiteFailedContextSummaryInfo.append("<tr><th>Test</th><th>Failed</th></tr>");
		suiteFailedContextSummaryInfo.append(failedtextContextSummaryReport);
		suiteFailedContextSummaryInfo.append("<tr><th>Total</th><th class='danger'>"+suiteFailedTests+"</th></tr>");
		suiteFailedContextSummaryInfo.append(endTable());
		return suiteFailedContextSummaryInfo;
	}
	
	public static StringBuffer suiteSkippedContextSummaryInfo(StringBuffer skippedtextContextSummaryReport, int suiteSkippedTests){
		StringBuffer suiteSkippedContextSummaryInfo = new StringBuffer();
		suiteSkippedContextSummaryInfo.append(startCondensedTable());
		suiteSkippedContextSummaryInfo.append("<tr><th>Test</th><th>Skipped</th></tr>");
		suiteSkippedContextSummaryInfo.append(skippedtextContextSummaryReport);
		suiteSkippedContextSummaryInfo.append("<tr><th>Total</th><th class='warning'>"+suiteSkippedTests+"</th></tr>");
		suiteSkippedContextSummaryInfo.append(endTable());
		return suiteSkippedContextSummaryInfo;
	}
	
	public static StringBuffer suiteListTableHeaderRow(){
		StringBuffer suiteListHeader = new StringBuffer();
		suiteListHeader.append("<thead>");
		suiteListHeader.append("<tr>");
		suiteListHeader.append("<th>Suite</th>");
		suiteListHeader.append("<th>Start Time</th>");
		suiteListHeader.append("<th>End Time</th>");
		suiteListHeader.append("<th>Time Taken</th>");
		suiteListHeader.append("<th>Total Tests</th>");
		suiteListHeader.append("<th>Passed</th>");
		suiteListHeader.append("<th>Failed</th>");
		suiteListHeader.append("<th>Skipped</th>");
		suiteListHeader.append("</tr>");
		suiteListHeader.append("</thead>");
		return suiteListHeader;
	}
	
	public static StringBuffer suiteListTableDetailsRow(boolean isMail,String suiteName, long suiteStartTime, long suiteEndTime, int suitePassedTests, int suiteFailedTests, int suiteSkippedTests){
		int suiteTotalTests = suitePassedTests+suiteFailedTests+suiteSkippedTests;
		StringBuffer suiteDetailRow = new StringBuffer();
		suiteDetailRow.append("<tr>");
		
		suiteDetailRow.append("<td>"+suiteName+"</td>");
		suiteDetailRow.append("<td>"+sdf.format(suiteStartTime)+"</td>");
		suiteDetailRow.append("<td>"+sdf.format(suiteEndTime)+"</td>");
		suiteDetailRow.append("<td>"+timeTaken(suiteEndTime-suiteStartTime)+"</td>");
		String passPercentage = Commons.percentageCalculator(suiteTotalTests,suitePassedTests);
		String failPercentage = Commons.percentageCalculator(suiteTotalTests,suiteFailedTests);
		String skipPercentage = Commons.percentageCalculator(suiteTotalTests,suiteSkippedTests);
		if(!isMail){
			suiteDetailRow.append("<td><a href='"+suiteName+File.separator+"suite-"+suiteName+"-index.html'>"+suiteTotalTests+"</a></td>");
			suiteDetailRow.append("<td class='success'>"+(suitePassedTests > 0 ? "<a href='"+suiteName+File.separator+"suite-"+suiteName+"-passed.html'>"+suitePassedTests+"</a> ("+passPercentage+"%)" : suitePassedTests)+"</td>");
			suiteDetailRow.append("<td class='danger'>"+(suiteFailedTests > 0 ? "<a href='"+suiteName+File.separator+"suite-"+suiteName+"-failed.html'>"+suiteFailedTests+"</a> ("+failPercentage+"%)" : suiteFailedTests)+"</td>");
			suiteDetailRow.append("<td class='warning'>"+(suiteSkippedTests > 0 ? "<a href='"+suiteName+File.separator+"suite-"+suiteName+"-skipped.html'>"+suiteSkippedTests+"</a> ("+skipPercentage+"%)" : suiteSkippedTests)+"</td>");
		}else{
			suiteDetailRow.append("<td>"+suiteTotalTests+"</td>");
			suiteDetailRow.append("<td class='success'>"+suitePassedTests+" ("+passPercentage+"%)</td>");
			suiteDetailRow.append("<td class='danger'>"+suiteFailedTests+" ("+failPercentage+"%)</td>");
			suiteDetailRow.append("<td class='warning'>"+suiteSkippedTests+" ("+skipPercentage+"%)</td>");
		}
		
		suiteDetailRow.append("</tr>");
		return suiteDetailRow;
	}
	
	public static StringBuffer suitesSummaryRow(long startTimeOfSuites, long endTimeOfSuites, int totalPassedTests,int totalFailedTests, int totalSkippedTests){
		int totalTests = totalPassedTests+totalFailedTests+totalSkippedTests;
		StringBuffer suiteListHeader = new StringBuffer();
		suiteListHeader.append("<tr>");
		suiteListHeader.append("<th>Total</th>");
		suiteListHeader.append("<th>"+sdf.format(startTimeOfSuites)+"</th>");
		suiteListHeader.append("<th>"+sdf.format(endTimeOfSuites)+"</th>");
		suiteListHeader.append("<th>"+timeTaken(endTimeOfSuites-startTimeOfSuites)+"</th>");
		suiteListHeader.append("<th>"+totalTests+"</th>");
		suiteListHeader.append("<th class='success'>"+totalPassedTests+(totalPassedTests > 0 ? " ("+Commons.percentageCalculator(totalTests,totalPassedTests)+"%)" : "")+"</th>");
		suiteListHeader.append("<th class='danger'>"+totalFailedTests+(totalFailedTests > 0 ? " ("+Commons.percentageCalculator(totalTests,totalFailedTests)+"%)" : "")+"</th>");
		suiteListHeader.append("<th class='warning'>"+totalSkippedTests+(totalSkippedTests > 0 ? " ("+Commons.percentageCalculator(totalTests,totalSkippedTests)+"%)" : "")+"</th>");
		suiteListHeader.append("</tr>");
		return suiteListHeader;
	}
	
	public static StringBuffer endTable(){
		return new StringBuffer().append("</table>");
	}
	
	public static StringBuffer endContainerToHtml(){
		return new StringBuffer().append("</div></body></html>");
	}
	
	public static StringBuffer endBodyAndHtml(){
		return new StringBuffer().append("</body></html>");
	}
	
	public static StringBuffer backToSuitesSummaryLink(){
		return new StringBuffer().append("<li><a href='../suites-summary-index.html'>Suite Summary</a></li>");
	}
	
	public static StringBuffer suiteIndexLink(String suiteName){
		return new StringBuffer().append("<li><a href='suite-"+suiteName+"-index.html'>"+suiteName+"</a></li>");
	}
	
	public static StringBuffer suiteActiveAll(){
		return new StringBuffer().append("<li class='active'>All&nbsp;</li>");
	}
	
	public static StringBuffer suiteActivePassed(){
		return new StringBuffer().append("<li class='active'>Passed&nbsp;</li>");
	}
	
	public static StringBuffer suiteActiveFailed(){
		return new StringBuffer().append("<li class='active'>Failed&nbsp;</li>");
	}
	
	public static StringBuffer suiteActiveSkipped(){
		return new StringBuffer().append("<li class='active'>Skipped&nbsp;</li>");
	}
	
	public static StringBuffer startButtonGroupInNavigationBar(){
		return new StringBuffer().append("<div class='btn-group'><button type='button' class='btn btn-default btn-sm dropdown-toggle' data-toggle='dropdown'><span class='glyphicon glyphicon-filter'></span> <span class='caret'></span></button>");
	}
	
	public static StringBuffer endButtonGroupInNavigationBar(){
		return endDiv();
	}
	
	public static StringBuffer startDropDownMenuInNavigationBar(){
		return new StringBuffer().append("<ul class='dropdown-menu' role='menu'>");
	}
	
	public static StringBuffer endDropDownMenuInNavigationBar(){
		return new StringBuffer().append("</ul>");
	}
	
	public static StringBuffer suiteAllDropDownMenu(String suiteName, int suitePassedTests, int suiteFailedTests, int suiteSkippedTests){
		StringBuffer suiteDropDownMenu = new StringBuffer();
		suiteDropDownMenu.append(startButtonGroupInNavigationBar());
		
		suiteDropDownMenu.append(startDropDownMenuInNavigationBar());
		if(suitePassedTests>0){
			suiteDropDownMenu.append(dropDownPassed(suiteName));
        }
        if(suiteFailedTests>0){
        	suiteDropDownMenu.append(dropDownFailed(suiteName));
        }
        if(suiteSkippedTests>0){
        	suiteDropDownMenu.append(dropDownSkipped(suiteName));
        }
		suiteDropDownMenu.append(endDropDownMenuInNavigationBar());
		
		suiteDropDownMenu.append(endButtonGroupInNavigationBar());
		return suiteDropDownMenu;
	}
	
	public static StringBuffer suitePassedDropDownMenu(String suiteName, int suiteFailedTests, int suiteSkippedTests){
		StringBuffer suiteDropDownMenu = new StringBuffer();
		suiteDropDownMenu.append(startButtonGroupInNavigationBar());
		
		suiteDropDownMenu.append(startDropDownMenuInNavigationBar());
		suiteDropDownMenu.append(dropDownAll(suiteName));
        if(suiteFailedTests>0){
        	suiteDropDownMenu.append(dropDownFailed(suiteName));
        }
        if(suiteSkippedTests>0){
        	suiteDropDownMenu.append(dropDownSkipped(suiteName));
        }
		suiteDropDownMenu.append(endDropDownMenuInNavigationBar());
		
		suiteDropDownMenu.append(endButtonGroupInNavigationBar());
		return suiteDropDownMenu;
	}
	
	public static StringBuffer suiteFailedDropDownMenu(String suiteName, int suitePassedTests, int suiteSkippedTests){
		StringBuffer suiteDropDownMenu = new StringBuffer();
		suiteDropDownMenu.append(startButtonGroupInNavigationBar());
		
		suiteDropDownMenu.append(startDropDownMenuInNavigationBar());
		suiteDropDownMenu.append(dropDownAll(suiteName));
        if(suitePassedTests>0){
        	suiteDropDownMenu.append(dropDownPassed(suiteName));
        }
        if(suiteSkippedTests>0){
        	suiteDropDownMenu.append(dropDownSkipped(suiteName));
        }
		suiteDropDownMenu.append(endDropDownMenuInNavigationBar());
		
		suiteDropDownMenu.append(endButtonGroupInNavigationBar());
		return suiteDropDownMenu;
	}
	
	public static StringBuffer suiteSkippedDropDownMenu(String suiteName, int suitePassedTests, int suiteFailedTests){
		StringBuffer suiteDropDownMenu = new StringBuffer();
		suiteDropDownMenu.append(startButtonGroupInNavigationBar());
		
		suiteDropDownMenu.append(startDropDownMenuInNavigationBar());
		suiteDropDownMenu.append(dropDownAll(suiteName));
        if(suitePassedTests>0){
        	suiteDropDownMenu.append(dropDownPassed(suiteName));
        }
        if(suiteFailedTests>0){
        	suiteDropDownMenu.append(dropDownFailed(suiteName));
        }
		suiteDropDownMenu.append(endDropDownMenuInNavigationBar());
		
		suiteDropDownMenu.append(endButtonGroupInNavigationBar());
		return suiteDropDownMenu;
	}
	
	public static StringBuffer dropDownAll(String suiteName){
		return new StringBuffer().append("<li><a href='suite-"+suiteName+"-index.html'>All</a></li>");
	}
	
	public static StringBuffer dropDownPassed(String suiteName){
		return new StringBuffer().append("<li><a href='suite-"+suiteName+"-passed.html'>Passed</a></li>");
	}
	
	public static StringBuffer dropDownFailed(String suiteName){
		return new StringBuffer().append("<li><a href='suite-"+suiteName+"-failed.html'>Failed</a></li>");
	}
	
	public static StringBuffer dropDownSkipped(String suiteName){
		return new StringBuffer().append("<li><a href='suite-"+suiteName+"-skipped.html'>Skipped</a></li>");
	}
	
	public static StringBuffer navigationRightDetailed(String suiteName){
		return new StringBuffer().append("<ul class='nav navbar-nav navbar-right'><li><a href='detailed-suite-"+suiteName+"-index.html'>Detailed</a></li></ul>");
	}
	
	public static final Comparator<ITestResult> TIME_COMPARATOR= new TimeComparator();
	  
	private static class TimeComparator implements Comparator<ITestResult>, Serializable {
		private static final long serialVersionUID = 381775815838366907L;
		public int compare(ITestResult o1, ITestResult o2) {
		  return (int) (o1.getStartMillis() - o2.getStartMillis());
		}
	} 
	
	public static StringBuffer suiteContextSummaryHeader(){
		return new StringBuffer().append("<tr><th>Test</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Total</th></tr>");
	}
	
	public static StringBuffer suiteContextSummaryFooter(int suiteTotalTests, int suitePassedTests, int suiteFailedTests, int suiteSkippedTests){
		return new StringBuffer().append("<tr><th>Total</th><th class='success'>"+suitePassedTests+(suitePassedTests > 0 ? " ("+Commons.percentageCalculator(suiteTotalTests,suitePassedTests)+"%)" : "")+"</th><th class='danger'>"+suiteFailedTests+(suiteFailedTests > 0 ? " ("+Commons.percentageCalculator(suiteTotalTests,suiteFailedTests)+"%)" : "")+"</th><th class='warning'>"+suiteSkippedTests+(suiteSkippedTests > 0 ? " ("+Commons.percentageCalculator(suiteTotalTests,suiteSkippedTests)+"%)" : "")+"</th><th>"+suiteTotalTests+"</th></tr>");
	}
	
	public static StringBuffer testDetailReport(List<ITestResult> testResults){
		StringBuffer resultsStringBuffer = new StringBuffer();
		int i=1;
		for (ITestResult eachTestResult : testResults) {
			String testName = eachTestResult.getName();
	    	String testCasePath=null;
	    	if(eachTestResult.getAttribute("reporterFilePath")!=null){
	    		testCasePath=eachTestResult.getAttribute("reporterFilePath").toString();
	    	}
	    	resultsStringBuffer.append("<tr class=\""+((eachTestResult.getStatus()==ITestResult.SUCCESS) ? "success" : (eachTestResult.getStatus()==ITestResult.FAILURE ? "danger" : "warning") )+"\">\n");
	    	
	    	resultsStringBuffer.append("<td>"+i+"</td>\n");
	    	String testDescription = eachTestResult.getMethod().getDescription();
	    	if(testCasePath==null){
	    		resultsStringBuffer.append("<td><div "+(testDescription != null ? "class=\"testNameToolTip\" data-toggle=\"tooltip\" data-placement=\"top\" title=\""+testDescription+"\"" : "")+" >"+testName+"</div></td>\n");
	    	}else{
	    		resultsStringBuffer.append("<td><a href=\""+testCasePath+"\" "+(testDescription != null ? "class=\"testNameToolTip\" data-toggle=\"tooltip\" data-placement=\"top\" title=\""+testDescription+"\"" : "")+" >"+testName+"</a></td>\n");
	    	}
	    	resultsStringBuffer.append("<td>"+startTimeForResult(eachTestResult)+"</td>\n");
	    	resultsStringBuffer.append("<td>"+endTimeForResult(eachTestResult)+"</td>\n");
	    	resultsStringBuffer.append("<td>"+tikeTakenForResult(eachTestResult)+"</td>\n");
	    	String testStatus = statusForResult(eachTestResult);
	    	String errorMessage = "";
	    	if(eachTestResult.getStatus() != ITestResult.SUCCESS){
	    		errorMessage+="<div class=\"panel-group\" id=\"accordion\">";
	    		int errorId = 1;
	    		if(VerificationErrorsInTest.hasVerificationErrors(eachTestResult)){
					List<VerificationError> verificationErrors = VerificationErrorsInTest.getTestErrors(eachTestResult);
					Iterator<VerificationError> iter = verificationErrors.iterator();
    				while(iter.hasNext()){
    					VerificationError errorDetails = iter.next();
    					errorMessage+="<div class=\"panel panel-info\">";
    						errorMessage+="<div class=\"panel-heading\">";
    							errorMessage+="<h4 class=\"panel-title\">";
    								errorMessage+="<a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse"+errorId+"\">"+errorDetails.getAssertionError().getMessage()+"</a>";
    							errorMessage+="</h4>";
    						errorMessage+="</div>";
    						errorMessage+="<div id=\"collapse"+errorId+"\" class=\"panel-collapse collapse\">";
    							errorMessage+="<div class=\"panel-body\">";
    								errorMessage+="Verification Failure <br>";
    								errorMessage+=Commons.getStackTraceAsString(errorDetails.getAssertionError())+"<br>";
    								errorMessage+="<a href=\"screenshots"+File.separator+errorDetails.getScreenshotPath()+"\">Screenshot</a>";
    							errorMessage+="</div>";
    						errorMessage+="</div>";
						errorMessage+="</div>";
						errorId++;
    				}
				}
	    		
				if(eachTestResult.getThrowable()!=null && !eachTestResult.getThrowable().getMessage().equals(Commons.verificationFailuresMessage)){
					String[] stackTraces = Utils.stackTrace(eachTestResult.getThrowable(), true);
					errorMessage+="<div class=\"panel panel-danger\">";
						errorMessage+="<div class=\"panel-heading\">";
							errorMessage+="<h4 class=\"panel-title\">";
								errorMessage+="<a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse"+errorId+"\">"+eachTestResult.getThrowable().getMessage()+"</a>";
							errorMessage+="</h4>";
						errorMessage+="</div>";
						errorMessage+="<div id=\"collapse"+errorId+"\" class=\"panel-collapse collapse\">";
							errorMessage+="<div class=\"panel-body\">";
								errorMessage+=stackTraces[1]+"<br>";
								if(eachTestResult.getAttribute("screenshot")!=null){
									errorMessage+="<a href=\"screenshots"+File.separator+eachTestResult.getAttribute("screenshot")+"\">Screenshot</a>";
								}
							errorMessage+="</div>";
						errorMessage+="</div>";
					errorMessage+="</div>";
				}
				
				errorMessage+="</div>";
	    	}
	    	resultsStringBuffer.append("<td>"+(eachTestResult.getStatus()== ITestResult.SUCCESS ? testStatus : "<a data-toggle=\"modal\" href=\"#myModal\" class=\"openDialog btn btn-sm btn-default\" data-showthismessage=\""+(errorMessage!="" ? Utils.escapeHtml(errorMessage) : "")+"\">"+testStatus+"</a>") +"</td>\n");
	    	resultsStringBuffer.append("</tr>\n");
    		i++;
    	}
		return resultsStringBuffer;
	}
	
	public static StringBuffer mailTestDetailReport(List<ITestResult> testResults){
		StringBuffer resultsStringBuffer = new StringBuffer();
		int i=1;
		for (ITestResult eachTestResult : testResults) {
			String testName = eachTestResult.getName();
	    	resultsStringBuffer.append("<tr class='"+((eachTestResult.getStatus()==ITestResult.SUCCESS) ? "success" : (eachTestResult.getStatus()==ITestResult.FAILURE ? "danger" : "warning") )+"'>");
	    	resultsStringBuffer.append("<td>"+i+"</td>");
	    	resultsStringBuffer.append("<td>"+testName+"</td>");
	    	resultsStringBuffer.append("<td>"+startTimeForResult(eachTestResult)+"</td>");
	    	resultsStringBuffer.append("<td>"+endTimeForResult(eachTestResult)+"</td>");
	    	resultsStringBuffer.append("<td>"+tikeTakenForResult(eachTestResult)+"</td>");
	    	String testStatus=statusForResult(eachTestResult);
	    	resultsStringBuffer.append("<td>"+testStatus+"</td>");
	    	resultsStringBuffer.append("</tr>");
    		i++;
    	}
		return resultsStringBuffer;
	}
	
	public static String startTimeForResult(ITestResult eachTestResult){
		return Utility.sdfTests.format(eachTestResult.getStartMillis());
	}
	
	public static String endTimeForResult(ITestResult eachTestResult){
		return Utility.sdfTests.format(eachTestResult.getEndMillis());
	}
	
	public static String tikeTakenForResult(ITestResult eachTestResult){
		return Utility.timeTaken(eachTestResult.getEndMillis()-eachTestResult.getStartMillis());
	}
	
	public static String statusForResult(ITestResult eachTestResult){
		return Utility.getStatusString(eachTestResult.getStatus());
	}
	
	public static StringBuffer headerContextDetailedReport(){
		StringBuffer testResultsHeader = new StringBuffer();
		testResultsHeader.append("<tr>");
		testResultsHeader.append("<th>S.No</th>");
		testResultsHeader.append("<th>Test Case</th>");
		testResultsHeader.append("<th>Start Time</th>");
		testResultsHeader.append("<th>End Time</th>");
		testResultsHeader.append("<th>Time Taken</th>");
		testResultsHeader.append("<th>Status</th>");
		testResultsHeader.append("</tr>");
		return testResultsHeader;
	}
	
	public static List<ITestResult> sortResults(Set<ITestResult> results){
		List<ITestResult> testResults = new ArrayList<ITestResult>();
		testResults.addAll(results);
    	Collections.sort(testResults, TIME_COMPARATOR);
	    return testResults;
	}
	
	private static StringBuffer getContextDetailedReport(String contextName,Set<ITestResult> results, boolean isMail){
		StringBuffer contextReport = new StringBuffer();
		contextReport.append("<div class='row'><div class='table-responsive'><table class='table table-bordered' id='"+contextName+"' >");
		contextReport.append("<caption>Detailed report of "+contextName+" Tests</caption>");
		contextReport.append(headerContextDetailedReport());
		if(isMail){
			contextReport.append(mailTestDetailReport(sortResults(results)));
		}else{
			contextReport.append(testDetailReport(sortResults(results)));
		}
	    
	    contextReport.append("</table></div></div><hr/>\n");
	    return contextReport;
	}
	
	public static StringBuffer skippedContextDetailedReport(ITestContext ctx){
		return getContextDetailedReport(ctx.getName(),ctx.getSkippedTests().getAllResults(), false);
	}
	
	public static StringBuffer failedContextDetailedReport(ITestContext ctx){
		return getContextDetailedReport(ctx.getName(),ctx.getFailedTests().getAllResults(), false);
	}
	
	public static StringBuffer passedContextDetailedReport(ITestContext ctx){
		return getContextDetailedReport(ctx.getName(),ctx.getPassedTests().getAllResults(), false);
	}
	
	public static StringBuffer contextSummaryReport(ITestContext ctx){
		String contextStartTime = Utility.sdf.format(ctx.getStartDate().getTime());
		String contextEndTime = Utility.sdf.format(ctx.getEndDate().getTime());
		String contextTimeTaken = Utility.timeTaken(ctx.getEndDate().getTime()-ctx.getStartDate().getTime());
		StringBuffer textContextSummary = new StringBuffer();
	    textContextSummary.append("<div class='row'><div class='col-md-6'><div class='table-responsive'><table class='table table-bordered' id='"+ctx.getName()+"'><tr><th>Test</th><td>"+ctx.getName()+"</td></tr>");
	    textContextSummary.append("<tr><th>Start Time</th><td>"+contextStartTime+"</td></tr>");
	    textContextSummary.append("<tr><th>End Time</th><td>"+contextEndTime+"</td></tr>");
	    textContextSummary.append("<tr><th>Time Taken</th><td>"+contextTimeTaken+"</td></tr>");
	    textContextSummary.append("</table></div></div></div>\n");
	    return textContextSummary;
	}
	
	public static StringBuffer contextDetailedReport(ITestContext ctx, boolean isMail){
		Set<ITestResult> testResults = new HashSet<ITestResult>();
		testResults.addAll(ctx.getPassedTests().getAllResults());
		testResults.addAll(ctx.getFailedTests().getAllResults());
		testResults.addAll(ctx.getSkippedTests().getAllResults());
		return getContextDetailedReport(ctx.getName(), testResults, isMail);
	}
	
	public static StringBuffer getErrorModelWindow(){
		StringBuffer errorModelWindow = new StringBuffer();
		errorModelWindow.append("<div class='modal fade' id='myModal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>");
		errorModelWindow.append("<div class='modal-dialog'>");
		errorModelWindow.append("<div class='modal-content'>");
		errorModelWindow.append("<div class='modal-header'><button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button><h4 class='modal-title'>Error Details</h4></div>");
		errorModelWindow.append("<div class='modal-body'></div>");
		errorModelWindow.append("</div>");
		errorModelWindow.append("</div>");
		errorModelWindow.append("</div>\n");
		return errorModelWindow;
	}
	
}
