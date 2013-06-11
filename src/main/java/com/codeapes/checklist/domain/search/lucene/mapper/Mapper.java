package com.codeapes.checklist.domain.search.lucene.mapper;

import org.apache.lucene.document.Document;

import com.codeapes.checklist.domain.persistence.Persistent;

/**
 * This validateAndMap method in this interface is used by the PersistenceDAO class
 * to create an indexable Document object, which is used to write information to the
 * search index.  The mapObjectToDocument method provides a place for implementing
 * classes to add mapping code that might not already be in the validateAndMap method.
 * See the AbstractMapper class for more information.
 * 
 * @author Joe Kuryla
 */
public interface Mapper {

    /**
     * This purpose of this method is to ensure that all fields necessary for
     * searching are mapped, and to map those fields into a new indexable Document
     * object.  The AbstractMapper class implements this interface, and its implementation
     * of this method calls the mapObjectToDocument method, also defined in this
     * interface.
     * 
     * @param object        The object being mapped
     * @return Document     An indexable document
     */
    Document validateAndMap(Persistent object);

    /**
     * The purpose of this method is to provide a place for implementing classes to add
     * mapping code for fields that are not already mapped by the validateAndMap method.
     * 
     * @param object        The object being mapped
     * @param document      An indexable document that has already been created
     * @return Document     An indexable document
     */
    Document mapObjectToDocument(Persistent object, Document document);
}
