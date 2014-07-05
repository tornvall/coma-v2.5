package com.coma.client.models;

public enum UserType {	
	    FACILITATOR("Facilitator"),
	    PARTICIPANT("Participant");
	    
	    private String value;

	    UserType(String value) {
	        this.value = value;
	    }

	    @Override
	    public String toString() {
	        return value;
	    }
	    
	    public static UserType fromString(String value) {
	        if (value != null) {
	            for (UserType userType: UserType.values()) {
	                if (value.equals(userType.toString())) {
	                    return userType;
	                }
	            }
	        }
	        return null;
	    }
}
