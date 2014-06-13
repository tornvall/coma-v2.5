package com.coma.client;

import com.coma.client.helpers.*;
import com.coma.client.views.*;
import com.coma.client.widgets.*;
import com.coma.v2.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.thirdparty.javascript.jscomp.graph.GraphColoring.Color;
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
	
	// Views
	private DefineBenefits defineBenefits = null;
	private ProblemsOpportunities problemsOpportunities = null;

			
	public MessageFrame oryxFrame = null;
	private ModelInfo model = null;
	
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	
	public ModelInfo getModel() {
		return this.model;
	}
	public void setModel(ModelInfo model) {
		this.model = model;
	}
	
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
	
		//Add facilitator funtionality
		if(User.getInstance().getUserType() == UserType.Facilitator){
			//panel.add(this.initDefineBenefitsView(), "Define Benefits");
			this.defineBenefits = new DefineBenefits();
			panel.add(this.defineBenefits.getView(), "Define Benefits");
			
		}
		this.problemsOpportunities = new ProblemsOpportunities();
		panel.add(this.problemsOpportunities.getView(), "Problems & Opportunities");
//		panel.add(initProposalView(), "Proposals");	
//		panel.add(initPreferencesView(), "Preferences");
		
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
	
	public void initializeOryxFrame() {
		oryxFrame = new MessageFrame("oryxFrame");
		oryxFrame.init();
		oryxFrame.setUrl(Settings.oryxFrameURL);
		oryxFrame.setHeight(Settings.oryxFrameHeight);
		oryxFrame.setWidth(Settings.oryxFrameWidth);
	}
	
	
	

}
