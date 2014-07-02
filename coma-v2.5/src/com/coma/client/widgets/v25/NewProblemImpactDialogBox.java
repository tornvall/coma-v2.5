package com.coma.client.widgets.v25;

import java.util.Date;
import java.util.List;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.classes.Benefit;
import com.coma.client.classes.ProblemImpact;
import com.coma.client.classes.User;
import com.coma.client.views.problemsopportunities.AddProblem;
import com.coma.v2.Comav200;
import com.coma.v2.ModelInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.Window.WindowAppearance;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextArea;

public class NewProblemImpactDialogBox {
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	private Dialog dialog; 
	private String benefitDesc;
	private int benefitID;
	private AddProblem addProblemInstance = null;

	public NewProblemImpactDialogBox(int benefitID, String benefitDesc, AddProblem addProblemInstance) {
		this.benefitDesc = benefitDesc;
		this.benefitID = benefitID;
		this.addProblemInstance = addProblemInstance;
		
		Dialog dialogBox = createDialogBox();
		dialogBox.center();
		dialogBox.show();
	}

	public Dialog createDialogBox(){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("New impact on "+benefitDesc);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.OK, PredefinedButton.CANCEL);	

		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.addStyleName("dialogVPanel");

		final TextBox writeDescriptionTextArea = new TextBox();
		//writeDescriptionTextArea.setWidth("100");

		verticalLayoutContainer.add(writeDescriptionTextArea);

		dialog.setWidget(verticalLayoutContainer);

		// Add a handler to create the new group
		dialog.getButton(PredefinedButton.OK).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				String impact = writeDescriptionTextArea.getText();

				//addProblemImpact(impact);
				addProblemImpactToList(impact);
				
				dialog.hide();
			}
		});
		
		//Add a handler to close the dialog
		dialog.getButton(PredefinedButton.CANCEL).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				dialog.hide();
			}
		});
		return dialog;
	}
	
	protected void addProblemImpactToList(String impact) {		
		ProblemImpact newImpact = new ProblemImpact(benefitID, benefitDesc, impact, true);
			
		addProblemInstance.addImpact(newImpact);	
		addProblemInstance.addSelection(newImpact);
		addProblemInstance.refreshProblemImpactList();
	}
}
