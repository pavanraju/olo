package com.olo.keyworddriven;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import com.olo.annotations.Command;
import com.olo.bot.BrowserBot;
import com.olo.keyworddriven.KeywordPropObject;
import com.olo.propertyutil.ConfigProperties;

public class Commands{
	
	private BrowserBot browser;
	
	public Commands(WebDriver driver){
		browser = new BrowserBot(driver);
	}
	
	@Command("Wait")
	public void Wait(KeywordPropObject step) throws Exception{
		browser.Wait(Integer.parseInt(step.getActualValue()));
	}
	
	@Command("WaitForElementPresent")
	public void waitForElementPresent(KeywordPropObject step) throws Exception{
		browser.waitForElementPresent(browser.byLocator(step.getTargetValue()));
	}
	
	@Command("WaitForElementNotPresent")
	public void waitForElementNotPresent(KeywordPropObject step) throws Exception{
		browser.waitForElementNotPresent(browser.findElement(step.getTargetValue()));
	}
	
	@Command("WaitForVisible")
	public void waitForVisible(KeywordPropObject step) throws Exception{
		browser.waitForVisible(browser.findElement(step.getTargetValue()));
	}
	
	@Command("WaitForNotVisible")
	public void waitForNotVisible(KeywordPropObject step) throws Exception{
		browser.waitForNotVisible(browser.findElement(step.getTargetValue()));
	}
	
