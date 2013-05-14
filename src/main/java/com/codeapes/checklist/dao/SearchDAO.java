package com.codeapes.checklist.dao;

import java.util.List;

import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.domain.search.SearchResult;

public interface SearchDAO {

    void addObjectToIndex(Persistent persistent);

    void updateObjectInIndex(Persistent persistent);

    void removeObjectFromIndex(Persistent object);

    void removeObjectFromIndex(Class<?> clazz, Long objectKey);
    
    void removeAllObjectsFromIndex();

    List<SearchResult> search(String searchText, int maxResults);
    
    List<SearchResult> matchAll(int maxResults);
    
    void refreshIndexSearcher();
    
    void refreshIndexSearcherBlocking();
}
