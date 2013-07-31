package com.olo.reporter;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.internal.Utils;

import com.olo.annotations.KeywordDriven;
import com.olo.annotations.Reporter;
import com.olo.propobject.KeywordPropObject;

public class TestReporter {
	
	private static final Logger logger = LogManager.getLogger(TestReporter.class.getName());
	
	@Reporter(name=KeywordDriven.class)
	public void keywordDrivenTest(ITestResult result){
		try {
			
			if(result.getStatus() == ITestResult.SUCCESS || result.getStatus() == ITestResult.FAILURE){
				String suiteName = result.getTestContext().getSuite().getName();
				StringBuffer sb = new StringBuffer();
				sb.append("<!DOCTYPE html><html><head><title>"+result.getName()+"</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+"<script type='text/javascript'>$( document ).ready(function() {   $('#checkboxform button').click(function(){ var checkboxId=this.id;  $('#tabledata thead tr th[id='+checkboxId+']').toggle();  var columnIndex=$('#tabledata thead tr th[id='+checkboxId+']').index();  $('#tabledata tbody tr td:nth-child('+(columnIndex+1)+')').toggle();      });     });</script></head><body>");
				
				sb.append("<div class='navbar navbar-inverse navbar-fixed-top'>");
				sb.append("<ul class='breadcrumb'>");
				sb.append("<li><a href='../../../suites-summary-index.html'>Suite Summary</a> <span class='divider'>/</span></li>");
				sb.append("<li><a href='../../suite-"+suiteName+"-index.html'>"+suiteName+"</a> <span class='divider'>/</span></li>");
				sb.append("<li class='active'>"+result.getName()+"<span class='divider'></span></li>");
				sb.append("</ul>");
				sb.append("</div>");
				
				sb.append("<div class='container-fluid'><div class='row-fluid' style='margin-top:50px;'>");
				String reporterFileName=result.getName()+".html";
				String reporterFileDirectory = result.getTestContext().getCurrentXmlTest().getName()+File.separator+Utility.getStatusString(result.getStatus());
				result.setAttribute("reporterFilePath", reporterFileDirectory+File.separator+reporterFileName);
				HashMap<String,Object> level3FinalArray = (HashMap<String, Object>) result.getTestContext().getAttribute(result.getName());
				sb.append("<div class='span5'>");
				sb.append("<table class='table table-bordered'>");
				sb.append("<tr><th>Test Case</th><td>"+result.getName()+"</td></tr>");
				sb.append("<tr><th>Test Path</th><td>"+level3FinalArray.get("testPath")+"</td></tr>");
				sb.append("<tr><th>Started</th><td>"+Utility.sdf.format(new Date(result.getStartMillis()))+"</td></tr>");
				sb.append("<tr><th>Completed</th><td>"+Utility.sdf.format(new Date(result.getEndMillis()))+"</td></tr>");
				sb.append("<tr><th>Time Taken</th><td>"+Utility.timeTaken(result.getEndMillis()-result.getStartMillis())+"</td></tr>");
				sb.append("<tr "+(result.getStatus()==1 ? "class='passed'" : "class='failed'")+"><th>Status</th><td>"+Utility.getStatusString(result.getStatus())+"</td></tr>");
				sb.append("<tr><th>Total Verifications</th><td>"+level3FinalArray.get("totalVerifications")+"</td></tr>");
				sb.append("<tr><th nowrap='nowrap'>Verifications Failed</th><td>"+level3FinalArray.get("totalVerificationFailures")+"</td></tr>");
				sb.append("</table>");
				sb.append("</div><div class='clearfix'></div>");
				
				LinkedHashMap<String, String> reportColumns = new LinkedHashMap<String, String>(Utility.testCaseReportColumns);
				ArrayList<KeywordPropObject> level3ReportArray = (ArrayList<KeywordPropObject>)level3FinalArray.get("level3ReportArray");
				sb.append("<form id='checkboxform'>");
				sb.append("<div class='btn-group' data-toggle='buttons-checkbox'>");
				
				for (Map.Entry<String, String> column : reportColumns.entrySet()) {
					if(column.getKey().equals("propertyValue") || column.getKey().equals("value") || column.getKey().equals("options") || column.getKey().equals("startTime") || column.getKey().equals("endTime") || column.getKey().equals("status")){
						sb.append("<button type='button' class='btn btn-primary' id='"+column.getKey()+"'>"+column.getValue()+"</button>");
					}else{
						sb.append("<button type='button' class='btn btn-primary active' id='"+column.getKey()+"'>"+column.getValue()+"</button>");
					}
				}
				sb.append("</div>");
				sb.append("</form>");
				
				sb.append("<table class='table table-bordered' id='tabledata'><thead><tr>");
				sb.append("<th>S.No</th>");
				
				for (Map.Entry<String, String> column : reportColumns.entrySet()) {
					if(column.getKey().equals("propertyValue") || column.getKey().equals("value") || column.getKey().equals("options") || column.getKey().equals("startTime") || column.getKey().equals("endTime") || column.getKey().equals("status")){
						sb.append("<th style='display:none' id='"+column.getKey()+"'> ");
					}else{
						sb.append("<th id='"+column.getKey()+"'> ");
					}
					sb.append(column.getValue()+"</th>");
				}

				sb.append("</tr></thead>");
				for(int i=0;i < level3ReportArray.size(); i++){
					KeywordPropObject localStep = level3ReportArray.get(i);
					String timeTaken = Utility.timeTaken(localStep.getEndTime()-localStep.getStartTime());
					
					sb.append("<tr "+ (!localStep.isConditionSkip() ? (localStep.getHasError() ? (localStep.getIsVerification() ? (localStep.getIsAssertionError() ? "class='warning'" : "class='error'") : "class='error'") : "class='success'" ) : "class='ifskipped'")+">");
					
					sb.append("<td>"+(i+1)+"</td>");
					
					for (Map.Entry<String, String> column : reportColumns.entrySet()) {
						if(column.getKey().equals("propertyName")){
							sb.append("<td>"+localStep.getPropertyName()+"</td>");
						}else if(column.getKey().equals("propertyValue")){
							sb.append("<td style='display:none'>"+localStep.getPropertyValue()+"</td>");
						}else if(column.getKey().equals("action")){
							sb.append("<td>"+localStep.getAction()+"</td>");
						}else if(column.getKey().equals("value")){
							sb.append("<td style='display:none'>"+(localStep.getValue()!=null ? localStep.getValue().replace("\n", "<br/>") : "null")+"</td>");
						}else if(column.getKey().equals("actualValue")){
							sb.append("<td>"+(localStep.getActualValue()!=null ? localStep.getActualValue().replace("\n", "<br/>") : "null")+"</td>");
						}else if(column.getKey().equals("options")){
							sb.append("<td style='display:none'>"+localStep.getOptions()+"</td>");
						}else if(column.getKey().equals("startTime")){
							sb.append("<td style='display:none'>"+(!localStep.isConditionSkip() ? (localStep.getStartTime()!=0 ? Utility.hourFormat.format(localStep.getStartTime()) : "") : "-")  +"</td>");
						}else if(column.getKey().equals("endTime")){
							sb.append("<td style='display:none'>"+(!localStep.isConditionSkip() ? (localStep.getEndTime()!=0 ? Utility.hourFormat.format(localStep.getEndTime()) : "") : "-")  +"</td>");
						}else if(column.getKey().equals("timeTaken")){
							sb.append("<td>"+(!localStep.isConditionSkip() ? (localStep.getStartTime()!=0 ? timeTaken : "") : "-") +"</td>");
						}else if(column.getKey().equals("status")){
							sb.append("<td style='display:none'>"+(!localStep.isConditionSkip() ? (localStep.getStartTime()!=0 ? localStep.getHasError() ? Utility.getStatusString(2) : Utility.getStatusString(1) : "") : "-")+"</td>");
						}else if(column.getKey().equals("exception")){
							sb.append("<td style='max-width:200px;overflow:hidden;'>"+(localStep.getHasError() ? localStep.getErrorMessage() : "")+"</td>");
						}else if(column.getKey().equals("screenShot")){
							sb.append("<td>"+(localStep.getScreenShotName()!="" ? "<a href='"+".."+File.separator+".."+File.separator+"screenshots"+File.separator+localStep.getScreenShotName()+"'>Screenshot</a>" : "")+"</td>");
						}
					}
					
					sb.append("</tr>");
				}
				sb.append("</table>");
				sb.append("</div></div></body></html>");
				Utils.writeFile(result.getTestContext().getOutputDirectory()+File.separator+reporterFileDirectory, reporterFileName, sb.toString());
			}
			
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
