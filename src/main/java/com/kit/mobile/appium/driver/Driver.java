package com.kit.mobile.appium.driver;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kit.mobile.appium.util.Constant.AppType;

import io.appium.java_client.AppiumDriver;

public abstract class Driver {
	
	protected DesiredCapabilities capabilities = null;
	private static final ThreadLocal<AppiumDriver<WebElement>> driver = new ThreadLocal<AppiumDriver<WebElement>>();
	
	public Driver(Map<String, Object> capabilitiesMap) {
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", (String) capabilitiesMap.get("deviceName"));
		capabilities.setCapability("platformVersion", (String) capabilitiesMap.get("platformVersion"));
		capabilities.setCapability("udid", (String) capabilitiesMap.get("udid"));
		capabilities.setCapability("unicodeKeyboard", (String) capabilitiesMap.get("unicodeKeyboard"));
		capabilities.setCapability("resetKeyboard", (String) capabilitiesMap.get("resetKeyboard"));
	}
	
	public abstract void create(AppType appType);
	
	public static void close() {
        driver.get().quit();
        driver.remove();
    }
	
	protected void setDriver(AppiumDriver<WebElement> appiumDriver) {
        driver.set(appiumDriver);
    }
	
	public static AppiumDriver<WebElement> getDriver() {
        return driver.get();
    }
	
	public static void capture(String filePath) {
        capture(new File(filePath));
    }
	
	public static void capture(File file) {
        try {
        	TakesScreenshot takesScreenshot = (TakesScreenshot) driver.get();
            byte[] byteArray = takesScreenshot
                    .getScreenshotAs(OutputType.BYTES);
            FileUtils.writeByteArrayToFile(file, byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
