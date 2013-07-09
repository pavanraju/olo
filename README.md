Olo
====

Automation Framework for WebDriver with TestNG.

Olo not only supports WebDriver Tests but also KeyWord Driven Tests.

Used Twitter Bootstrap for execution summary reports which makes reports look rich.


REQUIREMENTS
------------

The minimum requirement for Olo is that you must be on Java 1.6 or higher versions of Java.



PROJECT DIRECTORY STRUCTURE
-------------------
      
      src/                 Assuming your project source directory(classpath pointing to this folder).
          properties/
            config.properties        Olo configurations.(Will be comming with default config placed in olo jar)
            mail.properties          Mail configurations for olo.(Will be comming with default config placed in olo jar)
            log4j.properties         log4j configuration.(Will be comming with default config placed in olo jar)
          properties/webelements/    For KeywordDriven Tests put all your locator properties files in this folder.
          properties/messages  /     Mesages(properties) used by KeywordDriven Tests should be put into this folder.
      drivers/             WebDrivers
          win32/           Windows 32 bit driver.
          win64/           Windows 64 bit driver.
          linux32/         Linux 32 bit driver.
          linux64/         Linux 64 bit driver.
          mac32/           Mac 32 bit driver.


RQUIRED LIBRARIES BY OLO
-------------------
      javamail-1.4.7       Required to send email.
      log4j-1.2.17         Required to write logs.
      apache-poi-3.9       Required to read/write excel files.
      selenium-server-standalone-2.33.0.jar  Required to use WebDriver, TestNG and other dependencies.
