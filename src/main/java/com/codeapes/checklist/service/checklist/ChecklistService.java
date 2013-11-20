package com.codeapes.checklist.service.checklist;

import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.ChecklistGroup;
import com.codeapes.checklist.domain.user.OwnerExecutor;

public interface ChecklistService {
        
    Checklist saveOrUpdateChecklist(Checklist checklist, String modifiedBy);
    
    ChecklistGroup saveOrUpdateChecklistGroup(ChecklistGroup checklistGroup, String modifiedBy);
    
    Checklist createExecutableChecklist(Checklist checklist, OwnerExecutor executor, String createdBy);
    
    Checklist updateChecklist(Checklist checklist, String modifiedBy);
    
    Checklist saveChecklist(Checklist checklist, String createdBy);
    
    void deleteChecklist(Checklist checklist, String deletedBy);

}
