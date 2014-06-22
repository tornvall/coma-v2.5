package com.coma.client.views.problemsopportunities;

import org.eclipse.jdt.internal.core.CreateCompilationUnitOperation;

import com.coma.client.Comav25;
import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
import com.coma.client.helpers.UserType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class AddChangeProblems {
	
	private ListBox problemsListBox = new ListBox();
	private AddProblem addProblem = null;
	
	private Panel viewPanel = null;
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	
	public AddChangeProblems(){			
		addProblem = new AddProblem();
	}
	
	public Panel getView(){		
		this.viewPanel = initAddChangeProblemsView();
		
		return this.viewPanel;
	}	

	private Panel initAddChangeProblemsView(){
		HorizontalPanel panel = new HorizontalPanel();
		VerticalPanel leftSidePanel = new VerticalPanel();
		VerticalPanel rightSidePanel = new VerticalPanel();
				
		leftSidePanel.add(createdProblemsPanel());
				
		rightSidePanel.add(selectedProblemCausePanel());		
		rightSidePanel.add(selectedProbelmOtherCausePanel());		

		panel.add(leftSidePanel);
		panel.add(rightSidePanel);
		
		return panel;
	}
	
	private Panel createdProblemsPanel()
	{ 	
		VerticalPanel panel = new VerticalPanel();
		
		Label headerLabel = new Label("Created problems");

		HorizontalPanel menuPanel = new HorizontalPanel();
		TextButton createdProblemsAddButton = new TextButton("Add");
		TextButton createdProblemsRemoveButton = new TextButton("Remove");
		TextButton createdProblemsDetailsButton = new TextButton("Show details");	
		
		createdProblemsAddButton.getElement().setClassName("sendButton");
		createdProblemsRemoveButton.getElement().setClassName("sendButton");	
		createdProblemsDetailsButton.getElement().setClassName("sendButton");
		
		createdProblemsAddButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				Comav25.GetInstance().getMainWinPanel().clear();
				Comav25.GetInstance().getMainWinPanel().add(addProblem.getView());
			}

		});
		
		createdProblemsRemoveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {

			}

		});
		
		createdProblemsDetailsButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {

			}

		});
				
		panel.add(headerLabel);
		
		menuPanel.add(createdProblemsAddButton);
		menuPanel.add(createdProblemsRemoveButton);
		menuPanel.add(createdProblemsDetailsButton);		
		panel.add(menuPanel);
		
		panel.add(problemsListBox);
		
		return panel;  
	}

	private Panel selectedProblemCausePanel(){ 	
		VerticalPanel panel = new VerticalPanel();
		
		Label headerLabel = new Label("Cause of the selected problem");
		
		panel.add(headerLabel);
		
		return panel;
	}
	private Panel selectedProbelmOtherCausePanel(){ 			
		VerticalPanel panel = new VerticalPanel();
		
		Label headerLabel = new Label("Other causes");
		
		panel.add(headerLabel);
		
		return panel;
	}
	
}
