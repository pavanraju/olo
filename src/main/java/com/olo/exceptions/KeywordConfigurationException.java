package com.olo.exceptions;

@SuppressWarnings("serial")
public class KeywordConfigurationException extends Exception{
	
	public KeywordConfigurationException() { }
	
	public KeywordConfigurationException(String message) {
		super(message);
	}

	public KeywordConfigurationException(Throwable cause){
		super(cause);
	}
	
	public KeywordConfigurationException(String message, Throwable cause){
		super(message, cause);
	}

}
