package com.mycompany.myapp.client;

import java.util.List;

import org.cloudfoundry.client.lib.CloudService;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public interface CloudInfoServiceAsync {

	void getInfo(String i, AsyncCallback<String> callback);

	void stopApp(String appName, AsyncCallback<Void> callback);

	void startApp(String appName, AsyncCallback<Void> callback);

	void addApp(String appName, String framework, int memory,
			List<String> uris, List<String> servicesname,
			AsyncCallback<Void> callback);

	void deleteApp(String appName, AsyncCallback<Void> callback);

	void restartApp(String appName, AsyncCallback<Void> callback);

	void updateAppmemory(String appName, int memory,
			AsyncCallback<Void> callback);

	void updateAppinstance(String appName, int instance,
			AsyncCallback<Void> callback);

	//void uploadAppfile(AsyncCallback<Void> callback);	
	
	void bindingAppservice(String appName, String serviceName,
			AsyncCallback<Void> callback);
	
	void unbindingAppservice(String appName, String serviceName,
			AsyncCallback<Void> callback);

	void createAppservice(String serviceName, String vendor,
			AsyncCallback<Void> callback);

	void setAmazonCloudController(String c, AsyncCallback<String> callback);


	//1und1 Functions
	void start1und1(AsyncCallback<String> callback);

	void stop1und1(AsyncCallback<String> callback);

	void poweroff1und1(AsyncCallback<String> callback);

	void restart1und1(AsyncCallback<String> callback);

	void suspend1und1(AsyncCallback<String> callback);

	void handle1und1Hardware(String cpu, String HDD, String ram,
			AsyncCallback<String> callback);


	

	//AWS Functions
	void startAmazonDEA(AsyncCallback<String> callback);
	
	void stopAmazonDEA(AsyncCallback<String> callback);

	
	

	

	
}
