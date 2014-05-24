package com.coma.client;

import com.coma.client.helpers.UserType;

public class User {

	private static User instance = null;
	private int userId;
	private int activeGroupID;
	private String userEmail;
	private UserType userType;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getActiveGroupID() {
		return activeGroupID;
	}

	public void setActiveGroupID(int activeGroupID) {
		this.activeGroupID = activeGroupID;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public UserType getUserType() {
		return userType;
	}
	
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public static User getInstance(){
		
		if(instance == null){
			instance = new User();
			return instance;
		}else{
			return instance;
		}
		
	}
	
}
