package com.coma.client.widgets;


import com.coma.client.models.User;
import com.coma.v2.Comav200;
import com.coma.v2.ModelInfo;
import com.coma.v2.SaveModel;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class SendProposalDialog {

	private MessageFrame oryxFrame;
	private Dialog dialog;

	public SendProposalDialog(MessageFrame oryxFrame){
		this.oryxFrame = oryxFrame;
	}
		

	public Dialog createDialogBox(){
		// Create the popup dialog box
		dialog = new Dialog();
		dialog.setHeadingText("Send proposal?");
		dialog.add(new Label("Do you wish to send proposal for group: " + User.getInstance().getActiveGroupID()));
		dialog.setPixelSize(300, 100);
		dialog.setHideOnButtonClick(true);
		dialog.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.CANCEL);

		// Add a handler to create the new group
		dialog.getButton(PredefinedButton.YES).addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				
				ModelInfo model = Comav200.GetInstance().getModel();
				model.setIsProposal(1);
				new SaveModel().saveModel(oryxFrame);

			}
		});
		//Add a handler to close the dialog
		dialog.getButton(PredefinedButton.CANCEL).addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				dialog.hide();
			}
		});

		dialog.show();
		return dialog;

	}
}
