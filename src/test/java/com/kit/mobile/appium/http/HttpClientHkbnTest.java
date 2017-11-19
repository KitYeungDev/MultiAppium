package com.kit.mobile.appium.http;

import org.junit.Test;

public class HttpClientHkbnTest {

	@Test
	public void testHttpClientHkbn() {
		HttpClientHkbn.hkbnHttpGet("http://192.168.0.105:8081/rest/qmetry/latest/teststep?testCaseIssueId=10200");
	}
}
