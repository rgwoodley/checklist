package com.codeapes.checklist.util.constants;

public final class QueryConstants {

    public static final String SELECT_COUNT = " select count(*) ";
    public static final String OWNER_KEY = "ownerKey";
    public static final String EXECUTOR_KEY = "executorKey";
    public static final String EXECUTION_END = "executionEnd";
    
    private QueryConstants() { }
    
    public static String formatQueryParameter(String parameterName) {
        return "(:" + parameterName + ") ";
    }
}
