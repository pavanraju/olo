package com.olo.keywords;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONObject;

import com.olo.annotations.Keyword;
import com.olo.bot.OloBrowserBot;
import com.olo.propobject.KeywordPropObject;
import com.olo.util.Commons;

public class OloKeywords{
	
	private OloBrowserBot browser;
	
	public OloKeywords(OloBrowserBot webBot){
		this.browser=webBot;
	}
	
	@Keyword(name="Wait")
	public void Wait(KeywordPropObject step) throws Exception{
		browser.Wait(Integer.parseInt(step.getActualValue()));
	}
	
	@Keyword(name="WaitForPageToLoad")
	public void waitForPageToLoad(KeywordPropObject step) throws Exception{
		step.setStartTime(System.currentTimeMillis());
		browser.waitForPageToLoad();
		step.setEndTime(System.currentTimeMillis());
	}
	
	@Keyword(name="WaitForElementPresent")
	public void waitForElementPresent(KeywordPropObject step) throws Exception{
		browser.waitForElementPresent(step.getPropertyValue());
	}
	
	@Keyword(name="WaitForElementNotPresent")
	public void waitForElementNotPresent(KeywordPropObject step) throws Exception{
		browser.waitForElementNotPresent(step.getPropertyValue());
	}
	
	@Keyword(name="WaitForVisible")
	public void waitForVisible(KeywordPropObject step) throws Exception{
		browser.waitForVisible(step.getPropertyValue());
	}
	
	@Keyword(name="WaitForNotVisible")
	public void waitForNotVisible(KeywordPropObject step) throws Exception{
		browser.waitForNotVisible(step.getPropertyValue());
	}
	
