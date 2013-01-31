package com.codeapes.checklist.service.impl;

public final class ChecklistServiceQueries {

    public static final String FETCH_BY_USERNAME_QUERY = "from Checklist as c where c.owner.objectKey = ?";
    
    private ChecklistServiceQueries() {
        super();
    }
}
