package com.codeapes.checklist.dao.persistence.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.codeapes.checklist.dao.persistence.PersistenceDAO;
import com.codeapes.checklist.dao.search.SearchDAO;
import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.util.query.PagingQueryCriteria;
import com.codeapes.checklist.util.query.ResultPage;

/**
 * This class provides convenient access to the underlying datastore
 * and is intended for use by the application's service tier.
 * 
 * @author jkuryla
 * 
 */
@Repository("persistenceDAO")
public class AuditLoggingDAO implements PersistenceDAO {
     
    @Autowired
    @Qualifier("hibernateDAO")
    private PersistenceDAO databaseDAO;
    
    @Autowired
    private SearchDAO searchDAO;
    
    @Autowired
    private AuditLogger auditLogger;

    @Override
    public Persistent saveObject(Persistent persistentObj, String createdBy) {
        final Persistent savedObject = databaseDAO.saveObject(persistentObj, createdBy);
        auditLogger.addActionToAuditLog(Operation.SAVE, createdBy, savedObject);
        searchDAO.addObjectToIndex(savedObject);
        return savedObject;
    }

    @Override
    public Persistent update(Persistent persistentObj, String modifiedBy) {
        final Persistent updatedObject = databaseDAO.update(persistentObj, modifiedBy);
        auditLogger.addActionToAuditLog(Operation.UPDATE, modifiedBy, updatedObject);
        searchDAO.updateObjectInIndex(updatedObject);
        return updatedObject;
    }

    @Override
    public Persistent saveOrUpdate(Persistent persistentObj, String modifiedBy) {
        if (persistentObj.getObjectKey() == null) {
            return saveObject(persistentObj, modifiedBy);
        }
        return update(persistentObj, modifiedBy);
    }

    @Override
    public void delete(Persistent persistentObj, String deletedBy) {
        databaseDAO.delete(persistentObj, deletedBy);
        auditLogger.addActionToAuditLog(Operation.DELETE, deletedBy, persistentObj);
        searchDAO.removeObjectFromIndex(persistentObj);
    }

    @Override
    public Persistent findObjectByKey(Class<? extends Persistent> objectClass, Long key) {
        return databaseDAO.findObjectByKey(objectClass, key);
    }

    @Override
    public List<?> find(String query) {
        return databaseDAO.find(query);
    }

    @Override
    public List<?> find(String query, Object parameterValue) {
        return databaseDAO.find(query, parameterValue);
    }

    @Override
    public List<?> find(String query, Map<String, Object> parameters) {
        return databaseDAO.find(query, parameters);
    }

    @Override
    public ResultPage getPageOfResults(PagingQueryCriteria pageCriteria) {
        return databaseDAO.getPageOfResults(pageCriteria);
    }

}
