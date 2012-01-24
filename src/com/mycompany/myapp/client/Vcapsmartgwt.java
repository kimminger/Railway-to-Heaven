package com.mycompany.myapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
import com.smartgwt.client.widgets.form.fields.SelectItem;
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
	    
	    /*HLayout buttons = new HLayout();  
        buttons.setMembersMargin(15);*/  
        
        topTabset.draw(); 
	}
	
	
	@SuppressWarnings("deprecation")
	private Widget getOverview(){
		
		//Tab Overview: Overview, VCAP
		
		final DecoratedTabPanel tabPanel = new DecoratedTabPanel();  
        tabPanel.setWidth("550px");  
        tabPanel.setAnimationEnabled(true); 
        
       //Applications: Name, State, Instance, Memory, URI
        
        VerticalPanel vPanel0 = new VerticalPanel();  
        vPanel0.setSpacing(15);  
        vPanel0.setHeight("500px");
        
        VerticalPanel vPanel1 = new VerticalPanel();  
        vPanel1.setSpacing(15);  
        vPanel1.setHeight("100px");
        
        VerticalPanel vPanel2 = new VerticalPanel();  
        vPanel2.setSpacing(15);  
        vPanel2.setHeight("500px");
      
        HorizontalPanel hPanel1 = new HorizontalPanel();  
        hPanel1.setSpacing(15);
        hPanel1.setWidth("100px");
        
        /*HorizontalPanel hPanel4 = new HorizontalPanel();  
        hPanel4.setSpacing(15);
        hPanel4.setWidth("100px");*/
        
        final DynamicForm form = new DynamicForm();  
        form.setAutoWidth();  
        
        
        SelectItem appSelect = new SelectItem();
        appSelect.setName("appnameselect"); 
        appSelect.setTitle("Application");
        appSelect.setValueMap("hello","wardrobe");
        
        appSelect.setDefaultValue("hello");
        /*appSelect.addChangedHandler(new ChangedHandler() {  
            public void onChanged(ChangedEvent event) {  
                String ds = (String) event.getValue();  
                if (ds.equalsIgnoreCase("country")) {  
                    cEditor.setDatasource(countryDS);  
                } else {  
                    cEditor.setDatasource(supplyItemDS);  
                }  
            }  
        });*/  
        
        form.setItems(appSelect);
        
        
        HorizontalPanel hPanel2 = new HorizontalPanel();  
        hPanel2.setSpacing(15);  
        hPanel2.setWidth("100px");
        
        HorizontalPanel hPanel3 = new HorizontalPanel();  
        hPanel3.setSpacing(15);  
        hPanel3.setWidth("100px");
        
        final HTMLFlow serverResponseLabel = new HTMLFlow();
		serverResponseLabel.setWidth("550px");
		serverResponseLabel.setHeight("450px");
		
        
        final Button refreshButton = new Button("Refresh");
        refreshButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				cloudinfoSvc.getInfo(null, new AsyncCallback<String>(){
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
		});
            
			
		//VCAP: Start, Stop, Add, Delete
		
		final Button startButton = new Button("Start App");
		startButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				cloudinfoSvc.startApp(new AsyncCallback<Void>(){
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						//serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
      
		  
	        	
		final Button stopButton = new Button("Stop App");
		stopButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				cloudinfoSvc.stopApp(new AsyncCallback<Void>(){
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						//serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
			
		
		final Button updateMemoryButton = new Button("Update AppMemory");
		updateMemoryButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				cloudinfoSvc.updateAppmemory(new AsyncCallback<Void>(){
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						//serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
				
		final Button updateInstanceButton = new Button("Update Instance");
		updateInstanceButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				cloudinfoSvc.updateAppinstance(new AsyncCallback<Void>(){
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						//serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
						
        final Button addButton = new Button("Add App");
        addButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				cloudinfoSvc.addApp(new AsyncCallback<Void>(){
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						//serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
        
		
		final Button deleteButton = new Button("Delete App");
		deleteButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				cloudinfoSvc.deleteApp(new AsyncCallback<Void>(){
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						//serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		        
		final Button restartButton = new Button("Restart App");
		restartButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				cloudinfoSvc.restartApp(new AsyncCallback<Void>(){
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						//serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		
		
		final Button bindingserviceButton = new Button("Binding Service");
		bindingserviceButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				cloudinfoSvc.bindingAppservice(new AsyncCallback<Void>(){
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						//serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		final VerticalPanel vPanel3 = new VerticalPanel();  
        vPanel3.setSpacing(15);  
        vPanel3.setHeight("500px");
        
        final Button closeButton = new Button("Close Tab");  
	     closeButton.addClickHandler(new ClickHandler() {  
	            public void onClick(ClickEvent event) {  
	            	tabPanel.remove(vPanel3);
	            	                
	            }  
	        });  
		
        
		
	  
	    final FormPanel formupload = new FormPanel();
	    formupload.setEncoding(FormPanel.ENCODING_MULTIPART);
	    formupload.setMethod(FormPanel.METHOD_POST);
	    formupload.addStyleName("table-center");
	    formupload.addStyleName("demo-panel-padded");
	    formupload.setWidth("275px");
	    	    
	   
	    final VerticalPanel holder = new VerticalPanel();

	    FileUpload upload = new FileUpload();
	    upload.setName("upload");
	    holder.add(upload);

	    holder.add(new HTML("<hr />"));
	    final Button submitButton = new Button("Submit"); 
	    holder.setHorizontalAlignment(HasAlignment.ALIGN_RIGHT);
	    submitButton.addClickHandler(new ClickHandler() {
			
	    	public void onClick(ClickEvent event){
				cloudinfoSvc.uploadAppfile(new AsyncCallback<Void>(){
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						//serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	
	    final Button uploadFileButton = new Button("Upload File");
		uploadFileButton.addClickHandler(new ClickHandler(){
			
			public void onClick(ClickEvent event){
				holder.add(submitButton);
				formupload.add(holder);
				tabPanel.add(formupload,"Upload");
			}
			
		});
	    
	   

	    // form.setAction("url");

	    formupload.addFormHandler(new FormHandler()
	    {
	    	public void onSubmit(FormSubmitEvent event)
	    	{
	    		// if (something_is_wrong)
	    		// {
	    		// Take some action
	    		// event.setCancelled(true);
	    		// }
	    	}

	    	public void onSubmitComplete(FormSubmitCompleteEvent event)
	    	{
	    		Window.alert(event.getResults());
	    	}
	    });

	    
	    final Button addtabButton = new Button("Add Tab");  
	    addtabButton.addClickHandler(new ClickHandler() {  
	            public void onClick(ClickEvent event) {  
	            	
	            	vPanel3.setVisible(true);     
	              	vPanel3.add(addButton);
	            	vPanel3.add(bindingserviceButton);
	              	vPanel3.add(uploadFileButton);
	            	vPanel3.add(closeButton);
	              	tabPanel.add(vPanel3, "Add App");            
	              
	            }  
	        });  
	    //vPanel2.add(formupload);
	    
	    
	    
        // Add Buttons into Panel
		
		
		vPanel0.add(refreshButton);
		vPanel0.add(serverResponseLabel);
		
		//hPanel4.add(form);
		
		hPanel1.add(startButton);
		hPanel1.add(stopButton);
		hPanel1.add(restartButton);
		
		
		
		//hPanel2.add(addButton);
		hPanel2.add(deleteButton);
		//hPanel2.add(uploadFileButton);
				
		hPanel3.add(updateMemoryButton);
		hPanel3.add(updateInstanceButton);
		hPanel3.add(addtabButton);
		
		
		vPanel1.add(hPanel1);
		vPanel1.add(hPanel3);
		vPanel1.add(hPanel2);
		
		//vPanel2.add(hPanel4);
		vPanel2.add(form);
		
		tabPanel.add(vPanel0, "Overview");
		tabPanel.add(vPanel1, "VCAP");
		tabPanel.add(vPanel2, "Test");
		
		
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
        
        final HTML einsUndeinsResponseLabel = new HTML();
        einsUndeinsResponseLabel.setHeight("400");
        
        Button cloudcontrollerSubmitButton = new Button("Cloud Controller submit");
        cloudcontrollerSubmitButton.setAutoFit(true);
        cloudcontrollerSubmitButton.setLeft(50);
        
        final DialogBox annahmenEinsundEins = new DialogBox();
        annahmenEinsundEins.setText("HIER DEIN TEXT JUNGE!");
        annahmenEinsundEins.setHeight("350");
        annahmenEinsundEins.setWidth("50");
        //TODO Dialogbox aufpoppen lassen, die Infos über hardcodierte Annahmen (login-infos) anzeigt
        //CloudController auf 1&1 - Button - ClickHandler
        cloudcontrollerSubmitButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				cloudinfoSvc.start1und1(new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						einsUndeinsResponseLabel.setText(SERVER_ERROR);
					}

					public void onSuccess(String result) {
						einsUndeinsResponseLabel.setText(result);
					}
				});
			}
		});
        
                
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
        
                  

        vPanel1.add(einsUndeinsResponseLabel); 
      
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
