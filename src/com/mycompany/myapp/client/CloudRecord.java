package com.mycompany.myapp.client;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CloudRecord extends ListGridRecord {
	public CloudRecord() {  
    }  
  
    public CloudRecord(String name, String status, String version) {  
        setName(name);  
        setStatus(status);  
        setVersion(version);  
         
    }  

    public void setName(String name) {  
        setAttribute("name", name);  
    }  
  
    public String getName() {  
        return getAttributeAsString("name");  
    }  
    
    public void setStatus(String status) {  
        setAttribute("status", status);  
    }  
  
    public String getStatus() {  
        return getAttributeAsString("status");  
    }  
    public void setVersion(String version) {  
        setAttribute("version", version);  
    }  
  
    public String getVersion() {  
        return getAttributeAsString("version");  
    }  
}
