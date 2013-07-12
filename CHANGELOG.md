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
