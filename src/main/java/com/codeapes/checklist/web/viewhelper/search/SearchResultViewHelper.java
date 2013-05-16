package com.codeapes.checklist.web.viewhelper.search;

import com.codeapes.checklist.web.viewhelper.AbstractViewHelper;
import com.codeapes.checklist.web.viewhelper.annotation.Mapped;

public class SearchResultViewHelper extends AbstractViewHelper {

    private Long objectKey;
    private String objectType;
    private String name;
    private String description;
    private String createdBy;
    private String modifiedBy;
    private String dateCreated;
    private String dateModified;

    public SearchResultViewHelper(Class<?> parameterizedType) {
        super(parameterizedType);
    }

    public SearchResultViewHelper(Class<?> sourceType, Object inputObject) {
        super(sourceType, inputObject);
    }

    @Mapped
    public Long getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    @Mapped
    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @Mapped
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Mapped
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Mapped
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Mapped
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Mapped
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Mapped
    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

}
