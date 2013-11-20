package com.codeapes.checklist.dao.search.lucene.impl;

import java.util.HashMap;
import java.util.Map;

import com.codeapes.checklist.domain.annotation.Searchable;
import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.domain.search.lucene.mapper.Mapper;
import com.codeapes.checklist.exception.ChecklistException;

/**
 * DocumentMappers map domain objects to Lucene documents that can be indexed.  This
 * utility maintains a cache of mappers that are used when reading/writing search
 * data.  A domain object is converted into a document using a mapper when it is
 * written to the search index, for example.
 * 
 * @author jkuryla
 */
public final class DocumentMapperUtility {

    private static final DocumentMapperUtility instance = new DocumentMapperUtility(); // NOSONAR
    private Map<String, Mapper> documentMappers = new HashMap<String, Mapper>();

    private DocumentMapperUtility() {
        super();
    }

    public static DocumentMapperUtility getInstance() {
        return instance;
    }

    protected Mapper getDocumentMapper(Persistent object) {
        final String className = object.getClass().getName();
        Mapper mapper = documentMappers.get(className);
        if (mapper == null) {
            final Searchable annotation = (Searchable) object.getClass().getAnnotation(Searchable.class);
            final String mapperClassName = annotation.mapperClass();
            try {
                final Class<?> mapperClass = Class.forName(mapperClassName);
                mapper = (Mapper) mapperClass.newInstance();
                documentMappers.put(className, mapper);
            } catch (ClassNotFoundException cnfe) {
                throw new ChecklistException(cnfe, cnfe.getMessage());
            } catch (IllegalAccessException iae) {
                throw new ChecklistException(iae, iae.getMessage());
            } catch (InstantiationException ie) {
                throw new ChecklistException(ie, ie.getMessage());
            }
        }
        return mapper;
    }
}
