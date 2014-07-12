Olo
====

Automation Framework for WebDriver with TestNG.

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
      drivers/             WebDrivers
          win/           Windows drivers.
          linux/         Linux drivers.
          mac/           Mac drivers.


REQUIRED LIBRARIES BY OLO
-------------------
      javamail-1.4.7       Required to send email.
      selenium-server-standalone-2.39.0.jar  Required to use WebDriver, TestNG and other dependencies.
      

HOW TO START EXECUTION
--------------------
      Pass 1st argument as suitefiles(comma separated) where it is located to com.olo.initiator.ExecutionInitiator.
      If you are on maven project, then include these listeners(com.olo.listeners.SuiteListener,com.olo.listeners.Reporter) in your pom.xml and run your project(pom.xml) as maven tests
