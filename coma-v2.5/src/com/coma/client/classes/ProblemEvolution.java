package com.coma.client.classes;

public enum ProblemEvolution {
    CHANGED("Changed"),	    
    UNCHANGED("Unchanged");
    
    private String value;

    ProblemEvolution(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    
    public static ProblemEvolution fromString(String value) {
        if (value != null) {
            for (ProblemEvolution evolution: ProblemEvolution.values()) {
                if (value.equals(evolution.toString())) {
                    return evolution;
                }
            }
        }
        return null;
    }
}
