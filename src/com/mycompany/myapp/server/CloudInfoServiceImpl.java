package com.mycompany.myapp.server;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
import org.cloudfoundry.client.lib.CloudApplication;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.UploadStatusCallback;
import org.json.JSONException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.CloudInfoService;


public class CloudInfoServiceImpl extends RemoteServiceServlet implements
		CloudInfoService {

	//Konstanten für Methode handle1und1
	final String START = "start";
	final String STOP = "stop";
	final String RESTART = "restart";
	final String SUSPEND = "suspend";
	final String POWEROFF = "poweroff";
	final String HDD = "300";
	
	//Konstanten für AWS-Methoden
	final String IMAGEID = "ami-b96b55cd";


	
	public String getInfo(String i) {
		
	
		// VCAP Client auf Amazon Instanzen
		CloudFoundryClient client = null;
		try {

			client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
					"http://api.railwaytoheaven.de");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.login();
		List<CloudApplication> apps = client.getApplications();
		String appInfo = "";
		int n = 1;
		for (CloudApplication app : apps) {
			
			appInfo +="<table><tr><th>"+n+"</th><th>Application</th><th>State</th><th>Instance</th><th>Memory</th><th>Service</th><th>URI</th></tr><tr><td></td><td>"+app.getName()+"</td><td>"+app.getState()+"</td><td>"+app.getInstances()+"</td><td>"+app.getMemory()+"</td><td>"+app.getServices()+"</td><td>"+app.getUris()+"</td></tr></table>";
			n++;

		}

		return appInfo;
		
	}
	
	
	public void stopApp() {

		// VCAP Client auf 1&1 Instanzen
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
					"http://api.railwaytoheaven.de");

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
					"http://api.railwaytoheaven.de");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.login();
		List<CloudApplication> apps = client.getApplications();
		for (CloudApplication app : apps) {
			//if (app == equals(eingabe)){
				client.startApplication(app.getName());
				//System.out.println("App" + eingabe + "" );}
			//else
				//System.out.println("This app is not found!" );
					
			}
			//client.startApplication(app.getName());
			
		}
		

	

	public void restartApp() {

		// VCAP Client auf 1&1 Instanzen
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
					"http://api.railwaytoheaven.de");

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
					"http://api.railwaytoheaven.de");

		} catch (MalformedURLException e){
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
		client.login();
		String appname = "hello";
		String framework = "rails3";
		List<String> servicesname = new ArrayList<String> (); 
		List<String> uris = new ArrayList<String> ();
		uris.add("hai.railwaytoheaven.de");
		
	  	client.createApplication(appname,framework, 128, uris, servicesname);
	  	
	}
	
	
	public void deleteApp() {

		// VCAP Client auf 1&1 Instanzen
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
					"http://api.railwaytoheaven.de");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.login();
		String appname = "hello";
		client.deleteApplication(appname);
	
	}
	
		public void uploadAppfile(){
		
		// VCAP Client auf 1&1 Instanzen

				CloudFoundryClient client = null;
				try {
					client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
							"http://api.railwaytoheaven.de");

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				client.login();
				String appName = "hello";
				try {
					client.uploadApplication("hello", new File("/home/hai/git/Railway-to-Heaven/src/wardrobe.zip"),new UploadStatusCallback() {
						
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
						"http://api.railwaytoheaven.de");

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			client.login();
			client.updateApplicationMemory("hello", 256);
	
		}
		
		public void updateAppinstance(){
			
			// VCAP Client auf 1&1 Instanzen

					CloudFoundryClient client = null;
					try {
						client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
								"http://api.railwaytoheaven.de");

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					client.login();
					client.updateApplicationInstances("hello", 2);
			
		}
		

		public void bindingAppservice(){
			
			// VCAP Client auf 1&1 Instanzen

					CloudFoundryClient client = null;
					try {
						client = new CloudFoundryClient("moritz-behr@web.de", "moritz",
								"http://api.railwaytoheaven.de");

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					client.login();
					
					client.bindService("hello","mongodb_hai");
					
			
		}
		
	
		  
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Autor Kim Rohner Mailto: rohner.kim at gmail.com
		
		//TODO Übersicht über alle Instanzen
		
//		private void init(){
//			
//		}
		
		
		
		public String setAmazonCloudController(String c){
			/*
			instanz erstellen (large mit ami)
			instanz starten
			security group default
			keypair cf.pem
			
			anbinden an elastische ip nur bei cloud controller
			userdata setzen auf String Rest;
			
			instanz rebooten.*/
			
			AWSCredentials credentials;
			try {
				credentials = new PropertiesCredentials(
				        CloudInfoServiceImpl.class.getResourceAsStream("AwsCredentials.properties"));
				
				AmazonEC2 ec2 = new AmazonEC2Client(credentials);
		        ec2.setEndpoint("https://eu-west-1.ec2.amazonaws.com");
		        
		        String userData = "dea";
		        RunInstancesRequest req = new RunInstancesRequest()
		        	.withInstanceType("m1.large")
		        	.withImageId(IMAGEID)
		        	.withMinCount(2)
		        	.withMaxCount(2)
		        	.withSecurityGroupIds("sg-73243f07")
		        	.withKeyName("cf")
		        	.withUserData(Base64.encodeBase64String(userData.getBytes()));
		        
		        RunInstancesResult res = ec2.runInstances(req);
		        
		        List<Instance> instances = res.getReservation().getInstances();
		        int index = 1;
		        for(Instance instance : instances){
		        	CreateTagsRequest tagReq = new CreateTagsRequest();
		        	tagReq.withResources(instance.getInstanceId())
		        		.withTags(new Tag("Name", "Kim-DEA" + index));
		        	ec2.createTags(tagReq);
		        	index++;
		        }
		        
		        /*String inst = instances.toString();
		        //Start der neuen Instanz
		        StartInstancesRequest start = new StartInstancesRequest().withInstanceIds(inst);
				ec2.startInstances(start);*/
		        
		        
		        ec2.stopInstances(new StopInstancesRequest().withInstanceIds(instances.toString()));
				
			} catch (IOException e) {
				e.printStackTrace();
			}

	        
			
			return "Instanz gestartet";
			/*
				String text = "test";
							//AWS Client Initialisierung
							AWSCredentials credentials = null;
							try {
								credentials = new PropertiesCredentials(
								CloudInfoServiceImpl.class
								.getResourceAsStream("AwsCredentials.properties"));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
						return text;
						*/
		}
		

					

							/*
							DescribeInstancesResult describeInstancesRequest = ec2.describeInstances();
							List<Reservation> reservations = describeInstancesRequest.getReservations();
							Set<Instance> instances = new HashSet<Instance>();

							for (Reservation reservation : reservations) {
							instances.addAll(reservation.getInstances());
							}
							text = "You have " + instances.size() + " Amazon EC2 instance(s) running.";
							*/

		//Interne Funktion zum Umgang mit der 1und1 API
		private void handle1und1(String command){
			String[] server;
			//TODO Buttons im Frontend anpassen
			try {
				server = EinsundEinsServer.getAllvmIDs();
				//Startet alle Server
				for (int i = 0; i < server.length; i++) {
					EinsundEinsServer kim = new EinsundEinsServer(server[i]);
					if(command == STOP)
					kim.stop();
					else if (command == START){
						kim.start();
					}
					else if(command == RESTART){
						kim.restart();
					}
					else if(command == SUSPEND){
						kim.suspend();
					}
					else if(command == POWEROFF){
						kim.poweroff();
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
		
	//Function handlers
	public String start1und1() {
		handle1und1(START);
		return "1und1-Instanzen gestartet";
	}
	
	public String stop1und1() {
		handle1und1(STOP);
		return "1und1-Instanzen gestoppt";
	}
	
	public String restart1und1() {
		handle1und1(RESTART);
		return "1und1-Instanzen neu gestartet";
	}
	
	public String suspend1und1() {
		handle1und1(SUSPEND);
		return "1und1-Instanzen schlafen jetzt";
	}
	
	public String poweroff1und1() {
		handle1und1(POWEROFF);
		return "1und1-Instanzen ausgeschaltet";
	}
	
	public String handle1und1Hardware(String cpu, String HDD, String ram){
		//TODO checkbox oder 3 verschiedene Konfigs für jeden für cpu, hdd, ram , klein mittel groß
		
		String[] server;
		//TODO Buttons im Frontend anpassen
		try {
			server = EinsundEinsServer.getAllvmIDs();
			//Startet alle Server
			for (int i = 0; i < server.length; i++) {
				EinsundEinsServer kim = new EinsundEinsServer(server[i]);
				kim.configureHardware(cpu, HDD, ram);
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

		return "Changes completed!";
	}

	
}
		
