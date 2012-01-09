package com.mycompany.myapp.server;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cloudfoundry.client.lib.CloudApplication;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.CloudInfo;
import org.cloudfoundry.client.lib.CloudInfo.Runtime;


public class VCAPClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws MalformedURLException{
		
		CloudFoundryClient client = null;
		try {
			client = new CloudFoundryClient("moritz-behr@web.de", "moritz", "http://api.railwaytoheaven.de");
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CloudInfo info = client.getCloudInfo();
		String name = info.getName();
		System.out.println("CLOUD INFO");
		System.out.println("Name : "+name);
		String description = info.getDescription();
		System.out.println("Description : "+description);
		//Iterator eingefügt, aber noch nicht getestet
		/*Iterator<Runtime> it = info.getRuntimes().iterator();
		while(it.hasNext())
			{
				System.out.println("Runtime: " + it.next());
			}*/
		System.out.println();
		
		client.login();
		
		showApps(client);
		
		
		/*System.out.println("Create a new application.");	
		List<String> urls = new ArrayList<String>();
		urls.add("otto.vcap.me");
		client.createApplication("ottos-app", "sinatra", 32,urls ,new ArrayList<String>());
		showApps(client);

		// upload application
		
		// start application
		
		System.out.println("Delete an application.");
		client.deleteApplication("ottos-app");
		showApps(client);

		client.logout();
*/
	}

	private static void showApps(CloudFoundryClient client) {
		System.out.println("CLOUD APPLICATIONS");
		List<CloudApplication> apps = client.getApplications();
		for(CloudApplication app : apps)
			System.out.println("Application : "+app.getName()+" | State : "+app.getState() + " | Instances: "+app.getInstances());
		System.out.println();
	}

}
