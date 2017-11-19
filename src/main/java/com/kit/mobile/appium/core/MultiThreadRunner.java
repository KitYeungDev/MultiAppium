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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import com.kit.mobile.appium.util.Constant;

public class MultiThreadRunner extends Runner {

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
				String[] readCapabilities = appiumConfig.readCapabilitiesByDevice();
				if (readCapabilities.length <= 0) {
					throw new IllegalArgumentException("Capabilites List Can't be Null !");
				}
			}
			
			try {
				String[] readCapabilities = appiumConfig.readCapabilitiesByDevice();
				readCapabilities = reorganizeFileName(readCapabilities);
				for (int i = 0; i < readCapabilities.length; i++) {
					System.out.println("Device " + i + " : " + readCapabilities[i]);
				}
				List<Map<String, Object>> propList = getPropertiesMap(readCapabilities);
				
				// if threadNum > 1, use multi thread, else use main thread
				System.out.println("Thread Number : " + propList.size());
		        ExecutorService executorService = null;
		        if (propList.size() > 1) {
		            executorService = Executors.newFixedThreadPool(propList.size());
		        }
				
				for (Map<String, Object> capabilities : propList) {
					if (executorService != null) {
						System.out.println("run executorService device is : " + capabilities.get("deviceName"));
		                executorService.execute(new TestCaseJob(testMethod, capabilities, notifier));
		            } else {
		            	System.out.println("run executorService device is : " + capabilities.get("deviceName"));
		            	invokeWithTestCase(testMethod, capabilities, notifier);
		            }
				}
				
				if (executorService != null) {
					executorService.shutdown();

			        while (true) {
			            try {
			                if (executorService.awaitTermination(5, TimeUnit.MINUTES)) {
			                    break;
			                }
			            } catch (InterruptedException e) {
			                e.printStackTrace();
			            }
			        }
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("End run() .");
	}

	private void invokeWithTestCase(Method testMethod, Map<String, Object> capabilities, RunNotifier notifier) {

		notifier.fireTestStarted(description);
		do {
			try {
				testMethod.invoke(testClassInstance, capabilities);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
			
		} while(true);
		
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
	
	private String[] reorganizeFileName(String[] readCapabilities) {
		for (int i = 0; i < readCapabilities.length; i++) {
			readCapabilities[i] = Constant.DEVICE_FILE_PREFIX + readCapabilities[i] + Constant.DEVICE_FILE_SUFFIX;
		}
		return readCapabilities;
	}
	
	private class TestCaseJob implements Runnable {

		private Map<String, Object> capabilities;
        private Method              methodToInvoke;
        private RunNotifier         runNotifier;
		
        public TestCaseJob(Method methodToInvoke, Map<String, Object> capabilities, RunNotifier runNotifier) {
            this.capabilities = capabilities;
            this.methodToInvoke = methodToInvoke;
            this.runNotifier = runNotifier;
        }

        @Override
		public void run() { 
			try {
                invokeWithTestCase(methodToInvoke, capabilities, runNotifier);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		
	}
}
