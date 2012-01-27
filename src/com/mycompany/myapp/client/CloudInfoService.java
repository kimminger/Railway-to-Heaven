package com.mycompany.myapp.client;

import java.util.List;

import org.cloudfoundry.client.lib.CloudService;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("info")
public interface CloudInfoService extends RemoteService {
	

	public String getInfo(String i);
	//VCAP Functions
	public void stopApp(String appName);
	public void startApp(String appName);
	public void updateAppmemory(String appName, int memory);
	public void updateAppinstance(String appName, int instance);
	public void addApp(String appName, String framework, int memory, List<String> uris, List<String> servicesname );
	public void deleteApp(String appName);
	public void restartApp(String appName);
	public void uploadAppfile();
	public void bindingAppservice();
	//public void createAppservice(CloudService service);

	//AWS Functions
	public String startAmazonCloudController(String c);
	public String startAmazonDEA();
	public String startAmazonMongoDB();
	public String stopAmazonInstances();
	public String setElasticIp(String elasticIp);
	
	//1und1 Functions
	public String start1und1();
	public String stop1und1();
	public String restart1und1();
	public String suspend1und1();
	public String poweroff1und1();
	public String handle1und1Hardware(String cpu, String HDD, String ram);
	
}
