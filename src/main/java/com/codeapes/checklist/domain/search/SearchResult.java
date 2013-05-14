package com.codeapes.checklist.domain.search;

public class SearchResult {

    private Long objectKey;
    private String objectType;
    private String name;
    private String description;
    private String createdBy;
    private String modifiedBy;
    private String dateCreated;
    private String dateModified;

    public Long getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    public String getObjectType() {
        return objectType;
    }
    
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getDescription() {
        return description;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }
    
    public String getName() {
        return name;
    }

}
