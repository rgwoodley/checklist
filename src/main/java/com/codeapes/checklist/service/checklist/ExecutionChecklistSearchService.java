package com.codeapes.checklist.service.checklist;

import com.codeapes.checklist.domain.execution.ExecutionChecklist;
import com.codeapes.checklist.util.query.PagingQueryCriteria;
import com.codeapes.checklist.util.query.ResultPage;

public interface ExecutionChecklistSearchService {

    ExecutionChecklist findExecutionChecklistByObjectKey(Long objectKey);
    
    ResultPage getRecentlyCompletedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria);
    
    ResultPage getActiveChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria);
}
