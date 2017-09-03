package com.kit.mobile.appium.bean;

import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;

public class Capabilities {

	List<DesiredCapabilities> capabilitiesList ;
	
	public Capabilities() {
		
	}

	public List<DesiredCapabilities> getCapabilitiesList() {
		return capabilitiesList;
	}

	public void setCapabilitiesList(List<DesiredCapabilities> capabilitiesList) {
		this.capabilitiesList = capabilitiesList;
	}
	
}
