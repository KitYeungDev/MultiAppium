package com.kit.mobile.appium.http;

import java.io.Closeable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientHkbn {

	private static final String DEFAULT_USER = "kit.yeung";
	private static final String DEFAULT_PASS = "ManJay666";
	
	public static String hkbnHttpGet(String url) {
		
		HttpHost targetHost = new HttpHost("192.168.0.105", 8081, "http");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, 
		  new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS));
		 
		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());
		 
		// Add AuthCache to the execution context
		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		
		HttpResponse response1 = null;
		try {
			response1 = httpclient.execute(httpGet, context);
			System.out.println(response1.getStatusLine().getStatusCode());
			HttpEntity entity1 = response1.getEntity();
		    EntityUtils.consume(entity1);
		    if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		    	System.out.println("Response is 200");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
