package com.coma.client.models;



public enum ProblemSeverity {
	    VERYWEAK("Very Weak"),	    
	    WEAK("Weak"),
	    MEDIUM("Medium"),
	    STRONG("Strong"),
	    VERYSTRONG("Very Strong");
	    
	    private String value;

	    ProblemSeverity(String value) {
	        this.value = value;
	    }

	    @Override
	    public String toString() {
	        return value;
	    }
	    
	    public static ProblemSeverity fromString(String value) {
	        if (value != null) {
	            for (ProblemSeverity severity: ProblemSeverity.values()) {
	                if (value.equals(severity.toString())) {
	                    return severity;
	                }
	            }
	        }
	        return null;
	    }
}
