package com.olo.util;

public class VerificationError {
	
	private String screenshotPath = null;
	private Error assertionError = null;
	
	public String getScreenshotPath() {
		return screenshotPath;
	}
	
	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}
	
	public Error getAssertionError() {
		return assertionError;
	}
	
	public void setAssertionError(Error assertionError) {
		this.assertionError = assertionError;
	}
	
}
