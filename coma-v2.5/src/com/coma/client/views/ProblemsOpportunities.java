package com.coma.client.views;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
import com.coma.client.helpers.UserType;
import com.coma.client.views.problemsopportunities.AddChangeProblems;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class ProblemsOpportunities {

	public TextButton problemsOpportunitiesButton = new TextButton("Problems & Opportunities");
	public TextButton addChangeProblemsButton = new TextButton("Add & change problems");
	public TextButton addChangeOpportunitiesButton = new TextButton("Add & change opportunities");
	public TextButton consolidateProblemsButton = new TextButton("Consolidate problems");	
		
	//The "main" window, exluding top menu
	private VerticalPanel mainWinPanel = new VerticalPanel();
	
	//Sub views, passing reference for clearing
	private AddChangeProblems addChangeProblems = new AddChangeProblems(mainWinPanel);
	
	
	private Panel viewPanel = null;
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	
	public ProblemsOpportunities(){
		this.viewPanel = initProblemsOpportunitiesView();	
	}
	public Panel getView(){
		return this.viewPanel;
	}
	

	private Panel initProblemsOpportunitiesView(){
		VerticalPanel panel = new VerticalPanel();		

		panel.add(topMenuButtonsProblemsOpportunitiesView());

		//Reference to empty main window, gets built during run-time 
		panel.add(mainWinPanel);
		
		return panel;
	}
	
	private Panel topMenuButtonsProblemsOpportunitiesView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();
		addChangeProblemsButton.getElement().setClassName("utilityButton");
		addChangeOpportunitiesButton.getElement().setClassName("utilityButton");		

		addChangeProblemsButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				mainWinPanel.clear();
				mainWinPanel.add(addChangeProblems.getView());
			}

		});
		addChangeOpportunitiesButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {

			}

		});
				
		panel.add(addChangeProblemsButton);
		panel.add(addChangeOpportunitiesButton);

		//Add facilitator funtionality
		if(User.getInstance().getUserType()== UserType.Facilitator){
			consolidateProblemsButton.getElement().setClassName("utilityButton");
			consolidateProblemsButton.addSelectHandler(new SelectHandler(){
				@Override
				public void onSelect(SelectEvent event) {

				}

			});
			panel.add(consolidateProblemsButton);
		}
		
		panel.add(new Label("Logged in as: " + User.getInstance().getUserEmail()
						+ " id: "    + User.getInstance().getUserId()
						+ " Group: " + User.getInstance().getActiveGroupID()
						+ " Role: "  + User.getInstance().getUserType()));
		return panel;  
	}
	
}
