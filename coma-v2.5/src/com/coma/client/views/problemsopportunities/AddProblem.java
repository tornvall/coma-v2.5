package com.coma.client.views.problemsopportunities;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.helpers.Settings;
import com.coma.client.widgets.MessageFrame;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AddProblem {

	private MessageFrame oryxFrame = null;
	private Panel viewPanel = null;
	private Panel mainWinPanel = null;
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	
	public AddProblem(Panel mainWinPanel){		
		this.mainWinPanel = mainWinPanel;
	}
	
	public Panel getView(){		
		this.viewPanel = initAddProblemView();
		
		return this.viewPanel;
	}	
	
	private Panel initAddProblemView(){
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel headerPanel = new HorizontalPanel();
		HorizontalPanel mainPanel = new HorizontalPanel();				

		headerPanel.add(new Label("Enter problem"));		
				
		mainPanel.add(problemSettings());		
		this.initializeOryxFrame();
		mainPanel.add(oryxFrame);			

		panel.add(headerPanel);
		panel.add(mainPanel);
		
		return panel;
	}
	
	private Panel problemSettings(){
		VerticalPanel panel = new VerticalPanel();
		
		Label problemNameLabel = new Label("Problem name*:");
		
		
		panel.add(problemNameLabel);
		
		return panel;
	}
	
	private void initializeOryxFrame() {
		oryxFrame = new MessageFrame("oryxFrame");
		oryxFrame.init();
		oryxFrame.setUrl(Settings.oryxFrameURL);
		oryxFrame.setHeight(Settings.oryxFrameHeight);
		oryxFrame.setWidth(Settings.oryxFrameWidth);
	}
}
