package com.olo.keyworddriven;

import static com.olo.util.PropertyReader.configProp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import com.olo.annotations.Keyword;
import com.olo.bot.BrowserBot;
import com.olo.propobject.KeywordPropObject;

public class Keywords{
	
	private BrowserBot browser;
	
	public Keywords(BrowserBot browser){
		this.browser=browser;
	}
	
	@Keyword("Wait")
	public void Wait(KeywordPropObject step) throws Exception{
		browser.Wait(Integer.parseInt(step.getActualValue()));
	}
	
	@Keyword("WaitForElementPresent")
	public void waitForElementPresent(KeywordPropObject step) throws Exception{
		browser.waitForElementPresent(browser.byLocator(step.getTargetValue()));
	}
	
	@Keyword("WaitForElementNotPresent")
	public void waitForElementNotPresent(KeywordPropObject step) throws Exception{
		browser.waitForElementNotPresent(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("WaitForVisible")
	public void waitForVisible(KeywordPropObject step) throws Exception{
		browser.waitForVisible(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("WaitForNotVisible")
	public void waitForNotVisible(KeywordPropObject step) throws Exception{
		browser.waitForNotVisible(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("WaitForValue")
	public void waitForValue(KeywordPropObject step) throws Exception{
		browser.waitForValue(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("WaitForNotValue")
	public void waitForNotValue(KeywordPropObject step) throws Exception{
		browser.waitForNotValue(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("WaitForEditable")
	public void waitForEditable(KeywordPropObject step) throws Exception{
		browser.waitForEditable(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("WaitForNotEditable")
	public void waitForNotEditable(KeywordPropObject step) throws Exception{
		browser.waitForNotEditable(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("WaitForText")
	public void waitForText(KeywordPropObject step) throws Exception{
		browser.waitForText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("WaitForNotText")
	public void waitForNotText(KeywordPropObject step) throws Exception{
		browser.waitForNotText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("WaitForAlert")
	public void waitForAlert(KeywordPropObject step) throws Exception{
		browser.waitForAlert(step.getActualValue());
	}
	
	@Keyword("WaitForAlertPresent")
	public void waitForAlertPresent(KeywordPropObject step) throws Exception{
		browser.waitForAlertPresent();
	}
	
	@Keyword("WaitForTitle")
	public void waitForTitle(KeywordPropObject step) throws Exception{
		browser.waitForTitle(step.getActualValue());
	}
	
	@Keyword("WaitForFrameAndSwitchToIt")
	public void waitForFrameAndSwitchToIt(KeywordPropObject step) throws Exception{
		browser.waitForFrameToBeAvailableAndSwitchToIt(step.getActualValue());
	}
	
	@Keyword("SwitchToDefault")
	public void switchToDefault(KeywordPropObject step) throws Exception{
		browser.switchToDefault();
	}
	
	@Keyword("Get")
	public void get(KeywordPropObject step){
		browser.get(step.getActualValue());
	}
	
	@Keyword("Type")
	public void type(KeywordPropObject step){
		browser.type(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("Clear")
	public void clear(KeywordPropObject step){
		browser.clear(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("ClearAndType")
	public void clearAndType(KeywordPropObject step){
		browser.clearAndType(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("TypeRandomAlphabets")
	public void typeUnique(KeywordPropObject step){
		String typeValue = step.getActualValue()+RandomStringUtils.randomAlphabetic(8);
		browser.type(browser.findElement(step.getTargetValue()),typeValue);
		step.setActualValue(typeValue);
	}
	
	@Keyword("ClearAndTypeRandomAlphabets")
	public void clearAndTypeUnique(KeywordPropObject step){
		String typeValue = step.getActualValue()+RandomStringUtils.randomAlphabetic(8);
		browser.clearAndType(browser.findElement(step.getTargetValue()),typeValue);
		step.setActualValue(typeValue);
	}
	
	@Keyword("TypeDate")
	public void typeDate(KeywordPropObject step) throws Exception{
		Calendar cal = Calendar.getInstance();
		String value = step.getActualValue();
		if(value.equals(null) || value.equals("")){
			cal.add(Calendar.DATE, 0);
		}else{
			cal.add(Calendar.DATE, Integer.parseInt(value));
		}
		if(step.getOptions()==null || step.getOptions().equals("")){
			SimpleDateFormat defaultFormat = new SimpleDateFormat(configProp.getProperty("dateFormat"));
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
	
	@Keyword("Click")
	public void click(KeywordPropObject step) throws Exception{
		browser.click(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("ClickAt")
	public void clickAt(KeywordPropObject step) throws Exception{
		browser.clickAt(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("DoubleClick")
	public void doubleClick(KeywordPropObject step) throws Exception{
		browser.doubleClick(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("Check")
	public void check(KeywordPropObject step) throws Exception{
		browser.check(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("UnCheck")
	public void unCheck(KeywordPropObject step) throws Exception{
		browser.uncheck(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("SelectByText")
	public void selectByText(KeywordPropObject step) throws Exception{
		browser.selectByText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("ContextMenu")
	public void contextMenu(KeywordPropObject step) throws Exception{
		browser.contextMenu(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("ContextMenuAt")
	public void contextMenuAt(KeywordPropObject step) throws Exception{
		browser.contextMenuAt(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("MouseDown")
	public void mouseDown(KeywordPropObject step) throws Exception{
		browser.mouseDown(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("MouseDownAt")
	public void mouseDownAt(KeywordPropObject step) throws Exception{
		browser.mouseDownAt(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("MouseUp")
	public void mouseUp(KeywordPropObject step) throws Exception{
		browser.mouseUp(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("MouseUpAt")
	public void mouseUpAt(KeywordPropObject step) throws Exception{
		browser.mouseUpAt(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("MouseOver")
	public void mouseOver(KeywordPropObject step) throws Exception{
		browser.mouseOver(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("Focus")
	public void focus(KeywordPropObject step) throws Exception{
		browser.focus(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("KeyDown")
	public void keyDown(KeywordPropObject step) throws Exception{
		browser.keyDown(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("KeyUp")
	public void keyUp(KeywordPropObject step) throws Exception{
		browser.keyUp(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("ControlKeyUp")
	public void keyControlKeyUp(KeywordPropObject step) throws Exception{
		browser.controlKeyUp();
	}
	
	@Keyword("ControlKeyDown")
	public void controlKeyDown(KeywordPropObject step) throws Exception{
		browser.controlKeyDown();
	}
	
	@Keyword("ChooseOK")
	public void chooseOK(KeywordPropObject step) throws Exception{
		browser.chooseOk();
	}
	
	@Keyword("ChooseCancel")
	public void chooseCancel(KeywordPropObject step) throws Exception{
		browser.chooseCancel();
	}
	
	@Keyword("CaptureScreenshot")
	public void captureScreenShot(KeywordPropObject step) throws Exception{
		browser.captureScreenshot(step.getScreenShotPath());
	}
	
	@Keyword("AssertTitle")
	public void asertTitle(KeywordPropObject step) throws Exception{
		browser.assertTitle(step.getActualValue());
	}
	
	@Keyword("VerifyTitle")
	public void verifyTitle(KeywordPropObject step) throws Exception{
		browser.verifyTitle(step.getActualValue());
	}
	
	@Keyword("AssertNotTitle")
	public void asertNotTitle(KeywordPropObject step) throws Exception{
		browser.assertNotTitle(step.getActualValue());
	}
	
	@Keyword("VerifyNotTitle")
	public void verifyNotTitle(KeywordPropObject step) throws Exception{
		browser.verifyNotTitle(step.getActualValue());
	}
	
	@Keyword("AssertSelectedText")
	public void assertSelectedText(KeywordPropObject step) throws Exception{
		browser.assertSelectedText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("VerifySelectedText")
	public void verifySelectedLabel(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertSelectedText(step);
	}
	
	@Keyword("AssertNotSelectedText")
	public void assertNotSelectedText(KeywordPropObject step) throws Exception{
		browser.assertNotSelectedText(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("VerifyNotSelectedText")
	public void verifyNotSelectedLabel(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotSelectedText(step);
	}
	
	@Keyword("AssertElementPresent")
	public void assertElementPresent(KeywordPropObject step) throws Exception{
		browser.assertElementPresent(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("VerifyElementPresent")
	public void verifyElementPresent(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertElementPresent(step);
	}
	
	@Keyword("AssertElementNotPresent")
	public void assertElementNotPresent(KeywordPropObject step) throws Exception{
		browser.assertElementNotPresent(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("VerifyElementNotPresent")
	public void verifyElementNotPresent(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertElementNotPresent(step);
	}
	
	@Keyword("AssertChecked")
	public void assertChecked(KeywordPropObject step) throws Exception{
		browser.assertChecked(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("VerifyChecked")
	public void verifyChecked(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertChecked(step);
	}
	
	@Keyword("AssertNotChecked")
	public void assertNotChecked(KeywordPropObject step) throws Exception{
		browser.assertNotChecked(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("VerifyNotChecked")
	public void verifyNotChecked(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotChecked(step);
	}
	
	@Keyword("AssertSelectOptions")
	public void assertSelectOptions(KeywordPropObject step) throws Exception{
		browser.assertSelectOptions(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("VerifySelectOptions")
	public void verifySelectOptions(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertSelectOptions(step);
	}
	
	@Keyword("AssertNotSelectOptions")
	public void assertNotSelectOptions(KeywordPropObject step) throws Exception{
		browser.assertNotSelectOptions(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("VerifyNotSelectOptions")
	public void verifyNotSelectOptions(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotSelectOptions(step);
	}
	
	@Keyword("AssertSelectOptionsSize")
	public void assertSelectOptionsSize(KeywordPropObject step) throws Exception{
		browser.assertSelectOptionsSize(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("VerifySelectOptionsSize")
	public void verifySelectOptionsSize(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertSelectOptionsSize(step);
	}
	
	@Keyword("AssertNotSelectOptionsSize")
	public void assertNotSelectOptionsSize(KeywordPropObject step) throws Exception{
		browser.assertNotSelectOptionsSize(browser.findElement(step.getTargetValue()),step.getActualValue());
	}
	
	@Keyword("VerifyNotSelectOptionsSize")
	public void verifyNotSelectOptionsSize(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotSelectOptionsSize(step);
	}
	
	@Keyword("AssertEditable")
	public void assertEditable(KeywordPropObject step) throws Exception{
		browser.assertEditable(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("VerifyEditable")
	public void verifyEditable(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertEditable(step);
	}
	
	@Keyword("AssertNotEditable")
	public void assertNotEditable(KeywordPropObject step) throws Exception{
		browser.assertNotEditable(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("VerifyNotEditable")
	public void verifyNotEditable(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotEditable(step);
	}
	
	@Keyword("AssertAlert")
	public void assertAlert(KeywordPropObject step) throws Exception{
		browser.assertAlert(step.getActualValue());
	}
	
	@Keyword("VerifyAlert")
	public void verifyAlert(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertAlert(step);
	}
	
	@Keyword("AssertNotAlert")
	public void assertNotAlert(KeywordPropObject step) throws Exception{
		browser.assertNotAlert(step.getActualValue());
	}
	
	@Keyword("VerifyNotAlert")
	public void verifyNotAlert(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotAlert(step);
	}
	
	@Keyword("AssertVisible")
	public void assertVisible(KeywordPropObject step) throws Exception{
		browser.assertVisible(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("VerifyVisible")
	public void verifyVisible(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertVisible(step);
	}
	
	@Keyword("AssertNotVisible")
	public void assertNotVisible(KeywordPropObject step) throws Exception{
		browser.assertNotVisible(browser.findElement(step.getTargetValue()));
	}
	
	@Keyword("VerifyNotVisible")
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
	
	@Keyword("AssertAttribute")
	public void assertAttribute(KeywordPropObject step) throws Exception{
		HashMap<String, String> locatorAndAttr = getAttributeNameFromLocator(step.getTargetValue());
		browser.assertAttribute(browser.findElement(locatorAndAttr.get("locator")), locatorAndAttr.get("attribute") ,step.getActualValue());
	}
	
	@Keyword("VerifyAttribute")
	public void verifyAttribute(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertAttribute(step);
	}
	
	@Keyword("AssertNotAttribute")
	public void assertNotAttribute(KeywordPropObject step) throws Exception{
		HashMap<String, String> locatorAndAttr = getAttributeNameFromLocator(step.getTargetValue());
		browser.assertNotAttribute(browser.findElement(locatorAndAttr.get("locator")), locatorAndAttr.get("attribute") ,step.getActualValue());
	}
	
	@Keyword("VerifyNotAttribute")
	public void verifyNotAttribute(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotAttribute(step);
	}
	
	@Keyword("AssertValue")
	public void assertValue(KeywordPropObject step) throws Exception{
		browser.assertValue(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Keyword("VerifyValue")
	public void verifyValue(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertValue(step);
	}
	
	@Keyword("AssertNotValue")
	public void assertNotValue(KeywordPropObject step) throws Exception{
		browser.assertNotValue(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Keyword("VerifyNotValue")
	public void verifyNotValue(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotValue(step);
	}
	
	@Keyword("AssertText")
	public void assertText(KeywordPropObject step) throws Exception{
		browser.assertText(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Keyword("VerifyText")
	public void verifyText(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertText(step);
	}
	
	@Keyword("AssertNotText")
	public void assertNotText(KeywordPropObject step) throws Exception{
		browser.assertNotText(browser.findElement(step.getTargetValue()), step.getActualValue());
	}
	
	@Keyword("VerifyNotText")
	public void verifyNotText(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotText(step);
	}
	
	@Keyword("IfElementPresent")
	public void ifElementPresent(KeywordPropObject step) throws Exception{
		if(!browser.isElementPresent(browser.findElement(step.getTargetValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword("IfElementNotPresent")
	public void ifElementNotPresent(KeywordPropObject step) throws Exception{
		if(browser.isElementPresent(browser.findElement(step.getTargetValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword("IfElementVisible")
	public void ifElementVisible(KeywordPropObject step) throws Exception{
		if(!browser.isVisible(browser.findElement(step.getTargetValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword("IfElementNotVisible")
	public void ifElementNotVisible(KeywordPropObject step) throws Exception{
		if(browser.isVisible(browser.findElement(step.getTargetValue()))){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword("IfElementValueEquals")
	public void ifElementValueEquals(KeywordPropObject step) throws Exception{
		if(!browser.getValue(browser.findElement(step.getTargetValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword("IfElementValueNotEquals")
	public void ifElementValueNotEquals(KeywordPropObject step) throws Exception{
		if(browser.getValue(browser.findElement(step.getTargetValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword("IfElementTextEquals")
	public void ifElementTextEquals(KeywordPropObject step) throws Exception{
		if(!browser.getText(browser.findElement(step.getTargetValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword("IfElementTextNotEquals")
	public void ifElementTextNotEquals(KeywordPropObject step) throws Exception{
		if(browser.getText(browser.findElement(step.getTargetValue())).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
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
	
}
