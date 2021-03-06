package com.mycompany.myapp.server;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.eorg.client.Client;

public class EinsundEinsServer {
	/*
	 * Hier kommen alle Funktionen rein, die die 1&1 API anbietet
	 */

	// 1&1 Cloud Daten von Markus
	final static String HOST = "servermanagement-api.1und1.de";
	final static String USERNAME = "158341849";
	final static String PASSWORD = "emergent";
	final static int PORT = 443;

	private String vmID;

	private boolean configurable;

	private int ram;

	private int contract;

	private int cpu;

	private String hostname;

	private String imagetype;

	private int imageid;

	private String imagename;

	private String ip;

	public String getVmID() {
		return vmID;
	}

	public void setVmID(String vmID) {
		this.vmID = vmID;
	}

	public boolean isConfigurable() {
		return configurable;
	}

	public void setConfigurable(boolean configurable) {
		this.configurable = configurable;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getContract() {
		return contract;
	}

	public void setContract(int contract) {
		this.contract = contract;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getImagetype() {
		return imagetype;
	}

	public void setImagetype(String imagetype) {
		this.imagetype = imagetype;
	}

	public int getImageid() {
		return imageid;
	}

	public void setImageid(int imageid) {
		this.imageid = imageid;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	// Constructor für vollständige Konfiguration
	public EinsundEinsServer(String vmID, boolean configurable, int ram,
			int contract, int cpu, String hostname, String imagetype,
			int imageid, String imagename, String ip) {
		super();
		this.vmID = vmID;
		this.configurable = configurable;
		this.ram = ram;
		this.contract = contract;
		this.cpu = cpu;
		this.hostname = hostname;
		this.imagetype = imagetype;
		this.imageid = imageid;
		this.imagename = imagename;
		this.ip = ip;
	}

	// Constructor für vmID
	public EinsundEinsServer(String vmID) {
		super();
		Client client;
		try {
			client = new Client(HOST, PORT, USERNAME, PASSWORD);

			// Befüllt JSON Array mit allen verfügbaren Informationen, vmid,
			// cpu, ram, hdd, usw.
			JSONArray ja = client.doGetServers();
			// int index = 0;
			// Schleife über Array
			for (int i = 0; i < ja.length(); i++) {
				JSONObject j = ja.getJSONObject(i);

				String vmid = (String) j.get("vmid").toString();
				if (vmid.equals(vmID)) {
					this.vmID = (String) j.get("vmid").toString();
					this.configurable = (Boolean) j.get("configurable");
					this.ram = (Integer) j.get("ram");
					this.contract = (Integer) j.get("contract");
					this.cpu = (Integer) j.get("cpu");
					this.hostname = (String) j.get("hostname");
					this.imagetype = (String) j.get("imagetype");
					this.imageid = (Integer) j.get("imageid");
					this.imagename = (String) j.get("imagename");
					this.ip = (String) j.get("ip");
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	// Constructor für JSONObject
	public EinsundEinsServer(JSONObject server) throws JSONException {
		super();
		this.vmID = (String) server.get("vmid").toString();
		this.configurable = (Boolean) server.get("configurable");
		this.ram = (Integer) server.get("ram");
		this.contract = (Integer) server.get("contract");
		this.cpu = (Integer) server.get("cpu");
		this.hostname = (String) server.get("hostname");
		this.imagetype = (String) server.get("imagetype");
		this.imageid = (Integer) server.get("imageid");
		this.imagename = (String) server.get("imagename");
		this.ip = (String) server.get("ip");
	}

	public void start() {

		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			client.doPutServerStateChange(this.vmID, "CAN_START");

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void stop() {

		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			client.doPutServerStateChange(this.vmID, "CAN_STOP");

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void restart() {

		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			client.doPutServerStateChange(this.vmID, "CAN_RESTART");

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void suspend() {

		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			client.doPutServerStateChange(this.vmID, "CAN_SUSPEND");

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void poweroff() {

		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			client.doPutServerStateChange(this.vmID, "CAN_POWER_OFF");

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	// nice-to-have, aber für spätere Arbeiten überlassen
	public void priceInfo() {
	}

	public void configureHardware(String cpu, String HDD, String ram) {
		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
		
			client.doPutHardwareConfiguration(this.vmID, cpu, HDD, ram);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public String getState() {
		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			JSONObject j = client.doGetServerState(this.vmID);
			// j.get(key);
			return j.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Gibt alle vmIDs in einem Array zurück, die Team 3 zugeordnet wurden
	public static String[] getAllvmIDs() throws ClientProtocolException,
			IOException, JSONException, URISyntaxException {
		String[] result = new String[4];
		Client client;

		client = new Client(HOST, PORT, USERNAME, PASSWORD);

		// Befüllt Array mit allen verfügbaren Informationen
		JSONArray ja = client.doGetServers();
		int index = 0;
		// Schleife über Array
		for (int i = 0; i < ja.length(); i++) {
			JSONObject j = ja.getJSONObject(i);

			String ip = (String) j.get("ip");
			if (ip.equals("217.160.94.107")
					|| ip.equals("217.160.94.108")
					|| ip.equals("217.160.94.109")) {
				result[index] = (String) j.get("vmid").toString();
				index++;
			}

		}

		return result;
	}

}