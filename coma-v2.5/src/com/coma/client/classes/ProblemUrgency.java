package com.coma.client.classes;

public enum ProblemUrgency {
    VERYLOW("Very Low"),	    
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    VERYHIGH("Very high");
    
    private String value;

    ProblemUrgency(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    
    public static ProblemUrgency fromString(String value) {
        if (value != null) {
            for (ProblemUrgency urgency: ProblemUrgency.values()) {
                if (value.equals(urgency.toString())) {
                    return urgency;
                }
            }
        }
        return null;
    }
}
