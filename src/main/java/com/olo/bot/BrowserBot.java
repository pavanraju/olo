package com.olo.bot;


import static com.olo.util.PropertyReader.configProp;

import java.io.File;
import java.util.List;
import java.util.Set;

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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.olo.util.Commons;


public class BrowserBot{
	
	private final WebDriver driver;
	
	private long implicitWaitAndWaitTimeOut=30;

	public BrowserBot(WebDriver driver) {
		this.driver = driver;
		if(configProp.containsKey("implicitWaitAndWaitTimeOut")){
			implicitWaitAndWaitTimeOut=Integer.parseInt(configProp.getProperty("implicitWaitAndWaitTimeOut"));
		}
	}
	
	public void Wait(int timeOutSec) throws Exception {
			Thread.sleep(timeOutSec*1000);
	}
	
	public void waitForFrameToBeAvailableAndSwitchToIt(String frameLocator,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameToBeAvailableAndSwitchToIt(String frameLocator){
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForElementPresent(By by) throws Exception{
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut).until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	public void waitForElementPresent(By by,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	public void waitForElementNotPresent(WebElement element){
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut).until(ExpectedConditions.stalenessOf(element));
	}
	
	public void waitForElementNotPresent(WebElement element,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.stalenessOf(element));
	}
	
	public void waitForElementNotPresent(By by){
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut).until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(by)));
	}
	
	public void waitForElementNotPresent(By by,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(by)));
	}
	
	public void waitForVisible(WebElement element){
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForNotVisible(WebElement element) throws Exception{
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
	}
	
	public void waitForVisible(WebElement element,long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForNotVisible(final WebElement element,long timeOutInSeconds) throws Exception{
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
	}
	
	public void waitForValue(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return getValue(element).equals(value);
    		}
    	});
	}
	
	public void waitForNotValue(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut) {
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
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return isElementPresent(element) && isVisible(element) && isEnabled(element);
    		}
    	});
	}
	
	public void waitForNotEditable(final WebElement element) throws Exception{
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut) {
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
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut) {
    	}.until(new ExpectedCondition<Boolean>() {
    		public Boolean apply(WebDriver driver) {
    			return getText(element).equals(value);
    		}
    	});
	}
	
	public void waitForNotText(final WebElement element, final String value) throws Exception{
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut) {
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
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut).until(new ExpectedCondition<Boolean>() {
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
		new WebDriverWait(driver, implicitWaitAndWaitTimeOut).until(ExpectedConditions.alertIsPresent());
	}
	
	public void waitForAlertPresent(long timeOutInSeconds){
		new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.alertIsPresent());
	}
	
	public void waitForTitle(String pageTitle){
		new WebDriverWait(driver,implicitWaitAndWaitTimeOut).until(ExpectedConditions.titleIs(pageTitle));
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
	
	public void assertValue(WebElement element, String expectedValue){
		Assert.assertEquals(getAttribute(element,"value") , expectedValue);
	}
	
	public void assertNotValue(WebElement element,String unexpectedValue) throws Exception {
		Assert.assertNotEquals(getAttribute(element,"value"),unexpectedValue);
	}
	
	public void assertText(WebElement element, String expectedText){
		Assert.assertEquals(getText(element).toString(), expectedText);
	}
	
	public void assertNotText(WebElement element,String unexpectedText) throws Exception{
		Assert.assertNotEquals(getText(element),unexpectedText);
	}
	
	public void assertSelectedText(WebElement element, String expectedSelectedText){
		Assert.assertEquals(element.getAttribute("value"), expectedSelectedText);
	}
	
	public void assertNotSelectedText(WebElement element, String unexpectedSelectedText){
		Assert.assertNotEquals(element.getAttribute("value"), unexpectedSelectedText);
	}
	
	public void assertElementPresent(WebElement element){
		Assert.assertTrue(isElementPresent(element));
	}
	
	public void assertElementNotPresent(WebElement element) throws Exception{
		Assert.assertFalse(isElementPresent(element));
	}
	
	public void assertChecked(WebElement element){
		Assert.assertTrue(element.isSelected());
	}
	
	public void assertNotChecked(WebElement element){
		Assert.assertFalse(element.isSelected());
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
	
	public void assertNotSelectOptions(WebElement element,String unexpectedOptions) throws Exception{
		String expected[] = unexpectedOptions.split(",");
		List<WebElement> dropDownElement = select(element).getOptions();
		String actual[] = new String[dropDownElement.size()];
		for (int i = 0; i < dropDownElement.size(); i++) {
			actual[i]=dropDownElement.get(i).toString();
		}
		Assert.assertNotEquals(actual, expected);
	}
	
	public void assertSelectOptionsSize(WebElement element,String expectedSize) throws Exception {
		Assert.assertEquals(select(element).getOptions().size(),expectedSize);
	}
	
	public void assertNotSelectOptionsSize(WebElement element,String expectedNotSize) throws Exception {
		Assert.assertNotEquals(select(element).getOptions().size(),expectedNotSize);
	}
	
	public void assertVisible(WebElement element) throws Exception {
		Assert.assertTrue(isVisible(element));
	}
	
	public void assertNotVisible(WebElement element) throws Exception{
		Assert.assertFalse(isVisible(element));
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
	
	public void assertAttribute(WebElement element,String attributeName,String expectedAttributeValue) throws Exception{
		Assert.assertEquals(getAttribute(element, attributeName), expectedAttributeValue);
	}
	
	public void assertNotAttribute(WebElement element,String attributeName,String unexpectedAttributeValue) throws Exception{
		Assert.assertNotEquals(getAttribute(element, attributeName), unexpectedAttributeValue);
	}
	
	public Select select(WebElement element){
		return new Select(element);
	}
	
	public Actions actions(){
		return new Actions(driver);
	}
	
	public void get(String url){
		driver.get(url);
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
	
	public void typeUnique(WebElement element,String value){
		element.sendKeys(Commons.appendRandomNumber(value));
	}
	
	public void clearAndTypeUnique(WebElement element,String value){
		element.clear();
		element.sendKeys(Commons.appendRandomNumber(value));
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
		((HasInputDevices) driver).getMouse().mouseDown((Coordinates) element.getLocation());
	}
	
	public void mouseDownAt(WebElement element,String coordString) throws Exception {
		String[] v = coordString.split(",");
		((HasInputDevices) driver).getMouse().mouseDown((Coordinates) element.getLocation().moveBy(Integer.parseInt(v[0]), Integer.parseInt(v[1])));
	}
	
	public void mouseUp(WebElement element){
		((HasInputDevices) driver).getMouse().mouseUp((Coordinates) element.getLocation());
	}
	
	public void mouseUpAt(WebElement element,String coordString){
		String[] v = coordString.split(",");
		((HasInputDevices) driver).getMouse().mouseUp((Coordinates) element.getLocation().moveBy(Integer.parseInt(v[0]), Integer.parseInt(v[1])));
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
		return implicitWaitAndWaitTimeOut;
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
