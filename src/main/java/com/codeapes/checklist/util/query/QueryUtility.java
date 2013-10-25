package com.codeapes.checklist.util.query;


public final class QueryUtility {

    private QueryUtility() { }
    
    public static void setupQueries(PagingQueryCriteria criteria, String countQuery, String query) {
        criteria.setCountQuery(countQuery);
        criteria.setQuery(query);
        QueryUtility.addOrderByToQuery(query, criteria);
    }
    
    public static String addOrderByToQuery(String query, PagingQueryCriteria criteria) {
        String updatedQuery = query;
        final String sortColumn = criteria.getSortField();
        final SortOrder sortOrder = criteria.getSortOrder();
        if (sortColumn != null && sortOrder != null) {
            final StringBuilder sb = new StringBuilder(query);
            sb.append(" order by ");
            sb.append(sortColumn);
            sb.append(" ");
            sb.append(sortOrder);
            updatedQuery = sb.toString();
        }
        return updatedQuery;
    }
    
}
