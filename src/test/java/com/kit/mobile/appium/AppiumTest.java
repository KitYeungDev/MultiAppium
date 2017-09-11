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
	@AppiumConfig(readCapabilitiesByDevice = {"GT-I9508V", "Nexus-5X"})
	public void testAppium(Map<String, Object> capabilities) {
		try {
			Driver driver = new MyAndroidDriver(capabilities);
			driver.create(AppType.BROWSER);
			Driver.getDriver().get("http://www.baidu.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
