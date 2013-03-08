package com.codeapes.checklist.web.auditlogentry;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class AuditLogEntryForm {

    @NotEmpty(message = "Action is required")
    @Length(max = 10, message = "Action cannot be more than 10 characters.")
    private String action;

    @NotEmpty(message = "Detail is required")
    @Length(max = 10, message = "Detail cannot be more than 10 characters.")
    private String detail;

    @NotEmpty(message = "Type is required")
    @Length(max = 10, message = "Type cannot be more than 10 characters.")
    private String type;

    public String getAction() {
        return action;
    }

    public String getDetail() {
        return detail;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
