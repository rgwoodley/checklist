package com.codeapes.checklist.web.dashboard;

import java.util.List;

import com.codeapes.checklist.domain.execution.ExecutionChecklist;
import com.codeapes.checklist.domain.template.Checklist;

public class DashboardViewHelper {

    private List<Checklist> checklists;
    private List<ExecutionChecklist> executionChecklists;

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    public List<ExecutionChecklist> getExecutionChecklists() {
        return executionChecklists;
    }

    public void setExecutionChecklists(List<ExecutionChecklist> executionChecklists) {
        this.executionChecklists = executionChecklists;
    }

}
