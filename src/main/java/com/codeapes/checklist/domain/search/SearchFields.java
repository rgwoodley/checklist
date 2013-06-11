package com.codeapes.checklist.domain.search;

/**
 * This enumeration defines the fields required for adding objects
 * to the search index, and searching on indexed objects.
 * 
 * @author Joe Kuryla
 */
public enum SearchFields {
    OBJECT_KEY ("objectKey"),
    OBJECT_TYPE ("objectType"),
    DEFAULT ("default"),
    NAME ("name"),
    DESCRIPTION ("description"),
    CREATED_BY ("createdBy"),
    DATE_CREATED ("dateCreated"),
    MODIFIED_BY ("modifiedBy"),
    DATE_MODIFIED ("dateModified");
    
    private final String name;       

    private SearchFields(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
