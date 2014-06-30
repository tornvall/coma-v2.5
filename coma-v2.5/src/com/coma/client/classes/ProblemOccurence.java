package com.coma.client.classes;

public enum ProblemOccurence {
    VERYRARE("Very rare"),	    
    RARE("Rare"),
    MEDIUM("Medium"),
    OFTEN("Often"),
    VERYOFTEN("Very often");
    
    private String value;

    ProblemOccurence(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    
    public static ProblemOccurence fromString(String value) {
        if (value != null) {
            for (ProblemOccurence occurence: ProblemOccurence.values()) {
                if (value.equals(occurence.toString())) {
                    return occurence;
                }
            }
        }
        return null;
    }
}