	@Command("WaitForValue")
	public void waitForValue(KeywordPropObject step) throws Exception{
		browser.waitForValue(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("WaitForNotValue")
	public void waitForNotValue(KeywordPropObject step) throws Exception{
		browser.waitForNotValue(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("WaitForEditable")
	public void waitForEditable(KeywordPropObject step) throws Exception{
		browser.waitForEditable(browser.findElement(step.getTargetValue()));
	}
	
	@Command("WaitForNotEditable")
	public void waitForNotEditable(KeywordPropObject step) throws Exception{
		browser.waitForNotEditable(browser.findElement(step.getTargetValue()));
	}
	
	@Command("WaitForText")
	public void waitForText(KeywordPropObject step) throws Exception{
		browser.waitForText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("WaitForNotText")
	public void waitForNotText(KeywordPropObject step) throws Exception{
		browser.waitForNotText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("WaitForAlertPresent")
	public void waitForAlertPresent(KeywordPropObject step) throws Exception{
		browser.waitForAlertPresent();
	}
	
	@Command("WaitForTitle")
	public void waitForTitle(KeywordPropObject step) throws Exception{
		browser.waitForTitle(step.getActualValue());
	}
	
	@Command("WaitForFrameAndSwitchToIt")
	public void waitForFrameAndSwitchToIt(KeywordPropObject step) throws Exception{
		browser.waitForFrameToBeAvailableAndSwitchToIt(step.getActualValue());
	}
	
	@Command("SwitchToDefault")
	public void switchToDefault(KeywordPropObject step) throws Exception{
		browser.switchToDefault();
	}
	
	@Command("Get")
	public void get(KeywordPropObject step){
		browser.get(step.getActualValue());
	}
	
	@Command("Type")
	public void type(KeywordPropObject step){
		browser.type(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("Clear")
	public void clear(KeywordPropObject step){
		browser.clear(browser.findElement(step.getTargetValue()));
	}
	
	@Command("ClearAndType")
	public void clearAndType(KeywordPropObject step){
		browser.clearAndType(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("TypeRandomAlphabets")
	public void typeUnique(KeywordPropObject step){
		String typeValue = step.getActualValue()+RandomStringUtils.randomAlphabetic(8);
		browser.type(browser.findElement(step.getTargetValue()),typeValue);
		step.setActualValue(typeValue);
	}
	
	@Command("ClearAndTypeRandomAlphabets")
	public void clearAndTypeUnique(KeywordPropObject step){
		String typeValue = step.getActualValue()+RandomStringUtils.randomAlphabetic(8);
		browser.clearAndType(browser.findElement(step.getTargetValue()),typeValue);
		step.setActualValue(typeValue);
	}
	
	@Command("TypeDate")
	public void typeDate(KeywordPropObject step) throws Exception{
		Calendar cal = Calendar.getInstance();
		String value = step.getActualValue();
		if(value.equals(null) || value.equals("")){
			cal.add(Calendar.DATE, 0);
		}else{
			cal.add(Calendar.DATE, Integer.parseInt(value));
		}
		if(step.getOptions()==null || step.getOptions().equals("")){
			SimpleDateFormat defaultFormat = new SimpleDateFormat(ConfigProperties.getDateFormat());
			String newdate = defaultFormat.format(cal.getTime());
			step.setActualValue(newdate);
		}else{
			JSONObject options = new JSONObject(step.getOptions());
			SimpleDateFormat datePattern = new SimpleDateFormat(options.get("pattern").toString());
			String newdate = datePattern.format(cal.getTime());
			step.setActualValue(newdate);
		}
		
		browser.type(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("Click")
	public void click(KeywordPropObject step) throws Exception{
		browser.click(browser.findElement(step.getTargetValue()));
	}
	
	@Command("ClickAt")
	public void clickAt(KeywordPropObject step) throws Exception{
		browser.clickAt(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("DoubleClick")
	public void doubleClick(KeywordPropObject step) throws Exception{
		browser.doubleClick(browser.findElement(step.getTargetValue()));
	}
	
	@Command("Check")
	public void check(KeywordPropObject step) throws Exception{
		browser.check(browser.findElement(step.getTargetValue()));
	}
	
	@Command("UnCheck")
	public void unCheck(KeywordPropObject step) throws Exception{
		browser.uncheck(browser.findElement(step.getTargetValue()));
	}
	
	@Command("SelectByText")
	public void selectByText(KeywordPropObject step) throws Exception{
		browser.selectByText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("ContextMenu")
	public void contextMenu(KeywordPropObject step) throws Exception{
		browser.contextMenu(browser.findElement(step.getTargetValue()));
	}
	
	@Command("ContextMenuAt")
	public void contextMenuAt(KeywordPropObject step) throws Exception{
		browser.contextMenuAt(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("MouseDown")
	public void mouseDown(KeywordPropObject step) throws Exception{
		browser.mouseDown(browser.findElement(step.getTargetValue()));
	}
	
	@Command("MouseDownAt")
	public void mouseDownAt(KeywordPropObject step) throws Exception{
		browser.mouseDownAt(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("MouseUp")
	public void mouseUp(KeywordPropObject step) throws Exception{
		browser.mouseUp(browser.findElement(step.getTargetValue()));
	}
	
	@Command("MouseUpAt")
	public void mouseUpAt(KeywordPropObject step) throws Exception{
		browser.mouseUpAt(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("MouseOver")
	public void mouseOver(KeywordPropObject step) throws Exception{
		browser.mouseOver(browser.findElement(step.getTargetValue()));
	}
	
	@Command("Focus")
	public void focus(KeywordPropObject step) throws Exception{
		browser.focus(browser.findElement(step.getTargetValue()));
	}
	
	@Command("KeyDown")
	public void keyDown(KeywordPropObject step) throws Exception{
		browser.keyDown(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("KeyUp")
	public void keyUp(KeywordPropObject step) throws Exception{
		browser.keyUp(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("ControlKeyUp")
	public void keyControlKeyUp(KeywordPropObject step) throws Exception{
		browser.controlKeyUp();
	}
	
	@Command("ControlKeyDown")
	public void controlKeyDown(KeywordPropObject step) throws Exception{
		browser.controlKeyDown();
	}
	
	@Command("ChooseOK")
	public void chooseOK(KeywordPropObject step) throws Exception{
		browser.chooseOk();
	}
	
	@Command("ChooseCancel")
	public void chooseCancel(KeywordPropObject step) throws Exception{
		browser.chooseCancel();
	}
	
	@Command("AssertTitle")
	public void asertTitle(KeywordPropObject step) throws Exception{
		browser.assertTitle(step.getActualValue());
	}
	
	@Command("VerifyTitle")
	public void verifyTitle(KeywordPropObject step) throws Exception{
		browser.verifyTitle(step.getActualValue());
	}
	
	@Command("AssertNotTitle")
	public void asertNotTitle(KeywordPropObject step) throws Exception{
		browser.assertNotTitle(step.getActualValue());
	}
	
	@Command("VerifyNotTitle")
	public void verifyNotTitle(KeywordPropObject step) throws Exception{
		browser.verifyNotTitle(step.getActualValue());
	}
	
	@Command("AssertSelectedText")
	public void assertSelectedText(KeywordPropObject step) throws Exception{
		browser.assertSelectedText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("VerifySelectedText")
	public void verifySelectedLabel(KeywordPropObject step) throws Exception{
		browser.verifySelectedText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("AssertNotSelectedText")
	public void assertNotSelectedText(KeywordPropObject step) throws Exception{
		browser.assertNotSelectedText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("VerifyNotSelectedText")
	public void verifyNotSelectedLabel(KeywordPropObject step) throws Exception{
		browser.verifyNotSelectedText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("AssertElementPresent")
	public void assertElementPresent(KeywordPropObject step) throws Exception{
		browser.assertElementPresent(browser.findElement(step.getTargetValue()));
	}
	
	@Command("VerifyElementPresent")
	public void verifyElementPresent(KeywordPropObject step) throws Exception{
		browser.verifyElementPresent(browser.findElement(step.getTargetValue()));
	}
	
	@Command("AssertElementNotPresent")
	public void assertElementNotPresent(KeywordPropObject step) throws Exception{
		browser.assertElementNotPresent(browser.findElement(step.getTargetValue()));
	}
	
	@Command("VerifyElementNotPresent")
	public void verifyElementNotPresent(KeywordPropObject step) throws Exception{
		browser.verifyElementNotPresent(browser.findElement(step.getTargetValue()));
	}
	
	@Command("AssertChecked")
	public void assertChecked(KeywordPropObject step) throws Exception{
		browser.assertChecked(browser.findElement(step.getTargetValue()));
	}
	
	@Command("VerifyChecked")
	public void verifyChecked(KeywordPropObject step) throws Exception{
		browser.verifyChecked(browser.findElement(step.getTargetValue()));
	}
	
	@Command("AssertNotChecked")
	public void assertNotChecked(KeywordPropObject step) throws Exception{
		browser.assertNotChecked(browser.findElement(step.getTargetValue()));
	}
	
	@Command("VerifyNotChecked")
	public void verifyNotChecked(KeywordPropObject step) throws Exception{
		browser.verifyNotChecked(browser.findElement(step.getTargetValue()));
	}
	
	@Command("AssertSelectOptions")
	public void assertSelectOptions(KeywordPropObject step) throws Exception{
		browser.assertSelectOptions(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("VerifySelectOptions")
	public void verifySelectOptions(KeywordPropObject step) throws Exception{
		browser.verifySelectOptions(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("AssertNotSelectOptions")
	public void assertNotSelectOptions(KeywordPropObject step) throws Exception{
		browser.assertNotSelectOptions(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("VerifyNotSelectOptions")
	public void verifyNotSelectOptions(KeywordPropObject step) throws Exception{
		browser.verifyNotSelectOptions(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Command("AssertSelectOptionsSize")
	public void assertSelectOptionsSize(KeywordPropObject step) throws Exception{
		browser.assertSelectOptionsSize(browser.findElement(step.getTargetValue()),Integer.parseInt(step.getActualValue()));
	}
	
	@Command("VerifySelectOptionsSize")
	public void verifySelectOptionsSize(KeywordPropObject step) throws Exception{
		browser.verifySelectOptionsSize(browser.findElement(step.getTargetValue()),Integer.parseInt(step.getActualValue()));
	}
	
	@Command("AssertNotSelectOptionsSize")
	public void assertNotSelectOptionsSize(KeywordPropObject step) throws Exception{
		browser.assertNotSelectOptionsSize(browser.findElement(step.getTargetValue()),Integer.parseInt(step.getActualValue()));
	}
	
	@Command("VerifyNotSelectOptionsSize")
	public void verifyNotSelectOptionsSize(KeywordPropObject step) throws Exception{
		browser.verifyNotSelectOptionsSize(browser.findElement(step.getTargetValue()),Integer.parseInt(step.getActualValue()));
	}
	
	@Command("AssertEditable")
	public void assertEditable(KeywordPropObject step) throws Exception{
		browser.assertEditable(browser.findElement(step.getTargetValue()));
	}
	
	@Command("VerifyEditable")
	public void verifyEditable(KeywordPropObject step) throws Exception{
		browser.verifyEditable(browser.findElement(step.getTargetValue()));
	}
	
	@Command("AssertNotEditable")
	public void assertNotEditable(KeywordPropObject step) throws Exception{
		browser.assertNotEditable(browser.findElement(step.getTargetValue()));
	}
	
	@Command("VerifyNotEditable")
	public void verifyNotEditable(KeywordPropObject step) throws Exception{
		browser.verifyNotEditable(browser.findElement(step.getTargetValue()));
	}
	
	@Command("AssertAlert")
	public void assertAlert(KeywordPropObject step) throws Exception{
		browser.assertAlert(step.getActualValue());
	}
	
	@Command("VerifyAlert")
	public void verifyAlert(KeywordPropObject step) throws Exception{
		browser.verifyAlert(step.getActualValue());
	}
	
	@Command("AssertNotAlert")
	public void assertNotAlert(KeywordPropObject step) throws Exception{
		browser.assertNotAlert(step.getActualValue());
	}
	
	@Command("VerifyNotAlert")
	public void verifyNotAlert(KeywordPropObject step) throws Exception{
		browser.verifyNotAlert(step.getActualValue());
	}
	
	@Command("AssertVisible")
	public void assertVisible(KeywordPropObject step) throws Exception{
		browser.assertVisible(browser.findElement(step.getTargetValue()));
	}
	
	@Command("VerifyVisible")
	public void verifyVisible(KeywordPropObject step) throws Exception{
		browser.verifyVisible(browser.findElement(step.getTargetValue()));
	}
	
	@Command("AssertNotVisible")
	public void assertNotVisible(KeywordPropObject step) throws Exception{
		browser.assertNotVisible(browser.findElement(step.getTargetValue()));
	}
	
	@Command("VerifyNotVisible")
	public void verifyNotVisible(KeywordPropObject step) throws Exception{
		browser.verifyNotVisible(browser.findElement(step.getTargetValue()));
	}
	
	private HashMap<String, String> getAttributeNameFromLocator(String actualLocator){
		HashMap<String, String> locatorAndAttr = new HashMap<String, String>();
		int index=actualLocator.lastIndexOf("@");
		String locator = actualLocator.substring(0, index);
		String attribute = actualLocator.substring(index+1,actualLocator.length());
		locatorAndAttr.put("locator", locator);
		locatorAndAttr.put("attribute", attribute);
		return locatorAndAttr;
	}
	
	@Command("AssertAttribute")
	public void assertAttribute(KeywordPropObject step) throws Exception{
		HashMap<String, String> locatorAndAttr = getAttributeNameFromLocator(step.getTargetValue());
		browser.assertAttribute(browser.findElement(locatorAndAttr.get("locator")), locatorAndAttr.get("attribute") ,step.getActualValue());
	}
	
	@Command("VerifyAttribute")
	public void verifyAttribute(KeywordPropObject step) throws Exception{
		HashMap<String, String> locatorAndAttr = getAttributeNameFromLocator(step.getTargetValue());
		browser.verifyAttribute(browser.findElement(locatorAndAttr.get("locator")), locatorAndAttr.get("attribute") ,step.getActualValue());
	}
	
	@Command("AssertNotAttribute")
	public void assertNotAttribute(KeywordPropObject step) throws Exception{
		HashMap<String, String> locatorAndAttr = getAttributeNameFromLocator(step.getTargetValue());
		browser.assertNotAttribute(browser.findElement(locatorAndAttr.get("locator")), locatorAndAttr.get("attribute") ,step.getActualValue());
	}
	
	@Command("VerifyNotAttribute")
	public void verifyNotAttribute(KeywordPropObject step) throws Exception{
		HashMap<String, String> locatorAndAttr = getAttributeNameFromLocator(step.getTargetValue());
		browser.verifyNotAttribute(browser.findElement(locatorAndAttr.get("locator")), locatorAndAttr.get("attribute") ,step.getActualValue());
	}
	
	@Command("AssertValue")
	public void assertValue(KeywordPropObject step) throws Exception{
		browser.assertValue(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Command("VerifyValue")
	public void verifyValue(KeywordPropObject step) throws Exception{
		browser.verifyValue(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Command("AssertNotValue")
	public void assertNotValue(KeywordPropObject step) throws Exception{
		browser.assertNotValue(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Command("VerifyNotValue")
	public void verifyNotValue(KeywordPropObject step) throws Exception{
		browser.verifyNotValue(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Command("AssertText")
	public void assertText(KeywordPropObject step) throws Exception{
		browser.assertText(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Command("VerifyText")
	public void verifyText(KeywordPropObject step) throws Exception{
		browser.verifyText(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Command("AssertNotText")
	public void assertNotText(KeywordPropObject step) throws Exception{
		browser.assertNotText(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Command("VerifyNotText")
	public void verifyNotText(KeywordPropObject step) throws Exception{
		browser.verifyNotText(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Command("IfElementPresent")
	public void ifElementPresent(KeywordPropObject step) throws Exception{
		if(!browser.isElementPresent(browser.findElement(step.getTargetValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Command("IfElementNotPresent")
	public void ifElementNotPresent(KeywordPropObject step) throws Exception{
		if(browser.isElementPresent(browser.findElement(step.getTargetValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Command("IfElementVisible")
	public void ifElementVisible(KeywordPropObject step) throws Exception{
		if(!browser.isVisible(browser.findElement(step.getTargetValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Command("IfElementNotVisible")
	public void ifElementNotVisible(KeywordPropObject step) throws Exception{
		if(browser.isVisible(browser.findElement(step.getTargetValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Command("IfElementValueEquals")
	public void ifElementValueEquals(KeywordPropObject step) throws Exception{
		if(!browser.getValue(browser.findElement(step.getTargetValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Command("IfElementValueNotEquals")
	public void ifElementValueNotEquals(KeywordPropObject step) throws Exception{
		if(browser.getValue(browser.findElement(step.getTargetValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Command("IfElementTextEquals")
	public void ifElementTextEquals(KeywordPropObject step) throws Exception{
		if(!browser.getText(browser.findElement(step.getTargetValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Command("IfElementTextNotEquals")
	public void ifElementTextNotEquals(KeywordPropObject step) throws Exception{
		if(browser.getText(browser.findElement(step.getTargetValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	/*
	@Keyword("PutValueIn")
	public HashMap<String, String> putValueIn(KeywordPropObject step) throws Exception{
		HashMap<String, String> storeData = new HashMap<String, String>();
		String elementValue = browser.getValue(browser.findElement(step.getTargetValue()));
		String actualValue = step.getActualValue();
		storeData.put(actualValue, elementValue);
		step.setActualValue(elementValue);
		return storeData;
	}
	
	@Keyword("PutTextIn")
	public HashMap<String, String> putTextIn(KeywordPropObject step) throws Exception{
		HashMap<String, String> storeData = new HashMap<String, String>();
		String elementText = browser.getText(browser.findElement(step.getTargetValue()));
		String actualValue = step.getActualValue();
		storeData.put(actualValue, elementText);
		step.setActualValue(elementText);
		return storeData;
	}
	*/
}
