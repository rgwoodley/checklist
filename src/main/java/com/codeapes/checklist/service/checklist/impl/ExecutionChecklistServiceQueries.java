package com.codeapes.checklist.service.checklist.impl;

import com.codeapes.checklist.util.constants.QueryConstants;

public final class ExecutionChecklistServiceQueries {

    private static final String EXECUTOR_OWNER_SQL = " ec.checklist.group.type = 'PUBLIC' " 
            + "or ec.executor in (:" + QueryConstants.EXECUTOR_KEY + ") " 
            + "or ec.checklist.group.owner in (:" + QueryConstants.EXECUTOR_KEY + ") ";
    
    public static final String FETCH_ACTIVE_QUERY = "from ExecutionChecklist as ec where "
            + "ec.status = 'IN_PROGRESS' and (" 
            + EXECUTOR_OWNER_SQL
            + ")";
    public static final String FETCH_ACTIVE_QUERY_COUNT = QueryConstants.SELECT_COUNT + FETCH_ACTIVE_QUERY;

    public static final String FETCH_RECENT_QUERY = "from ExecutionChecklist as ec where "
            + "ec.status in ('PASSED','FAILED','CANCELLED') and " 
            + "ec.executionEnd >= :" + QueryConstants.EXECUTION_END 
            + " and ("
            + EXECUTOR_OWNER_SQL
            + ")";
    public static final String FETCH_RECENT_QUERY_COUNT = QueryConstants.SELECT_COUNT + FETCH_RECENT_QUERY;
    
    private ExecutionChecklistServiceQueries() {
        super();
    }
}
