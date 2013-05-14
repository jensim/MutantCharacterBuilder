package org.oauth;

import com.vaadin.ui.UI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public abstract class CallbackParent extends UI {

	//protected static final String REDIRECT_URI_BASE = "http://kungstroll.no-ip.org/CharacterBuilder/";
	protected static final String REDIRECT_URI_BASE = "http://127.0.0.1:8080/CharacterBuilder/";
	protected static final String LOGOUT_URL = REDIRECT_URI_BASE + "?refresh=1";
	//protected final String LOGOUT_URL = "../?refresh=1";

	protected abstract String getUserID(String user_url, String parameterset) throws Exception;

	protected abstract String getAccessToken(String access_token_url, String code,
			  String client_id, String client_secret) throws Exception;

	/**
	 * Perform the POST request for the
	 *
	 * @param address
	 * @param params
	 * @return
	 */
	protected String darksidePostRequest(String address, BasicNameValuePair... params) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(address);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.length);
			for (BasicNameValuePair paramPair : params) {
				nameValuePairs.add(paramPair);
			}
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			String string = sb.toString();

			string = string.split("access_token=")[1];
			string = string.split("&")[0];

			return string;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String getParamString(Map<String, String> paramMap) {
		StringBuilder sb = new StringBuilder("?");
		for (String key : paramMap.keySet()) {
			if (sb.toString().length() != 1) {
				sb.append("&");
			}
			sb.append(key).append("=").append(paramMap.get(key));
		}
		return sb.toString();
	}

	protected String darksideGetRequest(String address, String paramString) throws Exception {

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
		
		string = string.split("\"id\":")[1];
		string = string.split(",")[0];
		return string;
	}
}
