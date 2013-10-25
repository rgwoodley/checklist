package com.codeapes.checklist.service.checklist.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.domain.execution.ChecklistExecutionStatus;
import com.codeapes.checklist.domain.execution.ExecutionChecklist;
import com.codeapes.checklist.domain.execution.ExecutionStep;
import com.codeapes.checklist.domain.execution.StepExecutionStatus;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.template.Step;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.service.checklist.ExecutionChecklistService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ChecklistException;
import com.codeapes.checklist.util.constants.DateTimeConstants;

@Service(value = "executionChecklistService")
@Transactional
public class ExecutionChecklistServiceImpl implements ExecutionChecklistService {

    private static final AppLogger logger = new AppLogger(ExecutionChecklistServiceImpl.class); // NOSONAR
    
    @Autowired
    private PersistenceDAO persistenceDAO;
        
    @Override
    public ExecutionChecklist createExecutionChecklist(Checklist checklist, OwnerExecutor executor, String createdBy) {
        if (checklist == null) {
            throw new ChecklistException("Cannot create Execution Checklist - source Checklist is null.");
        }
        logger.debug("creating new execution checklist from checklist with object key: %s, name: %s", 
                checklist.getObjectKey(), checklist.getName());
        final ExecutionChecklist executionChecklist = createExecutionChecklist(checklist, executor);
        final List<ExecutionStep> executionSteps = createExecutionSteps(checklist.getSteps());
        executionChecklist.setSteps(executionSteps);
        return (ExecutionChecklist)persistenceDAO.saveObject(executionChecklist, createdBy);
    }
    
    private List<ExecutionStep> createExecutionSteps(List<Step> steps) {
        
        List<ExecutionStep> executionSteps = null;
        if (steps != null) {
            executionSteps = new ArrayList<ExecutionStep>();
            for (Step step : steps) {
                final ExecutionStep executionStep = createExecutionStep(step);
                final List<Step> preconditions = step.getPreConditions();
                if (preconditions != null) {
                    final List<ExecutionStep> executionPreconditions = createExecutionSteps(preconditions);
                    executionStep.setPreConditions(executionPreconditions);
                }
                executionSteps.add(executionStep);
            }
        }
        return executionSteps;
    }
    
    private ExecutionChecklist createExecutionChecklist(Checklist checklist, OwnerExecutor executor) {
        
        final ExecutionChecklist executionChecklist = new ExecutionChecklist();
        final Date currentTime = new Date();
        final String name = generateExecutionChecklistName(checklist.getName(), currentTime);
        executionChecklist.setChecklist(checklist);
        executionChecklist.setName(name);
        executionChecklist.setDescription(checklist.getDescription());
        executionChecklist.setExecutor(executor);
        executionChecklist.setStatus(ChecklistExecutionStatus.NOT_STARTED);
        return executionChecklist;
    }
    
    private ExecutionStep createExecutionStep(Step step) {
        
        final ExecutionStep executionStep = new ExecutionStep();
        executionStep.setName(step.getName());
        executionStep.setDescription(step.getDescription());
        executionStep.setExecutor(step.getDefaultExecutor());
        executionStep.setStatus(StepExecutionStatus.NOT_STARTED);
        executionStep.setExpectedDurationInMinutes(step.getExpectedDurationInMinutes());
        return executionStep;
    }
    
    private String generateExecutionChecklistName(String name, Date currentDate) {
        final StringBuilder nameSB = new StringBuilder(name);
        nameSB.append(" -");
        nameSB.append(new SimpleDateFormat(DateTimeConstants.ABBREVIATED_DATE_TIME_FORMAT).format(currentDate));
        return nameSB.toString();
    }
        
    @Override
    public ExecutionChecklist updateExecutionChecklist(ExecutionChecklist executionChecklist, String modifiedBy) {
        return (ExecutionChecklist)persistenceDAO.update(executionChecklist, modifiedBy);
    }
    
    @Override
    public void deleteExecutionChecklist(ExecutionChecklist executionChecklist, String deletedBy) {
        persistenceDAO.delete(executionChecklist, deletedBy);
    }
            
}
