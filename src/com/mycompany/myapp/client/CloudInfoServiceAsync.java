package com.mycompany.myapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public interface CloudInfoServiceAsync {

	//void createListGridRecords(CloudRecord[] result,
	//		AsyncCallback<CloudRecord[]> callback);

	//void myMethod(String s, AsyncCallback<String> callback);

	void getInfo(String i, AsyncCallback<String> callback);

		

	void setAmazonCloudController(String c, AsyncCallback<String> callback);



	void start1und1(AsyncCallback<String> callback);
}
