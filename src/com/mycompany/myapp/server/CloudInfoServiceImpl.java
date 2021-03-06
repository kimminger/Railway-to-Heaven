package com.mycompany.myapp.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
import org.cloudfoundry.client.lib.CloudApplication;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.CloudService;
import org.json.JSONException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AssociateAddressRequest;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceStatus;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.CloudInfoService;

public class CloudInfoServiceImpl extends RemoteServiceServlet implements
		CloudInfoService {

	/**
	 *  Konstanten für Methode handle1und1
	 */
	final String START = "start";
	final String STOP = "stop";
	final String RESTART = "restart";
	final String SUSPEND = "suspend";
	final String POWEROFF = "poweroff";
	final String HDD = "300";

	/**
	 *  Konstanten für AWS-Methoden
	 */
	final String IMAGEID = "ami-b96b55cd";
	/**
	 *  Objektvariablen
	 */
	private AmazonEC2 ec2;
	private List<Instance> instanceIds;

	/**
	 *  CloudFoundryClient Parameter
	 */
	final String email = "moritz-behr@web.de";
	final String password = "moritz";
	final String amazonCloudcontrollerURL = "http://api.railwaytoheaven.de";
	final String einsundeinsCloudcontrollerURL = "http://api.railwaytoheaven.com";

	private String elasticIp;
	
	/**
	 *  VCAP Methode
	 */
	/**
	 *  getInfo Methode gibt Apps-Information an
	 */
		
	public String getInfo(String url) {
		String title = "";
		/**
		 * if-else if wird benutzt, um die Service Providers automatisch zu erkennen
		 */
		if(url == amazonCloudcontrollerURL){
			title = "Amazon Web Service - Apps";
		}
		else if(url == einsundeinsCloudcontrollerURL){
			title = "1&1 Cloud Server - Apps";
		}
		
		/**
		 *  VCAP Client auf Amazon Instanzen
		 */
		CloudFoundryClient client = null;
		try {

			client = new CloudFoundryClient(email, password, url);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}  
		
		/**
		 * VCAP client login
		 */
			client.login();
			List<CloudApplication> apps = client.getApplications();
			String appInfo = "";
			int n = 1;
		/**
		 * for-Schleife wird benutzt, um die Apps in der Liste CloudApplication zu suchen und informieren 
		 */
		for (CloudApplication app : apps) {

			appInfo += "<p1><b>"+title+"</b></p1><table><tr><th>"
					+ n
					+ "</th><th>Application</th><th>State</th><th>Instance</th><th>Memory</th><th>Service</th><th>URI</th></tr><tr><td></td><td>"
					+ app.getName() + "</td><td>" + app.getState()
					+ "</td><td>" + app.getInstances() +"</td><td>"
					+ app.getMemory() + "</td><td>" + app.getServices()
					+ "</td><td>" + app.getUris() + "</td></tr></table>";
			n++;

		}

		return appInfo;
		
	}
	
	
	/**
	 * stopApp Mehthode stoppt die ausgewählte Applikation
	 */
	public String stopApp(String appName) {

		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient(email, password, amazonCloudcontrollerURL);

		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}

		client.login();
		List<CloudApplication> apps = client.getApplications();
		/**
		 * for-Schleife wird benutzt, um die Apps in der Liste CloudApplication zu suchen und informieren 
		 */
		for (CloudApplication app : apps) {
			/**
			 * if-else if wird benutzt, um die appName in der List CloudApplication zu identifizieren
			 */
			if (app.getName().equals(appName)) {
				client.stopApplication(appName);
			}

		}
		return "App is stopped!";
	}

	/**
	 * startApp Methode startet die ausgewählte Applikation
	 */
	public String startApp(String appName) {

		
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient(email, password, amazonCloudcontrollerURL);

		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}

		client.login();

		List<CloudApplication> apps = client.getApplications();
		for (CloudApplication app : apps) {

			if (app.getName().equals(appName)) {
				client.startApplication(appName);
			}
		}
		return "App start";
	}
	/**
	 * restartApp Methode startet die ausgewählte Applikation neu 
	 */
	public String restartApp(String appName) {

	
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient(email, password, amazonCloudcontrollerURL);

		} catch (MalformedURLException e) {
		
			e.printStackTrace();
		}

		client.login();
		List<CloudApplication> apps = client.getApplications();

		for (CloudApplication app : apps) {

			if (app.getName().equals(appName)) {
				client.restartApplication(appName);
			}

		}
		return "App restart";
	}
	/**
	 * addApp Methode fügt die ausgewählte Applikation hinzu
	 */
	public void addApp(String appName, String framework, int memory,
			List<String> uris, List<String> servicesname) {


		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient(email, password, amazonCloudcontrollerURL);

		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		client.login();

		client.createApplication(appName, framework, memory, uris, servicesname);

	}
	/**
	 * deleteApp Methode löscht die ausgewählte Applikation
	 */
	public String deleteApp(String appName) {

		
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient(email, password, amazonCloudcontrollerURL);

		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}

		client.login();
		client.deleteApplication(appName);
		return "finish";
	}
	/**
	 * updateAppmemory Methode aktualisiert neue Memory für die ausgewählte Applikation
	 */
	public String updateAppmemory(String appName, int memory) {

		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient(email, password, amazonCloudcontrollerURL);

		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}

		client.login();
		client.updateApplicationMemory(appName, memory);
		return "finish";
	}
	/**
	 * updateAppinstance Methode aktualisiert neue Instances für die ausgewählte Applikation
	 */
	public String updateAppinstance(String appName, int instances) {

		
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient(email, password, amazonCloudcontrollerURL);

		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}

		client.login();
		client.updateApplicationInstances(appName, instances);
		return "finish";
	}
	/**
	 * bindingAppservice Methode verbindet ein bestehendes Service für die ausgewählte Applikation
	 */
	public String bindingAppservice(String appName, String serviceName) {


		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient(email, password, amazonCloudcontrollerURL);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.login();

		client.bindService(appName, serviceName);
		return "finish";
	}
	/**
	 * unbindingAppservice Methode trennt ein verbundetes Service von der ausgewählten Applikation
	 */
	public String unbindingAppservice(String appName, String serviceName) {

		
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient(email, password, amazonCloudcontrollerURL);

		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}

		client.login();

		client.unbindService(appName, serviceName);
		return "finish";
	}
	/**
	 * createAppservice Methode erstellt ein neues Service (Es hat leider nicht funktioniert)
	 */
	public void createAppservice(String serviceName, String vendor/*
																 * , String
																 * tier, String
																 * version
																 */) {

		System.out.println("hai test create app");
		

		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient(email, password, amazonCloudcontrollerURL);

		} catch (MalformedURLException e) {
			

			e.printStackTrace();
		}

		client.login();
		CloudService service = new CloudService();
		service.setName(serviceName);
		service.setVendor(vendor);
		// service.setTier(tier);
		// service.setVersion(version);
		client.createService(service);

	}

	/**
	 *  Autor Kim Rohner Mailto: rohner.kim at gmail.com
	 */

	public String getAwsInfo(String i){
			
		AWSCredentials credentials;
			try {
				credentials = new PropertiesCredentials(
						CloudInfoServiceImpl.class
								.getResourceAsStream("AwsCredentials.properties"));
				
				ec2 = new AmazonEC2Client(credentials);
				ec2.setEndpoint("https://eu-west-1.ec2.amazonaws.com");
			
			 DescribeInstancesResult res = ec2.describeInstances();
			 
			 List<Reservation> reservations = res.getReservations(); 
			 String text = "";
			 int n = 1;
			 for (Reservation reservation : reservations) {
				 for(Instance instance : reservation.getInstances()){
					 text += "<br/><p1><b>Amazon Web Service Instances</b></p1><table><tr><th>"
								+ n
								+ "</th><th>Instance-ID</th><th>State</th><th>Type</th><th>Image-ID</th><th>Tags</th><th>Public DNS-Name</th></tr><tr><td></td><td>"
								+ instance.getInstanceId() + "</td><td>" + instance.getState()
								+ "</td><td>" + instance.getInstanceType() +"</td><td>"
								+ instance.getImageId() + "</td><td>" + instance.getTags()
								+ "</td><td>" + instance.getPublicDnsName() + "</td></tr></table>";
					 n++;
				 }
			 }

			return text;
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "Error while processing";
	}
	
	
	/**
	 *  Klasseninterne Methode zur Initialisierung eines EC2-Client Objektes und
	 * @param userData
	 * @return
	 */
	/**
	 *  RunInstance Aufrufs
	 * @param userData
	 * @return 
	 */
	private List<Instance> handleStartAws(String userData) {
		AWSCredentials credentials;
		try {
			credentials = new PropertiesCredentials(
					CloudInfoServiceImpl.class
							.getResourceAsStream("AwsCredentials.properties"));

			ec2 = new AmazonEC2Client(credentials);
			ec2.setEndpoint("https://eu-west-1.ec2.amazonaws.com");

			RunInstancesRequest awsRequest = new RunInstancesRequest()
					.withInstanceType("m1.large")
					.withImageId(IMAGEID)
					.withMinCount(1)
					.withMaxCount(1)
					.withSecurityGroupIds("sg-73243f07")
					.withKeyName("cf")
					.withUserData(
							Base64.encodeBase64String(userData.getBytes()));

			RunInstancesResult awsResult = ec2.runInstances(awsRequest);
			instanceIds = awsResult.getReservation().getInstances();

			int index = 1;
			for (Instance instance : instanceIds) {
				CreateTagsRequest tagReq = new CreateTagsRequest();
				tagReq.withResources(instance.getInstanceId()).withTags(
						new Tag("Name", "Kim-" + userData + index),
						new Tag("Node", userData));
				ec2.createTags(tagReq);
				index++;
			}

			return (List<Instance>) instanceIds;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *  getter and setter für ElasticIP
	 * @return
	 */
	public String getElasticIp() {
		return elasticIp;
	}

	public String setElasticIp(String elasticIp) {
		this.elasticIp = elasticIp;

		return "Magic in progress...Now START the Cloud Controller to continue";
	}

	/**
	 *  Starten einer CloudController und Rest Instanz auf Amazon
	 */
	/**
	 * Instanz sollte bestehen, bevor andere Nodes gestartet werden
	 */
	public String startAmazonCloudController(String c) {
		/**
		 * Ablauf: Instanz wird gestartet, Warten bis gestartet, Elastic IP übergeben, 
		 * , reboot
		 */

		handleStartAws("rest");

		/**
		 * Nimm dir alle ids der Instanzen, die gerade gestartet werden
		 */
		String instanceIDS = null;
		for(Instance inst : instanceIds){
			instanceIDS = inst.getInstanceId(); 
		}
		
		/**
		 *  Status der Instanzen wird abgefragt, so lange bis Instanzen gestartet sind
		 */
		
		DescribeInstanceStatusRequest describeInstanceStatusRequest = new DescribeInstanceStatusRequest()
				.withInstanceIds(instanceIDS);
		DescribeInstanceStatusResult describeInstanceStatusResult = ec2
				.describeInstanceStatus(describeInstanceStatusRequest);
		List<InstanceStatus> instanceStatus = describeInstanceStatusResult
				.getInstanceStatuses();
		while (instanceStatus.size() < 1) {
			/**
			 *  Status Aktualiserung
			 */
			describeInstanceStatusResult = ec2
					.describeInstanceStatus(describeInstanceStatusRequest);
			instanceStatus = describeInstanceStatusResult.getInstanceStatuses();
		}

		/**
		 * Zuordnung Elastic IP
		 */
		AssociateAddressRequest associateAddressReq = new AssociateAddressRequest();
		String instanceId = null;
		for (Instance instance : instanceIds) {
			instanceId = instance.getInstanceId();
			elasticIp = getElasticIp();
		}
		ec2.associateAddress(associateAddressReq.withInstanceId(instanceId)
				.withPublicIp(elasticIp));
		System.out.println(instanceId);
		ArrayList<String> rebootInstances = new ArrayList<String>();
		for (Instance instance : instanceIds) {
			rebootInstances.add(instance.getInstanceId());
		}
		System.out.println(rebootInstances);

		ec2.rebootInstances(new RebootInstancesRequest()
				.withInstanceIds(rebootInstances));

		String result = "Cloud Controller und Rest wird gestartet mit InstanceID: "
				+ instanceId;

		return result;

	}

	/**
	 * tartet DEA Instanz und gibt InstanzID in DialogBox zurück
	 */
	public String startAmazonDEA() {
		handleStartAws("dea");
		String inst = null;
		for (Instance instance : instanceIds) {
			inst = instance.getInstanceId();
		}
		String result = "DEA Node wird gestarted: " + "with InstanceID: "
				+ inst;
		return result;

	}

	/**
	 * tartet Mongodb Instanz und gibt InstanzID in DialogBox zurück
	 */
	public String startAmazonMongoDB() {
		handleStartAws("mongodb0");
		String inst = null;
		for (Instance instance : instanceIds) {
			inst = instance.getInstanceId();
		}
		String result = "MongoDB Node wird gestarted: " + "with InstanceID: "
				+ inst;
		return result;
	}

	/**
	 * toppt alle Amazon Instanzen
	 */
	public String stopAmazonInstances(String command) {
		System.out.println(command);
		AWSCredentials credentials;
		try {
			credentials = new PropertiesCredentials(
					CloudInfoServiceImpl.class
							.getResourceAsStream("AwsCredentials.properties"));
			
			ec2 = new AmazonEC2Client(credentials);
			ec2.setEndpoint("https://eu-west-1.ec2.amazonaws.com");
		
		 DescribeInstancesResult res = ec2.describeInstances();
		 
		 List<Reservation> reservations = res.getReservations(); 
		 
		 ArrayList<String> stopInstances = new ArrayList<String>();
		 for (Reservation reservation : reservations) {
			 for(Instance instance : reservation.getInstances()){
				 stopInstances.add(instance.getInstanceId());
			}
		 }
		 
		 if(command.equals("stop")){
			 ec2.stopInstances(new StopInstancesRequest(stopInstances));
			 return "Amazon Instanzen werden gestoppt!";
		 }
		 else if(command.equals("terminate")){
			 ec2.terminateInstances(new TerminateInstancesRequest(stopInstances));
			 return "Hasta La Vista Instances !!!";
		 }
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Error";
	}

	/**
	 * Interne Funktion zum Umgang mit der 1und1 API
	 * @param command
	 */
	private void handle1und1(String command) {
		String[] server;
		try {
			server = EinsundEinsServer.getAllvmIDs();
			/**
			 *  Startet alle Server
			 */
			for (int i = 0; i < server.length; i++) {
				EinsundEinsServer kim = new EinsundEinsServer(server[i]);
				if (command == STOP)
					kim.stop();
				else if (command == START) {
					kim.start();
				} else if (command == RESTART) {
					kim.restart();
				} else if (command == SUSPEND) {
					kim.suspend();
				} else if (command == POWEROFF) {
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

	/**
	 *  Function handlers
	 */
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

	/**
	 *  Übergibt im Frontend definierte Hardware-Werte an 1und1 Server
	 */
	public String handle1und1Hardware(String cpu, String HDD, String ram) {

		String[] server;
		try {
			server = EinsundEinsServer.getAllvmIDs();
			// Startet alle Server
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

		return "Changes transmitted!";
	}

}
