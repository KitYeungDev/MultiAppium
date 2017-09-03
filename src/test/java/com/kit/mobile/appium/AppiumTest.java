package com.kit.mobile.appium;

import java.io.File;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kit.mobile.appium.core.AppiumConfig;
import com.kit.mobile.appium.core.MultiThreadRunner;
import com.kit.mobile.appium.util.Constant;

@RunWith(MultiThreadRunner.class)
public class AppiumTest {
	@Test
	@AppiumConfig(readCapabilities = {"device_s4.properties"}, apkPath = "app-debug.apk")
	public void testAppium() {
		
	}
}
