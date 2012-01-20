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
	
			//	1&1 Cloud Daten von Markus
			final String HOST = "servermanagement-api.1und1.de";
			final String USERNAME = "158341849";
			final String PASSWORD= "emergent";
			final int PORT = 443;
			
	
	public String vmID;
	
	public boolean configurable;
	
	public int ram;
	
	public int contract;
	
	public int cpu;
	
	public String hostname;
	
	public String imagetype;
	
	public int imageid;
	
	public String imagename;
	
	public String ip;

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
	
	//Constructor für JSONObject
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
	
	//Constructor für leeres Argument
	//TODO hier weitermachen
	public EinsundEinsServer() throws JSONException {
		super();
		/*
		this.vmID = "";//String
		this.configurable = "true";
		this.ram = "0";
		this.contract = "0";
		this.cpu = "1";
		this.hostname = "host";
		this.imagetype = (String) server.get("imagetype");
		this.imageid = (Integer) server.get("imageid");
		this.imagename = (String) server.get("imagename");
		this.ip = (String) server.get("ip");
		*/
	}
	
	public void start(){
	
	try {
		Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
		client.doPutServerStateChange(vmID, "CAN_START");
		
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (URISyntaxException e) {
		e.printStackTrace();
	}
	}
	
	public void stop(){
		
		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			client.doPutServerStateChange(vmID, "CAN_STOP");
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		}
		
	public void restart(){
		
		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			client.doPutServerStateChange(vmID, "CAN_RESTART");
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		}
	
public void suspend(){
		
		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			client.doPutServerStateChange(vmID, "CAN_SUSPEND");
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		}
	
public void poweroff(){
	
	try {
		Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
		client.doPutServerStateChange(vmID, "CAN_POWER_OFF");
	
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (URISyntaxException e) {
		e.printStackTrace();
	}
	}

//nice-to-have, aber für spätere Arbeiten überlassen
	public void priceInfo(){} 
	
	public void configureHardware(String cpu, String hdd, String ram){
		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			client.doPutHardwareConfiguration(vmID, cpu, hdd, ram);
		
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public String getState(){
		try {
			Client client = new Client(HOST, PORT, USERNAME, PASSWORD);
			JSONObject j = client.doGetServerState(vmID);
//			j.get(key);
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
	
	//Gibt alle vmIDs zurück, die Team 3 zugeordnet wurden
	public String getAllvmIDs() throws ClientProtocolException, IOException, JSONException, URISyntaxException{
		String text = "";
		Client client;
		
			client = new Client(HOST, PORT, USERNAME, PASSWORD);
			
			//Befüllt Array mit allen verfügbaren Informationen 
			JSONArray ja = client.doGetServers();
			
			//Schleife über Array
			for (int i = 0; i < ja.length();i++){
				
					
					JSONObject j = ja.getJSONObject(i);
					
//					EinsundEinsServer server = new EinsundEinsServer(j);
//					server.getVmID();
					
					String ip = (String) j.get("ip");
//					int index = 0;
					if(ip.equals("217.160.94.112") || ip.equals("217.160.94.107") || ip.equals("217.160.94.108") || ip.equals("217.160.94.109")){
						String vmID = (String) j.get("vmid").toString();
						text += vmID + "<br/>";
						return text.toString();
					}	
			
		
			}
			
			return null;
	}
	
}