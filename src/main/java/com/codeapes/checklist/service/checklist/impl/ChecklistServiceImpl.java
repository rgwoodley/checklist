package com.codeapes.checklist.service.checklist.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.dao.persistence.PersistenceDAO;
import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.ChecklistExecutionInfo;
import com.codeapes.checklist.domain.ChecklistGroup;
import com.codeapes.checklist.domain.ChecklistType;
import com.codeapes.checklist.domain.Step;
import com.codeapes.checklist.domain.StepExecutionInfo;
import com.codeapes.checklist.domain.StepStatus;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.exception.ChecklistException;
import com.codeapes.checklist.service.checklist.ChecklistService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.constants.DateTimeConstants;

@Service(value = "checklistService")
@Transactional
public class ChecklistServiceImpl implements ChecklistService {

    private static final AppLogger logger = new AppLogger(ChecklistServiceImpl.class); // NOSONAR

    @Autowired
    private PersistenceDAO persistenceDAO;

    @Override
    public Checklist createExecutableChecklist(Checklist checklist, OwnerExecutor executor, String createdBy) {
        validateChecklist(checklist, executor);
        logger.debug("creating new executable checklist from checklist with object key: %s, name: %s", 
                checklist.getObjectKey(), checklist.getName());
        final Checklist executableChecklist = cloneExecutableChecklist(checklist, executor, createdBy);
        final List<Step> executableSteps = createExecutionSteps(checklist, checklist.getStepList(), createdBy);
        if (executableSteps != null && !executableSteps.isEmpty()) {
            for (Step theStep : executableSteps) {
                executableChecklist.addStep(theStep);
            }
        }
        return executableChecklist;
    }
    
    private void validateChecklist(Checklist checklist, OwnerExecutor executor) {
        if (checklist == null) {
            throw new ChecklistException("Cannot create executable Checklist - source Checklist is null.");
        }
        if (executor == null) {
            throw new ChecklistException("Cannot create executable Checklist - executor is null.");
        }
        if (checklist.getStepList() == null || checklist.getStepList().isEmpty()) {
            throw new ChecklistException("Cannot create executable Checklist - checklist has no steps.  "
                    + "Checklist object key: %s", checklist.getObjectKey());
        }
    }
        
    private List<Step> createExecutionSteps(Checklist checklist, List<Step> steps, String createdBy) {
        List<Step> executionSteps = null;
        if (steps != null) {
            executionSteps = new ArrayList<Step>();
            for (Step step : steps) {
                final Step executableStep = createExecutableStep(checklist, step, createdBy);
                final List<Step> preconditions = step.getPreConditionsList();
                if (preconditions != null) {
                    final List<Step> executablePreconditions = 
                            createExecutionSteps(checklist, preconditions, createdBy);
                    for (Step preconditionStep : executablePreconditions) {
                        executableStep.addPreCondition(preconditionStep);
                    }
                }
                executionSteps.add(executableStep);
            }
        }
        return executionSteps;
    }
    
    private Checklist cloneExecutableChecklist(Checklist checklist, OwnerExecutor executor, String createdBy) {     
        final Checklist executableChecklist = new Checklist(ChecklistType.EXECUTABLE);
        final Date currentTime = new Date();
        final Timestamp currentTimestamp = new Timestamp(currentTime.getTime());
        final String name = generateExecutionChecklistName(checklist.getName(), currentTime);
        executableChecklist.setName(name);
        executableChecklist.setDescription(checklist.getDescription());
        executableChecklist.setGroup(checklist.getGroup());
        executableChecklist.setNumSteps(checklist.getNumSteps());
        executableChecklist.setOwner(checklist.getOwner());
        executableChecklist.setDescription(checklist.getDescription());
        executableChecklist.getExecutionInfo().setExecutor(executor);
        executableChecklist.setCreatedBy(createdBy);
        executableChecklist.setModifiedBy(createdBy);
        executableChecklist.setCreatedTimestamp(currentTimestamp);
        final ChecklistExecutionInfo executionInfo = executableChecklist.getExecutionInfo();
        executionInfo.setCreatedBy(createdBy);
        executionInfo.setModifiedBy(createdBy);
        executionInfo.setCreatedTimestamp(currentTimestamp);
        return executableChecklist;
    }
    
    private Step createExecutableStep(Checklist checklist, Step step, String createdBy) {      
        final Step executableStep = new Step();
        final Date currentTime = new Date();
        final Timestamp currentTimestamp = new Timestamp(currentTime.getTime());
        executableStep.setChecklist(checklist);
        executableStep.setName(step.getName());
        executableStep.setDescription(step.getDescription());
        executableStep.setExecutor(step.getExecutor());
        executableStep.setStatus(StepStatus.NOT_STARTED);
        executableStep.setExpectedDurationInMinutes(step.getExpectedDurationInMinutes());
        executableStep.setExecutionInfo(new StepExecutionInfo());
        executableStep.getExecutionInfo().setStep(executableStep);
        executableStep.setCreatedBy(createdBy);
        executableStep.setModifiedBy(createdBy);
        executableStep.setCreatedTimestamp(currentTimestamp);
        final StepExecutionInfo executionInfo = executableStep.getExecutionInfo();
        executionInfo.setCreatedBy(createdBy);
        executionInfo.setModifiedBy(createdBy);
        executionInfo.setCreatedTimestamp(currentTimestamp);
        return executableStep;
    }
    
    protected String generateExecutionChecklistName(String name, Date currentDate) {
        if (name == null || currentDate == null) {
            throw new ChecklistException("Could not generate executable checklist "
                    + "name because source checklist name or current date is null.");
        }
        final String nameSuffix = new SimpleDateFormat(DateTimeConstants.ABBREVIATED_DATE_TIME_FORMAT)
            .format(currentDate);
        String namePrefix = name;
        final int len = nameSuffix.length();
        if (name.length() > len) {
            namePrefix = name.substring(0, len);
        }
        final StringBuilder nameSB = new StringBuilder(namePrefix);
        nameSB.append(" -");
        nameSB.append(nameSuffix);
        return nameSB.toString();
    }
  
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Checklist saveOrUpdateChecklist(Checklist checklist, String modifiedBy) {
        return (Checklist) persistenceDAO.saveOrUpdate(checklist, modifiedBy);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ChecklistGroup saveOrUpdateChecklistGroup(ChecklistGroup checklistGroup, String modifiedBy) {
        return (ChecklistGroup) persistenceDAO.saveOrUpdate(checklistGroup, modifiedBy);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Checklist updateChecklist(Checklist checklist, String modifiedBy) {
        return (Checklist)persistenceDAO.update(checklist, modifiedBy);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Checklist saveChecklist(Checklist checklist, String createdBy) {
        return (Checklist)persistenceDAO.saveObject(checklist, createdBy);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteChecklist(Checklist checklist, String deletedBy) {
        persistenceDAO.delete(checklist, deletedBy);
    }
    
}
