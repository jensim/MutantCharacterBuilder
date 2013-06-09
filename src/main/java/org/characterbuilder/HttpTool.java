/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.characterbuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">jens brimberg</a>
 */
public class HttpTool {

	public static String getParamString(Map<String, String> paramMap) {
		StringBuilder sb = new StringBuilder("?");
		for (String key : paramMap.keySet()) {
			if (sb.toString().length() != 1) {
				sb.append("&");
			}
			sb.append(key).append("=").append(paramMap.get(key));
		}
		return sb.toString();
	}

	public static void darksideGetRequest(String address, String paramString) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(address + paramString);
		client.execute(request);
	}

	public static String darksideGetRequestResponse(String address, String paramString) throws Exception {

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(address + paramString);
		HttpResponse response = client.execute(request);

// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		StringBuilder sb = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		String string = sb.toString();
		return string;
	}
}
