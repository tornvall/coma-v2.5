package com.coma.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Benefit implements IsSerializable {
	
	private Integer id;
	private String description;
	private Boolean isActive;
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public Benefit(){}
	
	public Benefit(int id, String description){
		this.id = id;
		this.description = description;
	}
}
