package com.codeapes.checklist.service.checklist;

import com.codeapes.checklist.domain.execution.ExecutionChecklist;

public interface ExecutionChecklistUpdateService {

    long updateCompletionTimeEstimate(ExecutionChecklist executionChecklist);
    
}
