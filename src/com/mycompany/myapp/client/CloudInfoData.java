package com.mycompany.myapp.client;

import java_cup.version;

public class CloudInfoData {
	private static CloudRecord[] records;  
	  
    public static CloudRecord[] getRecords() {  
        if (records == null) {  
            records = getNewRecords();  
        }  
        return records;  
    }  
  
    public static CloudRecord[] getNewRecords() {  
        return new CloudRecord[]{  
                new CloudRecord("wardrobe", "running", "1.0")

        };  
    }  
    
 /*   public static CloudRecord[] createListGridRecords() {  
        return new CloudRecord[]{  
                new CloudRecord(getName(),getStatus(), getVersion())

        };  
    } */ 
   }