	@Keyword(name="WaitForValue")
	public void waitForValue(KeywordPropObject step) throws Exception{
		browser.waitForValue(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="WaitForNotValue")
	public void waitForNotValue(KeywordPropObject step) throws Exception{
		browser.waitForNotValue(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="WaitForEditable")
	public void waitForEditable(KeywordPropObject step) throws Exception{
		browser.waitForEditable(step.getPropertyValue());
	}
	
	@Keyword(name="WaitForNotEditable")
	public void waitForNotEditable(KeywordPropObject step) throws Exception{
		browser.waitForNotEditable(step.getPropertyValue());
	}
	
	@Keyword(name="WaitForText")
	public void waitForText(KeywordPropObject step) throws Exception{
		browser.waitForText(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="WaitForNotText")
	public void waitForNotText(KeywordPropObject step) throws Exception{
		browser.waitForNotText(step.getPropertyValue(),step.getActualValue());
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
		browser.type(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="Clear")
	public void clear(KeywordPropObject step){
		browser.clear(step.getPropertyValue());
	}
	
	@Keyword(name="ClearAndType")
	public void clearAndType(KeywordPropObject step){
		browser.clearAndType(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="TypeUnique")
	public void typeUnique(KeywordPropObject step){
		String typeValue = step.getActualValue()+Commons.getRandomNumber();
		browser.type(step.getPropertyValue(),typeValue);
		step.setActualValue(typeValue);
	}
	
	@Keyword(name="ClearAndTypeUnique")
	public void clearAndTypeUnique(KeywordPropObject step){
		String typeValue = step.getActualValue()+Commons.getRandomNumber();
		browser.clearAndTypeUnique(step.getPropertyValue(),typeValue);
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
		
		browser.type(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="Click")
	public void click(KeywordPropObject step) throws Exception{
		browser.click(step.getPropertyValue());
	}
	
	@Keyword(name="ClickAt")
	public void clickAt(KeywordPropObject step) throws Exception{
		browser.clickAt(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="DoubleClick")
	public void doubleClick(KeywordPropObject step) throws Exception{
		browser.doubleClick(step.getPropertyValue());
	}
	
	@Keyword(name="Check")
	public void check(KeywordPropObject step) throws Exception{
		browser.check(step.getPropertyValue());
	}
	
	@Keyword(name="UnCheck")
	public void unCheck(KeywordPropObject step) throws Exception{
		browser.uncheck(step.getPropertyValue());
	}
	
	@Keyword(name="SelectByText")
	public void selectByText(KeywordPropObject step) throws Exception{
		browser.selectByText(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="ContextMenu")
	public void contextMenu(KeywordPropObject step) throws Exception{
		browser.contextMenu(step.getPropertyValue());
	}
	
	@Keyword(name="ContextMenuAt")
	public void contextMenuAt(KeywordPropObject step) throws Exception{
		browser.contextMenuAt(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="MouseDown")
	public void mouseDown(KeywordPropObject step) throws Exception{
		browser.mouseDown(step.getPropertyValue());
	}
	
	@Keyword(name="MouseDownAt")
	public void mouseDownAt(KeywordPropObject step) throws Exception{
		browser.mouseDownAt(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="MouseUp")
	public void mouseUp(KeywordPropObject step) throws Exception{
		browser.mouseUp(step.getPropertyValue());
	}
	
	@Keyword(name="MouseUpAt")
	public void mouseUpAt(KeywordPropObject step) throws Exception{
		browser.mouseUpAt(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="MouseOver")
	public void mouseOver(KeywordPropObject step) throws Exception{
		browser.mouseOver(step.getPropertyValue());
	}
	
	@Keyword(name="Focus")
	public void focus(KeywordPropObject step) throws Exception{
		browser.focus(step.getPropertyValue());
	}
	
	@Keyword(name="KeyDown")
	public void keyDown(KeywordPropObject step) throws Exception{
		browser.keyDown(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="KeyUp")
	public void keyUp(KeywordPropObject step) throws Exception{
		browser.keyUp(step.getPropertyValue(),step.getActualValue());
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
		browser.assertSelectedText(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="VerifySelectedText")
	public void verifySelectedLabel(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertSelectedText(step);
	}
	
	@Keyword(name="AssertNotSelectedText")
	public void assertNotSelectedText(KeywordPropObject step) throws Exception{
		browser.assertNotSelectedText(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="VerifyNotSelectedText")
	public void verifyNotSelectedLabel(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotSelectedText(step);
	}
	
	@Keyword(name="AssertElementPresent")
	public void assertElementPresent(KeywordPropObject step) throws Exception{
		browser.assertElementPresent(step.getPropertyValue());
	}
	
	@Keyword(name="VerifyElementPresent")
	public void verifyElementPresent(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertElementPresent(step);
	}
	
	@Keyword(name="AssertChecked")
	public void assertChecked(KeywordPropObject step) throws Exception{
		browser.assertChecked(step.getPropertyValue());
	}
	
	@Keyword(name="VerifyChecked")
	public void verifyChecked(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertChecked(step);
	}
	
	@Keyword(name="AssertSelectOptions")
	public void assertSelectOptions(KeywordPropObject step) throws Exception{
		browser.assertSelectOptions(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="VerifySelectOptions")
	public void verifySelectOptions(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertSelectOptions(step);
	}
	
	@Keyword(name="AssertSelectOptionsSize")
	public void assertSelectOptionsCount(KeywordPropObject step) throws Exception{
		browser.assertSelectOptionsSize(step.getPropertyValue(),step.getActualValue());
	}
	
	@Keyword(name="VerifySelectOptionsSize")
	public void verifySelectOptionsCount(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertSelectOptionsCount(step);
	}
	
	@Keyword(name="AssertEditable")
	public void assertEditable(KeywordPropObject step) throws Exception{
		browser.assertEditable(step.getPropertyValue());
	}
	
	@Keyword(name="VerifyEditable")
	public void verifyEditable(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertEditable(step);
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
		browser.assertVisible(step.getActualValue());
	}
	
	@Keyword(name="VerifyVisible")
	public void verifyVisible(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertVisible(step);
	}
	
	@Keyword(name="AssertNotChecked")
	public void assertNotChecked(KeywordPropObject step) throws Exception{
		browser.assertNotChecked(step.getPropertyValue());
	}
	
	@Keyword(name="VerifyNotChecked")
	public void verifyNotChecked(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotChecked(step);
	}
	
	@Keyword(name="AssertElementNotPresent")
	public void assertElementNotPresent(KeywordPropObject step) throws Exception{
		browser.assertElementNotPresent(step.getPropertyValue());
	}
	
	@Keyword(name="VerifyElementNotPresent")
	public void verifyElementNotPresent(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertElementNotPresent(step);
	}
	
	@Keyword(name="AssertNotVisible")
	public void assertNotVisible(KeywordPropObject step) throws Exception{
		browser.assertNotVisible(step.getPropertyValue());
	}
	
	@Keyword(name="VerifyNotVisible")
	public void verifyNotVisible(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotVisible(step);
	}
	
	@Keyword(name="AssertNotEditable")
	public void assertNotEditable(KeywordPropObject step) throws Exception{
		browser.assertNotEditable(step.getPropertyValue());
	}
	
	@Keyword(name="VerifyNotEditable")
	public void verifyNotEditable(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotEditable(step);
	}
	
	@Keyword(name="AssertAttribute")
	public void assertAttribute(KeywordPropObject step) throws Exception{
		browser.assertAttribute(step.getPropertyValue(), step.getActualValue());
	}
	
	@Keyword(name="VerifyAttribute")
	public void verifyAttribute(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertAttribute(step);
	}
	
	@Keyword(name="AssertValue")
	public void assertValue(KeywordPropObject step) throws Exception{
		browser.assertValue(step.getPropertyValue(), step.getActualValue());
	}
	
	@Keyword(name="VerifyValue")
	public void verifyValue(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertValue(step);
	}
	
	@Keyword(name="AssertNotValue")
	public void assertNotValue(KeywordPropObject step) throws Exception{
		browser.assertNotValue(step.getPropertyValue(), step.getActualValue());
	}
	
	@Keyword(name="VerifyNotValue")
	public void verifyNotValue(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotValue(step);
	}
	
	@Keyword(name="AssertText")
	public void assertText(KeywordPropObject step) throws Exception{
		browser.assertText(step.getPropertyValue(), step.getActualValue());
	}
	
	@Keyword(name="VerifyText")
	public void verifyText(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertText(step);
	}
	
	@Keyword(name="AssertNotText")
	public void assertNotText(KeywordPropObject step) throws Exception{
		browser.assertNotText(step.getPropertyValue(), step.getActualValue());
	}
	
	@Keyword(name="VerifyNotText")
	public void verifyNotText(KeywordPropObject step) throws Exception{
		step.setIsVerification(true);
		assertNotText(step);
	}
	
	@Keyword(name="IfElementPresent")
	public void ifElementPresent(KeywordPropObject step) throws Exception{
		if(!browser.isElementPresent(step.getPropertyValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementNotPresent")
	public void ifElementNotPresent(KeywordPropObject step) throws Exception{
		if(browser.isElementPresent(step.getPropertyValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementVisible")
	public void ifElementVisible(KeywordPropObject step) throws Exception{
		if(!browser.isVisible(step.getPropertyValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementNotVisible")
	public void ifElementNotVisible(KeywordPropObject step) throws Exception{
		if(browser.isVisible(step.getPropertyValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementValueEquals")
	public void ifElementValueEquals(KeywordPropObject step) throws Exception{
		if(!browser.getValue(step.getPropertyValue()).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementValueNotEquals")
	public void ifElementValueNotEquals(KeywordPropObject step) throws Exception{
		if(browser.getValue(step.getPropertyValue()).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementTextEquals")
	public void ifElementTextEquals(KeywordPropObject step) throws Exception{
		if(!browser.getText(step.getPropertyValue()).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="IfElementTextNotEquals")
	public void ifElementTextNotEquals(KeywordPropObject step) throws Exception{
		if(browser.getText(step.getPropertyValue()).equals(step.getActualValue())){
			step.setIfSkipped(true);
		}
	}
	
	@Keyword(name="PutValueIn")
	public HashMap<String, String> putValueIn(KeywordPropObject step) throws Exception{
		HashMap<String, String> storeData = new HashMap<String, String>();
		storeData.put(step.getActualValue(), browser.getValue(step.getPropertyValue()));
		return storeData;
	}
	
	@Keyword(name="PutTextIn")
	public HashMap<String, String> putTextIn(KeywordPropObject step) throws Exception{
		HashMap<String, String> storeData = new HashMap<String, String>();
		storeData.put(step.getActualValue(), browser.getText(step.getPropertyValue()));
		return storeData;
	}
	
}
