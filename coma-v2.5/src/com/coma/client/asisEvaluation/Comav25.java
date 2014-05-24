package com.coma.client.asisEvaluation;

import com.coma.client.Comav200;
import com.coma.client.DatabaseConnection;
import com.coma.client.DatabaseConnectionAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;



public class Comav25 {

	private static Comav25 instance = null;	
	LogIn logIn = new LogIn();
	
	public static Comav25 GetInstance(){
		if(instance == null){
			instance = new Comav25();
			return instance;
		}
		else{
			return instance;
		}
	}
	
	private final DatabaseConnectionAsync databaseConnection = GWT
			.create(DatabaseConnection.class);

	public void initMainProgram() {
		// TODO Auto-generated method stub
		
	}
	
	public void initialize(){
		RootPanel.get("mainDiv").add(logIn.screen());
	}
}
