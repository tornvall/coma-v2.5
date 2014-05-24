package com.coma.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class LogIn {

	TextButton participant1Button = new TextButton("Participant 1");
	TextButton participant2Button = new TextButton("Participant 2");
	TextButton facilitatorButton = new TextButton("Facilitator");
	
	String password = "a";

	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	public FormPanel screen() {
		final FormPanel form = new FormPanel();
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.addStyleName("table-center");
		form.addStyleName("demo-FormPanel");

		VerticalPanel holder = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		holder.add(new Label("Login as"));

		participant1Button.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				loginAs("p1");
			}
		});
		participant2Button.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				loginAs("p2");
			}
		});
		facilitatorButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				loginAs("f");
			}
		});

		hPanel.add(participant1Button);
		hPanel.add(participant2Button);
		hPanel.add(facilitatorButton);
		holder.add(hPanel);

		form.add(holder);
		return form;
	}

	protected void loginAs(String email) {
		getAndSetUserIDFromDatabase(email);
		User.getInstance().setUserEmail(email);		
	}

	/**
	*Gets active users ID from database and sets the ID in the User class
	*/
	public void getAndSetUserIDFromDatabase(String email) {
		databaseConnection.getUserID(email, new AsyncCallback<Integer>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Integer result) {
				// TODO Auto-generated method stub
				User.getInstance().setUserId(result);
				Comav25.GetInstance().initMainProgram();		
			}

		});
	}
}
