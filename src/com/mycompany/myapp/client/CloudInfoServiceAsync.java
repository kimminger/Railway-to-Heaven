package com.mycompany.myapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public interface CloudInfoServiceAsync {

	void getInfo(String i, AsyncCallback<String> callback);

	void stopApp(AsyncCallback<Void> callback);

	void startApp(AsyncCallback<Void> callback);

	void addApp(AsyncCallback<Void> callback);

	void deleteApp(AsyncCallback<Void> callback);

	void restartApp(AsyncCallback<Void> callback);

	void updateAppmemory(AsyncCallback<Void> callback);

	void updateAppinstance(AsyncCallback<Void> callback);

	void uploadAppfile(AsyncCallback<Void> callback);	

	void setAmazonCloudController(String c, AsyncCallback<String> callback);



	void start1und1(AsyncCallback<String> callback);
}
