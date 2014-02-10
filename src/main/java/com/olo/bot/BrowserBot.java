package com.olo.bot;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.seleniumemulation.JavascriptLibrary;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.olo.Verify;
import com.olo.propertyutil.ConfigProperties;
import com.olo.util.OloExpectedConditions;


public class BrowserBot{
	
	private final WebDriver driver;

	public BrowserBot(WebDriver driver) {
		this.driver = driver;
	}
	
	public BrowserBot Wait(int timeInSec) throws Exception {
		Thread.sleep(timeInSec*1000);
		return this;
	}
	
	public BrowserBot waitForFrameToBeAvailableAndSwitchToIt(String frameLocator){
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		return this;
	}
	
	public BrowserBot waitForFrameToBeAvailableAndSwitchToIt(String frameLocator,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		return this;
	}
	
	public BrowserBot waitForElementPresent(By by) throws Exception{
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.presenceOfElementLocated(by));
		return this;
	}
	
	public BrowserBot waitForElementPresent(By by,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.presenceOfElementLocated(by));
		return this;
	}
	
	public BrowserBot waitForElementNotPresent(WebElement element){
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(OloExpectedConditions.elementNotPresent(element));
		return this;
	}
	
	public BrowserBot waitForElementNotPresent(WebElement element,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(OloExpectedConditions.elementNotPresent(element));
		return this;
	}
	
	public BrowserBot waitForElementNotPresent(By by){
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(by)));
		return this;
	}
	
	public BrowserBot waitForElementNotPresent(By by,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(by)));
		return this;
	}
	
	public BrowserBot waitForVisible(WebElement element){
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.visibilityOf(element));
		return this;
	}
	
	public BrowserBot waitForNotVisible(WebElement element) throws Exception{
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
		return this;
	}
	
	public BrowserBot waitForVisible(WebElement element,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(element));
		return this;
	}
	
	public BrowserBot waitForNotVisible(final WebElement element,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
		return this;
	}
	
	public BrowserBot waitForValue(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.textToBePresentInElementValue(element, value));
		return this;
	}
	
	public BrowserBot waitForNotValue(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(element, value)));
		return this;
	}
	
	public BrowserBot waitForValue(final WebElement element, final String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.textToBePresentInElementValue(element, value));
		return this;
	}
	
	public BrowserBot waitForNotValue(final WebElement element, final String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementValue(element, value)));
		return this;
	}
	
	public BrowserBot waitForEditable(final WebElement element) throws Exception{
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.elementToBeClickable(element));
		return this;
	}
	
	public BrowserBot waitForNotEditable(final WebElement element) throws Exception{
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element)));
		return this;
	}
	
	public BrowserBot waitForEditable(final WebElement element,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.elementToBeClickable(element));
		return this;
	}
	
	public BrowserBot waitForNotEditable(final WebElement element,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element)));
		return this;
	}
	
	public BrowserBot waitForText(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.textToBePresentInElement(element, value));
		return this;
	}
	
	public BrowserBot waitForNotText(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, value)));
		return this;
	}
	
	public BrowserBot waitForText(final WebElement element, final String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.textToBePresentInElement(element, value));
		return this;
	}
	
	public BrowserBot waitForNotText(final WebElement element, final String value,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, value)));
		return this;
	}
	
	public BrowserBot waitForAlertPresent(){
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.alertIsPresent());
		return this;
	}
	
	public BrowserBot waitForAlertPresent(long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.alertIsPresent());
		return this;
	}
	
	public BrowserBot waitForTitle(String pageTitle){
		new WebDriverWait(driver, ConfigProperties.getWaitTimeOut()).until(ExpectedConditions.titleIs(pageTitle));
		return this;
	}
	
	public BrowserBot waitForTitle(String pageTitle,long timeOutInSeconds){
		new WebDriverWait(driver,timeOutInSeconds).until(ExpectedConditions.titleIs(pageTitle));
		return this;
	}
	
	public BrowserBot assertTitle(String expectedTitle) throws Exception{
		Assert.assertEquals(getTitle(),expectedTitle);
		return this;
	}
	
	public BrowserBot verifyTitle(String expectedTitle) throws Exception{
		Verify.verifyEquals(getTitle(),expectedTitle, driver);
		return this;
	}
	
	public BrowserBot assertNotTitle(String unexpectedTitle) throws Exception{
		Assert.assertNotEquals(getTitle(),unexpectedTitle);
		return this;
	}
	
	public BrowserBot verifyNotTitle(String unexpectedTitle) throws Exception{
		Verify.verifyNotEquals(getTitle(),unexpectedTitle, driver);
		return this;
	}
	
	public BrowserBot assertValue(WebElement element, String expectedValue){
		String actualValue = getAttribute(element,"value");
		Assert.assertEquals( actualValue, expectedValue);
		return this;
	}
	
	public BrowserBot verifyValue(WebElement element, String expectedValue) throws Exception{
		String actualValue = getAttribute(element,"value");
		Verify.verifyEquals( actualValue, expectedValue, driver);
		return this;
	}
	
	public BrowserBot assertNotValue(WebElement element,String unexpectedValue) throws Exception {
		String actualValue = getAttribute(element,"value");
		Assert.assertNotEquals(actualValue, unexpectedValue);
		return this;
	}
	
	public BrowserBot verifyNotValue(WebElement element,String unexpectedValue) throws Exception {
		String actualValue = getAttribute(element,"value");
		Verify.verifyNotEquals(actualValue, unexpectedValue, driver);
		return this;
	}
	
	public BrowserBot assertText(WebElement element, String expectedText){
		String actualText = getText(element).toString();
		Assert.assertEquals(actualText, expectedText);
		return this;
	}
	
	public BrowserBot verifyText(WebElement element, String expectedText) throws Exception{
		String actualText = getText(element).toString();
		Verify.verifyEquals(actualText, expectedText, driver);
		return this;
	}
	
	public BrowserBot assertNotText(WebElement element,String unexpectedText) throws Exception{
		String actualText = getText(element);
		Assert.assertNotEquals(actualText, unexpectedText);
		return this;
	}
	
	public BrowserBot verifyNotText(WebElement element,String unexpectedText) throws Exception{
		String actualText = getText(element);
		Verify.verifyNotEquals(actualText, unexpectedText, driver);
		return this;
	}
	
	public BrowserBot assertSelectedText(WebElement element, String expectedSelectedText){
		Assert.assertEquals(element.getAttribute("value"), expectedSelectedText);
		return this;
	}
	
	public BrowserBot verifySelectedText(WebElement element, String expectedSelectedText) throws Exception{
		Verify.verifyEquals(element.getAttribute("value"), expectedSelectedText, driver);
		return this;
	}
	
	public BrowserBot assertNotSelectedText(WebElement element, String unexpectedSelectedText){
		Assert.assertNotEquals(element.getAttribute("value"), unexpectedSelectedText);
		return this;
	}
	
	public BrowserBot verifyNotSelectedText(WebElement element, String unexpectedSelectedText) throws Exception{
		Verify.verifyNotEquals(element.getAttribute("value"), unexpectedSelectedText, driver);
		return this;
	}
	
	public BrowserBot assertElementPresent(WebElement element){
		Assert.assertTrue(isElementPresent(element));
		return this;
	}
	
	public BrowserBot verifyElementPresent(WebElement element) throws Exception{
		Verify.verifyTrue(isElementPresent(element), driver);
		return this;
	}
	
	public BrowserBot assertElementNotPresent(WebElement element) throws Exception{
		Assert.assertFalse(isElementPresent(element));
		return this;
	}
	
	public BrowserBot verifyElementNotPresent(WebElement element) throws Exception{
		Verify.verifyFalse(isElementPresent(element), driver);
		return this;
	}
	
	public BrowserBot assertChecked(WebElement element){
		Assert.assertTrue(isSelected(element));
		return this;
	}
	
	public BrowserBot verifyChecked(WebElement element) throws Exception{
		Verify.verifyTrue(isSelected(element), driver);
		return this;
	}
	
	public BrowserBot assertNotChecked(WebElement element){
		Assert.assertFalse(isSelected(element));
		return this;
	}
	
	public BrowserBot verifyNotChecked(WebElement element) throws Exception{
		Verify.verifyFalse(isSelected(element), driver);
		return this;
	}
	
	public BrowserBot assertSelectOptions(WebElement element,String expectedOptions) throws Exception{
		String expected[] = expectedOptions.split(",");
		List<WebElement> dropDownElement = select(element).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Assert.assertEquals(actual, expected);
		return this;
	}
	
	public BrowserBot verifySelectOptions(WebElement element,String expectedOptions) throws Exception{
		String expected[] = expectedOptions.split(",");
		List<WebElement> dropDownElement = select(element).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Verify.verifyEquals(actual, expected, driver);
		return this;
	}
	
	public BrowserBot assertNotSelectOptions(WebElement element,String unexpectedOptions) throws Exception{
		String expected[] = unexpectedOptions.split(",");
		List<WebElement> dropDownElement = select(element).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Assert.assertNotEquals(actual, expected);
		return this;
	}
	
	public BrowserBot verifyNotSelectOptions(WebElement element,String unexpectedOptions) throws Exception{
		String expected[] = unexpectedOptions.split(",");
		List<WebElement> dropDownElement = select(element).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Verify.verifyNotEquals(actual, expected, driver);
		return this;
	}
	
	public BrowserBot assertSelectOptionsSize(WebElement element,int expectedSize) throws Exception {
		Assert.assertEquals(select(element).getOptions().size(),expectedSize);
		return this;
	}
	
	public BrowserBot verifySelectOptionsSize(WebElement element,int expectedSize) throws Exception {
		Verify.verifyEquals(select(element).getOptions().size(),expectedSize, driver);
		return this;
	}
	
	public BrowserBot assertNotSelectOptionsSize(WebElement element,int expectedNotSize) throws Exception {
		Assert.assertNotEquals(select(element).getOptions().size(),expectedNotSize);
		return this;
	}
	
	public BrowserBot verifyNotSelectOptionsSize(WebElement element,int expectedNotSize) throws Exception {
		Verify.verifyNotEquals(select(element).getOptions().size(),expectedNotSize, driver);
		return this;
	}
	
	public BrowserBot assertVisible(WebElement element) throws Exception {
		Assert.assertTrue(isVisible(element));
		return this;
	}
	
	public BrowserBot verifyVisible(WebElement element) throws Exception {
		Verify.verifyTrue(isVisible(element), driver);
		return this;
	}
	
	public BrowserBot assertNotVisible(WebElement element) throws Exception{
		Assert.assertFalse(isVisible(element));
		return this;
	}
	
	public BrowserBot verifyNotVisible(WebElement element) throws Exception{
		Verify.verifyFalse(isVisible(element), driver);
		return this;
	}
	
	public BrowserBot assertEditable(WebElement element) throws Exception {
		Assert.assertTrue(isEnabled(element));
		return this;
	}
	
	public BrowserBot verifyEditable(WebElement element) throws Exception {
		Verify.verifyTrue(isEnabled(element), driver);
		return this;
	}
	
	public BrowserBot assertNotEditable(WebElement element) throws Exception{
		Assert.assertFalse(isEnabled(element));
		return this;
	}
	
	public BrowserBot verifyNotEditable(WebElement element) throws Exception{
		Verify.verifyFalse(isEnabled(element), driver);
		return this;
	}
	
	public BrowserBot assertAlert(String expectedAlertText) throws Exception{
		Assert.assertEquals(driver.switchTo().alert().getText(), expectedAlertText);
		return this;
	}
	
	public BrowserBot verifyAlert(String expectedAlertText) throws Exception{
		Verify.verifyEquals(driver.switchTo().alert().getText(), expectedAlertText, driver);
		return this;
	}
	
	public BrowserBot assertNotAlert(String unexpectedAlertText) throws Exception{
		Assert.assertNotEquals(driver.switchTo().alert().getText(), unexpectedAlertText);
		return this;
	}
	
	public BrowserBot verifyNotAlert(String unexpectedAlertText) throws Exception{
		Verify.verifyNotEquals(driver.switchTo().alert().getText(), unexpectedAlertText, driver);
		return this;
	}
	
	public BrowserBot assertAttribute(WebElement element,String attributeName,String expectedAttributeValue) throws Exception{
		Assert.assertEquals(getAttribute(element, attributeName), expectedAttributeValue);
		return this;
	}
	
	public BrowserBot verifyAttribute(WebElement element,String attributeName,String expectedAttributeValue) throws Exception{
		Verify.verifyEquals(getAttribute(element, attributeName), expectedAttributeValue, driver);
		return this;
	}
	
	public BrowserBot assertNotAttribute(WebElement element,String attributeName,String unexpectedAttributeValue) throws Exception{
		Assert.assertNotEquals(getAttribute(element, attributeName), unexpectedAttributeValue);
		return this;
	}
	
	public BrowserBot verifyNotAttribute(WebElement element,String attributeName,String unexpectedAttributeValue) throws Exception{
		Verify.verifyNotEquals(getAttribute(element, attributeName), unexpectedAttributeValue, driver);
		return this;
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
	
	public boolean isSelected(WebElement element){
		return element.isSelected();
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
	
	public String getValueFromHiddenElement(WebElement element){
		return executeJavascript(element, "return arguments[0].value");
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
	
	public BrowserBot clear(WebElement element){
		element.clear();
		return this;
	}
	
	public BrowserBot type(WebElement element,String value){
		element.sendKeys(value);
		return this;
	}
	
	public BrowserBot clearAndType(WebElement element,String value){
		element.clear();
		element.sendKeys(value);
		return this;
	}
	
	public BrowserBot typeRandomAlphabets(WebElement element,String value){
		element.sendKeys(value+RandomStringUtils.randomAlphabetic(8));
		return this;
	}
	
	public BrowserBot typeRandomAlphabets(WebElement element, String value, int numberOfRandomAlphabets){
		element.sendKeys(value+RandomStringUtils.randomAlphabetic(numberOfRandomAlphabets));
		return this;
	}
	
	public BrowserBot typeRandomAlphabets(WebElement element){
		element.sendKeys(RandomStringUtils.randomAlphabetic(8));
		return this;
	}
	
	public BrowserBot typeRandomAlphabets(WebElement element, int numberOfRandomAlphabets){
		element.sendKeys(RandomStringUtils.randomAlphabetic(numberOfRandomAlphabets));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomAlphabets(WebElement element, String value){
		element.clear();
		element.sendKeys(value+RandomStringUtils.randomAlphabetic(8));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomAlphabets(WebElement element, String value, int numberOfRandomAlphabets){
		element.clear();
		element.sendKeys(value+RandomStringUtils.randomAlphabetic(numberOfRandomAlphabets));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomAlphabets(WebElement element){
		element.clear();
		element.sendKeys(RandomStringUtils.randomAlphabetic(8));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomAlphabets(WebElement element, int numberOfRandomAlphabets){
		element.clear();
		element.sendKeys(RandomStringUtils.randomAlphabetic(numberOfRandomAlphabets));
		return this;
	}
	
	public BrowserBot typeRandomNumbers(WebElement element, String value){
		element.sendKeys(value+RandomStringUtils.randomNumeric(8));
		return this;
	}
	
	public BrowserBot typeRandomNumbers(WebElement element, String value, int numberOfRandomNumbers){
		element.sendKeys(value+RandomStringUtils.randomNumeric(numberOfRandomNumbers));
		return this;
	}
	
	public BrowserBot typeRandomNumbers(WebElement element){
		element.sendKeys(RandomStringUtils.randomNumeric(8));
		return this;
	}
	
	public BrowserBot typeRandomNumbers(WebElement element, int numberOfRandomNumbers){
		element.sendKeys(RandomStringUtils.randomNumeric(numberOfRandomNumbers));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomNumbers(WebElement element,String value){
		element.clear();
		element.sendKeys(value+RandomStringUtils.randomNumeric(8));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomNumbers(WebElement element,String value, int numberOfRandomNumbers){
		element.clear();
		element.sendKeys(value+RandomStringUtils.randomNumeric(numberOfRandomNumbers));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomNumbers(WebElement element){
		element.clear();
		element.sendKeys(RandomStringUtils.randomNumeric(8));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomNumbers(WebElement element, int numberOfRandomNumbers){
		element.clear();
		element.sendKeys(RandomStringUtils.randomNumeric(numberOfRandomNumbers));
		return this;
	}
	
	public BrowserBot typeRandomAlphaNumeric(WebElement element, String value){
		element.sendKeys(value+RandomStringUtils.randomAlphanumeric(8));
		return this;
	}
	
	public BrowserBot typeRandomAlphaNumeric(WebElement element, String value, int numberOfRandomAlphanumeric){
		element.sendKeys(value+RandomStringUtils.randomAlphanumeric(numberOfRandomAlphanumeric));
		return this;
	}
	
	public BrowserBot typeRandomAlphaNumeric(WebElement element){
		element.sendKeys(RandomStringUtils.randomAlphanumeric(8));
		return this;
	}
	
	public BrowserBot typeRandomAlphaNumeric(WebElement element, int numberOfRandomAlphanumeric){
		element.sendKeys(RandomStringUtils.randomAlphanumeric(numberOfRandomAlphanumeric));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomAlphaNumeric(WebElement element,String value){
		element.clear();
		element.sendKeys(value+RandomStringUtils.randomAlphanumeric(8));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomAlphaNumeric(WebElement element,String value, int numberOfRandomAlphanumeric){
		element.clear();
		element.sendKeys(value+RandomStringUtils.randomAlphanumeric(numberOfRandomAlphanumeric));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomAlphaNumeric(WebElement element){
		element.clear();
		element.sendKeys(RandomStringUtils.randomAlphanumeric(8));
		return this;
	}
	
	public BrowserBot clearAndTypeRandomAlphaNumeric(WebElement element, int numberOfRandomAlphanumeric){
		element.clear();
		element.sendKeys(RandomStringUtils.randomAlphanumeric(numberOfRandomAlphanumeric));
		return this;
	}
	
	public BrowserBot click(WebElement element) throws Exception{
		element.click();
		return this;
	}
	
	public BrowserBot clickAt(WebElement element,String value){
		String[] v = value.split(",");
		new Actions(driver).moveByOffset(Integer.parseInt(v[0]), Integer.parseInt(v[1])).click(element).build().perform();
		return this;
	}
	
	public BrowserBot doubleClick(WebElement element){
		new Actions(driver).doubleClick(element).build().perform();
		return this;
	}
	
	public BrowserBot selectByText(WebElement element, String visibleText){
		select(element).selectByVisibleText(visibleText);
		return this;
	}
	
	public BrowserBot dragAndDrop(WebElement element,String value) throws Exception {
		String[] v = value.split(",");
		new Actions(driver).dragAndDropBy(element, Integer.parseInt(v[0]), Integer.parseInt(v[1])).build().perform();
		return this;
	}
	
	public BrowserBot contextMenu(WebElement element){
		new Actions(driver).contextClick(element).build().perform();
		return this;
	}
	
	public BrowserBot contextMenuAt(WebElement element,String value){
		String[] v = value.split(",");
		new Actions(driver).moveByOffset(Integer.parseInt(v[0]), Integer.parseInt(v[1])).contextClick(element).build().perform();
		return this;
	}
	
	public BrowserBot mouseDown(WebElement element){
		getMouse().mouseDown((Coordinates) element.getLocation());
		return this;
	}
	
	public BrowserBot mouseDownAt(WebElement element,String coordString) throws Exception {
		String[] v = coordString.split(",");
		getMouse().mouseDown((Coordinates) element.getLocation().moveBy(Integer.parseInt(v[0]), Integer.parseInt(v[1])));
		return this;
	}
	
	public BrowserBot mouseUp(WebElement element){
		getMouse().mouseUp((Coordinates) element.getLocation());
		return this;
	}
	
	public BrowserBot mouseUpAt(WebElement element,String coordString){
		String[] v = coordString.split(",");
		getMouse().mouseUp((Coordinates) element.getLocation().moveBy(Integer.parseInt(v[0]), Integer.parseInt(v[1])));
		return this;
	}
	
	public BrowserBot mouseOver(WebElement element) throws Exception{
		new Actions(driver).moveToElement(element).build().perform();
		return this;
	}
	
	public BrowserBot focus(WebElement element){
		element.sendKeys(Keys.TAB);
		return this;
	}
	
	public BrowserBot keyDown(WebElement element, String value){
		new Actions(driver).keyDown(element, Keys.valueOf(value)).build().perform();
		return this;
	}
	
	public BrowserBot keyDown(WebElement element, Keys key){
		new Actions(driver).keyDown(element, key).build().perform();
		return this;
	}
	
	public BrowserBot keyUp(WebElement element, String value){
		new Actions(driver).keyUp(element, Keys.valueOf(value)).build().perform();
		return this;
	}
	
	public BrowserBot keyUp(WebElement element, Keys key){
		new Actions(driver).keyUp(element, key).build().perform();
		return this;
	}
	
	public BrowserBot controlKeyUp(){
		new Actions(driver).keyUp(Keys.CONTROL).build().perform();
		return this;
	}
	
	public BrowserBot controlKeyDown(){
		new Actions(driver).keyDown(Keys.CONTROL).build().perform();
		return this;
	}
	
	public BrowserBot chooseOk(){
		driver.switchTo().alert().accept();
		return this;
	}
	
	public BrowserBot chooseCancel(){
		driver.switchTo().alert().dismiss();
		return this;
	}
	
	public BrowserBot check(WebElement checkBoxElement){
		if(!isSelected(checkBoxElement)){
			checkBoxElement.click();
		}
		return this;
	}
	
	public BrowserBot uncheck(WebElement checkBoxElement){
		if(isSelected(checkBoxElement)){
			checkBoxElement.click();
		}
		return this;
	}
	
	public BrowserBot deleteAllVisibleCookies(){
		driver.manage().deleteAllCookies();
		return this;
	}
	
	public Object executeJavascript(String executeJavascript){
		return ((JavascriptExecutor)driver).executeScript(executeJavascript);
	}
	
	public String executeJavascript(WebElement webElement,String executeJavascript){
		return (String) ((JavascriptExecutor)driver).executeScript(executeJavascript, webElement);
	}
	
	public void fireEvent(WebElement element, String eventName){
		new JavascriptLibrary().callEmbeddedSelenium(driver, "triggerEvent", element, eventName);
	}
	
	public BrowserBot switchToDefault(){
		driver.switchTo().defaultContent();
		return this;
	}
	
	public BrowserBot windowMaximize(){
		driver.manage().window().maximize();
		return this;
	}
	
	public BrowserBot windowFocus(){
		driver.switchTo().window(driver.getWindowHandle());
		return this;
	}
	
	public String getWindowHandle(){
		return driver.getWindowHandle();
	}
	
	public Set<String> getWindowHandles(){
		return driver.getWindowHandles();
	}
	
	public BrowserBot switchToWindow(String nameOrHandle){
		driver.switchTo().window(nameOrHandle);
		return this;
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
	
}
