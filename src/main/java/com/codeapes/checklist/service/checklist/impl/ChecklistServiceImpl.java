package com.codeapes.checklist.service.checklist.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.template.ChecklistGroup;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.service.checklist.ChecklistService;
import com.codeapes.checklist.service.user.UserService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.constants.QueryConstants;
import com.codeapes.checklist.util.query.PagingQueryCriteria;
import com.codeapes.checklist.util.query.QueryUtility;
import com.codeapes.checklist.util.query.ResultPage;

@Service(value = "checklistService")
@Transactional
public class ChecklistServiceImpl implements ChecklistService {

    private static final AppLogger logger = new AppLogger(ChecklistServiceImpl.class); // NOSONAR

    @Autowired
    private PersistenceDAO persistenceDAO;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Checklist getChecklistByKey(Long key) {
        logger.debug("looking for checklist with key: %s.", key);
        return (Checklist) persistenceDAO.findObjectByKey(Checklist.class, key);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultPage getOwnedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria) {
        
        logger.debug("finding checklists for user with object key: %d.", userObjectKey);
        QueryUtility.setupQueries(pageCriteria, ChecklistServiceQueries.FETCH_BY_OWNER_QUERY_COUNT, 
                ChecklistServiceQueries.FETCH_BY_OWNER_QUERY);
        final List<OwnerExecutor> userAndGroups = userService.getUserAndGroups(userObjectKey);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(QueryConstants.OWNER_KEY, userAndGroups);
        pageCriteria.setParameters(parameters);
        return persistenceDAO.getPageOfResults(pageCriteria);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Checklist saveOrUpdateChecklist(Checklist checklist, String modifiedBy) {
        return (Checklist) persistenceDAO.saveOrUpdate(checklist, modifiedBy);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ChecklistGroup saveOrUpdateChecklistGroup(ChecklistGroup checklistGroup, String modifiedBy) {
        return (ChecklistGroup) persistenceDAO.saveOrUpdate(checklistGroup, modifiedBy);
    }
    
}
