package com.coma.client.views.problemsopportunities;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
