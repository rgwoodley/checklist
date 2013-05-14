package com.codeapes.checklist.service.search.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeapes.checklist.dao.SearchDAO;
import com.codeapes.checklist.domain.search.SearchResult;
import com.codeapes.checklist.service.search.SearchService;

@Service(value = "searchService")
public class LuceneSearchServiceImpl implements SearchService {

    @Autowired
    private SearchDAO searchDAO;

    @Override
    public List<SearchResult> search(String searchText, int maxResults) {
        return searchDAO.search(searchText, maxResults);
    }
    
    @Override
    public List<SearchResult> matchAll(int maxResults) {
        return searchDAO.matchAll(maxResults);
    }
    
    @Override
    public void removeAllObjectsFromIndex() {
        searchDAO.removeAllObjectsFromIndex();
    }
    
    @Override
    public void refreshIndexSearcher() {
        searchDAO.refreshIndexSearcher();
    }
    
    @Override
    public void refreshIndexSearcherBlocking() {
        searchDAO.refreshIndexSearcherBlocking();
    }

}
