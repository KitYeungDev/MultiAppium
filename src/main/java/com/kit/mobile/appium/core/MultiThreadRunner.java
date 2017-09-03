package com.kit.mobile.appium.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class MultiThreadRunner extends Runner{

	private Class<?>     testClass;
    private Object       testClassInstance;
    private Description  description;
    private List<Method> testMethods = new ArrayList<Method>();
    
    public MultiThreadRunner(Class<?> testClass) 
    		throws InstantiationException, IllegalAccessException{
    	this.testClass = testClass;
        testClassInstance = testClass.newInstance();
        description = Description.createTestDescription(testClass,
                testClass.getSimpleName());

        initTestMethods();
	}
	
	private void initTestMethods() {
		Method[] methods = testClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)
                    && method.isAnnotationPresent(AppiumConfig.class)) {
                testMethods.add(method);
            }
        }
	}

	@Override
	public Description getDescription() {
		return description;
	}

	@Override
	public void run(RunNotifier notifier) {
		for (Method testMethod : testMethods) {
			
			// Appium Config checking
			AppiumConfig appiumConfig = testMethod.getAnnotation(AppiumConfig.class);
			if (appiumConfig != null) {
				String[] readCapabilities = appiumConfig.readCapabilities();
				if (readCapabilities.length <= 0) {
					throw new IllegalArgumentException("Capabilites List Can't be Null !");
				}
				
				String apkPath = appiumConfig.apkPath();
				if (apkPath.equals("")) {
					throw new IllegalArgumentException("apkPath field Can't be Null !");
				}
			}
			
			try {
				String[] readCapabilities = appiumConfig.readCapabilities();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
