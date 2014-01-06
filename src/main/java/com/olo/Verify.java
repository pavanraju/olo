package com.olo;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.olo.util.VerificationError;
import com.olo.util.VerificationErrorsInTest;

public class Verify {
	
	private static final Logger logger = LogManager.getLogger(Verify.class.getName());
	
	protected Verify(){
		
	}
	
	static public void verifyTrue(boolean condition, String message) {
		try {
			Assert.assertTrue(condition, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyTrue(boolean condition, String message, WebDriver driver) {
		try {
			Assert.assertTrue(condition, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyTrue(boolean condition) {
		try {
			Assert.assertTrue(condition);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyTrue(boolean condition, WebDriver driver) {
		try {
			Assert.assertTrue(condition);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyFalse(boolean condition, String message) {
		try {
			Assert.assertFalse(condition, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyFalse(boolean condition, String message, WebDriver driver) {
		try {
			Assert.assertFalse(condition, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyFalse(boolean condition) {
		try {
			Assert.assertFalse(condition);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyFalse(boolean condition, WebDriver driver) {
		try {
			Assert.assertFalse(condition);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyFail(String message, Throwable realCause) {
		try {
			Assert.fail(message, realCause);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyFail(String message, Throwable realCause, WebDriver driver) {
		try {
			Assert.fail(message, realCause);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyFail(String message) {
		try {
			Assert.fail(message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyFail(String message, WebDriver driver) {
		try {
			Assert.fail(message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyFail() {
		try {
			Assert.fail();
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyFail(WebDriver driver) {
		try {
			Assert.fail();
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Object actual, Object expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Object actual, Object expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Object actual, Object expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Object actual, Object expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(String actual, String expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(String actual, String expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(String actual, String expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(double actual, double expected, double delta, String message) {
		try {
			Assert.assertEquals(actual, expected, delta, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(double actual, double expected, double delta, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, delta, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(double actual, double expected, double delta) {
		try {
			Assert.assertEquals(actual, expected, delta);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(double actual, double expected, double delta, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, delta);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(float actual, float expected, float delta, String message) {
		try {
			Assert.assertEquals(actual, expected, delta, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(float actual, float expected, float delta, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, delta, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(float actual, float expected, float delta) {
		try {
			Assert.assertEquals(actual, expected, delta);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(float actual, float expected, float delta, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, delta);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(long actual, long expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(long actual, long expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(long actual, long expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(long actual, long expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(boolean actual, boolean expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(boolean actual, boolean expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(boolean actual, boolean expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(boolean actual, boolean expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(byte actual, byte expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(byte actual, byte expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(byte actual, byte expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(byte actual, byte expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(char actual, char expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(char actual, char expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(char actual, char expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(char actual, char expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(short actual, short expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(short actual, short expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(short actual, short expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(short actual, short expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(int actual,  int expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(int actual,  int expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(int actual, int expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(int actual, int expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotNull(Object object) {
		try {
			Assert.assertNotNull(object);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotNull(Object object, WebDriver driver) {
		try {
			Assert.assertNotNull(object);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotNull(Object object, String message) {
		try {
			Assert.assertNotNull(object, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotNull(Object object, String message, WebDriver driver) {
		try {
			Assert.assertNotNull(object, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNull(Object object) {
		try {
			Assert.assertNull(object);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNull(Object object, WebDriver driver) {
		try {
			Assert.assertNull(object);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNull(Object object, String message) {
		try {
			Assert.assertNull(object, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNull(Object object, String message, WebDriver driver) {
		try {
			Assert.assertNull(object, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifySame(Object actual, Object expected, String message) {
		try {
			Assert.assertSame(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifySame(Object actual, Object expected, String message, WebDriver driver) {
		try {
			Assert.assertSame(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifySame(Object actual, Object expected) {
		try {
			Assert.assertSame(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifySame(Object actual, Object expected, WebDriver driver) {
		try {
			Assert.assertSame(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotSame(Object actual, Object expected, String message) {
		try {
			Assert.assertNotSame(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotSame(Object actual, Object expected, String message, WebDriver driver) {
		try {
			Assert.assertNotSame(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotSame(Object actual, Object expected) {
		try {
			Assert.assertNotSame(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotSame(Object actual, Object expected, WebDriver driver) {
		try {
			Assert.assertNotSame(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Collection<?> actual, Collection<?> expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Collection<?> actual, Collection<?> expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Collection<?> actual, Collection<?> expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Collection<?> actual, Collection<?> expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Iterator<?> actual, Iterator<?> expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Iterator<?> actual, Iterator<?> expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Iterator<?> actual, Iterator<?> expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Iterator<?> actual, Iterator<?> expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Iterable<?> actual, Iterable<?> expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Iterable<?> actual, Iterable<?> expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Iterable<?> actual, Iterable<?> expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Iterable<?> actual, Iterable<?> expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Object[] actual, Object[] expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Object[] actual, Object[] expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEqualsNoOrder(Object[] actual, Object[] expected, String message) {
		try {
			Assert.assertEqualsNoOrder(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEqualsNoOrder(Object[] actual, Object[] expected, String message, WebDriver driver) {
		try {
			Assert.assertEqualsNoOrder(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Object[] actual, Object[] expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Object[] actual, Object[] expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEqualsNoOrder(Object[] actual, Object[] expected) {
		try {
			Assert.assertEqualsNoOrder(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEqualsNoOrder(Object[] actual, Object[] expected, WebDriver driver) {
		try {
			Assert.assertEqualsNoOrder(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(final byte[] actual, final byte[] expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(final byte[] actual, final byte[] expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(final byte[] actual, final byte[] expected, final String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(final byte[] actual, final byte[] expected, final String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Set<?> actual, Set<?> expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Set<?> actual, Set<?> expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Set<?> actual, Set<?> expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Set<?> actual, Set<?> expected, String message, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyEquals(Map<?, ?> actual, Map<?, ?> expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyEquals(Map<?, ?> actual, Map<?, ?> expected, WebDriver driver) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotEquals(Object actual1, Object actual2, String message) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotEquals(Object actual1, Object actual2, String message, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotEquals(Object actual1, Object actual2) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotEquals(Object actual1, Object actual2, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotEquals(String actual1, String actual2, String message) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotEquals(String actual1, String actual2, String message, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotEquals(String actual1, String actual2) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotEquals(String actual1, String actual2, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotEquals(long actual1, long actual2, String message) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotEquals(long actual1, long actual2, String message, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotEquals(long actual1, long actual2) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotEquals(long actual1, long actual2, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static void verifyNotEquals(boolean actual1, boolean actual2, String message) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static void verifyNotEquals(boolean actual1, boolean actual2, String message, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static void verifyNotEquals(boolean actual1, boolean actual2) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static void verifyNotEquals(boolean actual1, boolean actual2, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static void verifyNotEquals(byte actual1, byte actual2, String message) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static void verifyNotEquals(byte actual1, byte actual2, String message, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static void verifyNotEquals(byte actual1, byte actual2) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static void verifyNotEquals(byte actual1, byte actual2, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static void verifyNotEquals(char actual1, char actual2, String message) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static void verifyNotEquals(char actual1, char actual2, String message, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static void verifyNotEquals(char actual1, char actual2) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static void verifyNotEquals(char actual1, char actual2, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static void verifyNotEquals(short actual1, short actual2, String message) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static void verifyNotEquals(short actual1, short actual2, String message, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static void verifyNotEquals(short actual1, short actual2) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static void verifyNotEquals(short actual1, short actual2, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static void verifyNotEquals(int actual1, int actual2, String message) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static void verifyNotEquals(int actual1, int actual2, String message, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static void verifyNotEquals(int actual1, int actual2) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static void verifyNotEquals(int actual1, int actual2, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotEquals(float actual1, float actual2, float delta, String message) {
		try {
			Assert.assertNotEquals(actual1, actual2, delta, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotEquals(float actual1, float actual2, float delta, String message, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, delta, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotEquals(float actual1, float actual2, float delta) {
		try {
			Assert.assertNotEquals(actual1, actual2, delta);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotEquals(float actual1, float actual2, float delta, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, delta);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotEquals(double actual1, double actual2, double delta, String message) {
		try {
			Assert.assertNotEquals(actual1, actual2, delta, message);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotEquals(double actual1, double actual2, double delta, String message, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, delta, message);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static public void verifyNotEquals(double actual1, double actual2, double delta) {
		try {
			Assert.assertNotEquals(actual1, actual2, delta);
		} catch (AssertionError e) {
			addVerificationError(e);
		}
	}
	
	static public void verifyNotEquals(double actual1, double actual2, double delta, WebDriver driver) {
		try {
			Assert.assertNotEquals(actual1, actual2, delta);
		} catch (AssertionError e) {
			addVerificationError(e, driver);
		}
	}
	
	static private void addVerificationError(AssertionError e) {
		ITestResult testResult = Reporter.getCurrentTestResult();
		VerificationError ve = new VerificationError();
		ve.setAssertionError(e);
		VerificationErrorsInTest.addError(testResult, ve);
	}
	
	static private void addVerificationError(AssertionError e, WebDriver driver) {
		ITestResult testResult = Reporter.getCurrentTestResult();
		VerificationError ve = new VerificationError();
		try {
			String screenShotFileName = System.currentTimeMillis()+".png";
			String screenShotPath = testResult.getTestContext().getOutputDirectory()+File.separator+"screenshots"+File.separator+screenShotFileName;
			File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(screenShotPath));
			ve.setScreenShotFileName(screenShotFileName);
		} catch (Exception e2) {
			logger.error("Screen shot Problem "+e2.getMessage());
		}
		ve.setAssertionError(e);
		VerificationErrorsInTest.addError(testResult, ve);
	}
	
}
