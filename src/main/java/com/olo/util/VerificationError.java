package com.olo.util;

public class VerificationError {
	
	private String screenShotFileName = null;
	private Error assertionError = null;

	public String getScreenShotFileName() {
		return screenShotFileName;
	}

	public void setScreenShotFileName(String screenShotFileName) {
		this.screenShotFileName = screenShotFileName;
	}

	public Error getAssertionError() {
		return assertionError;
	}
	
	public void setAssertionError(Error assertionError) {
		this.assertionError = assertionError;
	}
	
}
