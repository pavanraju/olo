package com.olo.util;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class OloExpectedConditions {
	
	public static ExpectedCondition<Boolean> elementNotPresent(
		      final WebElement element) {
		    return new ExpectedCondition<Boolean>() {
		      @Override
		      public Boolean apply(WebDriver ignored) {
		        try {
		          // Calling any method forces a staleness check
		          element.isEnabled();
		          return false;
		        } catch (NoSuchElementException expected) {
		        	return true;
		        } catch (StaleElementReferenceException expected) {
		          return true;
		        }
		      }

		      @Override
		      public String toString() {
		        return String.format("element (%s) to become stale", element);
		      }
		    };
		  }
	
}
