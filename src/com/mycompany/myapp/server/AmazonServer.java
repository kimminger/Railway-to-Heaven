package com.mycompany.myapp.server;

public class AmazonServer {
	
	String credentials;
	String endpoint;
	String ami;
	String elasticIp;
	String instanceID;
	int keyPair;
	String status;
	
	public String getCredentials() {
		return credentials;
	}
	
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getAmi() {
		return ami;
	}
	public void setAmi(String ami) {
		this.ami = ami;
	}
	public String getElasticIp() {
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
	public void setStatus(String status) {
		this.status = status;
	}
	
//	get elastic ip -> ec2.describeAddresses()
	
}
