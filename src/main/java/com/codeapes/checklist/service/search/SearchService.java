package com.codeapes.checklist.service.search;

import java.util.List;

import com.codeapes.checklist.domain.search.SearchResult;

/**
 * This interface provides methods allowing clients to search for search-indexed
 * objects.
 * 
 * @author Joe Kuryla
 */
public interface SearchService {

    /**
     * Search for ranked search results based on free form text input
     * 
     * @param searchText    Free form search text
     * @param maxResults    The maximum number of results to retrieve
     * @return List<SearchResult>   The search results in ranked order
     */
    List<SearchResult> search(String searchText, int maxResults);
    
    /**
     * Search on everything in the search index
     * 
     * @param maxResults    The maximum number of results to retrieve
     * @return List<SearchResult>   The search results in ranked order
     */
    List<SearchResult> matchAll(int maxResults);
    
    /**
     * Remove everything from the search index
     */
    void removeAllObjectsFromIndex();
   
    /**
     * Refresh the Index Searcher, giving it access to
     * objects indexed since the last refresh.
     */
    void refreshIndexSearcher();
 
    /**
     * Refresh the Index Searcher, causing the calling
     * thread to block until the refresh is complete.
     */
    void refreshIndexSearcherBlocking();
}
