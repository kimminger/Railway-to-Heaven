package com.mycompany.myapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;




/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Vcapsmartgwt implements EntryPoint {
	
	public void onModuleLoad() {
		
		//Show AppsList
		final ListGrid appsGrid = new ListGrid();  
        appsGrid.setWidth(580);  
        appsGrid.setHeight(400);  
        appsGrid.setShowAllRecords(true);  
  
        ListGridField appsField = new ListGridField("name", "Apps", 120);  
        appsField.setCanEdit(false);  
  
        ListGridField deploymentField = new ListGridField("time", "Deployment Time", 170);  
        ListGridField statusField = new ListGridField("status", "Status", 170); 
        ListGridField versionField = new ListGridField("version", "Version", 100); 
        
        appsGrid.setFields(appsField, deploymentField, versionField, statusField); 
        appsGrid.draw();
        
        //Tabset Definition
		final TabSet topTabset = new TabSet();
		topTabset.setTabBarPosition(Side.TOP);
		topTabset.setWidth (600);
		topTabset.setHeight (420);
		
		//Overview Tab
		Tab tTab1 = new Tab("Overview");
		tTab1.setPane(appsGrid);
		
		//Choose Provider Tab
		Tab tTab2 = new Tab("Choose Provider");
		Canvas tabPane2 = new Canvas();  
        tabPane2.setWidth100();  
        tabPane2.setHeight100();  
        tabPane2.addChild(getProviderTab());  
        tTab2.setPane(tabPane2); 
        
               
        //Benchmarking Tab         
        Tab tTab3 = new Tab("Benchmarking");
        Canvas tabPane3 = new Canvas();  
        tabPane3.setWidth100();  
        tabPane3.setHeight100();  
        tabPane3.addChild(getBenchmarkTab());  
        tTab3.setPane(tabPane3); 
        
		topTabset.addTab(tTab1);  
	    topTabset.addTab(tTab2);  
	    topTabset.addTab(tTab3); 
	    
	    HLayout buttons = new HLayout();  
        buttons.setMembersMargin(15);  
        
        topTabset.draw(); 
	}
	
	
    @SuppressWarnings("deprecation")
	private Widget getProviderTab() {  
        DecoratedTabPanel tabPanel = new DecoratedTabPanel();  
        tabPanel.setWidth("550px");  
        tabPanel.setAnimationEnabled(true);  
          
        //Provider Button
           //1&1 Cloud Server
        VerticalPanel vPanel1 = new VerticalPanel();  
        vPanel1.setSpacing(15);  
        vPanel1.setHeight("500px");
        
        Button cloudcontrollerSubmitButton = new Button("Cloud Controller submit");
        cloudcontrollerSubmitButton.setAutoFit(true);
        cloudcontrollerSubmitButton.setLeft(50);
        
       
        Button deaSubmitButton = new Button("DEA Submit");
        deaSubmitButton.setLeft(50);
        Button databaseSubmitButton = new Button("Database Submit"); 
        databaseSubmitButton.setLeft(50);      
        
        DynamicForm cloudControllerURIForm = new DynamicForm();  
        TextItem nameTextItem = new TextItem();
        nameTextItem.setTitle("Cloud Controller URI ");
        cloudControllerURIForm.setFields(nameTextItem);  
        
        DynamicForm deaForm = new DynamicForm();  
        TextItem nameDeaTextItem = new TextItem();
        nameDeaTextItem.setTitle("Amount of instances ");
        deaForm.setFields(nameDeaTextItem);  
        
       
        HTML cloudControllerText = new HTML("1. Cloud Controller");  
        HTML deaText = new HTML("2. DEA");  
        HTML databaseText = new HTML("3. Database");
        HTML mysqlText = new HTML("3.1 MySQL");
        HTML mongoDBText = new HTML("3.2 MongoDB");
        /*nameTextItem.addChangedHandler(new ChangedHandler() {  
            public void onChanged(ChangedEvent event) {  
                String newTitle = (event.getValue() == null ? "" : event.getValue() + "'s ") + "Preferences";  
                topTabSet.setTabTitle(preferencesTab, newTitle);  
            }  
        });  */
        
        
                    
        vPanel1.add(cloudControllerText);
        vPanel1.add(cloudControllerURIForm);
        vPanel1.add(cloudcontrollerSubmitButton); 
       
        vPanel1.add(deaText);
        vPanel1.add(deaForm);
        vPanel1.add(deaSubmitButton);
        
        vPanel1.add(databaseText);
        vPanel1.add(mysqlText);
        vPanel1.add(mongoDBText);
        vPanel1.add(databaseSubmitButton);
        
        
        tabPanel.add(vPanel1, "1&1 Cloud Server"); 
        
        
        
          //Amazon Web Service
        VerticalPanel vPanel2 = new VerticalPanel();  
        vPanel2.setSpacing(15);  
        vPanel2.setHeight("500px");  
        Button cloudcontroller2Button = new Button("Cloud Controller");
        Button dea2Button = new Button("DEA");
        Button database2Button = new Button("Database"); 
               
        vPanel2.add(cloudcontroller2Button);  
        vPanel2.add(dea2Button);
        vPanel2.add(database2Button);
        tabPanel.add(vPanel2, "Amazon Web Service");
        
        // Return the content  
        tabPanel.selectTab(0);  
        tabPanel.ensureDebugId("cwTabPanel");  
        return tabPanel;  
    } 
                           
    @SuppressWarnings("deprecation")
	private Widget getBenchmarkTab() {  
        DecoratedTabPanel tabPanel = new DecoratedTabPanel();  
        tabPanel.setWidth("550px");  
        tabPanel.setAnimationEnabled(true);  
          
        //RAINTOOLS Functions
          
        VerticalPanel vPanel1 = new VerticalPanel();  
        vPanel1.setSpacing(15);  
        vPanel1.setHeight("500px");  
        Button startButton = new Button("Start"); 
        Button stopButton = new Button("Stop!");
                       
        vPanel1.add(startButton);  
        vPanel1.add(stopButton);
      
        tabPanel.add(vPanel1, "RAIN Tool");  
        
        //Choose best configurations
        VerticalPanel vPanel2 = new VerticalPanel();  
        vPanel2.setSpacing(15);  
        vPanel2.setHeight("500px");  
              
        tabPanel.add(vPanel2, "Best configurations"); 
        
        final ListGrid bestConfigGrid = new ListGrid();  
        bestConfigGrid.setWidth(580);  
        bestConfigGrid.setHeight(400);  
        bestConfigGrid.setShowAllRecords(true);  
  
        ListGridField bestAppsField = new ListGridField("name", "Apps", 100);  
        bestAppsField.setCanEdit(false);  
  
        ListGridField bestDeploymentField = new ListGridField("time", "Deployment Speed", 100);  
        ListGridField bestVersionField = new ListGridField("version", "Version", 100); 
        ListGridField bestStatusField = new ListGridField("status", "Status", 100); 
        ListGridField bestCostField = new ListGridField("cost", "Cost", 100); 
        
        bestConfigGrid.setFields(bestAppsField, bestDeploymentField, bestVersionField, bestStatusField, bestCostField); 
        bestConfigGrid.draw();
        
        vPanel2.add(bestConfigGrid);
        
        //Choose cheapest configurations
        VerticalPanel vPanel3 = new VerticalPanel();  
        vPanel3.setSpacing(15);  
        vPanel3.setHeight("500px");  
       
        tabPanel.add(vPanel3, "Cheapest configurations"); 
        
        final ListGrid cheapestConfigGrid = new ListGrid();  
        cheapestConfigGrid.setWidth(580);  
        cheapestConfigGrid.setHeight(400);  
        cheapestConfigGrid.setShowAllRecords(true);  
  
        ListGridField cheapestAppsField = new ListGridField("name", "Apps", 100);  
        cheapestAppsField.setCanEdit(false);  
  
        ListGridField cheapestDeploymentField = new ListGridField("time", "Deployment Speed", 100);  
        ListGridField cheapestVersionField = new ListGridField("version", "Version", 100);
        ListGridField cheapestStatusField = new ListGridField("status", "Status", 100); 
        ListGridField cheapestCostField = new ListGridField("cost", "Cost", 100); 
        
        cheapestConfigGrid.setFields(cheapestAppsField, cheapestDeploymentField, cheapestVersionField, cheapestStatusField, cheapestCostField); 
        cheapestConfigGrid.draw();
        
        vPanel3.add(cheapestConfigGrid);
        
        
        
        // Return the content  
        tabPanel.selectTab(0);  
        tabPanel.ensureDebugId("cwTabPanel");  
        return tabPanel;  
    } 
		 		
}
