package com.kit.mobile.appium.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClientHkbn {

	public static String hkbnHttpGet(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Authorization", "Basic a2l0LnlldW5nOk1hbkpheTY2Ng==");
//		httpGet.addHeader();
		
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet);
			System.out.println(response1.getStatusLine().getStatusCode());
			HttpEntity entity1 = response1.getEntity();
		    EntityUtils.consume(entity1);
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
	
	public static void OkHttpGet() {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("http://192.168.0.105:8081/rest/qmetry/latest/teststep?testCaseIssueId=10200")
		  .get()
		  .addHeader("authorization", "Basic a2l0LnlldW5nOk1hbkpheTY2Ng==")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "94b6910c-7a9b-b239-7c80-16eb1f7aa99d")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				System.out.println(response.headers().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
