package com.mycompany.myapp.server;

import java.net.MalformedURLException;
import java.util.List;

import org.cloudfoundry.client.lib.CloudApplication;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.CloudInfo;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.CloudInfoService;

public class CloudInfoServiceImpl extends RemoteServiceServlet implements CloudInfoService{

	public String myMethod(String s) {
		// TODO Auto-generated method stub
		
		return "Hello, "+ s +" !<br/>Dein Test war erfolgreich! <br/> Phase 1 abgeschlossen!";
	}
	
	public String getInfo(String i) {
		//VCAP Client auf Amazon Instanzen
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz", "http://api.railwaytoheaven.de");
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		CloudInfo info = client.getCloudInfo();

		
		client.login();
		
		List<CloudApplication> apps = client.getApplications();
		String appInfo = "";
		for(CloudApplication app : apps){
			appInfo += "Application : "+app.getName()+" | State : "+app.getState() + " | Instances: "+app.getInstances();
		}
			
			
		
		return appInfo;
	}

}
