package com.codeapes.checklist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.service.PersistenceService;
import com.codeapes.checklist.util.paging.PagingQueryCriteria;
import com.codeapes.checklist.util.paging.ResultPage;

@Service(value = "persistenceService")
@Transactional
public class PersistenceServiceImpl implements PersistenceService {

    @Autowired
    private PersistenceDAO persistenceDAO;

    public Persistent saveObject(Persistent persistentObj, String createdBy) {
        return persistenceDAO.saveObject(persistentObj, createdBy);
    }

    public Persistent update(Persistent persistentObj, String modifiedBy) {
        return persistenceDAO.update(persistentObj, modifiedBy);
    }

    public Persistent saveOrUpdate(Persistent persistentObj, String modifiedBy) {
        return persistenceDAO.saveOrUpdate(persistentObj, modifiedBy);
    }

    public void delete(Persistent persistentObj, String deletedBy) {
        persistenceDAO.delete(persistentObj, deletedBy);
    }

    public Persistent findObjectByKey(Class<? extends Persistent> objectClass, Long key) {
        return persistenceDAO.findObjectByKey(objectClass, key);
    }

    public List<?> fetchAllObjectsByType(Class<? extends Persistent> objectClass) {
        final String className = objectClass.getSimpleName();
        return persistenceDAO.find("from " + className);
    }

    @Override
    public ResultPage getPageOfResults(PagingQueryCriteria pageCriteria) {
        return persistenceDAO.getPageOfResults(pageCriteria);
    }

}
