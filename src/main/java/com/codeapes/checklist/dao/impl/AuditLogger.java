package com.codeapes.checklist.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.domain.audit.AuditLogEntry;
import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ApplicationProperties;

@Component
public class AuditLogger {

    private static final AppLogger logger = new AppLogger(AuditLogger.class); // NOSONAR
    private static final String DEFAULT_CREATED_MODIFIED_BY = "system";

    @Autowired
    private ApplicationProperties applicationProperties;
    
    @Autowired
    @Qualifier("hibernateDAO")
    private PersistenceDAO hibernateDAO;

    protected void addActionToAuditLog(Operation operation, String user, Persistent obj) {
        if (applicationProperties.isAuditLogEnabled()) {
            final AuditLogEntry logEntry = new AuditLogEntry();
            logEntry.setAction(operation.toString());
            logEntry.setType(obj.getClass().getSimpleName());
            logEntry.setDetail(obj.toString());
            hibernateDAO.saveObject(logEntry, DEFAULT_CREATED_MODIFIED_BY);
            logger.debug("Operation %s written to audit log for object: %s", operation.toString(), obj.toString());
        }
    }
}
