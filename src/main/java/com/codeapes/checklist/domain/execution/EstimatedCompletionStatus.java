package com.codeapes.checklist.domain.execution;

public enum EstimatedCompletionStatus {
    ON_TIME ("On Time"), 
    LATE ("Late"), 
    PAST_THRESHOLD ("Past Threshold");
    
    private final String formattedName;
    
    EstimatedCompletionStatus(String name) {
        this.formattedName = name;
    }
    
    public String toString() {
        return this.formattedName;
    }
}
