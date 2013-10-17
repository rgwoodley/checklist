package com.codeapes.checklist.util.paging.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.util.paging.ResultPage;

public final class ResultPageImpl implements ResultPage, Serializable {

    private static final long serialVersionUID = 1898864946283539702L;

    private long totalResults;
    private List<?> results;
    private int resultsPerPage;
    private int currentPageNumber;

    public ResultPageImpl(long totalResults, int resultsPerPage, int currentPageNumber, List<?> results) {
        setTotalResults(totalResults);
        setResultsPerPage(resultsPerPage);
        setCurrentPageNumber(currentPageNumber);
        if (results == null) {
            setResults(new ArrayList<Persistent>());
        } else {
            setResults(results);
        }
    }

    public ResultPageImpl() {
        super();
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public List<?> getResults() {
        return results;
    }

    public void setResults(List<?> results) {
        this.results = results;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getTotalNumberOfPages() {

        if (getResultsPerPage() == 0) {
            return 0;
        }

        final double numberOfPages = (double) getTotalResults() / (double) getResultsPerPage();
        return (int) Math.ceil(numberOfPages);
    }

    public int getCurrentPageForDisplay() {
        return getCurrentPageNumber() + 1;
    }

    public boolean isNextEnabled() {
        boolean result = true;
        if (getCurrentPageForDisplay() == getTotalNumberOfPages()) {
            result = false;
        }
        return result;
    }

    public boolean isPrevEnabled() {
        boolean result = true;
        if (getCurrentPageNumber() == 0) {
            result = false;
        }
        return result;
    }
}
