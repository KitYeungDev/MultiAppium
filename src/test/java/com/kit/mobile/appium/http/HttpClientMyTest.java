package com.kit.mobile.appium.http;

import org.junit.Test;

public class HttpClientMyTest {

	@Test
	public void testHttpClientMy() {
		HttpClientMy.myHttpGet("http://192.168.0.105:8081/rest/qmetry/latest/teststep?testCaseIssueId=10200");
	}
}
