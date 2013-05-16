package com.codeapes.checklist.dao.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codeapes.checklist.dao.ResultPage;

public class ResultsPageImplTest {

    @Test
    public void totalNumberOfPagesEvenlyDivisible() {
        final ResultPage page = createResultPage(100, 5, 0);
        assertEquals(20, page.getTotalNumberOfPages());
    }

    @Test
    public void totalNumberOfPagesNotEvenlyDivisible() {
        final ResultPage page = createResultPage(100, 7, 0);
        assertEquals(15, page.getTotalNumberOfPages());
    }

    @Test
    public void resultsLessThanResultsPerPage() {
        final ResultPage page = createResultPage(5, 10, 0);
        assertEquals(1, page.getTotalNumberOfPages());
    }
    
    @Test
    public void resultsPerPageZero() {
        final ResultPage page = createResultPage(5, 0, 0);
        assertEquals(0, page.getTotalNumberOfPages());
    }
    
    private ResultPage createResultPage(int totalResults, int resultsPerPage, int currentPage) {

        return new ResultPageImpl(totalResults, resultsPerPage, currentPage, null);
    }
}
