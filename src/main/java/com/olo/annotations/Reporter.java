package com.olo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.olo.reporter.TestReporter;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Reporter {
	
	Class<?> value() ;
	
	Class<TestReporter> reporterClass() default TestReporter.class;
	
}
