package com.mycompany.myapp.server;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.cloudfoundry.client.lib.CloudApplication;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.UploadStatusCallback;
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


public class CloudInfoServiceImpl extends RemoteServiceServlet implements
		CloudInfoService {

		



	
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
		
		client.login();
		List<CloudApplication> apps = client.getApplications();
		String appInfo = "";
		int n = 1;
		for (CloudApplication app : apps) {
			
			appInfo +="<table><tr><th>"+n+"</th><th>Application</th><th>State</th><th>Instance</th><th>Memory</th><th>URI</th></tr><tr><td></td><td>"+app.getName()+"</td><td>"+app.getState()+"</td><td>"+app.getInstances()+"</td><td>"+app.getMemory()+"</td><td>"+app.getUris()+"</td></tr></table>";
			n++;

		}

		return appInfo;
		
	}
	
	
	public void stopApp() {

		// VCAP Client auf 1&1 Instanzen
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
					"http://api.railwaytoheaven.com");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.login();
		List<CloudApplication> apps = client.getApplications();
		
		for (CloudApplication app : apps) {
			client.stopApplication(app.getName());

		}

	}

	public void startApp() {

		// VCAP Client auf 1&1 Instanzen
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
					"http://api.railwaytoheaven.com");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.login();
		List<CloudApplication> apps = client.getApplications();
		for (CloudApplication app : apps) {
			client.startApplication(app.getName());
			
		}
		

	}

	public void restartApp() {

		// VCAP Client auf 1&1 Instanzen
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
					"http://api.railwaytoheaven.com");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.login();
		List<CloudApplication> apps = client.getApplications();
		
		for (CloudApplication app : apps) {
			
			client.restartApplication(app.getName());

			
		}
		
	}
	
	
	public void addApp() {

		// VCAP Client auf 1&1 Instanzen

		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
					"http://api.railwaytoheaven.com");

		} catch (MalformedURLException e){
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
		String appname = "hai";
		String framework = "rails3";
		List<String> servicesname = new ArrayList<String> (); 
		List<String> uris = new ArrayList<String> ();
		uris.add("hai.railwaytoheaven.com");
		client.login();
	  
		client.createApplication(appname,framework, 256, uris, servicesname);
	}
	
	public void deleteApp() {

		// VCAP Client auf 1&1 Instanzen
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
					"http://api.railwaytoheaven.com");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.login();
		String appname = "hai";
		client.deleteApplication(appname);
	
	}
	
		public void uploadAppfile(){
		
		// VCAP Client auf 1&1 Instanzen

				CloudFoundryClient client = null;
				try {
					client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
							"http://api.railwaytoheaven.com");

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				client.login();
				try {
					client.uploadApplication("hai", new File("/home/hai/git/Railway-to-Heaven/src/wardrobe.zip"),new UploadStatusCallback() {
						
						public void onProcessMatchedResources(int arg0) {
							// TODO Auto-generated method stub
							System.out.println(arg0 );
						}
						
						public void onMatchedFileNames(Set<String> arg0) {
							// TODO Auto-generated method stub
							System.out.println(arg0 );
						}
						
						public void onCheckResources() {
							// TODO Auto-generated method stub
							System.out.println("Check Resource");
						}
					} );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	

		public void updateAppmemory(){
	
			// VCAP Client auf 1&1 Instanzen

			CloudFoundryClient client = null;
			try {
				client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
						"http://api.railwaytoheaven.com");

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			client.login();
			client.updateApplicationMemory("wardrobe", 256);
	
		}
		
		public void updateAppinstance(){
			
			// VCAP Client auf 1&1 Instanzen

					CloudFoundryClient client = null;
					try {
						client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
								"http://api.railwaytoheaven.com");

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					client.login();
					client.updateApplicationInstances("wardrobe", 2);
			
		}
		
		

//Kim Rohner		
		
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
			
			String vmIDs = "";
			// EinsundEinsServer server = new EinsundEinsServer();
			try {

			String ids = EinsundEinsServer.getAllvmIDs();
			return ids;
			} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}


			/*
			//Schleife über alle JSON Objekte
			for (int i = 0; i < ja.length();i++){
			try {
			JSONObject j = ja.getJSONObject(i);
			EinsundEinsServer server = new EinsundEinsServer(j);
			server.getVmID();
			//Befüllt Array
			String ip = (String) j.get("ip");
			// int index = 0;
			if(ip.equals("217.160.94.112") || ip.equals("217.160.94.107") || ip.equals("217.160.94.108") || ip.equals("217.160.94.109")){
			String vmID = (String) j.get("vmid").toString();
			text += vmID + "<br/>";
			}
			*/



			// return vmIDs.toString();
			return vmIDs;
		}
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
