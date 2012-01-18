package com.mycompany.myapp.server;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.cloudfoundry.client.lib.CloudApplication;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.CloudInfo;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.CloudInfoService;
import com.mycompany.myapp.client.CloudRecord;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CloudInfoServiceImpl extends RemoteServiceServlet implements CloudInfoService{

	/*public String myMethod(String s) {
		// TODO Auto-generated method stub
		
		return "Hello, "+ s +" !<br/>Dein Test war erfolgreich! <br/> Phase 1 abgeschlossen!";
	}*/
	
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
			
			appInfo += "Application: "+app.getName() + " | State : "+app.getState() + " | Instances: "+app.getInstances() + "| Memory:" +app.getMemory() + "| RunningInstance:"+app.getRunningInstances()+ "| Meta"+ app.getMeta()+"| Uris"+app.getUris()+ "Resource" + app.getResources();
			
		}
			
			
		
		return appInfo;
	}
}
	



/*	public static CloudRecord[] createListGridRecords(CloudRecord[] result) {

		CloudRecord[] results = new CloudRecord[15];
    
		CloudFoundryClient client = null;
		
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz", "http://api.railwaytoheaven.de");
	
		} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
		client.login();
	
		List<CloudApplication> apps = client.getApplications();

		int i = 0;

		for(CloudApplication app : apps) {
            results[i] = new CloudRecord();
            results[i].setAttribute("name", app.getName());
            results[i].setAttribute("status", app.getState());
            results[i].setAttribute("version", app.getInstances());
            i++;
    }

    return results;

	}

	
}
	*/
