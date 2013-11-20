package com.codeapes.checklist.service.checklist;

import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.util.query.PagingQueryCriteria;
import com.codeapes.checklist.util.query.ResultPage;

public interface ChecklistSearchService {
    
    Checklist getChecklistByKey(Long key);
    
    ResultPage getOwnedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria);
    
    ResultPage getRecentlyCompletedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria);
    
    ResultPage getActiveChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria);
}
