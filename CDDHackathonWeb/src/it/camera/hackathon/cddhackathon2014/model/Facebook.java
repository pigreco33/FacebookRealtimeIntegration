package it.camera.hackathon.cddhackathon2014.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import it.camera.hackathon.cddhackathon2014.config.Config;
import it.camera.hackathon.cddhackathon2014.utils.SessionManager;
import it.camera.hackathon.cddhackathon2014.utils.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Facebook {
	private static final Logger LOGGER = Logger.getLogger(Facebook.class.getCanonicalName());
	
	public String publish(Publishable publishable) throws ClientProtocolException, IOException {
		String accessToken = SessionManager.getInstance().getAccessToken();
		
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClient httpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(Config.Facebook.FEED_URL);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("message", publishable.getMessage()));
        nameValuePairs.add(new BasicNameValuePair("link", publishable.getLink()));
        nameValuePairs.add(new BasicNameValuePair("access_token", accessToken));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        String response = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("id");
	}

	public String[] publish(Publishable[] publishables) throws ClientProtocolException, IOException {
		String[] responses = new String[publishables.length];
		for(int i=0;i<publishables.length;i++) {
			responses[i] = this.publish(publishables[i]);
		}
		return responses;
	}
	
	public String getPageAccessToken(String shortTermAccessToken) throws ParseException, IOException {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClient httpClient = httpClientBuilder.build();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("access_token", shortTermAccessToken));
        String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
		HttpGet httpGet = new HttpGet(Config.Facebook.ACCOUNTS_URL+"?"+paramString);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String response = EntityUtils.toString(httpResponse.getEntity());
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        for(int i=0;i<jsonArray.length();i++) {
        	JSONObject jsonItem = jsonArray.getJSONObject(i);
        	String id = jsonItem.getString("id");
        	if(id.equals(Config.Facebook.PAGE_ID)) {
        		return jsonItem.getString("access_token");
        	}
        }
		return null;
	}

	public String getShortLiveAccessToken(String code) throws ClientProtocolException, IOException {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClient httpClient = httpClientBuilder.build();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("client_id", Config.Facebook.APP_ID));
        nameValuePairs.add(new BasicNameValuePair("redirect_uri", Config.Facebook.REDERECT_URI));
        nameValuePairs.add(new BasicNameValuePair("client_secret", Config.Facebook.APP_SECRET));
        nameValuePairs.add(new BasicNameValuePair("code", code));
        String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
		HttpGet httpGet = new HttpGet(Config.Facebook.OAUTH_URL+"?"+paramString);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String response = EntityUtils.toString(httpResponse.getEntity());
        Map<String, String> queryMap = Utils.getQueryMap(response);
        return queryMap.get("access_token");
	}
	
	public String getLongLiveAccessToken(String shortLiveAccessToken) throws ClientProtocolException, IOException {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClient httpClient = httpClientBuilder.build();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("grant_type", "fb_exchange_token"));
        nameValuePairs.add(new BasicNameValuePair("client_id", Config.Facebook.APP_ID));
        nameValuePairs.add(new BasicNameValuePair("client_secret", Config.Facebook.APP_SECRET));
        nameValuePairs.add(new BasicNameValuePair("fb_exchange_token", shortLiveAccessToken));
        String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
		HttpGet httpGet = new HttpGet(Config.Facebook.OAUTH_URL+"?"+paramString);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String response = EntityUtils.toString(httpResponse.getEntity());
        Map<String, String> queryMap = Utils.getQueryMap(response);
        return queryMap.get("access_token");
	}
	

}
