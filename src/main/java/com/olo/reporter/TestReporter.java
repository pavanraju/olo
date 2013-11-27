package com.olo.reporter;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.internal.Utils;

import com.olo.annotations.KeywordDriven;
import com.olo.annotations.Reporter;
import com.olo.keyworddriven.KeywordReporterData;
import com.olo.propobject.KeywordPropObject;
import com.olo.util.TestProp;
import com.olo.util.VerificationErrorsInTest;

public class TestReporter {
	
	private static final Logger logger = LogManager.getLogger(TestReporter.class.getName());
	
	@Reporter(KeywordDriven.class)
	public void keywordDrivenTest(ITestResult result){
		if(result.getStatus() == ITestResult.SUCCESS || result.getStatus() == ITestResult.FAILURE){
			String testCount = "0";
			try {
				String suiteName = result.getTestContext().getSuite().getName();
				StringBuffer sb = new StringBuffer();
				sb.append(Utility.getHtmlToHead());
				sb.append("<title>"+result.getName()+"</title>"+Utility.getMetaInfo()+Utility.getBootstrapCss()+Utility.getInlineCss()+Utility.getJqueryJs()+Utility.getBootstrapJs()+"<script type='text/javascript'>$( document ).ready(function() {   $('#checkboxform button').click(function(){ var checkboxId=this.id;  $('#tabledata thead tr th[id='+checkboxId+']').toggle();  var columnIndex=$('#tabledata thead tr th[id='+checkboxId+']').index();  $('#tabledata tbody tr td:nth-child('+(columnIndex+1)+')').toggle();      });     });</script>");
				sb.append(Utility.endHeadAndStartBody());
				sb.append(Utility.startNavigationBar());
				sb.append("<li><a href='../../../suites-summary-index.html'>Suite Summary</a></li>");
				sb.append("<li><a href='../../suite-"+suiteName+"-index.html'>"+suiteName+"</a></li>");
				sb.append("<li class='active'>"+result.getName()+"</li>");
				sb.append(Utility.endNavigationBar());
				
				sb.append(Utility.startContainerWithMargin());
				sb.append(Utility.startRow());
				String reporterFileName = null;
				
				try {
					testCount = result.getParameters()[1].toString();
				} catch (Exception e) {
					
				}
				reporterFileName = result.getName()+"-"+testCount+".html";
				
				String reporterFileDirectory = result.getTestContext().getCurrentXmlTest().getName()+File.separator+Utility.getStatusString(result.getStatus());
				result.setAttribute("reporterFilePath", reporterFileDirectory+File.separator+reporterFileName);
				//HashMap<String,Object> keywordDrivenTestInfo = (HashMap<String, Object>) result.getTestContext().getAttribute(result.getName()+"-"+testCount);
				//HashMap<String,Object> keywordDrivenTestInfo = KeywordReporterData.getTestExecutionDetails(result);
				sb.append("<div class='table-responsive'>");
				sb.append("<table class='table table-bordered'>");
				sb.append("<tr><th>Test Case</th><td>"+result.getName()+"</td></tr>");
				sb.append("<tr><th>Test Path</th><td>"+result.getAttribute(TestProp.PATH)+"</td></tr>");
				sb.append("<tr><th>Started</th><td>"+Utility.sdf.format(new Date(result.getStartMillis()))+"</td></tr>");
				sb.append("<tr><th>Completed</th><td>"+Utility.sdf.format(new Date(result.getEndMillis()))+"</td></tr>");
				sb.append("<tr><th>Time Taken</th><td>"+Utility.timeTaken(result.getEndMillis()-result.getStartMillis())+"</td></tr>");
				sb.append("<tr "+(result.getStatus()==1 ? "class='passed'" : "class='danger'")+"><th>Status</th><td>"+Utility.getStatusString(result.getStatus())+"</td></tr>");
				//sb.append("<tr><th>Total Verifications</th><td>"+keywordDrivenTestInfo.get("totalVerifications")+"</td></tr>");
				sb.append("<tr><th nowrap='nowrap'>Verifications Failed</th><td>"+VerificationErrorsInTest.getTestErrors(result).size()+"</td></tr>");
				sb.append("</table>");
				sb.append("</div>");
				
				LinkedHashMap<String, String> reportColumns = new LinkedHashMap<String, String>(Utility.testCaseReportColumns);
				ArrayList<KeywordPropObject> keywordExecutionSteps = KeywordReporterData.getTestExecutionDetails(result);
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
				
				sb.append("<div class='table-responsive'>");
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
				for(int i=0;i < keywordExecutionSteps.size(); i++){
					KeywordPropObject localStep = keywordExecutionSteps.get(i);
					String timeTaken = Utility.timeTaken(localStep.getEndTime()-localStep.getStartTime());
					
					sb.append("<tr "+ (!localStep.isConditionSkip() ? (localStep.getHasError() ? (localStep.getIsVerification() ? (localStep.getHasVerificationError() ? "class='warning'" : "class='danger'") : "class='danger'") : "class='success'" ) : "class='ifskipped'")+">");
					
					sb.append("<td>"+(i+1)+"</td>");
					
					for (Map.Entry<String, String> column : reportColumns.entrySet()) {
						if(column.getKey().equals("action")){
							sb.append("<td>"+localStep.getAction()+"</td>");
						}else if(column.getKey().equals("propertyName")){
							sb.append("<td>"+localStep.getPropertyName()+"</td>");
						}else if(column.getKey().equals("propertyValue")){
							sb.append("<td style='display:none'>"+localStep.getPropertyValue()+"</td>");
						}else if(column.getKey().equals("value")){
							sb.append("<td style='display:none'>"+(localStep.getValue()!=null ? localStep.getValue().replace("\n", "<br/>").replace("<", "&lt;").replace(">", "&gt;") : "null")+"</td>");
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
				sb.append("</div>");
				sb.append("</div></div></body></html>");
				Utils.writeFile(result.getTestContext().getOutputDirectory()+File.separator+reporterFileDirectory, reporterFileName, sb.toString());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				result.getTestContext().removeAttribute(result.getName()+"-"+testCount);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
	}
	
}
