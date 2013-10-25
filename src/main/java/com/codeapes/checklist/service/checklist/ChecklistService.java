package com.codeapes.checklist.service.checklist;

import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.template.ChecklistGroup;
import com.codeapes.checklist.util.query.PagingQueryCriteria;
import com.codeapes.checklist.util.query.ResultPage;

public interface ChecklistService {

    Checklist getChecklistByKey(Long key);
    
    ResultPage getOwnedChecklists(Long userObjectKey, PagingQueryCriteria pageCriteria);
        
    Checklist saveOrUpdateChecklist(Checklist checklist, String modifiedBy);
    
    ChecklistGroup saveOrUpdateChecklistGroup(ChecklistGroup checklistGroup, String modifiedBy);
}
