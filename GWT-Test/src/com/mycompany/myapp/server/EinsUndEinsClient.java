package com.mycompany.myapp.server;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import edu.kit.eorg.client.Client;


public class EinsUndEinsClient {

	
	
	public static void main(String args[]) throws ClientProtocolException, IOException, JSONException, URISyntaxException{
		//1&1 Cloud Adresse benutzen!!!
		String host = "servermanagement-api.1und1.de";
		String username = "158341849";
		String password= "emergent";
		Client client = new Client(host, 443, username, password);
		
		System.out.println();
		
		//Gibt alle Metainfos über Server zurück
		/*JSONArray ja = client.doGetServers();
		System.out.println(ja);*/
		String vmId = "53135";
		org.json.JSONObject jprice = null;
		try {
			jprice = client.doGetServerState(vmId);
		} catch (org.json.JSONException e) {
			e.printStackTrace();
		}
		System.out.println(jprice);
		//TODO: vmId aus JSON-Format auslesen -> Michael fragen
		//Mit for schleife über length für alle json objekte attribute vmid auslesen
		
		//Server einschalten, ausschalten, neu-starten, abschalten
//		{"possibleActions":["CAN_RESTART","CAN_SUSPEND","CAN_POWER_OFF","CAN_STOP"],"state":"RUNNING"}
//		client.doPutServerStateChange(vmId, "CAN_START");
		
		
//		Frage an Markus: Kann man mit doPutInstallImage ein VCAP Image bei 1&1 hinterlegen, dass man damit starten kann?
//		System.out.println(client.doPutInstallImage(vmId, newImageId))
		System.out.println();
		
		

		//json.org/java
		
		
	}
	
	
	
}
