Olo
====

Automation Framework for WebDriver with TestNG.

Olo supports WebDriver Tests, KeyWord Driven Tests and Data Driven Tests.

Used Twitter Bootstrap for execution summary reports which makes reports look rich.


REQUIREMENTS
------------

The minimum requirement for Olo is that you must be on Java 1.6 or higher versions of Java.


PROJECT DIRECTORY STRUCTURE
-------------------
      
      src/                 Assuming your project source directory(classpath pointing to this folder).
          config/
            config.properties        Olo configurations.(Will be comming with default config placed in olo jar)
            mail.properties          Mail configurations for olo.(Will be comming with default config placed in olo jar)
          webElements/    For KeywordDriven Tests put all your locator properties files in this folder.
          app/       Messages or application information(properties) used by KeywordDriven Tests should be put into this folder.
          log4j.properties         log4j configuration.(Will be comming with default config placed in olo jar)
      drivers/             WebDrivers
          win/           Windows drivers.
          linux/         Linux drivers.
          mac/           Mac drivers.


REQUIRED LIBRARIES BY OLO
-------------------
      javamail-1.4.7       Required to send email.
      log4j-1.2.17         Required to write logs.
      apache-poi-3.9       Required to read/write excel files.
      selenium-server-standalone-2.35.0.jar  Required to use WebDriver, TestNG and other dependencies.
      

HOW TO START EXECUTION
--------------------
      Pass 1st argument as suitefiles(comma separated) where it is located to com.olo.initiator.ExecutionInitiator.
