package com.codeapes.checklist.dao.search;

import java.util.List;

import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.domain.search.SearchResult;

/**
 * This interface defines a set of methods that make it possible
 * to easily add and remove objects for the application's
 * search index.  Methods for searching are also defined.
 * 
 * The 'refresh' methods are used to update in-memory searcher
 * instances after the search index has been updated.
 * 
 * @author jkuryla
 *
 */
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
