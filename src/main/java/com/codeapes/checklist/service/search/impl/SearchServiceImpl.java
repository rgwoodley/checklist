package com.codeapes.checklist.service.search.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeapes.checklist.dao.search.SearchDAO;
import com.codeapes.checklist.domain.search.SearchResult;
import com.codeapes.checklist.service.search.SearchService;

/**
 * This class provides methods allowing clients to search for search-indexed
 * objects.
 * 
 * @author Joe Kuryla
 */
@Service(value = "searchService")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDAO searchDAO;

    /**
     * Search for ranked search results based on free form text input
     * 
     * @param searchText    Free form search text
     * @param maxResults    The maximum number of results to retrieve
     * @return List<SearchResult>   The search results in ranked order
     */
    @Override
    public List<SearchResult> search(String searchText, int maxResults) {
        return searchDAO.search(searchText, maxResults);
    }

    /**
     * Search on everything in the search index
     * 
     * @param maxResults    The maximum number of results to retrieve
     * @return List<SearchResult>   The search results in ranked order
     */
    @Override
    public List<SearchResult> matchAll(int maxResults) {
        return searchDAO.matchAll(maxResults);
    }

    /**
     * Remove everything from the search index
     */
    @Override
    public void removeAllObjectsFromIndex() {
        searchDAO.removeAllObjectsFromIndex();
    }

    /**
     * Refreshes the Index Searcher, giving it access to
     * objects indexed since the last refresh.
     */
    @Override
    public void refreshIndexSearcher() {
        searchDAO.refreshIndexSearcher();
    }

    /**
     * Refreshes the Index Searcher, and causes the calling
     * thread to block until the refresh is complete.
     */
    @Override
    public void refreshIndexSearcherBlocking() {
        searchDAO.refreshIndexSearcherBlocking();
    }

}
