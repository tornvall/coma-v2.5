package com.coma.client.views;

import java.util.ArrayList;
import java.util.List;

import java_cup.internal_error;

import org.apache.bcel.generic.Select;

import com.coma.client.Benefit;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
import com.coma.client.helpers.Settings;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.TableLayout;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class DefineBenefits {

	private TextButton defineBenefitsButton = new TextButton("Fetch benefits");	
	private Panel viewPanel = null;
	private DatabaseConnectionAsync databaseConnection = null;

	private List<CheckBox> benefitCheckBoxList = new ArrayList<CheckBox>();	

    private VerticalPanel benefitPanel = new VerticalPanel();   
	private VerticalPanel mainWinPanel = new VerticalPanel();
	
	private TextButton newBenefitButton = new TextButton("New benefit");
	private TextButton saveBenefitsButton = new TextButton("Save benefits");
	
	private String benefitCheckBoxId = "benefitCbId=";
	
	private List<Integer> selectedBenefits = new ArrayList<Integer>();
	
	public DefineBenefits(DatabaseConnectionAsync databaseConnection){
		this.databaseConnection = databaseConnection;
		this.viewPanel = initDefineBenefitsView();		
	}
	public Panel getView(){
		return this.viewPanel;
	}	

	private Panel initDefineBenefitsView(){		
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(topMenuButtonsDefineBenefitsView());				
		
		//Reference to empty main window, gets built during run-time 
		panel.add(mainWinPanel);
		
		return panel;
	}
		
	private Panel topMenuButtonsDefineBenefitsView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();
		defineBenefitsButton.getElement().setClassName("utilityButton");

		defineBenefitsButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {				
				mainWindowDefineBenefitsView();
			}
			
		});

		panel.add(defineBenefitsButton);		
		panel.add(new Label("Logged in as: " + User.getInstance().getUserEmail()
				+ " id: "    + User.getInstance().getUserId()
				+ " Group: " + User.getInstance().getActiveGroupID()
				+ " Role: "  + User.getInstance().getUserType()));
		
		return panel;  
	}
	
	private void mainWindowDefineBenefitsView()
	{ 			
		//Clear from previous
		benefitPanel.clear();
		mainWinPanel.clear();
		
		HorizontalPanel menupanel = new HorizontalPanel();	
		benefitPanel.getElement().getStyle().setProperty("backgroundColor", Settings.mainWindowBgColorString);
		benefitPanel.add(new Label("Loading benefits..."));
		
		//Get selected benefits, get all benefits and then build the list
		databaseConnection.getBenefitSelection(12, 53, new AsyncCallback<List<Integer>>() {		
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<Integer> selection) {
				selectedBenefits = selection;

				databaseConnection.getAllBenefits(new AsyncCallback<List<Benefit>>() {		
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<Benefit> benefits) {
						// TODO Auto-generated method stub
						populateBenefits(selectedBenefits, benefits);
					}
				});						
			}
		});	
	
		newBenefitButton.getElement().setClassName("sendButton");
		saveBenefitsButton.getElement().setClassName("sendButton");
		
		newBenefitButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
		
			}			
		});		
		
		saveBenefitsButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {				
				List<Integer> benefitIdToSaveIntegers = new ArrayList<Integer>();
				for(CheckBox cbBox : benefitCheckBoxList){
					if(cbBox.getValue()){
						int id = Integer.valueOf(cbBox.getElement().getId().substring(benefitCheckBoxId.length()));
						benefitIdToSaveIntegers.add(id);						
					}
				}
				
				//groupID, modelID, List<Integer> benefits
				databaseConnection.updateBenefitSelection(12, 53, benefitIdToSaveIntegers, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						benefitPanel.clear();
						benefitPanel.add(new Label("Error saving selection"));
					}
					@Override
					public void onSuccess(Void result) {
						benefitPanel.clear();
						benefitPanel.add(new Label("Selection saved!"));
					}
				});
			}			
		});

		menupanel.add(newBenefitButton);
		menupanel.add(saveBenefitsButton);		
		mainWinPanel.add(menupanel);		
		mainWinPanel.add(benefitPanel);		
	}

	private void populateBenefits(List<Integer> selectionList, List<Benefit> benefits){
		benefitPanel.clear();
		benefitCheckBoxList.clear();
		
		for(Benefit benefit: benefits){								
			CheckBox cb = new CheckBox(benefit.getDescription());
			cb.getElement().getStyle().setDisplay(Display.BLOCK);
			
			for(Integer selected: selectionList){
				if(benefit.getID() == selected){
					cb.setValue(true);					
				}
			}

			String pageIdString = benefitCheckBoxId + String.valueOf(benefit.getID());			
			cb.getElement().setId(pageIdString);			    
		    
		    benefitPanel.add(cb);
		    this.benefitCheckBoxList.add(cb);		    
		    
		}
		if(benefits.isEmpty()){
			benefitPanel.add(new Label("There are no benefits"));
		}
	}
	
}
