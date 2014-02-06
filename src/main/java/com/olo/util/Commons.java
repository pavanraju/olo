package com.olo.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;


public class Commons {
	
	public static final String verificationFailuresMessage = "Verification Failures";
	
	public static boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }catch( Exception e ) {
	        return false;
	    }
	}
	
	public static Date getDateInstanceForDay(int dayNumber){
		return DateUtils.addDays(new Date(), -dayNumber);
	}
	
	public static Long getStartOfDay(Date date) {
	    return DateUtils.truncate(date, Calendar.DATE).getTime();
	}
	
	public static Long getEndOfDay(Date date) {
	    return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1).getTime();
	}
	
	public static String percentageCalculator(int total,int whatis){
		float percent = (whatis * 100.0f) / total;
		return String.format("%.1f", percent);
	}
	
	public static String getSystemIpAddress() throws UnknownHostException{
		return InetAddress.getLocalHost().getHostAddress();
	}

}
