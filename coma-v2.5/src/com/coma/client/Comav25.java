package com.coma.client;

import com.coma.client.helpers.Settings;
import com.coma.client.widgets.MessageFrame;
import com.coma.client.widgets.NameModelDialog;
import com.coma.client.widgets.NewModelDialogBox;
import com.coma.client.widgets.SendProposalDialog;
import com.coma.v2.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;


public class Comav25 {

	private static Comav25 instance = null;	
	LogIn logIn = new LogIn();
	
	public TextButton defineBenefitsButton = new TextButton("Define benefits");
	public TextButton problemsOpportunitiesButton = new TextButton("Problems & Opportunities");
	
	//Problems and Opportunities
	public TextButton addChangeProblemsButton = new TextButton("Add & change problems");
	public TextButton addChangeOpportunitiesButton = new TextButton("Add & change opportunities");
	public TextButton consolidateProblemsButton = new TextButton("Consolidate problems");	
	
	public MessageFrame oryxFrame = null;
	
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	
	public static Comav25 GetInstance(){
		if(instance == null){
			instance = new Comav25();
			return instance;
		}
		else{
			return instance;
		}
	}
	
	public void initialize(){
		RootPanel.get("mainDiv").add(logIn.screen());
	}
	
	public void initMainProgram() {
		RootPanel.get("mainDiv").clear();
		RootPanel.get("mainDiv").add(initTabPanel());
		
	}
	
	public TabPanel initTabPanel(){
		final TabPanel panel = new TabPanel();
		
		panel.add(initDefineBenefitsView(), "Define Benefits");
//		panel.add(initGroupModelView(), "Group Model");
//		panel.add(initProposalView(), "Proposals");	
//		panel.add(initPreferencesView(), "Preferences");	
		panel.setSize("100%", "100%");	
		
		SelectionHandler<Widget> handler = new SelectionHandler<Widget>() {
	        @Override
	        public void onSelection(SelectionEvent<Widget> event) {
	        	TabPanel panel = (TabPanel) event.getSource();
		        Widget w = event.getSelectedItem();
		        int tabID = panel.getWidgetIndex(w);
		        Panel p = (Panel)panel.getWidget(tabID);
		         
		        if (tabID == 0 || tabID == 1) {
//		        	p.add(oryxFrame);
//		        	if(tabID == 1){
//		        		oryxFrame.setVisible(true);
//		        		new LoadModel().getActiveGroupModelFromDatabase(oryxFrame);
//		        	}
		        }
					
//				if (tabID == 2) {					
//					p.clear();
//					p.add(proposalButtonsPanel);
//					DockPanel dockPanel = new DockPanel();
//					dockPanel.setWidth("100%");
//					dockPanel.add(oryxFrame, DockPanel.CENTER);
//					getVoteMapData(dockPanel);
//					p.add(dockPanel);
//				}
//				if (tabID == 3) {
//					if(isFirstTime){						
//						p.add(editProfile.screen(userProfile));
//						isFirstTime = false;
//					}
//				}
			}};
	          	      
	      panel.addSelectionHandler(handler);
		
		return panel;
	}
	
	
	private Panel initDefineBenefitsView(){
		VerticalPanel panel = new VerticalPanel();
		//initializeOryxFrame();
		panel.add(topMenuButtonsProblemsOpportunitiesView());
		//panel.add(oryxFrame);
		//oryxFrame.setVisible(true);
		return panel;
	}
	
	public void initializeOryxFrame() {
		oryxFrame = new MessageFrame("oryxFrame");
		oryxFrame.init();
		oryxFrame.setUrl(Settings.oryxFrameURL);
		oryxFrame.setHeight(Settings.oryxFrameHeight);
		oryxFrame.setWidth(Settings.oryxFrameWidth);
	}

	private Panel topMenuButtonsProblemsOpportunitiesView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();

		addChangeProblemsButton.getElement().setClassName("utilityButton");
		addChangeOpportunitiesButton.getElement().setClassName("utilityButton");
		consolidateProblemsButton.getElement().setClassName("utilityButton");

		addChangeProblemsButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
		
			}
			
		});
		addChangeOpportunitiesButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {

			}
			
		});
		consolidateProblemsButton.addSelectHandler(new SelectHandler(){

			@Override
			public void onSelect(SelectEvent event) {
				
			}
			
		});

		panel.add(addChangeProblemsButton);
		panel.add(addChangeOpportunitiesButton);
		panel.add(consolidateProblemsButton);
		
		panel.add(new Label("Logged in as: " + User.getInstance().getUserEmail()+ " id: " + User.getInstance().getUserId() + "Group: " +User.getInstance().getActiveGroupID()));

		return panel;  
	}

}
