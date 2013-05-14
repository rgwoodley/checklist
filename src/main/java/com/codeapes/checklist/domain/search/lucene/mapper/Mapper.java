package com.codeapes.checklist.domain.search.lucene.mapper;

import org.apache.lucene.document.Document;

import com.codeapes.checklist.domain.persistence.Persistent;

public interface Mapper {

    Document validateAndMap(Persistent object);

    Document mapObjectToDocument(Persistent object, Document document);
}
