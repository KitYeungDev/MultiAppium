package com.kit.mobile.appium.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientHkbn {

	public static String hkbnHttpGet(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Authorization", "Basic a2l0LnlldW5nOk1hbkpheTY2Ng==");
		
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
}
