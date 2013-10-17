package com.codeapes.checklist.service;

import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.template.ChecklistGroup;
import com.codeapes.checklist.util.paging.PagingQueryCriteria;
import com.codeapes.checklist.util.paging.ResultPage;

public interface ChecklistService {

    Checklist getChecklistByKey(Long key);
    
    ResultPage getOwnedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria);
    
    ResultPage getRecentlyCompletedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria);
    
    ResultPage getActiveChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria);
    
    Checklist saveOrUpdateChecklist(Checklist checklist, String modifiedBy);
    
    ChecklistGroup saveOrUpdateChecklistGroup(ChecklistGroup checklistGroup, String modifiedBy);
}
