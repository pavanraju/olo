0.11.0
------------

**Enhancements**

* Reports are now sent by mail. Mailer is activated.
* Supporting Safari browser.
* Enhancements in reports.
* Annotations are now targeted to method.

**Breaking Changes**

* log4j.properties file should be put under classpath. config,webelements,messages folders should be put under classpath.



0.10.0
------------

**Enhancements**

* Enhancements in reports
* Enhancements for keyworddriven execution. User can have their own templates. Seperated reading from execution for KeyWord Driven.
* Added new methods getMouse and getKeyboard in BrowserBot
* Showing browser name in the generated report folder & showing folder name where reports will be generated in logs.

**Bug Fixes**

* Fixed issue window not closing if failure in beforemethod.
* Label name shown improper in suite skipped report.


0.9.0
------------

**Enhancements**

* Added Test Builder support for keyword driven tests
* Moved keyword driven handling classes to single package
* Showing test case path in keyword driven test report.
* Removed 'screenshothandled' from Reporter annotation

**Bug Fixes**

* Fixed issue for IE driver path problem

**Breaking Changes**

* removed testreporter property from config.properties

0.8.0
------------

**Enhancements**

* Added new Method getTimeOut in BrowserBot to get timeout at run time.
* Adding new class WebDriverConfiguration so that users can write their own WebDriverInitiators.
* WebDriverInitiator is using methods from WebDriverConfiguration
* removing implicitWait and waitForPageToload from BrowserBot as they are handled in WebDriverInitiator
* removed unnecessary code from keywordDrivenExection
* Moved opening url, maximize window, window focus to WebDriverInitiator, BrowserBotInitiator will only create instance for Browser Bot.
* removed WaitForPageToLoad keyword from keywords list as it handled at framework level
* Couple of enhancements in SuiteListener, better reports in Suite Level report.
 

**Bug Fixes**

* WaitForElementNotPresent for KeywordDriven
* Added extra div with class span1 in reporter, so that there will be no table breaks if we have very long url given for execution.



0.7.0
------------

**Enhancements**

* New Keywords implemented(AssertNotSelectOptions,VerifyNotSelectOptions,AssertNotSelectOptionsSize,VerifyNotSelectOptionsSize,AssertNotAttribute,VerifyNotAttribute)
* KeywordDriven keywords will use the same api of WebElements instead of seperate implementation in BrowserBot.

**Bug Fixes**

* Fixed issue with WaitForElementPresent for KeywordDriven Approach

**Breaking Changes**

* Removing support for old chrome drivers. PageLoadTimeOut is not implemented in old browsers. But it is handled in chrome driver 2.1. Request to download the latest version of chrome driver.

0.6.0
------------

**Breaking Changes**

* Removed keywords,browserBot from config.properties and handled the same implementation. All tests should extend from BrowserBotInitiaor.

**Enhancements**

* Moved keywordDriven execution from KeywordDrivenRunner to KeywordDrivenExecution.

0.5.0
------------

**Breaking Changes**

* For KeywordDriven Tests have one column to specify locatorname. Syntax for the same will be {{fileName}}.{{propertyName}} in the first column.


0.4.0
------------

**Enhancements**

* Added new property actualValue in KeywordDriven. It can seen in report.
* Removed Dependency of setting property value of reportOutputDirectory. Removed ExecutionListener and olostyles.css
* implicitWaitAndWaitTimeOut is default 30 sec. Optional in config.properties.
* Setting waitForPageToLoad in WebDriverInitiator.


**Bug Fixes**

* Shows proper error message if browserBot class not found.
* Removed implicit Wait in If keywords.


**Breaking Changes**

* Changed getWebElement to findElement and getWebElements to findElements.


0.3.0
------------

**Breaking Changes**

* Changed StoreValueIn,StoreTextIn to PutValueIn and PutTextIN. And to use this put values {{get.<putname>}} in KeywordDriven Tests.

**Enhancements**

* PutValueIn and PutTextIn are now customized keywords. Can write your own put keywords. Your put keywords must start with Put<syntax>.
* Showing Absolute path of properties which are loading under webelements and messages.
