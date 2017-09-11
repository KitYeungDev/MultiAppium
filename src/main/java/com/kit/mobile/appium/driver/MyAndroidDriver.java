package com.kit.mobile.appium.driver;

import java.net.URL;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kit.mobile.appium.util.Constant;
import com.kit.mobile.appium.util.Constant.AppType;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class MyAndroidDriver extends Driver {

	private Map<String, Object> capabilitiesMap;
	AppiumDriver<WebElement> appiumDriver;
	
	public MyAndroidDriver(Map<String, Object> capabilitiesMap) {
		super(capabilitiesMap);
		this.capabilitiesMap = capabilitiesMap;
	}

	@Override
	public void create(AppType appType) {
		try {
			if (appType == AppType.APP) {
				capabilities.setCapability("app", Constant.APK_ROOT_PATH + (String) capabilitiesMap.get("app"));
				appiumDriver = new AndroidDriver<WebElement>(new URL(Constant.SERVER_REQUEST_URL
								.replace(Constant.PORT_NO, (String) capabilitiesMap.get("portNo"))), 
						capabilities);
			} else if (appType == AppType.BROWSER) {
				capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
				appiumDriver = new AndroidDriver<WebElement>(new URL(Constant.SERVER_REQUEST_URL
								.replace(Constant.PORT_NO, (String) capabilitiesMap.get("portNo"))), 
						capabilities);
			} else {
				throw new IllegalArgumentException("Invalid app type : " + appType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDriver(appiumDriver);
		
	}

}
