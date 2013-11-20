package com.codeapes.checklist.domain.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.codeapes.checklist.domain.persistence.impl.PersistentEntity;

@Entity
@Table(name = "cl_audit_log") // NOSONAR
public class AuditLogEntry extends PersistentEntity {

    private static final long serialVersionUID = 1794958075167635645L;

    private String type;
    private String action;
    private String detail;

    @Column(name = "type", length = 200, nullable = false) // NOSONAR
    public String getType() {
        return type;
    }

    @Column(name = "action", length = 200, nullable = false) // NOSONAR
    public String getAction() {

        return action;
    }

    @Column(name = "detail", length = 1000, nullable = true) // NOSONAR
    public String getDetail() {

        return detail;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
