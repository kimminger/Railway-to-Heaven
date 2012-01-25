package com.mycompany.myapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("info")
public interface CloudInfoService extends RemoteService {
	

	public String getInfo(String i);
	//VCAP Functions
	public void stopApp();
	public void startApp();
	public void updateAppmemory();
	public void updateAppinstance();
	public void addApp();
	public void deleteApp();
	public void restartApp();
	public void uploadAppfile();
	public void bindingAppservice();

	public String setAmazonCloudController(String c);
	
	//1und1 Functions
	public String start1und1();
	public String stop1und1();
	public String restart1und1();
	public String suspend1und1();
	public String poweroff1und1();
	public String handle1und1Hardware(String cpu, String HDD, String ram);
	
}
