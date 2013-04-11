package com.codeapes.checklist.web.auditlogentry;

import com.codeapes.checklist.web.viewhelper.AbstractViewHelper;
import com.codeapes.checklist.web.viewhelper.annotation.Mapped;

public class AuditLogEntryViewHelper extends AbstractViewHelper {

    private Long objectKey;
    private String action;
    private String detail;
    private String type;

    public AuditLogEntryViewHelper(Class<?> sourceType) {
        super(sourceType);
    }
    
    public AuditLogEntryViewHelper(Class<?> sourceType, Object inputObject) {
        super(sourceType, inputObject);
    }

    public Long getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    @Mapped
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Mapped
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Mapped
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
