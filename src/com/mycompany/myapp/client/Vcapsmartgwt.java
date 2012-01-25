package com.mycompany.myapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.core.ext.linker.EmittedArtifact.Visibility;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Vcapsmartgwt implements EntryPoint {

	private final CloudInfoServiceAsync cloudinfoSvc = GWT
			.create(CloudInfoService.class);

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	final String HDD = "300";
	
	public void onModuleLoad() {

		// Tabset Definition
		final TabSet topTabset = new TabSet();
		topTabset.setTabBarPosition(Side.TOP);
		topTabset.setWidth(600);
		topTabset.setHeight(420);

		// Overview Tab
		Tab tTab1 = new Tab("Overview");
		Canvas tabPane1 = new Canvas();
		tabPane1.setWidth100();
		tabPane1.setHeight100();
		tabPane1.addChild(getOverview());
		tTab1.setPane(tabPane1);

		/*
		 * Vorlage für appgrid mit JSON Daten:
		 * http://www.smartclient.com/smartgwt
		 * /showcase/#json_integration_category_simple
		 */

		// Choose Provider Tab
		Tab tTab2 = new Tab("Choose Provider");
		Canvas tabPane2 = new Canvas();
		tabPane2.setWidth100();
		tabPane2.setHeight100();
		tabPane2.addChild(getProviderTab());
		tTab2.setPane(tabPane2);

		// Benchmarking Tab
		Tab tTab3 = new Tab("Benchmarking");
		Canvas tabPane3 = new Canvas();
		tabPane3.setWidth100();
		tabPane3.setHeight100();
		tabPane3.addChild(getBenchmarkTab());
		tTab3.setPane(tabPane3);

		topTabset.addTab(tTab1);
		topTabset.addTab(tTab2);
		topTabset.addTab(tTab3);

		/*
		 * HLayout buttons = new HLayout(); buttons.setMembersMargin(15);
		 */

		topTabset.draw();

	}

	@SuppressWarnings("deprecation")
	private Widget getOverview() {

		// Tab Overview: Overview, VCAP

		final DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("550px");
		tabPanel.setAnimationEnabled(true);

		// Applications: Name, State, Instance, Memory, URI

		final VerticalPanel vPanel0 = new VerticalPanel();
		vPanel0.setSpacing(15);
		vPanel0.setHeight("500px");

		final VerticalPanel vPanel1 = new VerticalPanel();
		vPanel1.setSpacing(15);
		vPanel1.setHeight("100px");

		final VerticalPanel vPanel2 = new VerticalPanel();
		vPanel2.setSpacing(15);
		vPanel2.setHeight("500px");

		final HorizontalPanel hPanel1 = new HorizontalPanel();
		hPanel1.setSpacing(15);
		hPanel1.setWidth("100px");

		/*
		 * HorizontalPanel hPanel4 = new HorizontalPanel();
		 * hPanel4.setSpacing(15); hPanel4.setWidth("100px");
		 */

		final DynamicForm form = new DynamicForm();
		form.setAutoWidth();

		SelectItem appSelect = new SelectItem();
		appSelect.setName("appnameselect");
		appSelect.setTitle("Application");
		appSelect.setValueMap("hello", "wardrobe");
		appSelect.setDefaultValue("hello");
		/*
		 * appSelect.addChangedHandler(new ChangedHandler() { public void
		 * onChanged(ChangedEvent event) { String ds = (String)
		 * event.getValue(); if (ds.equalsIgnoreCase("country")) {
		 * cEditor.setDatasource(countryDS); } else {
		 * cEditor.setDatasource(supplyItemDS); } } });
		 */

		form.setItems(appSelect);

		final HorizontalPanel hPanel2 = new HorizontalPanel();
		hPanel2.setSpacing(15);
		hPanel2.setWidth("100px");

		final HorizontalPanel hPanel3 = new HorizontalPanel();
		hPanel3.setSpacing(15);
		hPanel3.setWidth("100px");

		final HTMLFlow serverResponseLabel = new HTMLFlow();
		serverResponseLabel.setWidth("550px");
		serverResponseLabel.setHeight("450px");

		// TODO Button anpassen / löschen
		final Button refreshButton = new Button("Refresh");
		refreshButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.getInfo(null, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						// serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						serverResponseLabel.setContents(result);

					}
				});
			}
		});

		// VCAP: Start, Stop, Add, Delete

		final Button startButton = new Button("Start App");
		startButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.startApp(new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						// serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		final Button stopButton = new Button("Stop App");
		stopButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.stopApp(new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						// serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		final Button updateMemoryButton = new Button("Update AppMemory");
		updateMemoryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.updateAppmemory(new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						// serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		final Button updateInstanceButton = new Button("Update Instance");
		updateInstanceButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.updateAppinstance(new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						// serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		final Button addButton = new Button("Add App");
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.addApp(new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						// serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		final Button deleteButton = new Button("Delete App");
		deleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.deleteApp(new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						// serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		final Button restartButton = new Button("Restart App");
		restartButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.restartApp(new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						// serverResponseLabel.setHTML(SERVER_ERROR);
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
				cloudinfoSvc.bindingAppservice(new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						// serverResponseLabel.setHTML(SERVER_ERROR);
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
	   
	    holder.add(new HTML("<hr />"));
	    final Button submitButton = new Button("Submit"); 
	    holder.setHorizontalAlignment(HasAlignment.ALIGN_RIGHT);
	    submitButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event){
	    		
	    		formupload.submit();
	    	}
	    	
	     
	 	    
	    /*	public void onClick(ClickEvent event){
				cloudinfoSvc.uploadAppfile(new AsyncCallback<Void>(){

		bindingserviceButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
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

			public void onClick(ClickEvent event) {
				cloudinfoSvc.uploadAppfile(new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

						// serverResponseLabel.setHTML(SERVER_ERROR);
						serverResponseLabel.setContents(SERVER_ERROR);
					}

					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});
			}*/
		});

	    holder.add(upload);
 	    formupload.setAction("http://api.railwaytoheaven.de");
 	    
	    final Button uploadFileButton = new Button("Upload File");
		uploadFileButton.addClickHandler(new ClickHandler(){
			
			public void onClick(ClickEvent event){
				formupload.submit();
			}

			});
		
		uploadFileButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				holder.add(submitButton);
				formupload.add(holder);
				tabPanel.add(formupload, "Upload");
			}

		});
		
		

		// form.setAction("url");

		formupload.addFormHandler(new FormHandler() {
			public void onSubmit(FormSubmitEvent event) {
				// if (something_is_wrong)
				// {
				// Take some action
				// event.setCancelled(true);
				// }
			}

			public void onSubmitComplete(FormSubmitCompleteEvent event) {
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
		// vPanel2.add(formupload);

		// Add Buttons into Panel

		vPanel0.add(refreshButton);
		vPanel0.add(serverResponseLabel);

		// hPanel4.add(form);

		hPanel1.add(startButton);
		hPanel1.add(stopButton);
		hPanel1.add(restartButton);

		// hPanel2.add(addButton);
		hPanel2.add(deleteButton);
		// hPanel2.add(uploadFileButton);

		hPanel3.add(updateMemoryButton);
		hPanel3.add(updateInstanceButton);
		hPanel3.add(addtabButton);

		hPanel2.add(addButton);
		hPanel2.add(deleteButton);
		hPanel2.add(uploadFileButton);

		hPanel3.add(updateMemoryButton);
		hPanel3.add(updateInstanceButton);

		vPanel1.add(hPanel1);
		vPanel1.add(hPanel3);
		vPanel1.add(hPanel2);

		// vPanel2.add(hPanel4);
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

		// Provider Button
		// 1&1 Cloud Server
		VerticalPanel links = new VerticalPanel();
		links.setSpacing(15);
		links.setHeight("600");
		
		HorizontalPanel hPanel1 = new HorizontalPanel();
		VerticalPanel rechts = new VerticalPanel();
		
		
		final HTML einsUndeinsResponseLabel = new HTML();
		einsUndeinsResponseLabel.setHeight("570");
		
		final HTML ueberschriftVpanel = new HTML("<b>Instance Control</b>");

		// Erstelle Buttons
		Button start1und1Button = new Button("Start 1und1 Instances");
		start1und1Button.setAutoFit(true);

		Button stop1und1Button = new Button("Stopp 1und1 Instances");
		stop1und1Button.setAutoFit(true);

		Button restart1und1Button = new Button("Restart 1und1 Instances");
		restart1und1Button.setAutoFit(true);

		Button suspend1und1Button = new Button("Suspend 1und1 Instances");
		suspend1und1Button.setAutoFit(true);

		Button powerOff1und1Button = new Button("Power Off 1und1 Instances");
		powerOff1und1Button.setAutoFit(true);

		// Erstelle Popup DialogBox für Infos
		final DialogBox annahmenEinsundEins = new DialogBox();
		annahmenEinsundEins.setText("Implied Assumptions");
		annahmenEinsundEins.setAnimationEnabled(true);
		annahmenEinsundEins.setPopupPosition(610, 30);
		final Button closeButton = new Button("<b>Close to continue</b>");
		VerticalPanel dialogPanel = new VerticalPanel();
		dialogPanel.addStyleName("dialogVPanel");
		dialogPanel.add(new HTML(
				"<b>1und1 Login-Informations containing</b><br/>"));
		dialogPanel
				.add(new HTML(
						"<b>Host</b><br/><b>Username</b><br/><b>Password</b><br/><b>Port</b><br/>"));
		dialogPanel.add(new HTML("are <b>preconfigured</b>"));
		dialogPanel.add(new HTML(
				"Harddrive Configuration <b>is fixed</b> at 300GB,<br/>"));
		dialogPanel
				.add(new HTML(
						"because downsizing activities<br/>" +
						"would <b>delete all information</b> on the server"));
		dialogPanel.add(closeButton);

		closeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				annahmenEinsundEins.hide();
			}
		});
		annahmenEinsundEins.setWidget(dialogPanel);

		// ClickHandler der 1&1 - Buttons
		// Startet Instanzen auf 1&1 - Button - ClickHandler und RPC
		start1und1Button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				cloudinfoSvc.start1und1(new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						einsUndeinsResponseLabel.setText(SERVER_ERROR);
						annahmenEinsundEins.hide(true);
					}

					public void onSuccess(String result) {
						einsUndeinsResponseLabel.setText(result);
						annahmenEinsundEins.show();
					}
				});
			}
		});

		// Stoppt Instanzen auf 1&1 - Button - ClickHandler und RPC
		stop1und1Button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.stop1und1(new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						einsUndeinsResponseLabel.setText(SERVER_ERROR);
						annahmenEinsundEins.hide(true);
					}

					public void onSuccess(String result) {
						einsUndeinsResponseLabel.setText(result);
						annahmenEinsundEins.show();
					}
				});
			}
		});

		// Restartet Instanzen auf 1&1 - Button - ClickHandler und RPC
		restart1und1Button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.restart1und1(new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						einsUndeinsResponseLabel.setText(SERVER_ERROR);
						annahmenEinsundEins.hide(true);
					}

					public void onSuccess(String result) {
						einsUndeinsResponseLabel.setText(result);
						annahmenEinsundEins.show();
					}
				});
			}
		});

		// Suspends Instanzen auf 1&1 - Button - ClickHandler und RPC
		suspend1und1Button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.suspend1und1(new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						einsUndeinsResponseLabel.setText(SERVER_ERROR);
						annahmenEinsundEins.hide(true);
					}

					public void onSuccess(String result) {
						einsUndeinsResponseLabel.setText(result);
						annahmenEinsundEins.show();
					}
				});
			}
		});

		// Schaltet Instanzen auf 1&1 ab - Button - ClickHandler und RPC
		powerOff1und1Button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.poweroff1und1(new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						einsUndeinsResponseLabel.setText(SERVER_ERROR);
						annahmenEinsundEins.hide(true);
					}

					public void onSuccess(String result) {
						einsUndeinsResponseLabel.setText(result);
						annahmenEinsundEins.show();
					}
				});
			}
		});

		// Feld zur Eingabe von Hardware - Konfigs
		final HTML configureLabel = new HTML(
				"<b>Configure 1und1-Instance Hardware:</b><br/>");
		HTML cpuConfigLabel = new HTML("Change CPU");
		HTML ramConfigLabel = new HTML("Change RAM");
		
		final TextBox inputCpuConfig = new TextBox();
		inputCpuConfig.setText("Enter Number of CPU Cores here!");
		
		
		final TextBox inputRamConfig = new TextBox();
		inputRamConfig.setText("Enter RAM Capacity from 1 to 24GB here!");
		

		final Button configButton = new Button("Configure Hardware");
		//ClickHandler für cpuConfigButton - mit RPC
		
		configButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				final String cpu = inputCpuConfig.getText();
				final String ram = inputRamConfig.getText();
				cloudinfoSvc.handle1und1Hardware(cpu, HDD, ram, new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						einsUndeinsResponseLabel.setText("Check input type of Configuration!");
					}

					public void onSuccess(String result) {
						einsUndeinsResponseLabel.setText(result);
						annahmenEinsundEins.show();
					}
				});
			}
		});
		

		// Fügt Elemente zum VPanel hinzu
		links.add(einsUndeinsResponseLabel);
		links.add(ueberschriftVpanel);
		links.add(start1und1Button);
		links.add(stop1und1Button);
		links.add(restart1und1Button);
		links.add(suspend1und1Button);
		links.add(powerOff1und1Button);
		
		//Fügt Elemente zum vPanel2 hinzu
		rechts.add(configureLabel);
		rechts.add(cpuConfigLabel);
		rechts.add(inputCpuConfig);
		rechts.add(ramConfigLabel);
		rechts.add(inputRamConfig);
		rechts.add(configButton);

		// Fügt Elemente zum hPanel hinzu
		hPanel1.add(links);
		hPanel1.add(rechts);
		
		tabPanel.add(hPanel1, "1&1 Cloud Server");

		// Amazon Web Service
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

		// AWS Refresh Button - ClickHandler mit RPC
		refreshButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cloudinfoSvc.setAmazonCloudController(null,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								awsResponseLabel.setText(SERVER_ERROR);
							}

							public void onSuccess(String result) {
								awsResponseLabel.setText(result);
							}
						});
			}
		});

		// Cloud Controller Button- ClickHandler mit RPC
		cloudcontroller2Button.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				cloudinfoSvc.setAmazonCloudController(null,
						new AsyncCallback<String>() {
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

		// RAINTOOLS Functions

		VerticalPanel vPanel1 = new VerticalPanel();
		vPanel1.setSpacing(15);
		vPanel1.setHeight("500px");
		Button startButton = new Button("Start");
		Button stopButton = new Button("Stop!");

		vPanel1.add(startButton);
		vPanel1.add(stopButton);

		tabPanel.add(vPanel1, "RAIN Tool");

		// Choose best configurations
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

		ListGridField bestDeploymentField = new ListGridField("time",
				"Deployment Speed", 100);
		ListGridField bestVersionField = new ListGridField("version",
				"Version", 100);
		ListGridField bestStatusField = new ListGridField("status", "Status",
				100);
		ListGridField bestCostField = new ListGridField("cost", "Cost", 100);

		bestConfigGrid.setFields(bestAppsField, bestDeploymentField,
				bestVersionField, bestStatusField, bestCostField);
		bestConfigGrid.draw();

		vPanel2.add(bestConfigGrid);

		// Choose cheapest configurations
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

		ListGridField cheapestDeploymentField = new ListGridField("time",
				"Deployment Speed", 100);
		ListGridField cheapestVersionField = new ListGridField("version",
				"Version", 100);
		ListGridField cheapestStatusField = new ListGridField("status",
				"Status", 100);
		ListGridField cheapestCostField = new ListGridField("cost", "Cost", 100);

		cheapestConfigGrid.setFields(cheapestAppsField,
				cheapestDeploymentField, cheapestVersionField,
				cheapestStatusField, cheapestCostField);
		cheapestConfigGrid.draw();

		vPanel3.add(cheapestConfigGrid);

		// Return the content
		tabPanel.selectTab(0);
		tabPanel.ensureDebugId("cwTabPanel");
		return tabPanel;
	}

}
