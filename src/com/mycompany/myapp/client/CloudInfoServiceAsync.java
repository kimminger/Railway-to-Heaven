package com.mycompany.myapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CloudInfoServiceAsync {

	void myMethod(String s, AsyncCallback<String> callback);

	void getInfo(String i, AsyncCallback<String> asyncCallback);

	void setAmazonCloudController(String c, AsyncCallback<String> callback);
}