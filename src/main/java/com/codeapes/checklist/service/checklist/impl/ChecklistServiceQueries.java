package com.codeapes.checklist.service.checklist.impl;

import com.codeapes.checklist.util.constants.QueryConstants;

public final class ChecklistServiceQueries {

    public static final String FETCH_BY_OWNER_QUERY = "from Checklist as c where c.owner "
            + "in (:" + QueryConstants.OWNER_KEY + ")";
    public static final String FETCH_BY_OWNER_QUERY_COUNT = QueryConstants.SELECT_COUNT
            + FETCH_BY_OWNER_QUERY;
    
    private ChecklistServiceQueries() {
        super();
    }
}
