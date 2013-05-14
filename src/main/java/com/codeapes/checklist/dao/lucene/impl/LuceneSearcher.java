package com.codeapes.checklist.dao.lucene.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;

import com.codeapes.checklist.domain.search.SearchFields;
import com.codeapes.checklist.domain.search.SearchResult;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ChecklistException;

public class LuceneSearcher {

    private static final AppLogger logger = new AppLogger(LuceneSearcher.class);
    private static final String[] SEARCH_FIELDS = { SearchFields.NAME.toString(), SearchFields.DESCRIPTION.toString(),
        SearchFields.CREATED_BY.toString(), SearchFields.MODIFIED_BY.toString(), };
    private IndexingContext context;

    public List<SearchResult> matchAll(int maxResults) {
        return search(null, maxResults, true);
    }

    public List<SearchResult> search(String searchText, int maxResults) {
        return search(searchText, maxResults, false);
    }

    private List<SearchResult> search(String searchText, int maxResults, boolean matchAll) {
        final List<SearchResult> searchResult = new ArrayList<SearchResult>();
        if (StringUtils.isBlank(searchText)) {
            return searchResult;
        }
        logger.debug("Attempting Index Search.  Query: %s, MaxResults: %s.", searchText, maxResults);
        final IndexSearcher indexSearcher = context.getSearcherManager().acquire();
        try {
            search(searchText, maxResults, matchAll, indexSearcher, searchResult);

        } catch (ParseException pe) {
            logger.error("Invalid searchText: %s", pe.getMessage());
        } catch (IOException ioe) {
            logger.error("IOException while attempting to search for results: %s", ioe.getMessage());
            throw new ChecklistException(ioe, ioe.getMessage());
        } finally {
            releaseSearcher(indexSearcher);
        }
        logger.debug("Index Search Complete.  Query: %s, MaxResults: %s.  Number of results: %s", searchText,
                maxResults, searchResult.size());
        return searchResult;
    }

    private List<SearchResult> search(String searchText, int maxResults, boolean matchAll,
            IndexSearcher indexSearcher, List<SearchResult> searchResult) throws ParseException, IOException {
        Query query = null;
        if (matchAll) {
            query = new MultiFieldQueryParser(IndexingContext.LUCENE_VERSION, SEARCH_FIELDS, context.getAnalyzer())
                    .parse(searchText);
        } else {
            query = new MatchAllDocsQuery();
        }
        final TopScoreDocCollector collector = TopScoreDocCollector.create(maxResults, true);
        indexSearcher.search(query, collector);
        final ScoreDoc[] hits = collector.topDocs().scoreDocs;
        for (ScoreDoc scoreDoc : hits) {
            final int documentId = scoreDoc.doc;
            final Document document = indexSearcher.doc(documentId);
            final String objectKeyStr = document.get("objectKey");
            if (!StringUtils.isBlank(objectKeyStr) && NumberUtils.isDigits(objectKeyStr)) {
                searchResult.add(createAndPopulateSearchResult(document));
            }
        }
        return searchResult;
    }

    private void releaseSearcher(IndexSearcher indexSearcher) {
        try {
            context.getSearcherManager().release(indexSearcher);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }
    }

    public void refreshIndexSearcher() {
        try {
            context.getSearcherManager().maybeRefresh();
        } catch (IOException ioe) {
            logger.error("Failed to refresh Lucene Index Seacher: %s", ioe.getMessage());
        }
    }

    public void refreshIndexSearcherBlocking() {
        try {
            context.getSearcherManager().maybeRefreshBlocking();
        } catch (IOException ioe) {
            logger.error("Failed to blocking refresh Lucene Index Seacher: %s", ioe.getMessage());
        }
    }

    private SearchResult createAndPopulateSearchResult(Document document) {
        final SearchResult result = new SearchResult();
        result.setObjectKey(Long.parseLong(document.get(SearchFields.OBJECT_KEY.toString())));
        result.setObjectType(document.get(SearchFields.OBJECT_TYPE.toString()));
        result.setName(document.get(SearchFields.NAME.toString()));
        result.setDescription(document.get(SearchFields.DESCRIPTION.toString()));
        result.setCreatedBy(document.get(SearchFields.CREATED_BY.toString()));
        result.setModifiedBy(document.get(SearchFields.MODIFIED_BY.toString()));
        result.setDateCreated(document.get(SearchFields.DATE_CREATED.toString()));
        result.setDateModified(document.get(SearchFields.DATE_MODIFIED.toString()));
        return result;
    }

    public IndexingContext getContext() {
        return context;
    }

    public void setContext(IndexingContext context) {
        this.context = context;
    }

}
