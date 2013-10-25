package com.codeapes.checklist.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.dao.SearchDAO;
import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.util.query.PagingQueryCriteria;
import com.codeapes.checklist.util.query.ResultPage;

@Repository("persistenceDAO")
public class PersistenceDAOImpl implements PersistenceDAO {
     
    @Autowired
    @Qualifier("hibernateDAO")
    private PersistenceDAO hibernateDAO;
    
    @Autowired
    private SearchDAO searchDAO;
    
    @Autowired
    private AuditLogger auditLogger;
    
    @Override
    public SessionFactory getHibernateSessionFactory() {
        return hibernateDAO.getHibernateSessionFactory();
    }

    @Override
    public Persistent saveObject(Persistent persistentObj, String createdBy) {
        final Persistent savedObject = hibernateDAO.saveObject(persistentObj, createdBy);
        auditLogger.addActionToAuditLog(Operation.SAVE, createdBy, savedObject);
        searchDAO.addObjectToIndex(savedObject);
        return savedObject;
    }

    @Override
    public Persistent update(Persistent persistentObj, String modifiedBy) {
        final Persistent updatedObject = hibernateDAO.update(persistentObj, modifiedBy);
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
        hibernateDAO.delete(persistentObj, deletedBy);
        auditLogger.addActionToAuditLog(Operation.DELETE, deletedBy, persistentObj);
        searchDAO.removeObjectFromIndex(persistentObj);
    }

    @Override
    public Persistent findObjectByKey(Class<? extends Persistent> objectClass, Long key) {
        return hibernateDAO.findObjectByKey(objectClass, key);
    }

    @Override
    public List<?> find(String query) {
        return hibernateDAO.find(query);
    }

    @Override
    public List<?> find(String query, Object parameterValue) {
        return hibernateDAO.find(query, parameterValue);
    }

    @Override
    public List<?> find(String query, Map<String, Object> parameters) {
        return hibernateDAO.find(query, parameters);
    }

    @Override
    public ResultPage getPageOfResults(PagingQueryCriteria pageCriteria) {
        return hibernateDAO.getPageOfResults(pageCriteria);
    }

}
