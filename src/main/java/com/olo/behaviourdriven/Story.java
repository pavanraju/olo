package com.olo.behaviourdriven;

import java.util.List;

public class Story {
	
	private String name = null;
	private String storyFilePath = null;
	private List<Scenario> scenarios = null;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStoryFilePath() {
		return storyFilePath;
	}
	
	public void setStoryFilePath(String storyFilePath) {
		this.storyFilePath = storyFilePath;
	}
	
	public List<Scenario> getScenarios() {
		return scenarios;
	}
	
	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}
	
}
