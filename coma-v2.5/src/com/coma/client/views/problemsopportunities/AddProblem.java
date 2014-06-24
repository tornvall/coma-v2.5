package com.coma.client.views.problemsopportunities;

import java.util.List;

import com.coma.client.Benefit;
import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.LoadModel2;
import com.coma.client.SaveModel2;
import com.coma.client.helpers.Settings;
import com.coma.client.widgets.MessageFrame;
import com.coma.v2.ModelInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
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
		
		Label problemNameLabel = new Label("Problem name*:");
		TextButton saveProblemButton = new TextButton("Save problem");
		
		
		
		saveProblemButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				new SaveModel2().saveModel(oryxFrame, true);
				Info.display("Saved model", "Sucessfully saved the model");									
			}
		});	
		
		panel.add(problemNameLabel);
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
