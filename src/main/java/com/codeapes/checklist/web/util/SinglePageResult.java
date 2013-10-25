package com.codeapes.checklist.web.util;

import java.util.List;

import com.codeapes.checklist.util.query.ResultPage;

public class SinglePageResult {

    //count is the page number being returned
    private int count;
    private List<?> results;

    public SinglePageResult() {
        super();
    }
    
    public SinglePageResult(ResultPage resultPage) {
        if (resultPage != null) {
            count = resultPage.getTotalNumberOfPages();
            results = resultPage.getResults();
        }
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<?> getResults() {
        return results;
    }

    public void setResults(List<?> results) {
        this.results = results;
    }

}
