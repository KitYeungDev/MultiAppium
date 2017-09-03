package com.kit.mobile.appium.core;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kit.mobile.appium.util.Constant;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

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
				String apkPath = appiumConfig.apkPath();
				List<Map<String, String>> propList = getPropertiesMap(readCapabilities);
				List<DesiredCapabilities> capabilities = initCapabilities(propList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private List<Map<String, String>> getPropertiesMap(String[] readCapabilities) {
		List<Map<String, String>> propList = new ArrayList<Map<String,String>>();
		try {
			for (int i = 0; i < readCapabilities.length; i++) {
				String propertiesName = readCapabilities[i];
				InputStream inStream = new BufferedInputStream(
						new FileInputStream(Constant.ANDROID_DEVICE_PATH + propertiesName));
				Properties prop = new Properties(); 
				prop.load(inStream);
				
				for (Entry<Object, Object> entry : prop.entrySet()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put((String) entry.getKey(), (String) entry.getValue());
					propList.add(map);
				}
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
