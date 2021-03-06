0.19.0
------------

**Enhancements**

* Support for opera browser
* Updated selenium to 2.42

0.18.0
------------

**Enhancements**

* Splitted pageWaitAndWaitTimeOut to two different properties 'pageLoadTimeout' and 'waitTimeOut'. If pageLoadTimeOut is 0 then not setting pageloadtimeout at driver level.
* Added new methods to browserBot
* Showing screenshot at error message level
* Loggers using testng logger
* CheckVerificationListener is added to testNG services, so it works by default, no need to add in listeners either in testng.xml, maven, etc..
* Added new property captureScreenshot in config.properties to capture screenshots for your test execution or not.

**Bug Fixes**

* Fixed issues in suite detailed report
* Removed unused code

**Breaking Changes**

* Not Supporting keywordDriven due to limitations

0.17.0
------------

**Enhancements**

* Renamed InvokedMethodListener to CheckVerificationErrorListener
* Keyword driven tests will use the browser bot
* Using Apache commons api for invoking methods
* Using updated Expected Conditions for BrowserBot methods
* Added new class Verify for verifications which is a wrapper for Assert class
* Added ConfigProperties,MailProperties class for accessing config.properties,mail.properties data

**Breaking Changes**

* Removed WaitForAlert Keyword
* Removed few methods from BrowserBot which will be part of Verify class now

0.16.0
------------

**Enhancements**

* Rewritten KeywordDriven support
* Keyword driven tests will use the browser bot
* Added new methods to BrowserBot.
* Updated selenium to 2.39
* Logger enhancements
* New Listeners MailReporter and MailSuiteListener for sending emails
* Added ApplicationInitiator for starting and stopping driver
* Reports uses Bootstrap3 and are completely responsive
* Enhanced reports

**Breaking Changes**

* In config.properties 'url' property changed to 'applicationUrl'
* removed support for android driver and it is depricated in selenium 2.39
* keyword driven reports will be generated at suite listener level
* removed Put Keywords temporary
* removed app messages loading feature for keyword driven tests
* syntax changed for accessing data provider data
  - {{username}}  to access data

0.15.0
------------

**Enhancements**

* Added new property 'remoteExecution' in config.properties to run tests using grid.
* Added new methods to BrowserBot.
* Supporting 'HtmlUnit' which is a browser configuration in config.properties
* Updated selenium to 2.36
* Updated operadriver to 1.5
* Logger enhancements

**Bug Fixes**

* Fixed issue in SuiteListener.


**Breaking Changes**

* Removed WebDriverInitiator as it will create errors when running tests with grid.


0.14.0
------------

**Enhancements**

* Upgraded TestNg to 6.8.7
* Added Verifications in BrowserBot, verification failures are handled by InvokedMethodListener.
* Added new methods to BrowserBot.
* Enhanced Logs show the testname started and testname completed.
* New property 'implicitWait' added in config.properties('implicitWaitAndWaitTimeOut' property splitted into two different properties, 'implicitWait' and 'pageWaitAndWaitTimeOut')
* Test description will be shown as tooltip in reports.

**Bug Fixes**

* WaitForElementNotPresent not working as expected.

**Breaking Changes**

* TypeUnique keyword renamed to TypeRandomAlphabets, ClearAndTypeUnique renamed to ClearAndTypeRandomAlphabets
* 'implicitWaitAndWaitTimeOut' renamed to 'pageWaitAndWaitTimeOut'


0.13.0
------------

**Enhancements**

* Upgraded WebDriver to 2.35.0
* Upgraded OperaDriver to 1.4
* Data Driven support for Keyword Driven Tests.
* Added New Property 'dateFormat' which will be used in TypeDate keyword.
* New KeyWord CaptureScreenshot

**Bug Fixes**
* Fixed issue with getting values of Put keyword.

**Breaking Changes**

* Keyword driven test cases should start with 'KWD' instead of 'TC'.
* syntax changed for accessing test data,messages & put keyword
  - <<username>>   to access test data
  - <{login.username}>  to access app properties
  - <?username?> to access the put keyword values
* Removed Iphone&Ipad support as they are depricated in WebDriver 2.35
* drivers folder will now have sub folders with os names like win,linux & mac.
* For KeywordDriven Test 1st column will be action and 2nd column will be WebElement.
* changing 'messages' folder 'app' as it makes more meaningful.


0.12.0
------------

**Enhancements**

* Upgraded WebDriver to 2.34.0
* Upgraded TestNg to 6.8.5
* Added 'table-condensed' class to table, shows more tests on left pane. Added 'btn-small' class to test status in Suite Listener.
* added new properties 'webElements' and 'messages' in config.properties to handle webElements and messages by Olo.


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
