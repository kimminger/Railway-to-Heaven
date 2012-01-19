package com.mycompany.myapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mycompany.myapp.server.CloudInfoServiceImpl;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;




/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Vcapsmartgwt implements EntryPoint {
	
	private final CloudInfoServiceAsync cloudinfoSvc =
			GWT.create(CloudInfoService.class);
	
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	public void onModuleLoad() {
		
		//Overview Grid
		/*final ListGrid appsGrid = new ListGrid();  
        appsGrid.setWidth(580);  
        appsGrid.setHeight(400);  
        appsGrid.setShowAllRecords(true);  
        		
      //Erstellen der einzelnen Spalten zur Anzeige
        ListGridField appsField = new ListGridField("name", "App", 120);  
        appsField.setCanEdit(false);  
        
        ListGridField deploymentField = new ListGridField("time", "Deployment Time", 170);  
        ListGridField statusField = new ListGridField("status", "Status", 170); 
        ListGridField versionField = new ListGridField("version", "Version", 100);
        
        appsGrid.setFields(appsField, deploymentField, versionField, statusField); 
        CloudRecord[] result;
        appsGrid.setData(CloudRecord.createListGridRecords(result));
        
        appsGrid.draw();*/
		
		
        //Tabset Definition
		final TabSet topTabset = new TabSet();
		topTabset.setTabBarPosition(Side.TOP);
		topTabset.setWidth (600);
		topTabset.setHeight (420);
		
		//Overview Tab
		Tab tTab1 = new Tab("Overview");
		Canvas tabPane1 = new Canvas();  
        tabPane1.setWidth100();  
        tabPane1.setHeight100();  
        tabPane1.addChild(getOverview());  
        tTab1.setPane(tabPane1);
		//tTab1.setPane(member1);
		
		/*
		 * Vorlage für appgrid mit JSON Daten:
		 * http://www.smartclient.com/smartgwt/showcase/#json_integration_category_simple
		 */
		
		
		
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
	
	
	private Widget getOverview(){
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();  
        tabPanel.setWidth("550px");  
        tabPanel.setAnimationEnabled(true); 
      //Overview
        VerticalPanel vPanel0 = new VerticalPanel();  
        vPanel0.setSpacing(15);  
        vPanel0.setHeight("500px");
        
        final Button refreshButton = new Button("Refresh");
        
        /*final HTMLPane htmlPane = new HTMLPane();  
        htmlPane.setShowEdges(true);  
        
        htmlPane.setContentsType(ContentsType.valueOf(null));*/  
        /*String i = "";
        final HTMLFlow htmlFlow = new HTMLFlow(); 
        htmlFlow.setContents(i);
        htmlFlow.setContentsType(ContentsType.PAGE);*/
        
        final HTMLFlow serverResponseLabel = new HTMLFlow();
		serverResponseLabel.setWidth("550px");
		serverResponseLabel.setHeight("450px");
		//serverResponseLabel.setAutoHorizontalAlignment(null);
		//serverResponseLabel.setHTML(null);
		
		
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the refreshButton.
			 */
			public void onClick(ClickEvent event) {
				
				getInfo();
				
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					getInfo();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
	
			private void getInfo(){
				cloudinfoSvc.getInfo(
						null, new AsyncCallback<String>() {

							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

								//serverResponseLabel.setHTML(SERVER_ERROR);
								serverResponseLabel.setContents(SERVER_ERROR);
							}

							public void onSuccess(String result) {
								// TODO Auto-generated method stub
								
								serverResponseLabel.setContents(result);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		refreshButton.addClickHandler(handler);
		
		vPanel0.add(refreshButton);
		vPanel0.add(serverResponseLabel);
		tabPanel.add(vPanel0, "Overview");
		
		tabPanel.selectTab(0);  
	    tabPanel.ensureDebugId("cwTabPanel");  
	    return tabPanel; 
		
	}
	
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
        
//        TODO: Löschen, wenn TestButton erfolgreich
                  
//                    vPanel1.add(nameField);
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
        Button refreshButton = new Button("Refresh");
        final HTML awsOverviewLabel = new HTML();
        awsOverviewLabel.setWidth("400");
        awsOverviewLabel.setHeight("450");
        Button cloudcontroller2Button = new Button("Cloud Controller");
        final HTML awsResponseLabel = new HTML();
        awsResponseLabel.setWidth("400");
        awsResponseLabel.setHeight("450");
        Button dea2Button = new Button("DEA");
        Button database2Button = new Button("Database");
        
      //AWS Refresh Button - ClickHandler mit RPC 
        refreshButton.addClickHandler(new ClickHandler() {
			
     			public void onClick(ClickEvent event) {
     				
     				cloudinfoSvc.setAmazonCloudController(null, new AsyncCallback<String>() {
     					public void onFailure(Throwable caught) {
     						awsResponseLabel.setText(SERVER_ERROR);
     					}

     					public void onSuccess(String result) {
     						awsResponseLabel.setText(result);
     					}
     				});
     			}
     		});
        
        
        
        //Cloud Controller Button- ClickHandler mit RPC
        cloudcontroller2Button.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				cloudinfoSvc.setAmazonCloudController(null, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						awsResponseLabel.setText(SERVER_ERROR);
					}

					public void onSuccess(String result) {
						awsResponseLabel.setText(result);
					}
				});
			}
		});
        
               
        vPanel2.add(refreshButton);
        vPanel2.add(awsOverviewLabel);
        vPanel2.add(cloudcontroller2Button);
        vPanel2.add(awsResponseLabel);
        vPanel2.add(dea2Button);
        vPanel2.add(database2Button);
        tabPanel.add(vPanel2, "Amazon Web Service");
        
        // Return the content  
        tabPanel.selectTab(0);  
        tabPanel.ensureDebugId("cwTabPanel");  
        return tabPanel;  
    } 
                           
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
