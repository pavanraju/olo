package com.olo.bot;


import static com.olo.util.PropertyReader.configProp;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.HasInputDevices;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

import com.olo.util.Commons;


public class OloBrowserBot{
	
	private final WebDriver driver;
	
	private long explicitWait=30;

	public OloBrowserBot(WebDriver driver) {
		this.driver = driver;
		if(configProp.containsKey("explicitWait")){
			explicitWait=Integer.parseInt(configProp.getProperty("explicitWait"));
		}
		if(configProp.containsKey("implicitWait")){
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(configProp.getProperty("implicitWait")), TimeUnit.SECONDS);
		}
	}
	
	public WebDriver getDriver(){
		return driver;
	}
	
	public void implicitWait(){
		if(configProp.containsKey("implicitWait")){
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(configProp.getProperty("implicitWait")), TimeUnit.SECONDS);
		}
	}
	
	public void implicitWait(long sec){
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}
	
	public void Wait(int timeOutSec) throws Exception {
			Thread.sleep(timeOutSec*1000);
	}
	
	public void waitForPageToLoad(long timeOutInSeconds) throws Exception {
		if(((RemoteWebDriver) driver).getCapabilities().getBrowserName().equals("chrome")){
			driver.manage().timeouts().setScriptTimeout(timeOutInSeconds, TimeUnit.SECONDS);
		}else{
			driver.manage().timeouts().pageLoadTimeout(timeOutInSeconds, TimeUnit.SECONDS);
		}
	}
	
	public void waitForPageToLoad() throws Exception {
		if(((RemoteWebDriver) driver).getCapabilities().getBrowserName().equals("chrome")){
			driver.manage().timeouts().setScriptTimeout(explicitWait, TimeUnit.SECONDS);
		}else{
			driver.manage().timeouts().pageLoadTimeout(explicitWait, TimeUnit.SECONDS);
		}
	}
	
	public void waitForFrameToBeAvailableAndSwitchToIt(String frameLocator,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameToBeAvailableAndSwitchToIt(String frameLocator){
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForElementPresent(String locator) throws Exception{
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.presenceOfElementLocated(byLocator(locator)));
	}
	
	public void waitForElementNotPresent(String locator){
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.stalenessOf(driver.findElement(byLocator(locator))));
	}
	
	public void waitForElementPresent(String locator,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.presenceOfElementLocated(byLocator(locator)));
	}
	
	public void waitForElementNotPresent(String locator,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.stalenessOf(driver.findElement(byLocator(locator))));
	}
	
	public void waitForElementPresent(final WebElement element) throws Exception{
		new WebDriverWait(driver, explicitWait)
    		.until(new ExpectedCondition<Boolean>() {
    			public Boolean apply(WebDriver driver) {
    				return isElementPresent(element);
    			}
    		});
	}
	
	public void waitForElementNotPresent(WebElement element){
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.stalenessOf(element));
	}
	
	public void waitForElementPresent(final WebElement element,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds)
    		.until(new ExpectedCondition<Boolean>() {
    			public Boolean apply(WebDriver driver) {
    				return isElementPresent(element);
    			}
    		});
	}
	
	public void waitForElementNotPresent(WebElement element,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.stalenessOf(element));
	}
	
	public void waitForVisible(String locator){
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.visibilityOfElementLocated(byLocator(locator)));
	}
	
	public void waitForNotVisible(String locator){
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.invisibilityOfElementLocated(byLocator(locator)));
	}
	
	public void waitForVisible(String locator,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(byLocator(locator)));
	}
	
	public void waitForNotVisible(String locator,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(byLocator(locator)));
	}
	
	public void waitForVisible(WebElement element){
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForNotVisible(final WebElement element) throws Exception{
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
	}
	
	public void waitForVisible(WebElement element,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForNotVisible(final WebElement element,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
	}
	
	public void waitForValue(String locator,String value) throws Exception{
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.textToBePresentInElementValue(byLocator(locator), value));
	}
	
	public void waitForNotValue(final String locator, final String value) throws Exception{
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(byLocator(locator), value)));
	}
	
	public void waitForValue(String locator,String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.textToBePresentInElementValue(byLocator(locator), value));
	}
	
	public void waitForNotValue(final String locator, final String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(byLocator(locator), value)));
	}
	
	public void waitForValue(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, explicitWait) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return getValue(element).equals(value);
    		}
    	});
	}
	
	public void waitForNotValue(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, explicitWait) {
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
	
	public void waitForEditable(final String locator) throws Exception{
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.elementToBeClickable(byLocator(locator)));
	}
	
	public void waitForNotEditable(final String locator) throws Exception{
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(byLocator(locator))));
	}
	
	public void waitForEditable(final String locator,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.elementToBeClickable(byLocator(locator)));
	}
	
	public void waitForNotEditable(final String locator,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(byLocator(locator))));
	}
	
	public void waitForEditable(final WebElement element) throws Exception{
		new WebDriverWait(driver, explicitWait) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return isElementPresent(element) && isVisible(element) && isEnabled(element);
    		}
    	});
	}
	
	public void waitForNotEditable(final WebElement element) throws Exception{
		new WebDriverWait(driver, explicitWait) {
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
	
	public void waitForText(String locator, String value) throws Exception{
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.textToBePresentInElement(byLocator(locator), value));
	}
	
	public void waitForNotText(final String locator, final String value) throws Exception{
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(byLocator(locator),value)));
	}
	
	public void waitForText(String locator, String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.textToBePresentInElement(byLocator(locator), value));
	}
	
	public void waitForNotText(final String locator, final String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(byLocator(locator),value)));
	}
	
	public void waitForText(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, explicitWait) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return getText(element).equals(value);
    		}
    	});
	}
	
	public void waitForNotText(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, explicitWait) {
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
		new WebDriverWait(driver, explicitWait).until(new ExpectedCondition<Boolean>() {
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
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.alertIsPresent());
	}
	
	public void waitForAlertPresent(long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.alertIsPresent());
	}
	
	public void waitForTitle(String pageTitle){
		new WebDriverWait(driver,explicitWait).until(ExpectedConditions.titleIs(pageTitle));
	}
	
	public void waitForTitle(String pageTitle,long timeOutInSeconds){
		new WebDriverWait(driver,timeOutInSeconds).until(ExpectedConditions.titleIs(pageTitle));
	}
	
	public void assertTitle(String expectedTitle) throws Exception{
		Assert.assertEquals(driver.getTitle(),expectedTitle);
	}
	
	public void assertNotTitle(String unexpectedTitle) throws Exception{
		Assert.assertNotEquals(driver.getTitle(),unexpectedTitle);
	}
	
	public void assertValue(String locator, String expectedValue){
		Assert.assertEquals(getAttribute(locator,"value") , expectedValue);
	}
	
	public void assertNotValue(String locator,String unexpectedValue) throws Exception {
		Assert.assertNotEquals(getAttribute(locator,"value"),unexpectedValue);
	}
	
	public void assertValue(WebElement element, String expectedValue){
		Assert.assertEquals(getAttribute(element,"value") , expectedValue);
	}
	
	public void assertNotValue(WebElement element,String unexpectedValue) throws Exception {
		Assert.assertNotEquals(getAttribute(element,"value"),unexpectedValue);
	}
	
	public void assertText(String locator, String expectedText){
		Assert.assertEquals(getText(locator).toString(), expectedText);
	}
	
	public void assertNotText(String locator,String unexpectedText) throws Exception{
		Assert.assertNotEquals(getText(locator), unexpectedText);
	}
	
	public void assertText(WebElement element, String expectedText){
		Assert.assertEquals(getText(element).toString(), expectedText);
	}
	
	public void assertNotText(WebElement element,String unexpectedText) throws Exception{
		Assert.assertNotEquals(getText(element),unexpectedText);
	}
	
	public void assertSelectedText(String locator, String expectedSelectedText){
		Assert.assertEquals(driver.findElement(byLocator(locator)).getAttribute("value"), expectedSelectedText);
	}
	
	public void assertNotSelectedText(String locator, String unexpectedSelectedText){
		Assert.assertNotEquals(driver.findElement(byLocator(locator)).getAttribute("value"), unexpectedSelectedText);
	}
	
	public void assertSelectedText(WebElement element, String expectedSelectedText){
		Assert.assertEquals(element.getAttribute("value"), expectedSelectedText);
	}
	
	public void assertNotSelectedText(WebElement element, String unexpectedSelectedText){
		Assert.assertNotEquals(element.getAttribute("value"), unexpectedSelectedText);
	}
	
	public void assertElementPresent(String locator){
		Assert.assertTrue(isElementPresent(locator));
	}
	
	public void assertElementNotPresent(String locator) throws Exception{
		Assert.assertFalse(isElementPresent(locator));
	}
	
	public void assertElementPresent(WebElement element){
		Assert.assertTrue(isElementPresent(element));
	}
	
	public void assertElementNotPresent(WebElement element) throws Exception{
		Assert.assertFalse(isElementPresent(element));
	}
	
	public void assertChecked(String locator){
		Assert.assertTrue(driver.findElement(byLocator(locator)).isSelected());
	}
	
	public void assertNotChecked(String locator){
		Assert.assertFalse(driver.findElement(byLocator(locator)).isSelected());
	}
	
	public void assertChecked(WebElement element){
		Assert.assertTrue(element.isSelected());
	}
	
	public void assertNotChecked(WebElement element){
		Assert.assertFalse(element.isSelected());
	}
	
	public void assertSelectOptions(String locator,String expectedOptions) throws Exception{
		String expected[] = expectedOptions.split(",");
		List<WebElement> dropDownElement = new Select(driver.findElement(byLocator(locator))).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Assert.assertEquals(actual, expected);
	}
	
	public void assertNotSelectOptions(String locator,String unexpectedOptions) throws Exception{
		String expected[] = unexpectedOptions.split(",");
		List<WebElement> dropDownElement = new Select(driver.findElement(byLocator(locator))).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Assert.assertNotEquals(actual, expected);
	}
	
	public void assertSelectOptions(WebElement element,String expectedOptions) throws Exception{
		String expected[] = expectedOptions.split(",");
		List<WebElement> dropDownElement = new Select(element).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Assert.assertEquals(actual, expected);
	}
	
	public void assertNotSelectOptions(WebElement element,String unexpectedOptions) throws Exception{
		String expected[] = unexpectedOptions.split(",");
		List<WebElement> dropDownElement = new Select(element).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Assert.assertEquals(actual, expected);
	}
	
	public void assertSelectOptionsSize(String locator,String expectedSize) throws Exception {
		Assert.assertEquals(new Select(driver.findElement(byLocator(locator))).getOptions().size(),expectedSize);
	}
	
	public void assertSelectOptionsSize(WebElement element,String expectedSize) throws Exception {
		Assert.assertEquals(new Select(element).getOptions().size(),expectedSize);
	}
	
	public void assertVisible(String locator) throws Exception {
		Assert.assertTrue(isVisible(locator));
	}
	
	public void assertNotVisible(String locator) throws Exception{
		Assert.assertFalse(isVisible(locator));
	}
	
	public void assertVisible(WebElement element) throws Exception {
		Assert.assertTrue(isVisible(element));
	}
	
	public void assertNotVisible(WebElement element) throws Exception{
		Assert.assertFalse(isVisible(element));
	}
	
	public void assertEditable(String locator) throws Exception {
		Assert.assertTrue(isEnabled(locator));
	}
	
	public void assertNotEditable(String locator) throws Exception{
		Assert.assertFalse(isEnabled(locator));
	}
	
	public void assertEditable(WebElement element) throws Exception {
		Assert.assertTrue(isEnabled(element));
	}
	
	public void assertNotEditable(WebElement element) throws Exception{
		Assert.assertFalse(isEnabled(element));
	}
	
	public void assertAlert(String expectedAlertText) throws Exception{
		Assert.assertEquals(driver.switchTo().alert().getText(), expectedAlertText);
	}
	
	public void assertNotAlert(String unexpectedAlertText) throws Exception{
		Assert.assertNotEquals(driver.switchTo().alert().getText(), unexpectedAlertText);
	}
	
	public void assertAttribute(String locator,String expectedAttributeValue) throws Exception{
		int index=locator.lastIndexOf("@");
		Assert.assertEquals(getAttribute(locator.substring(0, index), locator.substring(index+1, locator.length())), expectedAttributeValue);
	}
	
	public void assertNotAttribute(String locator,String unexpectedAttributeValue) throws Exception{
		int index=locator.lastIndexOf("@");
		Assert.assertNotEquals(getAttribute(locator.substring(0, index), locator.substring(index+1, locator.length())), unexpectedAttributeValue);
	}
	
	public void assertAttribute(WebElement element,String attributeName,String expectedAttributeValue) throws Exception{
		Assert.assertEquals(getAttribute(element, attributeName), expectedAttributeValue);
	}
	
	public void assertNotAttribute(WebElement element,String attributeName,String unexpectedAttributeValue) throws Exception{
		Assert.assertNotEquals(getAttribute(element, attributeName), unexpectedAttributeValue);
	}
	
	public void get(String url){
		driver.get(url);
	}
	
	public boolean isElementPresent(String locator){
		try {
			driver.findElement(byLocator(locator));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isElementPresent(WebElement element){
		try {
			element.getTagName();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
	}
	
	public boolean isVisible(String locator){
		return driver.findElement(byLocator(locator)).isDisplayed();
	}
	
	public boolean isVisible(WebElement element){
		return element.isDisplayed();
	}
	
	public boolean isEnabled(String locator){
		return driver.findElement(byLocator(locator)).isEnabled();
	}
	
	public boolean isEnabled(WebElement element){
		return element.isEnabled();
	}
	
	public String getText(String locator){
		return driver.findElement(byLocator(locator)).getText();
	}
	
	public String getText(WebElement element){
		return element.getText();
	}
	
	public String getValue(String locator){
		return driver.findElement(byLocator(locator)).getAttribute("value");
	}
	
	public String getValue(WebElement element){
		return element.getAttribute("value");
	}
	
	public String getAttribute(String locator,String attributeName){
		return driver.findElement(byLocator(locator)).getAttribute(attributeName);
	}
	
	public String getAttribute(WebElement element,String attributeName){
		return element.getAttribute(attributeName);
	}
	
	public String getTagName(String locator){
		return driver.findElement(byLocator(locator)).getTagName();
	}
	
	public String getTagName(WebElement element){
		return element.getTagName();
	}
	
	public WebElement getWebElement(String locator){
		return driver.findElement(byLocator(locator));
	}
	
	public WebElement getWebElement(By by){
		return driver.findElement(by);
	}
	
	public List<WebElement> getWebElements(String locator){
		return driver.findElements(byLocator(locator));
	}
	
	public List<WebElement> getWebElements(By by){
		return driver.findElements(by);
	}
	
	public void clear(String locator){
		driver.findElement(byLocator(locator)).clear();
	}
	
	public void clear(WebElement element){
		element.clear();
	}
	
	public void type(String locator,String value){
		driver.findElement(byLocator(locator)).sendKeys(value);
	}
	
	public void type(WebElement element,String value){
		element.sendKeys(value);
	}
	
	public void clearAndType(String locator,String value){
		WebElement element = driver.findElement(byLocator(locator));
		element.clear();
		element.sendKeys(value);
	}
	
	public void clearAndType(WebElement element,String value){
		element.clear();
		element.sendKeys(value);
	}
	
	public void typeUnique(WebElement element,String value){
		element.sendKeys(Commons.appendRandomNumber(value));
	}
	
	public void typeUnique(String locator,String value){
		driver.findElement(byLocator(locator)).sendKeys(Commons.appendRandomNumber(value));
	}
	
	public void clearAndTypeUnique(WebElement element,String value){
		element.clear();
		element.sendKeys(Commons.appendRandomNumber(value));
	}
	
	public void clearAndTypeUnique(String locator,String value){
		WebElement element = driver.findElement(byLocator(locator));
		element.clear();
		element.sendKeys(Commons.appendRandomNumber(value));
	}
	
	public void click(String locator) throws Exception{
		driver.findElement(byLocator(locator)).click();
	}
	
	public void click(WebElement element) throws Exception{
		element.click();
	}
	
	public void clickAt(String locator,String value){
		String[] v = value.split(",");
		new Actions(driver).moveByOffset(Integer.parseInt(v[0]), Integer.parseInt(v[1])).click(driver.findElement(byLocator(locator))).perform();
	}
	
	public void clickAt(WebElement element,String value){
		String[] v = value.split(",");
		new Actions(driver).moveByOffset(Integer.parseInt(v[0]), Integer.parseInt(v[1])).click(element).perform();
	}
	
	public void doubleClick(String locator){
		new Actions(driver).doubleClick(driver.findElement(byLocator(locator))).perform();
	}
	
	public void doubleClick(WebElement element){
		new Actions(driver).doubleClick(element).perform();
	}
	
	public void selectText(String locator, String visibleText){
		new Select(driver.findElement(byLocator(locator))).selectByVisibleText(visibleText);
	}
	
	public void selectByText(WebElement element, String visibleText){
		new Select(element).selectByVisibleText(visibleText);
	}
	
	public void dragAndDrop(String locator,String value) throws Exception {
		String[] v = value.split(",");
		new Actions(driver).dragAndDropBy(driver.findElement(byLocator(locator)), Integer.parseInt(v[0]), Integer.parseInt(v[1]));
	}
	
	public void dragAndDrop(WebElement element,String value) throws Exception {
		String[] v = value.split(",");
		new Actions(driver).dragAndDropBy(element, Integer.parseInt(v[0]), Integer.parseInt(v[1]));
	}
	
	public void contextMenu(String locator){
		new Actions(driver).contextClick(driver.findElement(byLocator(locator))).perform();
	}
	
	public void contextMenu(WebElement element){
		new Actions(driver).contextClick(element).perform();
	}
	
	public void contextMenuAt(String locator,String value){
		String[] v = value.split(",");
		new Actions(driver).moveByOffset(Integer.parseInt(v[0]), Integer.parseInt(v[1])).contextClick(driver.findElement(byLocator(locator))).perform();
	}
	
	public void contextMenuAt(WebElement element,String value){
		String[] v = value.split(",");
		new Actions(driver).moveByOffset(Integer.parseInt(v[0]), Integer.parseInt(v[1])).contextClick(element).perform();
	}
	
	public void mouseDown(String locator){
		((HasInputDevices) driver).getMouse().mouseDown((Coordinates) driver.findElement(byLocator(locator)).getLocation());
	}
	
	public void mouseDown(WebElement element){
		((HasInputDevices) driver).getMouse().mouseDown((Coordinates) element.getLocation());
	}
	
	public void mouseDownAt(String locator,String coordString) throws Exception {
		String[] v = coordString.split(",");
		((HasInputDevices) driver).getMouse().mouseDown((Coordinates) driver.findElement(byLocator(locator)).getLocation().moveBy(Integer.parseInt(v[0]), Integer.parseInt(v[1])));
	}
	
	public void mouseDownAt(WebElement element,String coordString) throws Exception {
		String[] v = coordString.split(",");
		((HasInputDevices) driver).getMouse().mouseDown((Coordinates) element.getLocation().moveBy(Integer.parseInt(v[0]), Integer.parseInt(v[1])));
	}
	
	public void mouseUp(String locator){
		((HasInputDevices) driver).getMouse().mouseUp((Coordinates) driver.findElement(byLocator(locator)).getLocation());
	}
	
	public void mouseUp(WebElement element){
		((HasInputDevices) driver).getMouse().mouseUp((Coordinates) element.getLocation());
	}
	
	public void mouseUpAt(String locator,String coordString){
		String[] v = coordString.split(",");
		((HasInputDevices) driver).getMouse().mouseUp((Coordinates) driver.findElement(byLocator(locator)).getLocation().moveBy(Integer.parseInt(v[0]), Integer.parseInt(v[1])));
	}
	
	public void mouseUpAt(WebElement element,String coordString){
		String[] v = coordString.split(",");
		((HasInputDevices) driver).getMouse().mouseUp((Coordinates) element.getLocation().moveBy(Integer.parseInt(v[0]), Integer.parseInt(v[1])));
	}
	
	public void mouseOver(String locator) throws Exception{
		new Actions(driver).moveToElement(driver.findElement(byLocator(locator))).build().perform();
	}
	
	public void mouseOver(WebElement element) throws Exception{
		new Actions(driver).moveToElement(element).build().perform();
	}
	
	public void focus(String locator){
		driver.findElement(byLocator(locator)).sendKeys(Keys.TAB);
	}
	
	public void focus(WebElement element){
		element.sendKeys(Keys.TAB);
	}
	
	public void keyDown(String locator, String value){
		new Actions(driver).keyDown(driver.findElement(byLocator(locator)), Keys.valueOf(value)).perform();
	}
	
	public void keyDown(WebElement element, String value){
		new Actions(driver).keyDown(element, Keys.valueOf(value)).perform();
	}
	
	public void keyUp(String locator, String value){
		new Actions(driver).keyUp(driver.findElement(byLocator(locator)), Keys.valueOf(value)).perform();
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
	
	public void check(String locator){
		WebElement checkBox = driver.findElement(byLocator(locator));
		if (!checkBox.getAttribute("type").toLowerCase().equals("checkbox")) {
			throw new InvalidElementStateException("This elementLocator is not a checkbox!");
        }
		if(!checkBox.isSelected()){
			checkBox.click();
		}
	}
	
	public void check(WebElement checkBoxElement){
		if (!checkBoxElement.getAttribute("type").toLowerCase().equals("checkbox")) {
			throw new InvalidElementStateException("This elementLocator is not a checkbox!");
        }
		if(!checkBoxElement.isSelected()){
			checkBoxElement.click();
		}
	}
	
	public void uncheck(String locator){
		WebElement checkBox = driver.findElement(byLocator(locator));
		if (!checkBox.getAttribute("type").toLowerCase().equals("checkbox")) {
			throw new InvalidElementStateException("This elementLocator is not a checkbox!");
        }
		if(checkBox.isSelected()){
			checkBox.click();
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
	
	public static By byLocator(String locator) {
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
	
}
