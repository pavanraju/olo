package com.olo.behaviourdriven;

import java.util.List;

public class Step {
	
	private String name = null;
	private String description = null;
	private long startTime = 0;
	private long endTime = 0;
	private int status = 0;
	private List<Integer> verificationErrorIndexes = null;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Integer> getVerificationErrorIndexes() {
		return verificationErrorIndexes;
	}
	
	public void setVerificationErrorIndexes(List<Integer> verificationErrorIndexes) {
		this.verificationErrorIndexes = verificationErrorIndexes;
	}
	
}
