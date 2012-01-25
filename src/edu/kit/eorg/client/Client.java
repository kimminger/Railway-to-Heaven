package edu.kit.eorg.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Client {

	private DefaultHttpClient httpclient = new DefaultHttpClient();

	private final String authorizationHeaderValue;

	public static String DEFAULT_HOST_ENDPOINT = "servermanagement-api.1und1.de";
	public static int DEFAULT_HOST_PORT = -1; // 443
	public final String host;
	public final int port;

	public Client(final String host, final int port, final String username,
			final String password) throws ClientProtocolException, IOException {
		String str = username + ":" + password;
		// for some strange reason, the encoded string ends with a newline
		// symbol. trim it away!
		this.authorizationHeaderValue = "Basic "
				+ new String(Base64.encodeBase64(str.getBytes())).trim();
		this.host = host;
		this.port = port;
	}

	public Client(final String username, final String password)
			throws ClientProtocolException, IOException {
		this(DEFAULT_HOST_ENDPOINT, DEFAULT_HOST_PORT, username, password);
	}

	public JSONObject doGet(final String path) throws ClientProtocolException,
			IOException, JSONException, URISyntaxException {
		String result = doHttpGet(path);
		if (result != null)
			return new JSONObject(result);
		else
			return null;
	}

	public JSONArray doList(final String path) throws ClientProtocolException,
			IOException, JSONException, URISyntaxException {
		String result = doHttpGet(path);
		if (result != null)
			return new JSONArray(result);
		else
			return null;
	}

	public void doPut(final String path) {

	}

	public JSONArray doGetServers() throws ClientProtocolException,
			IOException, JSONException, URISyntaxException {
		return doList("/cloudServer/mobile/servers/");
	}

	public JSONObject doGetServer(final String vmId)
			throws ClientProtocolException, IOException, JSONException,
			URISyntaxException {
		return doGet("/cloudServer/mobile/servers/" + vmId);
	}

	public JSONObject doGetServerState(final String vmId)
			throws ClientProtocolException, IOException, JSONException,
			URISyntaxException {
		return doGet("/cloudServer/mobile/servers/" + vmId + "/state");
	}

	public JSONArray doGetServerAvailableImages(final String vmId)
			throws ClientProtocolException, IOException, JSONException,
			URISyntaxException {
		return doList("/cloudServer/mobile/servers/" + vmId + "/images");
	}

	public JSONObject doGetServerPriceInfo(final String vmId)
			throws ClientProtocolException, IOException, JSONException,
			URISyntaxException {
		return doGet("/cloudServer/mobile/servers/" + vmId
				+ "/hardware/priceInfo");
	}

	public JSONObject doGetServerTerms(final String vmId)
			throws ClientProtocolException, IOException, JSONException,
			URISyntaxException {
		return doGet("/cloudServer/mobile/servers/" + vmId + "/hardware/terms");
	}

	public JSONArray doGetServerPredefinedHardwareConfigurations(
			final String vmId) throws ClientProtocolException, IOException,
			JSONException, URISyntaxException {
		return doList("/cloudServer/mobile/servers/" + vmId
				+ "/hardware/priceInfo");
	}

	public JSONObject doGetServerAvailableHardware(final String vmId)
			throws ClientProtocolException, IOException, JSONException,
			URISyntaxException {
		return doGet("/cloudServer/mobile/servers/" + vmId
				+ "/hardware/available");
	}

	public JSONObject doGetServerEstimatedDurationOfHardwareChange(
			final String vmId, String cpu, String hdd, String ram)
			throws ClientProtocolException, IOException, JSONException,
			URISyntaxException {
		return doGet("/cloudServer/mobile/servers/" + vmId
				+ "/hardware/configurationEstimate?cpu=" + cpu + "&hdd=" + hdd
				+ "&ram=");
	}

	public JSONObject doGetServerEstimatedDurationOfImageInstallTime(
			final String vmId, final String imageId)
			throws ClientProtocolException, IOException, JSONException,
			URISyntaxException {
		return doGet("/cloudServer/mobile/servers/" + vmId
				+ "/images/configurationEstimate?imageId=" + imageId);
	}
	
	public String doPutHardwareConfiguration(final String vmId,String cpu, String hdd, String ram) throws ClientProtocolException, IOException, URISyntaxException {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("cpu", cpu);
		params.put("hdd", hdd);
		params.put("ram", ram);
		System.out.println(cpu);
		System.out.println(hdd);
		System.out.println(ram);
		System.out.println(params.toString());
		return doHttpPut("/cloudServer/mobile/servers/"+vmId+"/hardware",params);
	}
	
	// FIXME Unfortunately we don't know the ACTION parameters...
	public String doPutServerStateChange(final String vmId,String action) throws ClientProtocolException, IOException, URISyntaxException {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("action", action);
		return doHttpPut("/cloudServer/mobile/servers/"+vmId+"/state",params);
	}
	
	public String doPutInstallImage(final String vmId,String newImageId) throws ClientProtocolException, IOException, URISyntaxException {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("newImageId", newImageId);
		return doHttpPut("/cloudServer/mobile/servers/"+vmId+"/images",params);
	}

	public void close() {
		httpclient.getConnectionManager().shutdown();
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private String doHttpGet(final String path) throws ClientProtocolException,
			IOException, JSONException, URISyntaxException {
		httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(URIUtils.createURI("https", host, port,
				path, null, null));
		String toReturn = null;
		httpget.addHeader("Authorization", authorizationHeaderValue);
		httpget.addHeader("Accept", "application/json");

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		// convert entity in json object
		if (entity != null) {
			InputStream instream = entity.getContent();
			toReturn = convertStreamToString(instream);
			instream.close();
		}
		// clean up
//		EntityUtils.consume(entity);
		close();
		return toReturn;
	}

	private String doHttpPut(final String path, final HashMap<String,String> params)
			throws ClientProtocolException, IOException, URISyntaxException {
		httpclient = new DefaultHttpClient();
		HttpPut httpput = new HttpPut(URIUtils.createURI("https", host,
				port, path, null, null));
		String toReturn = null;
		final String prettyParams = printParams(params);
		httpput.addHeader("Authorization", authorizationHeaderValue);
//		httpput.addHeader("Accept", "application/json");
		httpput.addHeader("Content-Type", "application/x-www-form-urlencoded");
//		httpput.setHeader("Content-Length", ""+prettyParams.length());

		HttpEntity entity = new StringEntity(prettyParams, "UTF-8");
		httpput.setEntity(entity);
		HttpResponse resp = httpclient.execute(httpput);
		System.out.println(convertStreamToString(resp.getEntity().getContent()));
		toReturn = resp.getStatusLine().toString();
		
		close();
		return toReturn;
	}
	
	private String printParams(HashMap<String, String> params) {
		StringBuffer toReturn = new StringBuffer();
		int size = params.size();
		int i=0;
		for(String param : params.keySet()) {
			toReturn.append(param);
			toReturn.append("=");
			toReturn.append(params.get(param));
			if(++i<size)
				toReturn.append("&");
		}
		return toReturn.toString();
	}
}
