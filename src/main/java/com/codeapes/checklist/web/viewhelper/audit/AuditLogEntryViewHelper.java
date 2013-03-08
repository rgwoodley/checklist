package com.codeapes.checklist.web.viewhelper.audit;

import com.codeapes.checklist.domain.audit.AuditLogEntry;
import com.codeapes.checklist.web.viewhelper.util.AbstractViewHelper;

public class AuditLogEntryViewHelper extends AbstractViewHelper {

    private Long objectKey;
    private String action;
    private String detail;
    private String type;

    public AuditLogEntryViewHelper() {
        super();
        setClassDerivedFrom(AuditLogEntry.class);
    }
    
    public Long getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
