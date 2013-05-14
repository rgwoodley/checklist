package com.codeapes.checklist.dao.lucene.impl;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.codeapes.checklist.dao.SearchDAO;
import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.domain.search.SearchResult;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ApplicationProperties;

public class LuceneSearchDAOImpl implements SearchDAO, InitializingBean, DisposableBean {

    private static final AppLogger logger = new AppLogger(LuceneSearchDAOImpl.class);
    private ApplicationProperties applicationProperties;
    private LuceneIndexer indexer = new LuceneIndexer();
    private LuceneSearcher searcher = new LuceneSearcher();
    private IndexingContext indexingContext = new IndexingContext();

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("initializing LuceneSearchDAO implementation.");
        indexingContext.initialize(applicationProperties);
        indexer.setContext(indexingContext);
        searcher.setContext(indexingContext);
        logger.info("LuceneSearchDAO initialization complete.");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("LuceneSearchDAO being destroyed.  Closing indexing context.");
        indexingContext.close();
        logger.info("Indexing context closed.");
    }

    @Override
    public void addObjectToIndex(Persistent object) {
        indexer.addObjectToIndex(object);
    }

    @Override
    public void updateObjectInIndex(Persistent object) {
        indexer.updateObjectInIndex(object);
    }

    @Override
    public void removeObjectFromIndex(Persistent object) {
        indexer.removeObjectFromIndex(object.getClass(), object.getObjectKey());
    }

    @Override
    public void removeObjectFromIndex(Class<?> clazz, Long objectKey) {
        indexer.removeObjectFromIndex(clazz, objectKey);
    }

    @Override
    public void removeAllObjectsFromIndex() {
        indexer.removeAllObjectsFromIndex();
    }

    @Override
    public List<SearchResult> matchAll(int maxResults) {
        return searcher.matchAll(maxResults);
    }

    @Override
    public List<SearchResult> search(String searchText, int maxResults) {
        return searcher.search(searchText, maxResults);
    }

    @Override
    public void refreshIndexSearcher() {
        searcher.refreshIndexSearcher();
    }

    @Override
    public void refreshIndexSearcherBlocking() {
        searcher.refreshIndexSearcherBlocking();
    }

    public ApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }

    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

}
