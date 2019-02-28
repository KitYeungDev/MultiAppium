package com.kit.mobile.appium.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import sun.misc.BASE64Encoder;

public class HttpClientMy {

	private static final String USERNAME = "kit.yeung";
	private static final String PASSWORD = "kit.yeung";
	private static final String enc = USERNAME + ":" + PASSWORD;
	
	public static String myHttpGet(String url) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
		httpGet.setHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + new BASE64Encoder().encode(enc.getBytes()));
//		httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic a2l0LnlldW5nOk1hbkpheTY2Ng==");
//		httpGet.addHeader();
		
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
			System.out.println(response1.getStatusLine().getStatusCode());
			HttpEntity entity1 = response1.getEntity();
		    if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		    	System.out.println("Response is 200");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpclient != null) {
					httpclient.close();
				}
				if (response1 != null) {
					response1.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
