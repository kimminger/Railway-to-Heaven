package com.mycompany.myapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.smartgwt.client.widgets.grid.ListGridRecord;

@RemoteServiceRelativePath("info")
public interface CloudInfoService extends RemoteService {
	
	//public String myMethod(String s);
	public String getInfo(String i);
	//public CloudRecord[] createListGridRecords(CloudRecord result);

}
