package com.mycompany.myapp.server;

import java.io.IOException;
import java.util.ArrayList;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;

public class AmazonServer {
	//Klassenvariablen
	private AmazonEC2 ec2;
    private ArrayList<String> instanceIds;
	
	public String endpoint;
	public String ami;
	public String elasticIp;
	public String instanceID;
	public int keyPair;
	public String status;
	
	

	
	public String getEndpoint() {
		return endpoint;
	}
	
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String getAmi() {
		return ami;
	}
	
	
	public String getElasticIp() {
//		get elastic ip -> ec2.describeAddresses()
		return elasticIp;
	}
	
	public void setElasticIp(String elasticIp) {
		this.elasticIp = elasticIp;
	}
	
	public String getInstanceID() {
		return instanceID;
	}
	
	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}
	
	public int getKeyPair() {
		return keyPair;
	}
	
	public void setKeyPair(int keyPair) {
		this.keyPair = keyPair;
	}
	
	public String getStatus() {
		return status;
	}
	

	//Constructor generiert EC2-Client
	public AmazonServer() throws IOException {
		super();
		init();		
	}

	private void init() throws IOException {
		AWSCredentials credentials = new PropertiesCredentials(
                CloudInfoServiceImpl.class.getResourceAsStream("AwsCredentials.properties"));

        ec2 = new AmazonEC2Client(credentials);
        ec2.setEndpoint("https://eu-west-1.ec2.amazonaws.com");
	}
	
	//übergibt alle verfügbaren Instance IDs
	public String getAllInstanceIDs(){
		return null;
		
	}
	
	public void startInstance(){
		StartInstancesRequest start = new StartInstancesRequest();
		ec2.startInstances(start);
	}
	
	public void stoppInstance(){
		StopInstancesRequest stopp = new StopInstancesRequest(instanceIds);
		ec2.stopInstances(stopp);
	}
	

	
	//TODO
//	methode cleanup -> terminateinstances -> arrays leeren, private AmazonEC2      ec2; private ArrayList<String> instanceIds;

	
}
