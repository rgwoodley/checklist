package com.codeapes.checklist.dao;

import java.util.List;

public interface ResultPage {

    long getTotalResults();

    List<?> getResults();

    int getResultsPerPage();

    int getCurrentPageNumber();

    int getTotalNumberOfPages();

    int getCurrentPageForDisplay();

    boolean isNextEnabled();

    boolean isPrevEnabled();

}
