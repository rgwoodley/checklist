package com.codeapes.checklist.domain;

public enum StepStatus {
    TEMPLATE ("Template"),
    NOT_STARTED ("Not Started"), 
    IN_PROGRESS ("In Progress"), 
    SKIPPED ("Skipped"),
    PASSED ("Passed"),
    FAILED ("Failed");
    
    private final String formattedName;
    
    StepStatus(String name) {
        this.formattedName = name;
    }
    
    public String toString() {
        return this.formattedName;
    }

}
