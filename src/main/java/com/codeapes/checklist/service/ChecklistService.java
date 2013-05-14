package com.codeapes.checklist.service;

import java.util.List;

import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.template.ChecklistGroup;

public interface ChecklistService {

    Checklist getChecklistByKey(Long key);
    
    List<Checklist> getOwnedChecklistsForUser(Long userObjectKey);
    
    Checklist saveOrUpdateChecklist(Checklist checklist, String modifiedBy);
    
    ChecklistGroup saveOrUpdateChecklistGroup(ChecklistGroup checklistGroup, String modifiedBy);
}
