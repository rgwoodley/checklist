package com.codeapes.checklist.service.search;

import java.util.List;

import com.codeapes.checklist.domain.search.SearchResult;

public interface SearchService {

    List<SearchResult> search(String searchText, int maxResults);
    
    List<SearchResult> matchAll(int maxResults);
    
    void removeAllObjectsFromIndex();
    
    void refreshIndexSearcher();
    
    void refreshIndexSearcherBlocking();
}
