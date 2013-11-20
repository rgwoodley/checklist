package com.codeapes.checklist.service.checklist.impl;

import com.codeapes.checklist.util.constants.QueryConstants;

public final class ChecklistSearchServiceQueries {

    private static final String EXECUTOR_OWNER_SQL = " c.group.type = 'PUBLIC' " 
            + "or c.executionInfo.executor in (:" + QueryConstants.EXECUTOR_KEY + ") " 
            + "or c.group.owner in (:" + QueryConstants.EXECUTOR_KEY + ") ";
    
    public static final String FETCH_ACTIVE_QUERY = "from Checklist as c where "
            + "c.status = 'IN_PROGRESS' and (" 
            + EXECUTOR_OWNER_SQL
            + ")";
    public static final String FETCH_ACTIVE_QUERY_COUNT = QueryConstants.SELECT_COUNT + FETCH_ACTIVE_QUERY;

    public static final String FETCH_RECENT_QUERY = "from Checklist as c where "
            + "c.status = 'COMPLETE' and " 
            + "c.executionInfo.executionEnd >= :" + QueryConstants.EXECUTION_END 
            + " and ("
            + EXECUTOR_OWNER_SQL
            + ")";
    public static final String FETCH_RECENT_QUERY_COUNT = QueryConstants.SELECT_COUNT + FETCH_RECENT_QUERY;
    
    public static final String FETCH_BY_OWNER_QUERY = "from Checklist as c where c.owner "
            + "in (:" + QueryConstants.OWNER_KEY + ")";
    public static final String FETCH_BY_OWNER_QUERY_COUNT = QueryConstants.SELECT_COUNT
            + FETCH_BY_OWNER_QUERY;
    
    private ChecklistSearchServiceQueries() {
        super();
    }
}
