package com.olo.reporter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.LinkedHashMap;

import org.testng.ISuite;
import org.testng.ITestResult;

public class Utility {
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss, z");
	public static final SimpleDateFormat sdfTests = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss.SSS");
	public static final NumberFormat formatter = new DecimalFormat("#,##0.0");
	
	public static final LinkedHashMap<String, String> testCaseReportColumns = new LinkedHashMap<String, String>(){
		private static final long serialVersionUID = 1L;

			{
			        put("propertyName", "Property Name");
			        put("propertyValue", "Property Value");
			        put("action", "Action");
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
		return "<link href='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css' rel='stylesheet'>";
	}
	
	public static String getBootstrapJs(){
		return "<script src='http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js'></script>";
	}
	
	public static String getJqueryJs(){
		return "<script src='http://code.jquery.com/jquery-1.10.1.min.js'></script>";
	}
	
	public static String getInlineCss(){
		return "<style type='text/css'>.ifskipped{background-color: #d6e1c9;} th.success,td.success{background-color: #dff0d8;} th.error,td.error{background-color: #f2dede;} th.warning,td.warning{background-color: #fcf8e3;}</style>";
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
	
}
