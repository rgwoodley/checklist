package com.codeapes.checklist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.template.ChecklistGroup;
import com.codeapes.checklist.service.ChecklistService;
import com.codeapes.checklist.util.AppLogger;

@Service(value = "checklistService")
@Transactional
public class ChecklistServiceImpl implements ChecklistService {

    private static final AppLogger logger = new AppLogger(ChecklistServiceImpl.class); // NOSONAR

    @Autowired
    private PersistenceDAO persistenceDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Checklist getChecklistByKey(Long key) {
        logger.debug("looking for checklist with key: %s.", key);
        return (Checklist) persistenceDAO.findObjectByKey(Checklist.class, key);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Checklist> getOwnedChecklistsForUser(Long userObjectKey) {
        logger.debug("finding checklists where owner has the object key: %d.", userObjectKey);
        final List<Checklist> checklists = (List<Checklist>)persistenceDAO.find(
            ChecklistServiceQueries.FETCH_BY_USERNAME_QUERY, userObjectKey);
        return checklists;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Checklist saveOrUpdateChecklist(Checklist checklist, String modifiedBy) {
        return (Checklist)persistenceDAO.saveOrUpdate(checklist, modifiedBy);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ChecklistGroup saveOrUpdateChecklistGroup(ChecklistGroup checklistGroup, String modifiedBy) {
        return (ChecklistGroup)persistenceDAO.saveOrUpdate(checklistGroup, modifiedBy);
    }

}
