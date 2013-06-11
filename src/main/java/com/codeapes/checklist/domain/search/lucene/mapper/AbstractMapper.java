package com.codeapes.checklist.domain.search.lucene.mapper;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;

import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.domain.search.SearchFields;
import com.codeapes.checklist.util.ChecklistException;

/**
 * This abstract class maps fields that are common to all Persistent objects.
 * 
 * @author Joe Kuryla
 */
public abstract class AbstractMapper implements Mapper {

    /**
     * This methods maps fields that are common to all Persistent objects.
     * Note that the validateAndMap method calls the mapObjectToDocument method,
     * which must be implemented by any class that extends this abstract class.  The
     * mapObjectToDocument method should map the SearchFields.NAME and SearchFields.DESCRIPTION
     * fields, at a minimum.
     * 
     * @param object        The object being mapped
     * @return Document     An indexable document
     */
    public Document validateAndMap(Persistent object) {
        if (validObject(object)) {
            final Document document = new Document();
            document.add(new StringField(SearchFields.OBJECT_KEY.toString(), object.getObjectKey().toString(),
                    Field.Store.YES));
            document.add(new StringField(SearchFields.OBJECT_TYPE.toString(), object.getClass().getName(),
                    Field.Store.YES));
            document.add(new StringField(SearchFields.CREATED_BY.toString(), object.getCreatedBy(), Field.Store.YES));
            document.add(new StringField(SearchFields.DATE_CREATED.toString(), DateTools.timeToString(object
                    .getCreatedTimestamp().getTime(), DateTools.Resolution.MINUTE), Field.Store.YES));
            document.add(new StringField(SearchFields.MODIFIED_BY.toString(), object.getModifiedBy(), Field.Store.YES));
            document.add(new StringField(SearchFields.DATE_MODIFIED.toString(), DateTools.timeToString(object
                    .getModifiedTimestamp().getTime(), DateTools.Resolution.MINUTE), Field.Store.YES));
            mapObjectToDocument(object, document);
            final String name = document.get(SearchFields.NAME.toString());
            if (StringUtils.isBlank(name)) {
                throw new ChecklistException(
                        "Attempt to map object to document for search indexing failed.  "
                        + "The Name field is not set.  Object Type: %s, Key: %2",
                        object.getClass().getName(), object.getObjectKey());
            }
            return document;
        }
        throw new ChecklistException("Cannot convert object to searchable indexable Document.");
    }

    protected boolean validObject(Persistent object) {
        boolean valid = true;
        if (object == null) {
            valid = false;
        }
        return valid;
    }

}
