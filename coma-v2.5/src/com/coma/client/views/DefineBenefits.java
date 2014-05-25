package com.coma.client.views;

import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.generic.Select;

import com.coma.client.Benefit;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.User;
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
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class DefineBenefits {

	private TextButton defineBenefitsButton = new TextButton("Define benefits");	
	private Panel viewPanel = null;
	private DatabaseConnectionAsync databaseConnection = null;

	private List<CheckBox> benefitCheckBoxList = new ArrayList<CheckBox>();	
    private List<Benefit> benefitsList = new ArrayList<Benefit>();
    private VerticalPanel benefitPanel = null;
	
	private TextButton newBenefitButton = new TextButton("New benefit");
	private TextButton saveBenefitsButton = new TextButton("Save benefits");
	
	public DefineBenefits(DatabaseConnectionAsync databaseConnection){
		this.databaseConnection = databaseConnection;
		this.viewPanel = initDefineBenefitsView();		
	}
	public Panel getView(){
		return this.viewPanel;
	}
	
	private Panel initDefineBenefitsView(){
		VerticalPanel panel = new VerticalPanel();
		
		//panel.add(topMenuButtonsDefineBenefitsView());
		panel.add(mainWindowDefineBenefitsView());
		
		return panel;
	}
		
	private Panel topMenuButtonsDefineBenefitsView()
	{ 	
		HorizontalPanel panel = new HorizontalPanel();
		defineBenefitsButton.getElement().setClassName("utilityButton");

		defineBenefitsButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {				

			}
			
		});

		panel.add(defineBenefitsButton);		
		panel.add(new Label("Logged in as: " + User.getInstance().getUserEmail()
				+ " id: "    + User.getInstance().getUserId()
				+ " Group: " + User.getInstance().getActiveGroupID()
				+ " Role: "  + User.getInstance().getUserType()));
		
		return panel;  
	}
	
	private Panel mainWindowDefineBenefitsView()
	{ 			
		VerticalPanel outerPanel = new VerticalPanel();
		HorizontalPanel menupanel = new HorizontalPanel();	
		benefitPanel = new VerticalPanel();

		databaseConnection.getAllBenefits(new AsyncCallback<List<Benefit>>() {		
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<Benefit> result) {
				// TODO Auto-generated method stub
				//benefitsList = result;	
				populateBenefits(result);
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
		
			}			
		});

		menupanel.add(newBenefitButton);
		menupanel.add(saveBenefitsButton);	
		
		
		outerPanel.add(menupanel);
		outerPanel.add(benefitPanel);		
		return outerPanel;  
	}

	private void populateBenefits(List<Benefit> list){
		for(Benefit benefit: list){
			
			CheckBox cb = new CheckBox(benefit.getDescription());
			cb.getElement().getStyle().setDisplay(Display.BLOCK);
			
		    // Hook up a listener to find out when it's clicked.
		    cb.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					

//				      public void onClick(Widget sender) {
//				        boolean checked = ((CheckBox) sender).isChecked();
//				        Window.alert("It is " + (checked ? "" : "not") + "checked");
				}
			});					        
		    
		    benefitPanel.add(cb);
		    this.benefitCheckBoxList.add(cb);
		}

	}
	
}
