package com.kit.mobile.appium.util;

import java.io.File;

public class Constant {

	public static final String BASE_PATH               	=	System.getProperty("user.dir");
	
	public static final String APK_ROOT_PATH           	=	System.getProperty("user.dir") + 
										  					File.separator + 
										  					"app/";
	
	public static final String ANDROID_DEVICE_PATH     	= 	BASE_PATH + 
															File.separator + 
															"resource/device/android/";
	
	public static final String SERVER_REQUEST_URL		=	"http://127.0.0.1:PORT_NO/wd/hub";
}
