package com.kit.mobile.appium.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AppiumConfig {
	/**
	 * read capabilties from resource properties
	 * @return
	 */
	String[] readCapabilitiesByDevice() default {};
}
