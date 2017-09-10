package com.kit.mobile.appium;

import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kit.mobile.appium.core.AppiumConfig;
import com.kit.mobile.appium.core.MultiThreadRunner;
import com.kit.mobile.appium.util.Constant;

import io.appium.java_client.android.AndroidDriver;

@RunWith(MultiThreadRunner.class)
public class AppiumTest {
	
	@Test
	@AppiumConfig(readCapabilities = {"device_s4.properties"})
	public void testAppium(Map<String, Object> capabilities) {
		try {
			AndroidDriver<WebElement> driver1;
			DesiredCapabilities capabilities1 = new DesiredCapabilities();
			capabilities1.setCapability("deviceName", (String) capabilities.get("deviceName"));
			capabilities1.setCapability("platformVersion", (String) capabilities.get("platformVersion"));
			capabilities1.setCapability("udid", (String) capabilities.get("udid"));
			capabilities1.setCapability("unicodeKeyboard", (String) capabilities.get("unicodeKeyboard"));
			capabilities1.setCapability("resetKeyboard", (String) capabilities.get("resetKeyboard"));
			capabilities1.setCapability("app", Constant.APK_ROOT_PATH + (String) capabilities.get("app"));
			driver1 = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:"+(String)capabilities.get("portNo")+"/wd/hub"), capabilities1);
			driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
