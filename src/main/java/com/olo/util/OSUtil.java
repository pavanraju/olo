package com.olo.util;

public final class OSUtil {
	
	public static String getJavaBitVersion(){
		return System.getProperty("sun.arch.data.model");
	}
	
}
