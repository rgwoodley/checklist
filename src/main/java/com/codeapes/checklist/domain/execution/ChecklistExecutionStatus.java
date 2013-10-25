package com.codeapes.checklist.domain.execution;

public enum ChecklistExecutionStatus {
    NOT_STARTED ("Not Started"), 
    IN_PROGRESS ("In Progress"), 
    FAILED ("Failed"), 
    PASSED ("Passed"), 
    CANCELLED ("Cancelled");
    
    private final String formattedName;
    
    ChecklistExecutionStatus(String name) {
        this.formattedName = name;
    }
    
    public String toString() {
        return this.formattedName;
    }
}
