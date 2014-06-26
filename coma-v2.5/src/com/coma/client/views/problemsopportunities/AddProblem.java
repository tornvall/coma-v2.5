package com.coma.client.views.problemsopportunities;

import java.util.List;

import org.eclipse.jdt.internal.compiler.problem.ProblemSeverities;

import com.coma.client.Benefit;
import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.LoadModel2;
import com.coma.client.SaveModel2;
import com.coma.client.helpers.ProblemEvolution;
import com.coma.client.helpers.ProblemOccurence;
import com.coma.client.helpers.ProblemSeverity;
import com.coma.client.helpers.ProblemUrgency;
import com.coma.client.helpers.Settings;
import com.coma.client.widgets.MessageFrame;
import com.coma.v2.ModelInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

public class AddProblem {

	private MessageFrame oryxFrame = null;
	private Panel viewPanel = null;
	private ModelInfo modelInfo = null;
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);
	
	public AddProblem(){		
	}
	
	public ModelInfo getModelInfo (){
		return modelInfo;
	}
	
	public void setModelInfo(ModelInfo modelInfo){
		this.modelInfo = modelInfo;
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
		oryxFrame.setVisible(true);
		//Loads latest activegroupmodel
		//new LoadModel2().getActiveGroupModelFromDatabase(oryxFrame);
		//Loads original
		new LoadModel2().getModelFromDatabase(Settings.groupModelId, oryxFrame);
		
		panel.add(headerPanel);
		panel.add(mainPanel);
		
		return panel;
	}
	
	private Panel problemSettings(){
		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("300px");
		
		//Name
		HorizontalPanel problemNameHorizontalPanel = new HorizontalPanel();
		Label problemNameLabel = new Label("Problem name*:");
		TextBox problemNameTextBox = new TextBox();
		problemNameHorizontalPanel.add(problemNameLabel);
		problemNameHorizontalPanel.add(problemNameTextBox);
		
		//Description
		HorizontalPanel problemDescHorizontalPanel = new HorizontalPanel();
		Label problemDescLabel = new Label("Description*:");
		TextArea problemDescTextBox = new TextArea();
		problemDescHorizontalPanel.add(problemDescLabel);
		problemDescHorizontalPanel.add(problemDescTextBox);
		
		//Explain how to select problem area

		//Severity
		HorizontalPanel problemSeverityHorizontalPanel = new HorizontalPanel();
		Label problemSeverityLabel = new Label("Severity:");
		ListBox problemSeverityListBox = new ListBox();
		for(ProblemSeverity severity: ProblemSeverity.values()){
			problemSeverityListBox.addItem(severity.toString());
		}
		problemSeverityHorizontalPanel.add(problemSeverityLabel);
		problemSeverityHorizontalPanel.add(problemSeverityListBox);
		
		//Evolution
		HorizontalPanel problemEvolutionHorizontalPanel = new HorizontalPanel();
		Label problemEvolutionLabel = new Label("Evolution:");
		ListBox problemEvolutionListBox = new ListBox();
		for(ProblemEvolution evolution: ProblemEvolution.values()){
			problemEvolutionListBox.addItem(evolution.toString());
		}
		problemEvolutionHorizontalPanel.add(problemEvolutionLabel);
		problemEvolutionHorizontalPanel.add(problemEvolutionListBox);
		
		//Urgency
		HorizontalPanel problemUrgencyHorizontalPanel = new HorizontalPanel();
		Label problemUrgencyLabel = new Label("Urgency:");
		ListBox problemUrgencyListBox = new ListBox();
		for(ProblemUrgency urgency: ProblemUrgency.values()){
			problemUrgencyListBox.addItem(urgency.toString());
		}
		problemUrgencyHorizontalPanel.add(problemUrgencyLabel);
		problemUrgencyHorizontalPanel.add(problemUrgencyListBox);
		
		//Occurence
		HorizontalPanel problemOccurenceHorizontalPanel = new HorizontalPanel();
		Label problemOccurenceLabel = new Label("Occurence:");
		ListBox problemOccurenceListBox = new ListBox();
		for(ProblemOccurence occurence: ProblemOccurence.values()){
			problemOccurenceListBox.addItem(occurence.toString());
		}
		problemOccurenceHorizontalPanel.add(problemOccurenceLabel);
		problemOccurenceHorizontalPanel.add(problemOccurenceListBox);
		
		//Explanation
		HorizontalPanel problemExplanationHorizontalPanel = new HorizontalPanel();
		Label problemExplanationLabel = new Label("Explanation:");
		TextArea problemExplanationTextBox = new TextArea();		
		problemExplanationHorizontalPanel.add(problemExplanationLabel);
		problemExplanationHorizontalPanel.add(problemExplanationTextBox);
		
		
		
		//Save
		TextButton saveProblemButton = new TextButton("Save problem");	
		saveProblemButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				new SaveModel2().saveModel(oryxFrame, true);
				Info.display("Saved model", "Sucessfully saved the model");									
			}
		});	
		
		//Add all panels
		panel.add(problemNameHorizontalPanel);
		panel.add(problemDescHorizontalPanel);
		panel.add(problemSeverityHorizontalPanel);
		panel.add(problemEvolutionHorizontalPanel);
		panel.add(problemUrgencyHorizontalPanel);
		panel.add(problemOccurenceHorizontalPanel);
		panel.add(problemExplanationHorizontalPanel);		
		
		panel.add(saveProblemButton);
		
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
