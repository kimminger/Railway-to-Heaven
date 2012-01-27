package com.mycompany.myapp.server;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
import org.cloudfoundry.client.lib.CloudApplication;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.CloudInfo.Framework;
import org.cloudfoundry.client.lib.CloudService;
import org.cloudfoundry.client.lib.UploadStatusCallback;
import org.json.JSONException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AssociateAddressRequest;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
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

	// Konstanten für Methode handle1und1
	final String START = "start";
	final String STOP = "stop";
	final String RESTART = "restart";
	final String SUSPEND = "suspend";
	final String POWEROFF = "poweroff";
	final String HDD = "300";

	// Konstanten für AWS-Methoden
	final String IMAGEID = "ami-b96b55cd";
	// Objektvariablen
	private AmazonEC2 ec2;
	private List<Instance> instanceIds;
	private String elasticIp;

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

			appInfo += "<table><tr><th>"
					+ n
					+ "</th><th>Application</th><th>State</th><th>Instance</th><th>Memory</th><th>Service</th><th>URI</th></tr><tr><td></td><td>"
					+ app.getName() + "</td><td>" + app.getState()
					+ "</td><td>" + app.getInstances() + "</td><td>"
					+ app.getMemory() + "</td><td>" + app.getServices()
					+ "</td><td>" + app.getUris() + "</td></tr></table>";
			n++;

		}

		return appInfo;

	}

	public void stopApp(String appName) {

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
			if (app.getName().equals(appName)) {
				client.stopApplication(appName);
			}

		}

	}

	public void startApp(String appName) {

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

			if (app.getName().equals(appName)) {
				client.startApplication(appName);
			}
		}

	}

	public void restartApp(String appName) {

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

			if (app.getName().equals(appName)) {
				client.restartApplication(appName);
			}

		}

	}

	public void addApp(String appName, String framework, int memory,
			List<String> uris, List<String> servicesname) {

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

		// String appname = "hello";
		// String framework = "rails3";
		// List<String> servicesname = new ArrayList<String> ();
		// List<String> uris = new ArrayList<String> ();
		// uris.add("hai.railwaytoheaven.de");

		client.createApplication(appName, framework, memory, uris, servicesname);

	}

	public void deleteApp(String appName) {

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

		client.deleteApplication(appName);

		String appname = "hello";
		client.deleteApplication(appname);

	}

	public void uploadAppfile() {

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
			client.uploadApplication("hello", new File(
					"/home/hai/git/Railway-to-Heaven/src/wardrobe.zip"),
					new UploadStatusCallback() {

						public void onProcessMatchedResources(int arg0) {
							// TODO Auto-generated method stub
							System.out.println(arg0);
						}

						public void onMatchedFileNames(Set<String> arg0) {
							// TODO Auto-generated method stub
							System.out.println(arg0);
						}

						public void onCheckResources() {
							// TODO Auto-generated method stub
							System.out.println("Check Resource");
						}
					});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateAppmemory(String appName, int memory) {

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
		client.updateApplicationMemory(appName, memory);

	}

	public void updateAppinstance(String appName, int instances) {

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
		client.updateApplicationInstances(appName, instances);

	}

	public void updateAppmemory() {

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

	public void updateAppinstance() {

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

	public void bindingAppservice() {

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

		client.bindService("hello", "mongodb_hai");

	}

	public void createAppservice(CloudService service) {

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

		client.createService(service);

	}

	// Autor Kim Rohner Mailto: rohner.kim at gmail.com

	// TODO Übersicht über alle Instanzen

	// Klasseninterne Methode zur Initialisierung eines EC2-Client Objektes und
	// RunInstance Aufrufs
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

	// getter and setter für ElasticIP
	public String getElasticIp() {
		return elasticIp;
	}

	public String setElasticIp(String elasticIp) {
		this.elasticIp = elasticIp;
		
		return "Magic in progress...Now START the Cloud Controller to continue";
	}

	// Starten einer CloudController und Rest Instanz auf Amazon
	// Instanz sollte bestehen, bevor andere Nodes gestartet werden
	public String startAmazonCloudController(String c) {
		/*
		 * Ablauf: Instanz wird gestartet, Elastic IP übergeben, reboot
		 */

		handleStartAws("rest");

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

	public String startAmazonDEA() {
		handleStartAws("dea");
		String inst = null;
		for (Instance instance : instanceIds) {
			inst = instance.getInstanceId();
		}
		String result = "MongoDB Node wird gestarted: " + "with InstanceIDs: "
				+ inst;
		return result;

	}

	// Startet Mongodb Instanz und gibt InstanzID in DialogBox zurück
	public String startAmazonMongoDB() {
		handleStartAws("mongodb0");
		String inst = null;
		for (Instance instance : instanceIds) {
			inst = instance.getInstanceId();
		}
		String result = "MongoDB Node wird gestarted: " + "with InstanceIDs: "
				+ inst;
		return result;
	}

	// Stoppt alle Instanzen
	public String stopAmazonInstances() {

		ArrayList<String> stopInstances = new ArrayList<String>();
		for (Instance instance : instanceIds) {
			stopInstances.add(instance.getInstanceId());
		}

		ec2.stopInstances(new StopInstancesRequest(stopInstances));

		return "Amazon Instanzen werden gestoppt";
	}

	// TODO buttons DEA, mongodb-> sollten erst gestartet werden, wenn
	// cloudcontroller richtig läuft -> mehrere Knoten,
	// cloudController+rest -> 1Node Userdata rest
	/*
	 * 
	 * //TODO infos in Overview reinpacken /* DescribeInstancesResult
	 * describeInstancesRequest = ec2.describeInstances(); List<Reservation>
	 * reservations = describeInstancesRequest.getReservations(); Set<Instance>
	 * instances = new HashSet<Instance>();
	 * 
	 * for (Reservation reservation : reservations) {
	 * instances.addAll(reservation.getInstances()); } text = "You have " +
	 * instances.size() + " Amazon EC2 instance(s) running.";
	 */
	/*
	 * DescribeInstancesResult result = ec2.describeInstances(); for
	 * (Reservation reservation : result.getReservations()) { for (Instance
	 * instance : reservation.getInstances()) { text += "Instanzen: " +
	 * instance.getInstanceId(); text += "Typ: " + instance.getInstanceType();
	 * text += "Lifecycle: " + instance.getInstanceLifecycle();
	 * 
	 * } } return text;
	 */

	// Interne Funktion zum Umgang mit der 1und1 API
	private void handle1und1(String command) {
		String[] server;
		try {
			server = EinsundEinsServer.getAllvmIDs();
			// Startet alle Server
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

	// Function handlers
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

		return "Changes completed!";
	}

}