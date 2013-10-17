package com.codeapes.checklist.service.impl;

public final class ChecklistServiceQueries {

    public static final String OWNER_KEY_PARAM = "ownerKey";

    public static final String FETCH_BY_OWNER_QUERY = "from Checklist as c ";

    public static final String FETCH_BY_OWNER_QUERY_COUNT = "select count(*) from Checklist ";

    private ChecklistServiceQueries() {
        super();
    }
}
