package com.codeapes.checklist.web.search;

public class SearchForm {

    private static final int RESULTS_PER_PAGE = 10;

    private String searchText;
    private int resultsPerPage = RESULTS_PER_PAGE;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

}
