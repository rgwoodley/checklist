package com.codeapes.checklist.domain;

public enum CompletionStatus {
    ON_TIME ("On Time"), 
    LATE ("Late"), 
    PAST_THRESHOLD ("Past Threshold");
    
    private final String formattedName;
    
    CompletionStatus(String name) {
        this.formattedName = name;
    }
    
    public String toString() {
        return this.formattedName;
    }
}
