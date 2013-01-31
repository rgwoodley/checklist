package com.codeapes.checklist.service;

import java.util.List;

import com.codeapes.checklist.domain.group.ChecklistGroup;
import com.codeapes.checklist.domain.template.Checklist;

public interface ChecklistService {

    Checklist getChecklistByKey(Long key);
    
    List<Checklist> getOwnedChecklistsForUser(Long userObjectKey);
    
    Checklist saveOrUpdateChecklist(Checklist checklist, String modifiedBy);
    
    ChecklistGroup saveOrUpdateChecklistGroup(ChecklistGroup checklistGroup, String modifiedBy);
}
