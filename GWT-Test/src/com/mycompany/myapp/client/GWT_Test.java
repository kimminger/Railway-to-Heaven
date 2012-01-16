package com.mycompany.myapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWT_Test implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		final TabSet topTabSet = new TabSet();  
        topTabSet.setTabBarPosition(Side.TOP);  
        topTabSet.setWidth(400);  
        topTabSet.setHeight(200);
        	Tab t1 = new Tab("Overview");
        	//All Objects within Overview Tab
	            final IButton stretchButton = new IButton("StretchTest");  
	            stretchButton.setWidth(150);  
	            stretchButton.setShowRollOver(true);  
	            stretchButton.setShowDisabled(true);  
	            stretchButton.setShowDown(true);
	            stretchButton.setTitleStyle("stretchTitle");  
	            stretchButton.setVisible(true);
	            
	            com.smartgwt.client.widgets.events.ClickHandler handler = new com.smartgwt.client.widgets.events.ClickHandler() {
					
					public void onClick(ClickEvent event) {
						stretchButton.setTitle("Click Test war erfolgreich");
					}
				};
	            stretchButton.addClickHandler(handler);
	            
        	t1.setPane(stretchButton);
                   		
        	Tab t2 = new Tab("Bla1");
        	//All Objects in Bla1	
        	Tab t3 = new Tab("Bla2");
        	//All Objects in Bla2
        
        	
        //Erstelle Tabset
        topTabSet.addTab(t1);
        topTabSet.addTab(t2);
        topTabSet.addTab(t3);
		
        
        //Erstelle Layout
		HLayout layout = new HLayout();
		layout.addMember(topTabSet);
		layout.addMember(stretchButton);
		layout.draw();
		
		
	
	}
}
