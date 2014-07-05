package com.coma.client.widgets.v25;

import java.util.Date;
import java.util.List;

import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.coma.client.models.User;
import com.coma.v2.Comav200;
import com.coma.v2.ModelInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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


public class NewBenefitDialogBox{

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	private Dialog dialog; 

	public NewBenefitDialogBox() {
		Dialog dialogBox = createDialogBox();
		dialogBox.center();
		dialogBox.show();
	}

	public Dialog createDialogBox(){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("Enter new benefit");
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.CANCEL);	

		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.addStyleName("dialogVPanel");

		final TextBox writeDescriptionTextArea = new TextBox();
		//writeDescriptionTextArea.setWidth("100");

		verticalLayoutContainer.add(writeDescriptionTextArea);

		dialog.setWidget(verticalLayoutContainer);

		// Add a handler to create the new group
		dialog.getButton(PredefinedButton.YES).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				String description = writeDescriptionTextArea.getText();

				addBenefit(description);
				
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

	protected void addBenefit(String description) {
		databaseConnection.createNewBenefit(description, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
			}
		});
	}

}
