package com.mycompany.myapp.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.cloudfoundry.client.lib.CloudApplication;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.CloudInfoService;

import edu.kit.eorg.client.Client;

public class CloudInfoServiceImpl extends RemoteServiceServlet implements
		CloudInfoService {

	/*public String myMethod(String s) {
		// TODO Auto-generated method stub


		return "Hello, "
				+ s
				+ " !<br/>Dein Test war erfolgreich! <br/> Phase 1 abgeschlossen!";
	}


		
		return "Hello, "+ s +" !<br/>Dein Test war erfolgreich! <br/> Phase 1 abgeschlossen!";
	}*/
	
	public String getInfo(String i) {
		// VCAP Client auf Amazon Instanzen
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
					"http://api.railwaytoheaven.com");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// CloudInfo info = client.getCloudInfo();

		client.login();

		List<CloudApplication> apps = client.getApplications();
		String appInfo = "";

		int n = 1;
		for(CloudApplication app : apps){
			
			//appInfo += "<table border = 1 > <td>Application: "+app.getName() + "</td> <td><span style= padding-left:20px> State : </span>"+app.getState() + "</td><td> <span style= padding-left:20px> Instances:</span> "+app.getInstances() + "</td><td><span style= padding-left:20px> Memory: </span>" +app.getMemory() + "</td><td> <span style= padding-left:20px> Uris: </span>"+app.getUris()+ "</td></table>";
			appInfo += "<table><tr><th>"+n+"</th><th>Application</th><th>State</th><th>Instance</th><th>Memory</th><th>URI</th></tr><tr><td></td><td>"+app.getName()+"</td><td>"+app.getState()+"</td><td>"+app.getInstances()+"</td><td>"+app.getMemory()+"</td><td>"+app.getUris()+"</td></tr></table>";
			n++;

		}

		return appInfo;
	}
	

	public String setAmazonCloudController(String c) {
		String text = "test";
		
		//AWS Client Initialisierung
		AWSCredentials credentials = null;
		try {
			credentials = new PropertiesCredentials(
					CloudInfoServiceImpl.class
							.getResourceAsStream("AwsCredentials.properties"));
		} catch (IOException e1) {
			// return
			// "Credentials were not properly entered into AwsCredentials.properties.";
		}

		
		
		
		AmazonEC2 ec2 = new AmazonEC2Client(credentials);
		 ec2.setEndpoint("https://eu-west-1.ec2.amazonaws.com");

		
		
		 DescribeInstancesResult result = ec2.describeInstances();
		for (Reservation reservation : result.getReservations()) {
			for (Instance instance : reservation.getInstances()) {
				text += "Instanzen: " + instance.getInstanceId(); 
				text += "Typ: " + instance.getInstanceType();
				text += "Lifecycle: " + instance.getInstanceLifecycle();
				
			}
		}
		
		/* VORÜBERLEGUNGEN automatischer Instanzenstart
		RunInstancesRequest req = new RunInstancesRequest();
		req.setImageId("AMI mit cloudcontroller oder was anderem");
		req.setInstanceType("t1.small");
		req.setUserData("");
		*/
		
		//TODO Herausfinden wie man elastische IP festlegt

		/* 
		StartInstancesRequest start = new StartInstancesRequest().withInstanceIds("i-acd85be5");
		ec2.startInstances(start);
		*/
		
		 /*
		 DescribeInstancesResult describeInstancesRequest = ec2.describeInstances();
         List<Reservation> reservations = describeInstancesRequest.getReservations();
         Set<Instance> instances = new HashSet<Instance>();

         for (Reservation reservation : reservations) {
             instances.addAll(reservation.getInstances());
         }
         
         text = "You have " + instances.size() + " Amazon EC2 instance(s) running.";
         */
        
         
         return text;
         
	}


	public String start1und1() {
		// TODO Auto-generated method stub
		String text = "";
		
		//1&1 Cloud Adresse benutzen!!!
		String host = "servermanagement-api.1und1.de";
		String username = "158341849";
		String password= "emergent";
		
		
		Client client = null;
		try {
			client = new Client(host, 443, username, password);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Alle verfügbaren Informationen über 1und1 Server
		JSONArray ja = null;
		try {
			ja = client.doGetServers();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
//		String[] vmIDs = new String[4];
		
		//Schleife über alle JSON Objekte
		for (int i = 0; i < ja.length();i++){
			try {
				JSONObject j = ja.getJSONObject(i);
				EinsundEinsServer server = new EinsundEinsServer(j);
//				server.getIp()
				//Befüllt Array 
				String ip = (String) j.get("ip");
//				int index = 0;
				if(ip.equals("217.160.94.112") || ip.equals("217.160.94.107") || ip.equals("217.160.94.108") || ip.equals("217.160.94.109")){
					String vmID = (String) j.get("vmid").toString();
					text += vmID + "<br/>";
//					vmIDs[index] = vmID;
//					index++;
				}	
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
	
		
//		return vmIDs.toString();
		return text;
	}
}
