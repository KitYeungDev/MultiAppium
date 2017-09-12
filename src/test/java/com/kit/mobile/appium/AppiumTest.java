package com.kit.mobile.appium;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.kit.mobile.appium.core.AppiumConfig;
import com.kit.mobile.appium.core.MultiThreadRunner;
import com.kit.mobile.appium.driver.Driver;
import com.kit.mobile.appium.driver.MyAndroidDriver;
import com.kit.mobile.appium.util.Constant.AppType;

/**
 * A sample of MultiAppium test tool
 * @author kit.yeung
 *
 */

@RunWith(MultiThreadRunner.class)
public class AppiumTest {
	
	@Test
	//input devices name
	@AppiumConfig(readCapabilitiesByDevice = {"GT-I9508V", "Nexus-5X"})
	public void testAppium(Map<String, Object> capabilities) {
		try {
			//Init a new AppiumDriver
			Driver driver = new MyAndroidDriver(capabilities);
			//Create A mobile web app test request 
			driver.create(AppType.BROWSER);
			//Open web app page 
			Driver.getDriver().get("http://www.baidu.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
