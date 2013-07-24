package com.olo.initiator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;

import com.olo.bot.BrowserBot;

public class BrowserBotInitiator extends WebDriverInitiator{
	
	private static final Logger logger = LogManager.getLogger(BrowserBotInitiator.class.getName());
	
	public BrowserBot browser = null;
	
	@BeforeMethod
	public void browserBot() throws Exception{
		browser = new BrowserBot(driver);
		logger.info("Browser Bot Initialized");
	}
	
}
