package com.coma.client.views.problemsopportunities;

import java.util.ArrayList;
import java.util.List;

import com.coma.client.Comav25;
import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.helpers.Settings;
import com.coma.client.models.ProblemClass;
import com.coma.client.models.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;

public class AddChangeProblemsPanel {
		
	private Panel viewPanel = null;
	private AddProblem addProblem = null;
	private UpdateProblem updateProblem = null;
	
	private CellTable<ProblemClass> cellTable;
	private List<ProblemClass> problemList = new ArrayList<ProblemClass>();
	private SingleSelectionModel<ProblemClass> selectionModel = null;
	private int nextProblemId = 0;	
	
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	
	public AddChangeProblemsPanel(){			
		addProblem = new AddProblem();
		updateProblem = new UpdateProblem();
	}
	
	public Panel getView(){		
		this.viewPanel = initProblemsPanel();
		
		return this.viewPanel;
	}

	private Panel initProblemsPanel()
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
				Comav25.GetInstance().getMainWinPanel().add(addProblem.getNewView());
			}
		});
		
		createdProblemsRemoveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				final ProblemClass problem = selectionModel.getSelectedObject();
				selectionModel.clear();
				
				databaseConnection.deleteProblem(problem, new AsyncCallback<Void>() {		
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Void result) {
						Info.display("Problem deleted", "Deleted " + problem.getName());
						Comav25.GetInstance().getMainWinPanel().clear();
						Comav25.GetInstance().getMainWinPanel().add(Comav25.GetInstance().getProblemsOpportunities().getAddChangeProblems().getView());
					}
				});
			}
		});
		
		createdProblemsDetailsButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				Comav25.GetInstance().getMainWinPanel().clear();
				Comav25.GetInstance().getMainWinPanel().add(updateProblem.getUpdateView(selectionModel.getSelectedObject()));
			}
		});				
				
		//Add headers and button
		panel.add(headerLabel);		
		menuPanel.add(createdProblemsAddButton);
		menuPanel.add(createdProblemsRemoveButton);
		menuPanel.add(createdProblemsDetailsButton);		
		panel.add(menuPanel);
		
		//Make problem table
		cellTable = new CellTable<ProblemClass>();
		//cellTable.setWidth("100%", true);
		
		problemList = new ArrayList<ProblemClass>();
		
		//Selection model
		selectionModel = new SingleSelectionModel<ProblemClass>();
		cellTable.setSelectionModel(selectionModel);			
		
		//Create Problems column
		TextColumn<ProblemClass> problemColumn = new TextColumn<ProblemClass>() {
		      @Override
		      public String getValue(ProblemClass object) {
		        return object.getName();
		      }
		    };
		cellTable.addColumn(problemColumn, "Problems");
				
		//Fetch data
		this.fetchProblemList();
		
		//Add table
		panel.add(cellTable);		
		
		return panel;  
	}
	
	private void fetchProblemList(){
		databaseConnection.getProblemsFromUser(User.getInstance().getUserId(), Settings.groupId, Settings.activegroupModelID, new AsyncCallback<List<ProblemClass>>() {		
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<ProblemClass> result) {
				//Add impacts and generate uniqueId for selection
				for (ProblemClass problem:result) {
					problemList.add(problem);
				}
				
				//Refresh list and push data
				refreshProblemList();				
			}
		});
	}
	
	public void refreshProblemList(){
		//For paging calculations
	    cellTable.setRowCount(problemList.size(), true);

	    //Push data to widget
	    cellTable.setRowData(0, problemList);
	}
}
