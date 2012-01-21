package com.mycompany.myapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.smartgwt.client.widgets.grid.ListGridRecord;

@RemoteServiceRelativePath("info")
public interface CloudInfoService extends RemoteService {
	

	public String getInfo(String i);
	
	public void stopApp();
	
	public void startApp();
	
	public void updateAppmemory();
	
	public void updateAppinstance();
	
	public void addApp();
		
	public void deleteApp();
	
	public void restartApp();
	
	public void updateAppfile();

}
