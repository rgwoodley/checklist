package com.codeapes.checklist.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.template.ChecklistGroup;
import com.codeapes.checklist.service.ChecklistService;
import com.codeapes.checklist.service.SortOrder;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.paging.PagingQueryCriteria;
import com.codeapes.checklist.util.paging.ResultPage;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultPage getOwnedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria) {
        logger.debug("finding checklists for user with object key: %d.", userObjectKey);
        String query = ChecklistServiceQueries.FETCH_BY_OWNER_QUERY;
        query = addOrderByToQuery(query, pageCriteria.getSortField(), pageCriteria.getSortOrder());
        pageCriteria.setQuery(query);
        final String countQuery = ChecklistServiceQueries.FETCH_BY_OWNER_QUERY_COUNT;
        pageCriteria.setCountQuery(countQuery);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        // parameters.put(ChecklistServiceQueries.OWNER_KEY_PARAM,
        // userObjectKey);
        pageCriteria.setParameters(parameters);
        return persistenceDAO.getPageOfResults(pageCriteria);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultPage getRecentlyCompletedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria) {
        logger.debug("finding recently completed checklists for user with object key: %d.", userObjectKey);
        return getOwnedChecklists(userObjectKey, pageCriteria);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultPage getActiveChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria) {
        logger.debug("finding active checklists for user with object key: %d.", userObjectKey);
        return getOwnedChecklists(userObjectKey, pageCriteria);
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

    private String addOrderByToQuery(String query, String sortColumn, SortOrder sortOrder) {
        String updatedQuery = query;
        if (sortColumn != null && sortOrder != null) {
            final StringBuilder sb = new StringBuilder(query);
            sb.append(" order by ");
            sb.append(sortColumn);
            sb.append(" ");
            sb.append(sortOrder);
            updatedQuery = sb.toString();
        }
        return updatedQuery;
    }

}
