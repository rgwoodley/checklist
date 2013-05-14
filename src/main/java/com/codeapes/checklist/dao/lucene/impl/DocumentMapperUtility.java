package com.codeapes.checklist.dao.lucene.impl;

import java.util.HashMap;
import java.util.Map;

import com.codeapes.checklist.domain.annotation.Searchable;
import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.domain.search.lucene.mapper.Mapper;
import com.codeapes.checklist.util.ChecklistException;

public final class DocumentMapperUtility {

    private static final DocumentMapperUtility instance = new DocumentMapperUtility();
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
