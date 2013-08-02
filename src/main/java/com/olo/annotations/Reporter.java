package com.olo.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.olo.reporter.TestReporter;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(METHOD)
public @interface Reporter {
	
	Class<?> value() ;
	
	Class<TestReporter> reporterClass() default TestReporter.class;
	
}
