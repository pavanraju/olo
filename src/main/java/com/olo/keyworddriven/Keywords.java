package com.olo.keyworddriven;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONObject;

import com.olo.annotations.Keyword;
import com.olo.bot.BrowserBot;
import com.olo.propobject.KeywordPropObject;
import com.olo.util.Commons;

public class Keywords{
	
	private BrowserBot browser;
	
	public Keywords(BrowserBot browser){
		this.browser=browser;
	}
	
	@Keyword(name="Wait")
	public void Wait(KeywordPropObject step) throws Exception{
		browser.Wait(Integer.parseInt(step.getActualValue()));
	}
	
	@Keyword(name="WaitForElementPresent")
	public void waitForElementPresent(KeywordPropObject step) throws Exception{
		browser.waitForElementPresent(browser.byLocator(step.getPropertyValue()));
	}
	
	@Keyword(name="WaitForElementNotPresent")
	public void waitForElementNotPresent(KeywordPropObject step) throws Exception{
		browser.waitForElementNotPresent(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="WaitForVisible")
	public void waitForVisible(KeywordPropObject step) throws Exception{
		browser.waitForVisible(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="WaitForNotVisible")
	public void waitForNotVisible(KeywordPropObject step) throws Exception{
		browser.waitForNotVisible(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="WaitForValue")
	public void waitForValue(KeywordPropObject step) throws Exception{
		browser.waitForValue(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="WaitForNotValue")
	public void waitForNotValue(KeywordPropObject step) throws Exception{
		browser.waitForNotValue(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="WaitForEditable")
	public void waitForEditable(KeywordPropObject step) throws Exception{
		browser.waitForEditable(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="WaitForNotEditable")
	public void waitForNotEditable(KeywordPropObject step) throws Exception{
		browser.waitForNotEditable(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="WaitForText")
	public void waitForText(KeywordPropObject step) throws Exception{
		browser.waitForText(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="WaitForNotText")
	public void waitForNotText(KeywordPropObject step) throws Exception{
		browser.waitForNotText(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="WaitForAlert")
	public void waitForAlert(KeywordPropObject step) throws Exception{
		browser.waitForAlert(step.getActualValue());
	}
	
	@Keyword(name="WaitForAlertPresent")
	public void waitForAlertPresent(KeywordPropObject step) throws Exception{
		browser.waitForAlertPresent();
	}
	
	@Keyword(name="WaitForTitle")
	public void waitForTitle(KeywordPropObject step) throws Exception{
		browser.waitForTitle(step.getActualValue());
	}
	
	@Keyword(name="WaitForFrameAndSwitchToIt")
	public void waitForFrameAndSwitchToIt(KeywordPropObject step) throws Exception{
		browser.waitForFrameToBeAvailableAndSwitchToIt(step.getActualValue());
	}
	
	@Keyword(name="SwitchToDefault")
	public void switchToDefault(KeywordPropObject step) throws Exception{
		browser.switchToDefault();
	}
	
	@Keyword(name="Get")
	public void get(KeywordPropObject step){
		browser.get(step.getActualValue());
	}
	
	@Keyword(name="Type")
	public void type(KeywordPropObject step){
		browser.type(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="Clear")
	public void clear(KeywordPropObject step){
		browser.clear(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="ClearAndType")
	public void clearAndType(KeywordPropObject step){
		browser.clearAndType(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="TypeUnique")
	public void typeUnique(KeywordPropObject step){
		String typeValue = step.getActualValue()+Commons.getRandomNumber();
		browser.type(browser.findElement(step.getPropertyValue()),typeValue);
		step.setActualValue(typeValue);
	}
	
	@Keyword(name="ClearAndTypeUnique")
	public void clearAndTypeUnique(KeywordPropObject step){
		String typeValue = step.getActualValue()+Commons.getRandomNumber();
		browser.clearAndTypeUnique(browser.findElement(step.getPropertyValue()),typeValue);
		step.setActualValue(typeValue);
	}
	
	@Keyword(name="TypeDate")
	public void typeDate(KeywordPropObject step) throws Exception{
		Calendar cal = Calendar.getInstance();
		String value = step.getActualValue();
		if(value.equals(null) || value.equals("")){
			cal.add(Calendar.DATE, 0);
		}else{
			cal.add(Calendar.DATE, Integer.parseInt(value));
		}
		if(step.getOptions()==null || step.getOptions().equals("")){
			String newdate = Commons.defaultFormat.format(cal.getTime());
			step.setActualValue(newdate);
		}else{
			JSONObject options = new JSONObject(step.getOptions());
			SimpleDateFormat datePattern = new SimpleDateFormat(options.get("pattern").toString());
			String newdate = datePattern.format(cal.getTime());
			step.setActualValue(newdate);
		}
		
		browser.type(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="Click")
	public void click(KeywordPropObject step) throws Exception{
		browser.click(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="ClickAt")
	public void clickAt(KeywordPropObject step) throws Exception{
		browser.clickAt(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="DoubleClick")
	public void doubleClick(KeywordPropObject step) throws Exception{
		browser.doubleClick(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="Check")
	public void check(KeywordPropObject step) throws Exception{
		browser.check(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="UnCheck")
	public void unCheck(KeywordPropObject step) throws Exception{
		browser.uncheck(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="SelectByText")
	public void selectByText(KeywordPropObject step) throws Exception{
		browser.selectByText(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="ContextMenu")
	public void contextMenu(KeywordPropObject step) throws Exception{
		browser.contextMenu(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="ContextMenuAt")
	public void contextMenuAt(KeywordPropObject step) throws Exception{
		browser.contextMenuAt(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="MouseDown")
	public void mouseDown(KeywordPropObject step) throws Exception{
		browser.mouseDown(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="MouseDownAt")
	public void mouseDownAt(KeywordPropObject step) throws Exception{
		browser.mouseDownAt(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="MouseUp")
	public void mouseUp(KeywordPropObject step) throws Exception{
		browser.mouseUp(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="MouseUpAt")
	public void mouseUpAt(KeywordPropObject step) throws Exception{
		browser.mouseUpAt(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="MouseOver")
	public void mouseOver(KeywordPropObject step) throws Exception{
		browser.mouseOver(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="Focus")
	public void focus(KeywordPropObject step) throws Exception{
		browser.focus(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="KeyDown")
	public void keyDown(KeywordPropObject step) throws Exception{
		browser.keyDown(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="KeyUp")
	public void keyUp(KeywordPropObject step) throws Exception{
		browser.keyUp(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="ControlKeyUp")
	public void keyControlKeyUp(KeywordPropObject step) throws Exception{
		browser.controlKeyUp();
	}
	
	@Keyword(name="ControlKeyDown")
	public void controlKeyDown(KeywordPropObject step) throws Exception{
		browser.controlKeyDown();
	}
	
	@Keyword(name="ChooseOK")
	public void chooseOK(KeywordPropObject step) throws Exception{
		browser.chooseOk();
	}
	
	@Keyword(name="ChooseCancel")
	public void chooseCancel(KeywordPropObject step) throws Exception{
		browser.chooseCancel();
	}
	
	@Keyword(name="AssertTitle")
	public void asertTitle(KeywordPropObject step) throws Exception{
		browser.assertTitle(step.getActualValue());
	}
	
	@Keyword(name="VerifyTitle")
	public void verifyTitle(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		asertTitle(step);
	}
	
	@Keyword(name="AssertNotTitle")
	public void asertNotTitle(KeywordPropObject step) throws Exception{
		browser.assertNotTitle(step.getActualValue());
	}
	
	@Keyword(name="VerifyNotTitle")
	public void verifyNotTitle(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		asertNotTitle(step);
	}
	
	@Keyword(name="AssertSelectedText")
	public void assertSelectedText(KeywordPropObject step) throws Exception{
		browser.assertSelectedText(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="VerifySelectedText")
	public void verifySelectedLabel(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertSelectedText(step);
	}
	
	@Keyword(name="AssertNotSelectedText")
	public void assertNotSelectedText(KeywordPropObject step) throws Exception{
		browser.assertNotSelectedText(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="VerifyNotSelectedText")
	public void verifyNotSelectedLabel(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotSelectedText(step);
	}
	
	@Keyword(name="AssertElementPresent")
	public void assertElementPresent(KeywordPropObject step) throws Exception{
		browser.assertElementPresent(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="VerifyElementPresent")
	public void verifyElementPresent(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertElementPresent(step);
	}
	
	@Keyword(name="AssertElementNotPresent")
	public void assertElementNotPresent(KeywordPropObject step) throws Exception{
		browser.assertElementNotPresent(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="VerifyElementNotPresent")
	public void verifyElementNotPresent(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertElementNotPresent(step);
	}
	
	@Keyword(name="AssertChecked")
	public void assertChecked(KeywordPropObject step) throws Exception{
		browser.assertChecked(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="VerifyChecked")
	public void verifyChecked(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertChecked(step);
	}
	
	@Keyword(name="AssertNotChecked")
	public void assertNotChecked(KeywordPropObject step) throws Exception{
		browser.assertNotChecked(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="VerifyNotChecked")
	public void verifyNotChecked(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotChecked(step);
	}
	
	@Keyword(name="AssertSelectOptions")
	public void assertSelectOptions(KeywordPropObject step) throws Exception{
		browser.assertSelectOptions(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="VerifySelectOptions")
	public void verifySelectOptions(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertSelectOptions(step);
	}
	
	@Keyword(name="AssertNotSelectOptions")
	public void assertNotSelectOptions(KeywordPropObject step) throws Exception{
		browser.assertNotSelectOptions(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="VerifyNotSelectOptions")
	public void verifyNotSelectOptions(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotSelectOptions(step);
	}
	
	@Keyword(name="AssertSelectOptionsSize")
	public void assertSelectOptionsSize(KeywordPropObject step) throws Exception{
		browser.assertSelectOptionsSize(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="VerifySelectOptionsSize")
	public void verifySelectOptionsSize(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertSelectOptionsSize(step);
	}
	
	@Keyword(name="AssertNotSelectOptionsSize")
	public void assertNotSelectOptionsSize(KeywordPropObject step) throws Exception{
		browser.assertNotSelectOptionsSize(browser.findElement(step.getPropertyValue()),step.getActualValue());
	}
	
	@Keyword(name="VerifyNotSelectOptionsSize")
	public void verifyNotSelectOptionsSize(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotSelectOptionsSize(step);
	}
	
	@Keyword(name="AssertEditable")
	public void assertEditable(KeywordPropObject step) throws Exception{
		browser.assertEditable(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="VerifyEditable")
	public void verifyEditable(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertEditable(step);
	}
	
	@Keyword(name="AssertNotEditable")
	public void assertNotEditable(KeywordPropObject step) throws Exception{
		browser.assertNotEditable(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="VerifyNotEditable")
	public void verifyNotEditable(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotEditable(step);
	}
	
	@Keyword(name="AssertAlert")
	public void assertAlert(KeywordPropObject step) throws Exception{
		browser.assertAlert(step.getActualValue());
	}
	
	@Keyword(name="VerifyAlert")
	public void verifyAlert(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertAlert(step);
	}
	
	@Keyword(name="AssertNotAlert")
	public void assertNotAlert(KeywordPropObject step) throws Exception{
		browser.assertNotAlert(step.getActualValue());
	}
	
	@Keyword(name="VerifyNotAlert")
	public void verifyNotAlert(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotAlert(step);
	}
	
	@Keyword(name="AssertVisible")
	public void assertVisible(KeywordPropObject step) throws Exception{
		browser.assertVisible(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="VerifyVisible")
	public void verifyVisible(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertVisible(step);
	}
	
	@Keyword(name="AssertNotVisible")
	public void assertNotVisible(KeywordPropObject step) throws Exception{
		browser.assertNotVisible(browser.findElement(step.getPropertyValue()));
	}
	
	@Keyword(name="VerifyNotVisible")
	public void verifyNotVisible(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotVisible(step);
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
	
	@Keyword(name="AssertAttribute")
	public void assertAttribute(KeywordPropObject step) throws Exception{
		HashMap<String, String> locatorAndAttr = getAttributeNameFromLocator(step.getPropertyValue());
		browser.assertAttribute(browser.findElement(locatorAndAttr.get("locator")), locatorAndAttr.get("attribute") ,step.getActualValue());
	}
	
	@Keyword(name="VerifyAttribute")
	public void verifyAttribute(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertAttribute(step);
	}
	
	@Keyword(name="AssertNotAttribute")
	public void assertNotAttribute(KeywordPropObject step) throws Exception{
		HashMap<String, String> locatorAndAttr = getAttributeNameFromLocator(step.getPropertyValue());
		browser.assertNotAttribute(browser.findElement(locatorAndAttr.get("locator")), locatorAndAttr.get("attribute") ,step.getActualValue());
	}
	
	@Keyword(name="VerifyNotAttribute")
	public void verifyNotAttribute(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotAttribute(step);
	}
	
	@Keyword(name="AssertValue")
	public void assertValue(KeywordPropObject step) throws Exception{
		browser.assertValue(browser.findElement(step.getPropertyValue()), step.getActualValue());
	}
	
	@Keyword(name="VerifyValue")
	public void verifyValue(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertValue(step);
	}
	
	@Keyword(name="AssertNotValue")
	public void assertNotValue(KeywordPropObject step) throws Exception{
		browser.assertNotValue(browser.findElement(step.getPropertyValue()), step.getActualValue());
	}
	
	@Keyword(name="VerifyNotValue")
	public void verifyNotValue(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotValue(step);
	}
	
	@Keyword(name="AssertText")
	public void assertText(KeywordPropObject step) throws Exception{
		browser.assertText(browser.findElement(step.getPropertyValue()), step.getActualValue());
	}
	
	@Keyword(name="VerifyText")
	public void verifyText(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertText(step);
	}
	
	@Keyword(name="AssertNotText")
	public void assertNotText(KeywordPropObject step) throws Exception{
		browser.assertNotText(browser.findElement(step.getPropertyValue()), step.getActualValue());
	}
	
	@Keyword(name="VerifyNotText")
	public void verifyNotText(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotText(step);
	}
	
	@Keyword(name="IfElementPresent")
	public void ifElementPresent(KeywordPropObject step) throws Exception{
		if(!browser.isElementPresent(browser.findElement(step.getPropertyValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementNotPresent")
	public void ifElementNotPresent(KeywordPropObject step) throws Exception{
		if(browser.isElementPresent(browser.findElement(step.getPropertyValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementVisible")
	public void ifElementVisible(KeywordPropObject step) throws Exception{
		if(!browser.isVisible(browser.findElement(step.getPropertyValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementNotVisible")
	public void ifElementNotVisible(KeywordPropObject step) throws Exception{
		if(browser.isVisible(browser.findElement(step.getPropertyValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementValueEquals")
	public void ifElementValueEquals(KeywordPropObject step) throws Exception{
		if(!browser.getValue(browser.findElement(step.getPropertyValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementValueNotEquals")
	public void ifElementValueNotEquals(KeywordPropObject step) throws Exception{
		if(browser.getValue(browser.findElement(step.getPropertyValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementTextEquals")
	public void ifElementTextEquals(KeywordPropObject step) throws Exception{
		if(!browser.getText(browser.findElement(step.getPropertyValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementTextNotEquals")
	public void ifElementTextNotEquals(KeywordPropObject step) throws Exception{
		if(browser.getText(browser.findElement(step.getPropertyValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="PutValueIn")
	public HashMap<String, String> putValueIn(KeywordPropObject step) throws Exception{
		HashMap<String, String> storeData = new HashMap<String, String>();
		storeData.put(step.getActualValue(), browser.getValue(browser.findElement(step.getPropertyValue())));
		return storeData;
	}
	
	@Keyword(name="PutTextIn")
	public HashMap<String, String> putTextIn(KeywordPropObject step) throws Exception{
		HashMap<String, String> storeData = new HashMap<String, String>();
		storeData.put(step.getActualValue(), browser.getText(browser.findElement(step.getPropertyValue())));
		return storeData;
	}
	
}
