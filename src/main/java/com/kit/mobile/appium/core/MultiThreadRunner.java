package com.kit.mobile.appium.core;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kit.mobile.appium.util.Constant;

public class MultiThreadRunner extends Runner{

	private Class<?>     testClass;
    private Object       testClassInstance;
    private Description  description;
    private List<Method> testMethods = new ArrayList<Method>();
    
    public MultiThreadRunner(Class<?> testClass) 
    		throws InstantiationException, IllegalAccessException{
    	
    	System.out.println("Start MultiThread Runner Constructor.");
    	
    	this.testClass = testClass;
        testClassInstance = testClass.newInstance();
        description = Description.createTestDescription(testClass,
                testClass.getSimpleName());

        System.out.println("End MultiThread Runner Constructor.");
        
        initTestMethods();
	}
	
	private void initTestMethods() {
		System.out.println("Start initTestMethods() .");
		
		Method[] methods = testClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)
                    && method.isAnnotationPresent(AppiumConfig.class)) {
                testMethods.add(method);
            }
        }
        
        System.out.println("End initTestMethods() .");
	}

	@Override
	public Description getDescription() {
		return description;
	}

	@Override
	public void run(RunNotifier notifier) {
		System.out.println("Start run() .");
		for (Method testMethod : testMethods) {
			System.out.println("Start function : " + testMethod.getName() + " .");
			// Appium Config checking
			AppiumConfig appiumConfig = testMethod.getAnnotation(AppiumConfig.class);
			if (appiumConfig != null) {
				String[] readCapabilities = appiumConfig.readCapabilities();
				if (readCapabilities.length <= 0) {
					throw new IllegalArgumentException("Capabilites List Can't be Null !");
				}
			}
			
			try {
				String[] readCapabilities = appiumConfig.readCapabilities();
				List<Map<String, Object>> propList = getPropertiesMap(readCapabilities);
				
				for (Map<String, Object> capabilities : propList) {
					invokeWithTestCase(testMethod, notifier, capabilities);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("End run() .");
	}

	private void invokeWithTestCase(Method testMethod, RunNotifier notifier, Map<String, Object> capabilities) {

		notifier.fireTestStarted(description);
		
		try {
			testMethod.invoke(testClassInstance, capabilities);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		notifier.fireTestFinished(description);
	}

	private List<Map<String, Object>> getPropertiesMap(String[] readCapabilities) {
		List<Map<String, Object>> propList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 0; i < readCapabilities.length; i++) {
				String propertiesName = readCapabilities[i];
				InputStream inStream = new BufferedInputStream(
						new FileInputStream(Constant.ANDROID_DEVICE_PATH + propertiesName));
				Properties prop = new Properties(); 
				prop.load(inStream);
				
				Map<String, Object> map = new HashMap<String, Object>();
				for (Entry<Object, Object> entry : prop.entrySet()) {
					map.put((String) entry.getKey(), entry.getValue());
				}
				propList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return propList;
	}

	private List<DesiredCapabilities> initCapabilities(List<Map<String, String>> propList) {
		List<DesiredCapabilities> capabilities = new ArrayList<DesiredCapabilities>();
		
		try {
			for (Map<String, String> map : propList) {
				DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
				desiredCapabilities.setCapability("deviceName", (String) map.get("deviceName"));
				desiredCapabilities.setCapability("platformVersion", (String) map.get("platformVersion"));
				desiredCapabilities.setCapability("udid", (String) map.get("udid"));
				desiredCapabilities.setCapability("unicodeKeyboard", (String) map.get("unicodeKeyboard"));
				desiredCapabilities.setCapability("resetKeyboard", (String) map.get("resetKeyboard"));
				desiredCapabilities.setCapability("app", Constant.APK_ROOT_PATH + (String) map.get("app"));
				
				capabilities.add(desiredCapabilities);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return capabilities;
	}
}
