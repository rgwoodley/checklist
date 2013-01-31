package com.codeapes.checklist.dao;

import java.util.Map;

import com.codeapes.checklist.util.ChecklistException;

public class PagingQueryCriteria {

    private String query;
    private String countQuery;
    private int pageNumber;
    private int resultsPerPage;
    
    //this attribute can be set to null if the queries have no parameters.
    //it is assumed that both the countQuery and query will use the same parameters.
    //parameters are assumed to be named parameters in the query (:paramName, for example) instead of positional (?)
    //this map should hold key/value pairs where the name is the named parameter in the query.
    private Map<String, Object> parameters;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        if (query == null) {
            throw new ChecklistException("query attribute in ResultPageCriteria must be non-null.");
        }
        this.query = query;
    }

    public String getCountQuery() {
        return countQuery;
    }

    public void setCountQuery(String countQuery) {
        if (countQuery == null) {
            throw new ChecklistException("countQuery attribute in ResultPageCriteria must be non-null.");
        }
        this.countQuery = countQuery;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber < 0) {
            throw new ChecklistException(
                "pageNumber attribute in ResultPageCriteria must be non-negative.  Error attempting to set it to: ",
                pageNumber);
        }
        this.pageNumber = pageNumber;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        if (resultsPerPage < 1) {
            throw new ChecklistException("resultsPerPage attribute in ResultPageCriteria must be greater than zero.  "
                + "Exception attempting to set it to: ", resultsPerPage);
        }
        this.resultsPerPage = resultsPerPage;
    }

}
