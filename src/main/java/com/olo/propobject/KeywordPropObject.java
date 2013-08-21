package com.olo.propobject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class KeywordPropObject implements Cloneable {
	
	private static final Logger logger = LogManager.getLogger(KeywordPropObject.class.getName());
	
	private String propertyName = "";
	private String propertyValue = "";
	private String action = "";
	private String value = "";
	private String actualValue="";
	private Boolean skip = false;
	private Boolean hasError = false;
	private String errorMessage = "";
	private Boolean isVerification = false;
	private String screenShotName = "";
	private long startTime = 0;
	private long endTime = 0;
	private Boolean isAssertionError = false;
	private boolean ifSkipped = false;
	private boolean conditionSkip=false;
	private String options = "" ;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
	
	public Boolean getIsAssertionError() {
		return isAssertionError;
	}

	public void setIsAssertionError(Boolean isAssertionError) {
		this.isAssertionError = isAssertionError;
	}

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

	public String getScreenShotName() {
		return screenShotName;
	}

	public void setScreenShotName(String screenShotName) {
		this.screenShotName = screenShotName;
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
		return "PropObject [propertyName=" + propertyName + ", action=" + action+ ", actualValue=" + actualValue + "]";
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
