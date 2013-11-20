package com.codeapes.checklist.service.checklist.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.dao.persistence.PersistenceDAO;
import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.service.checklist.ChecklistSearchService;
import com.codeapes.checklist.service.user.UserService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ApplicationProperties;
import com.codeapes.checklist.util.constants.QueryConstants;
import com.codeapes.checklist.util.query.PagingQueryCriteria;
import com.codeapes.checklist.util.query.QueryUtil;
import com.codeapes.checklist.util.query.ResultPage;

@Service(value = "checklistSearchService")
@Transactional
public class ChecklistSearchServiceImpl implements ChecklistSearchService {

    private static final AppLogger logger = new AppLogger(ChecklistSearchServiceImpl.class); // NOSONAR
    
    @Autowired
    private PersistenceDAO persistenceDAO;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ApplicationProperties appProperties;
        
    @Override
    public Checklist getChecklistByKey(Long key) {
        logger.debug("looking for checklist with key: %s.", key);
        return (Checklist)persistenceDAO.findObjectByKey(Checklist.class, key);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultPage getRecentlyCompletedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria) {
        logger.debug("finding recently completed checklists for user with object key: %d.", userObjectKey);
        QueryUtil.setupQueries(pageCriteria, ChecklistSearchServiceQueries.FETCH_RECENT_QUERY_COUNT, 
                ChecklistSearchServiceQueries.FETCH_RECENT_QUERY);
        final List<OwnerExecutor> userAndGroups = userService.getUserAndGroups(userObjectKey);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(QueryConstants.EXECUTOR_KEY, userAndGroups);
        pageCriteria.setParameters(parameters);
        final int numDaysLookback = appProperties.getRecentlyCompletedChecklistLookback();
        final Date oldestExecutionDate = subtractDaysFromCurrentDate(numDaysLookback);
        logger.debug("  looking back %s days", numDaysLookback);
        logger.debug("  converts to this date: %s", oldestExecutionDate);
        parameters.put(QueryConstants.EXECUTION_END, oldestExecutionDate);
        return persistenceDAO.getPageOfResults(pageCriteria);    
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultPage getActiveChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria) {
        logger.debug("finding actively running checklists for user with object key: %d.", userObjectKey);
        QueryUtil.setupQueries(pageCriteria, ChecklistSearchServiceQueries.FETCH_ACTIVE_QUERY_COUNT, 
                ChecklistSearchServiceQueries.FETCH_ACTIVE_QUERY);
        final List<OwnerExecutor> userAndGroups = userService.getUserAndGroups(userObjectKey);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(QueryConstants.EXECUTOR_KEY, userAndGroups);
        pageCriteria.setParameters(parameters);
        return persistenceDAO.getPageOfResults(pageCriteria);  
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultPage getOwnedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria) {
        
        logger.debug("finding checklists for user with object key: %d.", userObjectKey);
        QueryUtil.setupQueries(pageCriteria, ChecklistSearchServiceQueries.FETCH_BY_OWNER_QUERY_COUNT, 
                ChecklistSearchServiceQueries.FETCH_BY_OWNER_QUERY);
        final List<OwnerExecutor> userAndGroups = userService.getUserAndGroups(userObjectKey);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(QueryConstants.OWNER_KEY, userAndGroups);
        pageCriteria.setParameters(parameters);
        return persistenceDAO.getPageOfResults(pageCriteria);
    }
    
    private Date subtractDaysFromCurrentDate(int numDays) {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, numDays * -1);
        return calendar.getTime();
    }
    
}
