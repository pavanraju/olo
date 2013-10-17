package com.olo.bot;


import static com.olo.util.PropertyReader.configProp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.olo.util.Commons;
import com.olo.util.OloExpectedConditions;
import com.olo.util.VerificationErrors;


public class BrowserBot{
	
	private final WebDriver driver;
	
	protected long pageWaitAndWaitTimeOut=30;
	
	private static final Logger logger = LogManager.getLogger(BrowserBot.class.getName());

	public BrowserBot(WebDriver driver) {
		this.driver = driver;
		if(configProp.containsKey("pageWaitAndWaitTimeOut")){
			pageWaitAndWaitTimeOut=Integer.parseInt(configProp.getProperty("pageWaitAndWaitTimeOut"));
		}
	}
	
	public void Wait(int timeOutSec) throws Exception {
			Thread.sleep(timeOutSec*1000);
	}
	
	public void waitForFrameToBeAvailableAndSwitchToIt(String frameLocator,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameToBeAvailableAndSwitchToIt(String frameLocator){
		new WebDriverWait(driver, pageWaitAndWaitTimeOut).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForElementPresent(By by) throws Exception{
		new WebDriverWait(driver, pageWaitAndWaitTimeOut).until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	public void waitForElementPresent(By by,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	public void waitForElementNotPresent(WebElement element){
		new WebDriverWait(driver, pageWaitAndWaitTimeOut).until(OloExpectedConditions.elementNotPresent(element));
	}
	
	public void waitForElementNotPresent(WebElement element,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(OloExpectedConditions.elementNotPresent(element));
	}
	
	public void waitForElementNotPresent(By by){
		new WebDriverWait(driver, pageWaitAndWaitTimeOut).until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(by)));
	}
	
	public void waitForElementNotPresent(By by,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(by)));
	}
	
