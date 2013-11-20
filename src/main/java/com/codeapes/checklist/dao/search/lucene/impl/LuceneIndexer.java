package com.codeapes.checklist.dao.search.lucene.impl;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;

import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.domain.search.SearchFields;
import com.codeapes.checklist.domain.search.lucene.mapper.Mapper;
import com.codeapes.checklist.exception.ChecklistException;
import com.codeapes.checklist.util.AppLogger;

/**
 * A Lucene indexer is responsible for writing objects to the search index.  Supported
 * operation are add/update & remove.
 * 
 * @author jkuryla
 */
public final class LuceneIndexer {

    private static final AppLogger logger = new AppLogger(LuceneIndexer.class); // NOSONAR
    private IndexingContext context;
    private final DocumentMapperUtility mapperUtility = DocumentMapperUtility.getInstance();

    public void addObjectToIndex(Persistent object) {
        if (object == null || object.getObjectKey() == null) {
            logger.error("Unable to add object to search index because the object or object key is null.");
            return;
        }
        final IndexWriter indexWriter = context.getIndexWriter();
        final String className = object.getClass().getName();
        logger.debug("Attempting to add object to search index.  Type: %s, Key: %s.", className, object.getObjectKey());
        try {
            final Mapper documentMapper = mapperUtility.getDocumentMapper(object);
            if (documentMapper == null) {
                logger.error("Could not find document mapper for class: %s", className);
                return;
            }
            final Document theDocument = documentMapper.validateAndMap(object);
            indexWriter.addDocument(theDocument);
            indexWriter.commit();
        } catch (IOException ioe) {
            logger.error("Unable to add or commit search index changes for object of type: %s", object.getClass());
            logger.error(ioe.getMessage());
        }
        logger.debug("Object added to search index.  Type: %s, Key: %s.", className, object.getObjectKey());
    }

    public void updateObjectInIndex(Persistent object) {
        removeObjectFromIndex(object);
        addObjectToIndex(object);
    }

    public void removeObjectFromIndex(Persistent object) {
        if (object == null || object.getObjectKey() == null) {
            return;
        }
        removeObjectFromIndex(object.getClass(), object.getObjectKey());
    }

    public void removeObjectFromIndex(Class<?> clazz, Long objectKey) {
        logger.debug("Attempting to remove item from index.  Type: %s, Key: %s ", clazz, objectKey);
        if (clazz == null || objectKey == null) {
            logger.error("Attempt to remove object from index failed.  Type: %s, Key %s.", clazz, objectKey);
            return;
        }
        final IndexWriter indexWriter = context.getIndexWriter();
        final String objectType = clazz.getName();
        try {
            final TermQuery keyQuery = new TermQuery(new Term(SearchFields.OBJECT_KEY.toString()
                    , objectKey.toString()));
            final TermQuery typeQuery = new TermQuery(new Term(SearchFields.OBJECT_TYPE.toString(), objectType));
            final BooleanQuery query = new BooleanQuery();
            query.add(keyQuery, BooleanClause.Occur.MUST);
            query.add(typeQuery, BooleanClause.Occur.MUST);
            indexWriter.deleteDocuments(query);
            indexWriter.commit();
        } catch (IOException ioe) {
            logger.error("IOException while attempting to remove objects from search index.");
            throw new ChecklistException(ioe, ioe.getMessage());
        }
        logger.debug("Object removed from search index.  Type: %s, Key %s.", objectType, objectKey);
    }

    public void removeAllObjectsFromIndex() {
        logger.info("Removing all objects from index.");
        try {
            final IndexWriter indexWriter = context.getIndexWriter();
            indexWriter.deleteAll();
            indexWriter.commit();
        } catch (IOException ioe) {
            throw new ChecklistException(ioe, ioe.getMessage());
        }
    }

    public IndexingContext getContext() {
        return context;
    }

    public void setContext(IndexingContext context) {
        this.context = context;
    }

}
