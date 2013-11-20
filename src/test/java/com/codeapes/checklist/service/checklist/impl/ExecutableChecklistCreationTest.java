package com.codeapes.checklist.service.checklist.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.ChecklistStatus;
import com.codeapes.checklist.domain.ChecklistType;
import com.codeapes.checklist.domain.Step;
import com.codeapes.checklist.domain.StepStatus;
import com.codeapes.checklist.domain.user.User;
import com.codeapes.checklist.exception.ChecklistException;
import com.codeapes.checklist.service.checklist.ChecklistService;

public class ExecutableChecklistCreationTest {

    private static final String CREATED_BY = "test";
    
    @Test (expected = ChecklistException.class)
    public void testExecutionOfNullChecklist() {
        final ChecklistService checklistService = new ChecklistServiceImpl();
        checklistService.createExecutableChecklist(null, TestChecklistCreator.createExecutor(), CREATED_BY);
    }
    
    @Test (expected = ChecklistException.class)
    public void testExecutionOfNullExecutor() {
        final ChecklistService checklistService = new ChecklistServiceImpl();
        checklistService.createExecutableChecklist(TestChecklistCreator
                .createChecklistTemplate(TestChecklistCreator.createExecutor()), null, CREATED_BY);
    }
    
    @Test (expected = ChecklistException.class)
    public void testExecutionNoName() {
        final ChecklistService checklistService = new ChecklistServiceImpl();
        final Checklist checklist = new Checklist(ChecklistType.TEMPLATE);
        checklistService.createExecutableChecklist(checklist, 
                TestChecklistCreator.createExecutor(), CREATED_BY);
    }
    
    @Test (expected = ChecklistException.class)
    public void testCreationNoSteps() {
        final User user = TestChecklistCreator.createExecutor();
        final ChecklistService checklistService = new ChecklistServiceImpl();
        final Checklist checklist = TestChecklistCreator.createChecklistTemplate(user);
        checklistService.createExecutableChecklist(checklist, user, CREATED_BY);
    }
    
    @Test
    public void testCreationNoPreconditions() {
        final User user = TestChecklistCreator.createExecutor();
        final ChecklistService checklistService = new ChecklistServiceImpl();
        final Checklist checklist = TestChecklistCreator.createChecklistTemplate(user);
        TestChecklistCreator.createChecklistSteps(checklist, user);
        final Checklist executableChecklist = checklistService
                .createExecutableChecklist(checklist, user, CREATED_BY);
        validateExecutableChecklistStructure(checklist, executableChecklist);
        validateSteps(checklist.getStepList(), executableChecklist.getStepList());
    }
    
    @Test
    public void testCreationPreconditions() {
        final User user = TestChecklistCreator.createExecutor();
        final ChecklistService checklistService = new ChecklistServiceImpl();
        final Checklist checklist = TestChecklistCreator.createChecklistTemplate(user);
        TestChecklistCreator.createChecklistSteps(checklist, user);
        TestChecklistCreator.addChecklistPreconditions(checklist);
        final Checklist executableChecklist = checklistService
                .createExecutableChecklist(checklist, user, CREATED_BY);
        validateExecutableChecklistStructure(checklist, executableChecklist);
        validateSteps(checklist.getStepList(), executableChecklist.getStepList());
    }
        
    private void validateExecutableChecklistStructure(Checklist template, Checklist executable) {
        assertTrue(executable.getName().startsWith(template.getName()));
        assertNull(template.getExecutionInfo());
        assertNotNull(template.getStepList());
        assertEquals(template.getNumSteps(), executable.getNumSteps());
        assertEquals(template.getStepList().size(), executable.getStepList().size());
        assertEquals(ChecklistType.EXECUTABLE, executable.getType());
        assertEquals(template.getGroup(), executable.getGroup());
        assertNotNull(executable.getExecutionInfo());
        final int minutesLeft = template.getExpectedDurationInMinutes();
        assertEquals(minutesLeft, executable.getExpectedDurationInMinutes());
        assertEquals(minutesLeft, executable.getExecutionInfo().getMinutesLeft());
        assertNull(executable.getExecutionInfo().getExecutionStart());
        assertNull(executable.getExecutionInfo().getExecutionEnd());
        assertEquals(TestChecklistCreator.createExecutor(), executable.getExecutionInfo().getExecutor());
        assertEquals(ChecklistStatus.NOT_STARTED, executable.getStatus());
    }
    
    private void validateSteps(List<Step> templateSteps, List<Step> executableSteps) {
        for (int i = 0; i < templateSteps.size(); i++) {
            final Step templateStep = templateSteps.get(i);
            final Step executableStep = executableSteps.get(i);
            assertNull(templateStep.getExecutionInfo());
            assertEquals(templateStep.getName(), executableStep.getName());
            if (templateStep.getPreConditionsList() != null) {
                assertEquals(templateStep.getPreConditionsList().size(), executableStep.getPreConditionsList().size());
                assertEquals(templateStep.getPreconditionTotalDuration(), 
                        executableStep.getPreconditionTotalDuration());
                assertEquals(templateStep.getPreconditionTotalDuration(), 
                        executableStep.getExecutionInfo().getPreconditionTimeRemaining());
            }
            assertEquals(templateStep.getExecutor(), executableStep.getExecutor());
            assertEquals(templateStep.getExpectedDurationInMinutes(), executableStep.getExpectedDurationInMinutes());
            assertEquals(StepStatus.NOT_STARTED, executableStep.getStatus());
            assertNull(templateStep.getExecutionInfo());
            assertNull(executableStep.getExecutionInfo().getExecutionStart());
            assertNull(executableStep.getExecutionInfo().getExecutionEnd());
        }
    }
}
