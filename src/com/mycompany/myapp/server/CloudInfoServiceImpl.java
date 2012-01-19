package com.mycompany.myapp.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.cloudfoundry.client.lib.CloudApplication;
import org.cloudfoundry.client.lib.CloudFoundryClient;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.CloudInfoService;

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
}
