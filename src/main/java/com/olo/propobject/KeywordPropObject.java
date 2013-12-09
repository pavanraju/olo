package com.olo.propobject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class KeywordPropObject implements Cloneable {
	
	private static final Logger logger = LogManager.getLogger(KeywordPropObject.class.getName());
	
	private String target = "";
	private String targetValue = "";
	private String command = "";
	private String value = "";
	private String actualValue="";
	private Boolean skip = false;
	private Boolean hasError = false;
	private String errorMessage = "";
	private Boolean isVerification = false;
	private Boolean hasVerificationError = false;
	private Error verificationError = null;
	private String screenShotName = "";
	private String screenShotPath = "";
	private long startTime = 0;
	private long endTime = 0;
	//private Boolean isAssertionError = false;
	private boolean ifSkipped = false;
	private boolean conditionSkip=false;
	private String options = "" ;
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getActualValue() {
		return actualValue;
	}

	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}

	public Boolean getSkip() {
		return skip;
	}

	public void setSkip(Boolean skip) {
		this.skip = skip;
	}
	
	public Boolean getHasError() {
		return hasError;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}
	
	public boolean getIfSkipped() {
		return ifSkipped;
	}

	public void setIfSkipped(boolean ifSkipped) {
		this.ifSkipped = ifSkipped;
	}
	/*
	public Boolean getIsAssertionError() {
		return isAssertionError;
	}

	public void setIsAssertionError(Boolean isAssertionError) {
		this.isAssertionError = isAssertionError;
	}
	*/
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getIsVerification() {
		return isVerification;
	}

	public void setIsVerification(Boolean isVerification) {
		this.isVerification = isVerification;
	}

	public Boolean getHasVerificationError() {
		return hasVerificationError;
	}

	public void setHasVerificationError(Boolean hasVerificationError) {
		this.hasVerificationError = hasVerificationError;
	}
	
	public Error getVerificationError() {
		return verificationError;
	}

	public void setVerificationError(Error verificationError) {
		this.verificationError = verificationError;
	}

	public String getScreenShotName() {
		return screenShotName;
	}

	public void setScreenShotName(String screenShotName) {
		this.screenShotName = screenShotName;
	}
	
	public String getScreenShotPath() {
		return screenShotPath;
	}

	public void setScreenShotPath(String screenShotPath) {
		this.screenShotPath = screenShotPath;
	}

	public boolean isConditionSkip() {
		return conditionSkip;
	}

	public void setConditionSkip(boolean conditionSkip) {
		this.conditionSkip = conditionSkip;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "PropObject [command=" + command+ ", target=" + target + ", actualValue=" + actualValue + "]";
	}

	public KeywordPropObject clone() {
		try {
			return (KeywordPropObject) super.clone();
		} catch (CloneNotSupportedException e) {
			logger.error("Clone doesn't support");
		}
		return null;
	}
}
