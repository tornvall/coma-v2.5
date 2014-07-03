package com.coma.client.views.problemsopportunities;

import java.util.ArrayList;
import java.util.List;

import com.coma.client.Comav25;
import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.classes.ProblemClass;
import com.coma.client.classes.ProblemImpact;
import com.coma.client.classes.User;
import com.coma.client.classes.UserType;
import com.coma.client.helpers.Settings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class AddChangeProblems {
	
	private Panel viewPanel = null;
	
	//Panels
	private AddChangeProblemsPanel problemsPanel;
	
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	
	public AddChangeProblems(){
		problemsPanel = new AddChangeProblemsPanel();
	}
	
	public Panel getView(){		
		this.viewPanel = initAddChangeProblemsView();
		
		return this.viewPanel;
	}	

	private Panel initAddChangeProblemsView(){
		HorizontalPanel panel = new HorizontalPanel();
		VerticalPanel leftSidePanel = new VerticalPanel();
		VerticalPanel rightSidePanel = new VerticalPanel();
		
		//Problems panel			
		leftSidePanel.add(problemsPanel.getView());
				
		rightSidePanel.add(selectedProblemCausePanel());		
		rightSidePanel.add(selectedProblemOtherCausePanel());		

		panel.add(leftSidePanel);
		panel.add(rightSidePanel);
		
		return panel;
	}

	private Panel selectedProblemCausePanel(){ 	
		VerticalPanel panel = new VerticalPanel();
		
		Label headerLabel = new Label("Cause of the selected problem");
		
		panel.add(headerLabel);
		
		return panel;
	}
	private Panel selectedProblemOtherCausePanel(){ 			
		VerticalPanel panel = new VerticalPanel();
		
		Label headerLabel = new Label("Other causes");
		
		panel.add(headerLabel);
		
		return panel;
	}
	
}
