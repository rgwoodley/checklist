package com.codeapes.checklist.util.query;

import java.util.List;

public interface ResultPage {

    long getTotalResults();

    List<?> getResults();
    
    void setResults(List<?> results);

    int getResultsPerPage();

    int getCurrentPageNumber();

    int getTotalNumberOfPages();

    int getCurrentPageForDisplay();

    boolean isNextEnabled();

    boolean isPrevEnabled();

}
