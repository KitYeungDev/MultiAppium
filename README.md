# Multi-Thread Appium Test Tool

An Java Test Tool for mobile auto test with Multi-Thread .


# Prepare
* Install nodejs, appium in your test server .
* Clone current program in your PC which installed JDK & Maven .
* Confirm your PC and test server connected .
* Confirm test devices and test server connected with USB or Wi-Fi .


# How to Use
**1. Create A new Test Class & add customized junit runner for multi-thread .**
```java
@RunWith(MultiThreadRunner.class)
public class AppiumTest {
	
	@Test
	//input devices name
	@AppiumConfig(readCapabilitiesByDevice = {"GT-I9508V", "Nexus-5X"})
	public void testAppium(Map<String, Object> capabilities) {
		try {
			//Init a new AppiumDriver
			Driver driver = new MyAndroidDriver(capabilities);
			//Create A mobile app test request 
			driver.create(AppType.APP);
			//Your Test logic
			//... ...
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```
**2. Create a new properties for `DesiredCapabilities`**
> file naming rules : device_`devicename`.properties [e.g : device_GT-I9508V.properties]

```
deviceName=GT-I9508V
platformVersion=5.0.1
udid=d368b6d8
unicodeKeyboard=true
resetKeyboard=true
portNo=4723
waitTime=10
app=app-debug.apk
```

**3. Copy your test apk file into `app` folder**

**4. Start Multi Appium Service by different udid**
> Command for checking device's udid : `adb devices -l`<br>
> Command for start appium service : `appium -p 4723 -bp 2000 -U [udid]`

**5. Run Junit Test**