	public void waitForVisible(WebElement element){
		new WebDriverWait(driver, pageWaitAndWaitTimeOut).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForNotVisible(WebElement element) throws Exception{
		new WebDriverWait(driver, pageWaitAndWaitTimeOut).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
	}
	
	public void waitForVisible(WebElement element,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForNotVisible(final WebElement element,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
	}
	
	public void waitForValue(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, pageWaitAndWaitTimeOut) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return getValue(element).equals(value);
    		}
    	});
	}
	
	public void waitForNotValue(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, pageWaitAndWaitTimeOut) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return !getValue(element).equals(value);
    		}
    	});
	}
	
	public void waitForValue(final WebElement element, final String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return getValue(element).equals(value);
    		}
    	});
	}
	
	public void waitForNotValue(final WebElement element, final String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return !getValue(element).equals(value);
    		}
    	});
	}
	
	public void waitForEditable(final WebElement element) throws Exception{
		new WebDriverWait(driver, pageWaitAndWaitTimeOut) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return isElementPresent(element) && isVisible(element) && isEnabled(element);
    		}
    	});
	}
	
	public void waitForNotEditable(final WebElement element) throws Exception{
		new WebDriverWait(driver, pageWaitAndWaitTimeOut) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return isElementPresent(element) && isVisible(element) && !isEnabled(element);
    		}
    	});
	}
	
	public void waitForEditable(final WebElement element,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return isElementPresent(element) && isVisible(element) && isEnabled(element);
    		}
    	});
	}
	
	public void waitForNotEditable(final WebElement element,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return isElementPresent(element) && isVisible(element) && !isEnabled(element);
    		}
    	});
	}
	
	public void waitForText(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, pageWaitAndWaitTimeOut) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return getText(element).equals(value);
    		}
    	});
	}
	
	public void waitForNotText(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, pageWaitAndWaitTimeOut) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return !getText(element).equals(value);
    		}
    	});
	}
	
	public void waitForText(final WebElement element, final String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return getText(element).equals(value);
    		}
    	});
	}
	
	public void waitForNotText(final WebElement element, final String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return !getText(element).equals(value);
    		}
    	});
	}
	
	public void waitForAlert(final String pattern){
		new WebDriverWait(driver, pageWaitAndWaitTimeOut).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
            	return d.switchTo().alert().getText().equals(pattern);
            }
        });
	}
	
	public void waitForAlert(final String pattern,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
            	return d.switchTo().alert().getText().equals(pattern);
            }
        });
	}
	
	public void waitForAlertPresent(){
		new WebDriverWait(driver, pageWaitAndWaitTimeOut).until(ExpectedConditions.alertIsPresent());
	}
	
	public void waitForAlertPresent(long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.alertIsPresent());
	}
	
	public void waitForTitle(String pageTitle){
		new WebDriverWait(driver,pageWaitAndWaitTimeOut).until(ExpectedConditions.titleIs(pageTitle));
	}
	
	public void waitForTitle(String pageTitle,long timeOutInSeconds){
		new WebDriverWait(driver,timeOutInSeconds).until(ExpectedConditions.titleIs(pageTitle));
	}
	
	public void assertFail(String errorMessage){
		Assert.fail(errorMessage);
	}
	
	public void verifyFail(String errorMessage) throws Exception{
		try {
			assertFail(errorMessage);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertTrue(boolean condition,String message){
		Assert.assertTrue(condition, message);
	}
	
	public void verifyTrue(boolean condition,String message) throws Exception{
		try {
			assertTrue(condition,message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertTrue(boolean condition){
		Assert.assertTrue(condition);
	}
	
	public void verifyTrue(boolean condition) throws Exception{
		try {
			assertTrue(condition);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertFalse(boolean condition,String message){
		Assert.assertFalse(condition, message);
	}
	
	public void verifyFalse(boolean condition,String message) throws Exception{
		try {
			assertFalse(condition,message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertFalse(boolean condition){
		Assert.assertTrue(condition);
	}
	
	public void verifyFalse(boolean condition) throws Exception{
		try {
			assertTrue(condition);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertTitle(String expectedTitle) throws Exception{
		Assert.assertEquals(getTitle(),expectedTitle);
	}
	
	public void verifyTitle(String expectedTitle) throws Exception{
		try {
			assertTitle(expectedTitle);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotTitle(String unexpectedTitle) throws Exception{
		Assert.assertNotEquals(getTitle(),unexpectedTitle);
	}
	
	public void verifyNotTitle(String unexpectedTitle) throws Exception{
		try {
			assertNotTitle(unexpectedTitle);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertValue(WebElement element, String expectedValue){
		String actualValue = getAttribute(element,"value");
		logger.info("Comparing elementValue : "+actualValue +" equals to expectedValue : "+expectedValue);
		Assert.assertEquals( actualValue, expectedValue);
	}
	
	public void verifyValue(WebElement element, String expectedValue) throws Exception{
		try {
			assertValue(element, expectedValue);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotValue(WebElement element,String unexpectedValue) throws Exception {
		String actualValue = getAttribute(element,"value");
		logger.info("Comparing elementValue : "+actualValue +" not equals to unexpectedValue : "+unexpectedValue);
		Assert.assertNotEquals(actualValue, unexpectedValue);
	}
	
	public void verifyNotValue(WebElement element,String unexpectedValue) throws Exception {
		try {
			assertNotValue(element, unexpectedValue);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertText(WebElement element, String expectedText){
		String actualText = getText(element).toString();
		logger.info("Comparing elementText : "+actualText +" equals to expectedText : "+expectedText);
		Assert.assertEquals(actualText, expectedText);
	}
	
	public void verifyText(WebElement element, String expectedText) throws Exception{
		try {
			assertText(element, expectedText);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotText(WebElement element,String unexpectedText) throws Exception{
		String actualText = getText(element);
		logger.info("Comparing elementText : "+actualText +" not equals to unexpectedText : "+unexpectedText);
		Assert.assertNotEquals(actualText, unexpectedText);
	}
	
	public void verifyNotText(WebElement element,String unexpectedText) throws Exception{
		try {
			assertNotText(element, unexpectedText);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertSelectedText(WebElement element, String expectedSelectedText){
		Assert.assertEquals(element.getAttribute("value"), expectedSelectedText);
	}
	
	public void verifySelectedText(WebElement element, String expectedSelectedText) throws Exception{
		try {
			assertSelectedText(element, expectedSelectedText);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotSelectedText(WebElement element, String unexpectedSelectedText){
		Assert.assertNotEquals(element.getAttribute("value"), unexpectedSelectedText);
	}
	
	public void verifyNotSelectedText(WebElement element, String unexpectedSelectedText) throws Exception{
		try {
			assertSelectedText(element, unexpectedSelectedText);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertElementPresent(WebElement element){
		Assert.assertTrue(isElementPresent(element));
	}
	
	public void verifyElementPresent(WebElement element) throws Exception{
		try {
			assertElementPresent(element);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertElementNotPresent(WebElement element) throws Exception{
		Assert.assertFalse(isElementPresent(element));
	}
	
	public void verifyElementNotPresent(WebElement element) throws Exception{
		try {
			assertElementNotPresent(element);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertChecked(WebElement element){
		Assert.assertTrue(element.isSelected());
	}
	
	public void verifyChecked(WebElement element) throws Exception{
		try {
			assertChecked(element);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotChecked(WebElement element){
		Assert.assertFalse(element.isSelected());
	}
	
	public void verifyNotChecked(WebElement element) throws Exception{
		try {
			assertNotChecked(element);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertSelectOptions(WebElement element,String expectedOptions) throws Exception{
		String expected[] = expectedOptions.split(",");
		List<WebElement> dropDownElement = select(element).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Assert.assertEquals(actual, expected);
	}
	
	public void verifySelectOptions(WebElement element,String expectedOptions) throws Exception{
		try {
			assertSelectOptions(element, expectedOptions);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotSelectOptions(WebElement element,String unexpectedOptions) throws Exception{
		String expected[] = unexpectedOptions.split(",");
		List<WebElement> dropDownElement = select(element).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Assert.assertNotEquals(actual, expected);
	}
	
	public void verifyNotSelectOptions(WebElement element,String unexpectedOptions) throws Exception{
		try {
			assertNotSelectOptions(element, unexpectedOptions);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertSelectOptionsSize(WebElement element,String expectedSize) throws Exception {
		Assert.assertEquals(select(element).getOptions().size(),expectedSize);
	}
	
	public void verifySelectOptionsSize(WebElement element,String expectedSize) throws Exception {
		try {
			assertSelectOptionsSize(element, expectedSize);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotSelectOptionsSize(WebElement element,String expectedNotSize) throws Exception {
		Assert.assertNotEquals(select(element).getOptions().size(),expectedNotSize);
	}
	
	public void verifyNotSelectOptionsSize(WebElement element,String expectedNotSize) throws Exception {
		try {
			assertNotSelectOptionsSize(element, expectedNotSize);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertVisible(WebElement element) throws Exception {
		Assert.assertTrue(isVisible(element));
	}
	
	public void verifyVisible(WebElement element) throws Exception {
		try {
			assertVisible(element);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotVisible(WebElement element) throws Exception{
		Assert.assertFalse(isVisible(element));
	}
	
	public void verifyNotVisible(WebElement element) throws Exception{
		try {
			assertNotVisible(element);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertEditable(WebElement element) throws Exception {
		Assert.assertTrue(isEnabled(element));
	}
	
	public void verifyEditable(WebElement element) throws Exception {
		try {
			assertEditable(element);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotEditable(WebElement element) throws Exception{
		Assert.assertFalse(isEnabled(element));
	}
	
	public void verifyNotEditable(WebElement element) throws Exception{
		try {
			assertNotEditable(element);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertAlert(String expectedAlertText) throws Exception{
		Assert.assertEquals(driver.switchTo().alert().getText(), expectedAlertText);
	}
	
	public void verifyAlert(String expectedAlertText) throws Exception{
		try {
			assertAlert(expectedAlertText);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotAlert(String unexpectedAlertText) throws Exception{
		Assert.assertNotEquals(driver.switchTo().alert().getText(), unexpectedAlertText);
	}
	
	public void verifyNotAlert(String unexpectedAlertText) throws Exception{
		try {
			assertNotAlert(unexpectedAlertText);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertAttribute(WebElement element,String attributeName,String expectedAttributeValue) throws Exception{
		Assert.assertEquals(getAttribute(element, attributeName), expectedAttributeValue);
	}
	
	public void verifyAttribute(WebElement element,String attributeName,String expectedAttributeValue) throws Exception{
		try {
			assertAttribute(element, attributeName, expectedAttributeValue);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public void assertNotAttribute(WebElement element,String attributeName,String unexpectedAttributeValue) throws Exception{
		Assert.assertNotEquals(getAttribute(element, attributeName), unexpectedAttributeValue);
	}
	
	public void verifyNotAttribute(WebElement element,String attributeName,String unexpectedAttributeValue) throws Exception{
		try {
			assertNotAttribute(element, attributeName, unexpectedAttributeValue);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	public Select select(WebElement element){
		return new Select(element);
	}
	
	public Keyboard getKeyBoard(){
		return ((HasInputDevices) driver).getKeyboard();
	}
	
	public Mouse getMouse(){
		return ((HasInputDevices) driver).getMouse();
	}
	
	public Actions actions(){
		return new Actions(driver);
	}
	
	public String getBrowserName(){
		return ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
	}
	
	public String getBrowserVersion(){
		return ((RemoteWebDriver) driver).getCapabilities().getVersion();
	}
	
	public void get(String url){
		driver.get(url);
	}
	
	public String getTitle(){
		return driver.getTitle();
	}
	
	public String getCurrentUrl(){
		return driver.getCurrentUrl();
	}
	
	public boolean isElementPresent(WebElement element){
		try {
			element.getTagName();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
	}
	
	public boolean isVisible(WebElement element){
		return element.isDisplayed();
	}
	
	public boolean isEnabled(WebElement element){
		return element.isEnabled();
	}
	
	public String getText(WebElement element){
		return element.getText();
	}
	
	public String getTextFromHiddenElement(WebElement element){
		return executeJavascript(element, "return arguments[0].innerHTML");
	}
	
	public String getValue(WebElement element){
		return element.getAttribute("value");
	}
	
	public String getAttribute(WebElement element,String attributeName){
		return element.getAttribute(attributeName);
	}
	
	public String getTagName(WebElement element){
		return element.getTagName();
	}
	
	public WebElement findElement(String locator){
		return driver.findElement(byLocator(locator));
	}
	
	public WebElement findElement(By by){
		return driver.findElement(by);
	}
	
	public List<WebElement> findElements(String locator){
		return driver.findElements(byLocator(locator));
	}
	
	public List<WebElement> findElements(By by){
		return driver.findElements(by);
	}
	
	public void clear(WebElement element){
		element.clear();
	}
	
	public void type(WebElement element,String value){
		element.sendKeys(value);
	}
	
	public void clearAndType(WebElement element,String value){
		element.clear();
		element.sendKeys(value);
	}
	
	public void typeRandomAlphabets(WebElement element,String value){
		element.sendKeys(value+RandomStringUtils.randomAlphabetic(8));
	}
	
	public void typeRandomRandomAlphabets(WebElement element){
		element.sendKeys(RandomStringUtils.randomAlphabetic(8));
	}
	
	public void clearAndTypeRandomAlphabets(WebElement element,String value){
		element.clear();
		element.sendKeys(value+RandomStringUtils.randomAlphabetic(8));
	}
	
	public void clearAndTypeRandomAlphabets(WebElement element){
		element.clear();
		element.sendKeys(RandomStringUtils.randomAlphabetic(8));
	}
	
	public void typeRandomNumbers(WebElement element,String value){
		element.sendKeys(value+RandomStringUtils.randomNumeric(8));
	}
	
	public void typeRandomNumbers(WebElement element){
		element.sendKeys(RandomStringUtils.randomNumeric(8));
	}
	
	public void clearAndTypeRandomNumbers(WebElement element,String value){
		element.clear();
		element.sendKeys(value+RandomStringUtils.randomNumeric(8));
	}
	
	public void clearAndTypeRandomNumbers(WebElement element){
		element.clear();
		element.sendKeys(RandomStringUtils.randomNumeric(8));
	}
	
	public void typeRandomAlphaNumeric(WebElement element,String value){
		element.sendKeys(value+RandomStringUtils.randomAlphanumeric(8));
	}
	
	public void typeRandomAlphaNumeric(WebElement element){
		element.sendKeys(RandomStringUtils.randomAlphanumeric(8));
	}
	
	public void clearAndTypeRandomAlphaNumeric(WebElement element,String value){
		element.clear();
		element.sendKeys(value+RandomStringUtils.randomAlphanumeric(8));
	}
	
	public void clearAndTypeRandomAlphaNumeric(WebElement element){
		element.clear();
		element.sendKeys(RandomStringUtils.randomAlphanumeric(8));
	}
	
	public void click(WebElement element) throws Exception{
		element.click();
	}
	
	public void clickAt(WebElement element,String value){
		String[] v = value.split(",");
		new Actions(driver).moveByOffset(Integer.parseInt(v[0]), Integer.parseInt(v[1])).click(element).perform();
	}
	
	public void doubleClick(WebElement element){
		new Actions(driver).doubleClick(element).perform();
	}
	
	public void selectByText(WebElement element, String visibleText){
		select(element).selectByVisibleText(visibleText);
	}
	
	public void dragAndDrop(WebElement element,String value) throws Exception {
		String[] v = value.split(",");
		new Actions(driver).dragAndDropBy(element, Integer.parseInt(v[0]), Integer.parseInt(v[1]));
	}
	
	public void contextMenu(WebElement element){
		new Actions(driver).contextClick(element).perform();
	}
	
	public void contextMenuAt(WebElement element,String value){
		String[] v = value.split(",");
		new Actions(driver).moveByOffset(Integer.parseInt(v[0]), Integer.parseInt(v[1])).contextClick(element).perform();
	}
	
	public void mouseDown(WebElement element){
		getMouse().mouseDown((Coordinates) element.getLocation());
	}
	
	public void mouseDownAt(WebElement element,String coordString) throws Exception {
		String[] v = coordString.split(",");
		getMouse().mouseDown((Coordinates) element.getLocation().moveBy(Integer.parseInt(v[0]), Integer.parseInt(v[1])));
	}
	
	public void mouseUp(WebElement element){
		getMouse().mouseUp((Coordinates) element.getLocation());
	}
	
	public void mouseUpAt(WebElement element,String coordString){
		String[] v = coordString.split(",");
		getMouse().mouseUp((Coordinates) element.getLocation().moveBy(Integer.parseInt(v[0]), Integer.parseInt(v[1])));
	}
	
	public void mouseOver(WebElement element) throws Exception{
		new Actions(driver).moveToElement(element).build().perform();
	}
	
	public void focus(WebElement element){
		element.sendKeys(Keys.TAB);
	}
	
	public void keyDown(WebElement element, String value){
		new Actions(driver).keyDown(element, Keys.valueOf(value)).perform();
	}
	
	public void keyUp(WebElement element, String value){
		new Actions(driver).keyUp(element, Keys.valueOf(value)).perform();
	}
	
	public void controlKeyUp(){
		new Actions(driver).keyUp(Keys.CONTROL);
	}
	
	public void controlKeyDown(){
		new Actions(driver).keyDown(Keys.CONTROL);
	}
	
	public void chooseOk(){
		driver.switchTo().alert().accept();
	}
	
	public void chooseCancel(){
		driver.switchTo().alert().dismiss();
	}
	
	public void check(WebElement checkBoxElement){
		if (!checkBoxElement.getAttribute("type").toLowerCase().equals("checkbox")) {
			throw new InvalidElementStateException("This elementLocator is not a checkbox!");
        }
		if(!checkBoxElement.isSelected()){
			checkBoxElement.click();
		}
	}
	
	public void uncheck(WebElement checkBoxElement){
		if (!checkBoxElement.getAttribute("type").toLowerCase().equals("checkbox")) {
			throw new InvalidElementStateException("This elementLocator is not a checkbox!");
        }
		if(checkBoxElement.isSelected()){
			checkBoxElement.click();
		}
	}
	
	public void captureScreenshot(String screenShotPath) throws Exception{
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(screenShotPath));
	}
	
	public void deleteAllVisibleCookies(){
		driver.manage().deleteAllCookies();
	}
	
	public Object executeJavascript(String executeJavascript){
		return ((JavascriptExecutor)driver).executeScript(executeJavascript);
	}
	
	public String executeJavascript(WebElement webElement,String executeJavascript){
		return (String) ((JavascriptExecutor)driver).executeScript(executeJavascript, webElement);
	}
	
	public void switchToDefault(){
		driver.switchTo().defaultContent();
	}
	
	public void windowMaximize(){
		driver.manage().window().maximize();
	}
	
	public void windowFocus(){
		driver.switchTo().window(driver.getWindowHandle());
	}
	
	public String getWindowHandle(){
		return driver.getWindowHandle();
	}
	
	public Set<String> getWindowHandles(){
		return driver.getWindowHandles();
	}
	
	public void switchToWindow(String nameOrHandle){
		driver.switchTo().window(nameOrHandle);
	}
	
	public long getTimeOut(){
		return pageWaitAndWaitTimeOut;
	}
	
	public By byLocator(String locator) {
		if(locator.startsWith("css=")){
			locator=locator.replaceFirst("css=", "");
			return By.cssSelector(locator);
		}else if(locator.startsWith("xpath=")){
			locator=locator.replaceFirst("xpath=", "");
			return By.xpath(locator);
		}else if(locator.startsWith("class=")){
			locator=locator.replaceFirst("class=", "");
			return By.className(locator);
		}else if(locator.startsWith("id=")){
			locator=locator.replaceFirst("id=", "");
			return By.id(locator);
		}else if(locator.startsWith("linkText=")){
			locator=locator.replaceFirst("linkText=", "");
			return By.linkText(locator);
		}else if(locator.startsWith("partialLinkText=")){
			locator=locator.replaceFirst("partialLinkText=", "");
			return By.partialLinkText(locator);
		}else if(locator.startsWith("name=")){
			locator=locator.replaceFirst("name=", "");
			return By.partialLinkText(locator);
		}else{
			return By.id(locator);
		}
	}
	
	protected void addVerificationError(AssertionError e) throws Exception{
		ITestResult testResult = Reporter.getCurrentTestResult();
		String screenShotFileName = System.currentTimeMillis()+".png";
		String screenShotPath = testResult.getTestContext().getOutputDirectory()+File.separator+"screenshots"+File.separator+screenShotFileName;
		captureScreenshot(screenShotPath);
		HashMap<String, Object> errorDetails = new HashMap<String, Object>();
		errorDetails.put("message", e.getMessage());
		String stackTrace = null;
		if(e.getStackTrace() != null) {
			stackTrace = Commons.getStackTraceAsString(e);
			logger.error("Verification Error : "+stackTrace);
		}else{
			logger.error("Verification Error : Null");
		}
		errorDetails.put("stackTrace", stackTrace);
		errorDetails.put("screenshot", screenShotFileName);
		VerificationErrors.addError(testResult, errorDetails);
	}
	
}
