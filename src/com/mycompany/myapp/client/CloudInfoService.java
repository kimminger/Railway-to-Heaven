package com.mycompany.myapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("info")
public interface CloudInfoService extends RemoteService {
	
	//public String myMethod(String s);
	public String getInfo(String i);
	public String setAmazonCloudController(String c);
	
}
