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
import com.olo.util.Commons;

public class OloTestReporter {
	
	private static final Logger logger = LogManager.getLogger(OloTestReporter.class.getName());
	
	@Reporter(name=KeywordDriven.class)
	public void keywordDrivenTest(ITestResult result){
		try {
			
			switch(result.getStatus()){
				case ITestResult.SUCCESS:
				case ITestResult.FAILURE:
					StringBuffer sb = new StringBuffer();
					sb.append("<!DOCTYPE html><html><head><title>"+result.getName()+"</title><meta name='viewport' content='width=device-width, initial-scale=1.0'><link href='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css' rel='stylesheet'>"+Commons.customStyle+"<script src='http://code.jquery.com/jquery-1.10.1.min.js'></script><script src='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js'></script><script type='text/javascript'>$( document ).ready(function() {   $('#checkboxform button').click(function(){ var checkboxId=this.id;  $('#tabledata thead tr th[id='+checkboxId+']').toggle();  var columnIndex=$('#tabledata thead tr th[id='+checkboxId+']').index();  $('#tabledata tbody tr td:nth-child('+(columnIndex+1)+')').toggle();      });     });</script></head><body>");
					sb.append("<div class='container-fluid'><div class='row-fluid' style='margin-top:20px;'>");
					String reporterFileName=result.getName()+".html";
					String reporterFileDirectory = result.getTestContext().getCurrentXmlTest().getName()+File.separator+Commons.getStatusString(result.getStatus());
					result.setAttribute("reporterFileName", result.getName());
					result.setAttribute("reporterFilePath", reporterFileDirectory+File.separator+reporterFileName.replace("/", "_"));
					HashMap<String,Object> level3FinalArray = (HashMap<String, Object>) result.getTestContext().getAttribute(result.getName());
					sb.append("<div class='span4'>");
					sb.append("<table class='table table-bordered'>");
					sb.append("<tr><th>Test Case</th><td>"+result.getName()+"</td></tr>");
					sb.append("<tr><th>Started</th><td>"+Commons.sdfTests.format(new Date(result.getStartMillis()))+"</td></tr>");
					sb.append("<tr><th>Completed</th><td>"+Commons.sdfTests.format(new Date(result.getEndMillis()))+"</td></tr>");
					sb.append("<tr><th>Time Taken</th><td>"+Commons.timeTaken(result.getEndMillis()-result.getStartMillis())+"</td></tr>");
					sb.append("<tr "+(result.getStatus()==1 ? "class='passed'" : "class='failed'")+"><th>Status</th><td>"+Commons.getStatusString(result.getStatus())+"</td></tr>");
					sb.append("<tr><th>Total Verifications</th><td>"+level3FinalArray.get("totalVerifications")+"</td></tr>");
					sb.append("<tr><th>Verifications Failed</th><td>"+level3FinalArray.get("totalVerificationFailures")+"</td></tr>");
					sb.append("</table>");
					sb.append("</div><div class='clearfix'></div>");
					
					LinkedHashMap<String, String> reportColumns = new LinkedHashMap<String, String>(Commons.testCaseReportColumns);
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
						String timeTaken=Commons.timeTaken(localStep.getEndTime()-localStep.getStartTime());
						
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
								sb.append("<td style='display:none'>"+(!localStep.isConditionSkip() ? (localStep.getStartTime()!=0 ? Commons.hourFormat.format(localStep.getStartTime()) : "") : "-")  +"</td>");
							}else if(column.getKey().equals("endTime")){
								sb.append("<td style='display:none'>"+(!localStep.isConditionSkip() ? (localStep.getEndTime()!=0 ? Commons.hourFormat.format(localStep.getEndTime()) : "") : "-")  +"</td>");
							}else if(column.getKey().equals("timeTaken")){
								sb.append("<td>"+(!localStep.isConditionSkip() ? (localStep.getStartTime()!=0 ? timeTaken : "") : "-") +"</td>");
							}else if(column.getKey().equals("status")){
								sb.append("<td style='display:none'>"+(!localStep.isConditionSkip() ? (localStep.getStartTime()!=0 ? localStep.getHasError() ? Commons.getStatusString(2) : Commons.getStatusString(1) : "") : "-")+"</td>");
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
					break;
			
			}
			
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
