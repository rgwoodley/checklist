package com.codeapes.checklist.domain;

public enum ChecklistStatus {
    TEMPLATE ("Template"),
    NOT_STARTED ("Not Started"), 
    IN_PROGRESS ("In Progress"),
    COMPLETE ("Failed");
    
    private final String formattedName;
    
    ChecklistStatus(String name) {
        this.formattedName = name;
    }
    
    public String toString() {
        return this.formattedName;
    }
}
