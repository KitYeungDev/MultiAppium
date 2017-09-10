package com.kit.mobile.appium;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kit.mobile.appium.util.Constant;

import io.appium.java_client.android.AndroidDriver;

public class MutilDevicesAppium {

	private static AndroidDriver<WebElement> driver1;
//	private static AndroidDriver<WebElement> driver2;
	
	@Before
	public void setUp() throws MalformedURLException {
		File classpathRoot = new File(Constant.BASE_PATH);
		System.out.println(classpathRoot);
		File appDir = new File(classpathRoot, "/app");
		File app = new File(appDir, "/app-debug.apk");
		
		DesiredCapabilities capabilities1 = new DesiredCapabilities();
		capabilities1.setCapability("deviceName","GT-I9508V");
		capabilities1.setCapability("platformVersion", "5.0.1");
		capabilities1.setCapability("udid","d368b6d8");
		capabilities1.setCapability("app", app.getAbsolutePath());
		capabilities1.setCapability("unicodeKeyboard", true);
		capabilities1.setCapability("resetKeyboard", true);
		driver1 = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities1);
		driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
//		DesiredCapabilities capabilities2 = new DesiredCapabilities();
//		capabilities2.setCapability("deviceName","Nexus-5X");
//		capabilities2.setCapability("platformVersion", "7.1.1");
//		capabilities2.setCapability("udid","emulator-5554");
//		capabilities2.setCapability("app", app.getAbsolutePath());
//		capabilities2.setCapability("unicodeKeyboard", true);
//		capabilities2.setCapability("resetKeyboard", true);
//		driver2 = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4725/wd/hub"), capabilities2);
//		driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void test() {
		WebElement username1 = driver1.findElement(By.id("usernameEV"));
//		WebElement username2 = driver2.findElement(By.id("usernameEV"));
		username1.sendKeys("Kit Yeung 1");
//		username2.sendKeys("Kit Yeung 2");
		WebElement submit1 = driver1.findElement(By.id("btn_submit"));
//		WebElement submit2 = driver2.findElement(By.id("btn_submit"));
		submit1.click();
//		submit2.click();
	}

	@After
	public void tearDown() {
		driver1.quit();
//		driver2.quit();
	}
	
}
