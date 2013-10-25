package com.codeapes.checklist.service.checklist;

import com.codeapes.checklist.domain.execution.ExecutionChecklist;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.user.OwnerExecutor;

public interface ExecutionChecklistService {

    ExecutionChecklist createExecutionChecklist(Checklist checklist, OwnerExecutor executor, String createdBy);

    ExecutionChecklist updateExecutionChecklist(ExecutionChecklist executionChecklist, String modifiedBy);

    void deleteExecutionChecklist(ExecutionChecklist executionChecklist, String deletedBy);

}
