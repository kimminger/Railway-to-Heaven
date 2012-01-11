package com.mycompany.myapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.CloudInfoService;

public class CloudInfoServiceImpl extends RemoteServiceServlet implements CloudInfoService{

	public String myMethod(String s) {
		// TODO Auto-generated method stub
		return s;
	}

}
