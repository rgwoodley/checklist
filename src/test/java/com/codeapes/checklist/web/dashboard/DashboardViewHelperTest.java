package com.codeapes.checklist.web.dashboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class DashboardViewHelperTest {

    ///private static final Long OBJECT_KEY = new Long("1");
    //private static final String CHECKLIST_NAME = "NAME";
    //private static final String OWNER_EXECUTOR_NAME = "OWNER_EXECUTOR";
    //private static final int NUM_STEPS = 10;
    //private static final int EXPECTED_DURATION = Integer.parseInt("100");
    
    @Test
    public void testNullChecklist() {
        final DashboardRESTController controller = new DashboardRESTController();
        final DashboardChecklistDTO viewHelper = controller.createChecklistViewHelper(null);
        assertNotNull(viewHelper);
        assertAllFieldsNullOrEmpty(viewHelper);
    }
/*
    @Test
    public void testCreateChecklistViewHelper() {
        final Checklist checklist = createTestChecklist();
        final DashboardRESTController controller = new DashboardRESTController();
        final DashboardChecklistViewHelper viewHelper = controller.createChecklistViewHelper(checklist);
        assertEquals(CHECKLIST_NAME, viewHelper.getName());
        assertEquals(OBJECT_KEY, viewHelper.getObjectKey());
        assertEquals(OWNER_EXECUTOR_NAME, viewHelper.getOwnerName());
        assertEquals("100 min", viewHelper.getDuration());
        assertEquals(NUM_STEPS, viewHelper.getNumSteps());
    }
    
    @Test
    public void testCreateExecutionChecklistViewHelperJustCreated() {
        final ExecutionChecklist checklist = createTestExecutionChecklist();
        final ChecklistExecutionStatus status = ChecklistExecutionStatus.NOT_STARTED;
        checklist.setStatus(status);
        final DashboardRESTController controller = new DashboardRESTController();        
        final DashboardChecklistViewHelper viewHelper = controller.createExecutionChecklistViewHelper(checklist);
        assertEquals(CHECKLIST_NAME, viewHelper.getName());
        assertEquals(OBJECT_KEY, viewHelper.getObjectKey());
        assertEquals(WebConstants.CHECKMARK_GREEN_IMG, viewHelper.getStatusImageURL());
        assertEquals(checklist.getStatus().toString(), viewHelper.getStatus());
        final String dateString = DateFormatUtility.formatDate(checklist.getEstimatedCompletion());
        assertEquals(dateString, viewHelper.getEstimatedCompletionTime());
    }

    @Test
    public void testCreateExecutionChecklistViewHelperFailed() {
        testStatus(ChecklistExecutionStatus.FAILED, WebConstants.CHECKMARK_RED_IMG);
    }
    
    @Test
    public void testCreateExecutionChecklistViewHelperCancelled() {
        testStatus(ChecklistExecutionStatus.CANCELLED, WebConstants.CHECKMARK_RED_IMG);
    }
    
    @Test
    public void testCreateExecutionChecklistViewHelperPassed() {
        testStatus(ChecklistExecutionStatus.PASSED, WebConstants.CHECKMARK_GREEN_IMG);
    }
    
    @Test
    public void testCreateExecutionChecklistViewHelperInProgressLate() {
        testInProgress(EstimatedCompletionStatus.LATE, WebConstants.CHECKMARK_YELLOW_IMG);
    }
    
    @Test
    public void testCreateExecutionChecklistViewHelperInProgressOnTime() {
        testInProgress(EstimatedCompletionStatus.ON_TIME, WebConstants.CHECKMARK_GREEN_IMG);
    }
    
    @Test
    public void testCreateExecutionChecklistViewHelperInProgressPastThreshhold() {
        testInProgress(EstimatedCompletionStatus.PAST_THRESHOLD, WebConstants.CHECKMARK_RED_IMG);
    }
    
    private void testStatus(ChecklistExecutionStatus status, String imageURL) {
        final ExecutionChecklist checklist = createTestExecutionChecklist();
        checklist.setStatus(status);
        final DashboardRESTController controller = new DashboardRESTController();        
        final DashboardChecklistViewHelper viewHelper = controller.createExecutionChecklistViewHelper(checklist);
        assertEquals(imageURL, viewHelper.getStatusImageURL());
        assertEquals(checklist.getStatus().toString(), viewHelper.getStatus());
    }
    
    private void testInProgress(EstimatedCompletionStatus completionStatus, String imageURL) {
        final ExecutionChecklist checklist = createTestExecutionChecklist();
        final ChecklistExecutionStatus status = ChecklistExecutionStatus.IN_PROGRESS;
        checklist.setStatus(status);
        checklist.setCompletionStatus(completionStatus);
        final DashboardRESTController controller = new DashboardRESTController();        
        final DashboardChecklistViewHelper viewHelper = controller.createExecutionChecklistViewHelper(checklist);
        assertEquals(imageURL, viewHelper.getStatusImageURL());
        assertEquals(checklist.getStatus().toString(), viewHelper.getStatus());
    }
    
    private Checklist createTestChecklist(ChecklistType type) {
        final Checklist checklist = new Checklist(type);
        checklist.setObjectKey(OBJECT_KEY);
        checklist.setName(CHECKLIST_NAME);
        for (int i = 0; i < NUM_STEPS; i++) {
            final Step step = new Step();
            checklist.addStep(step);
        }
        final User owner = new User();
        owner.setName(OWNER_EXECUTOR_NAME);
        checklist.setOwner(owner);
        return checklist;
    }
    */

    private void assertAllFieldsNullOrEmpty(DashboardChecklistDTO viewHelper) {
        assertEquals(null, viewHelper.getCurrentStep());
        assertEquals(null, viewHelper.getDuration());
        assertEquals(null, viewHelper.getEstimatedCompletionTime());
        assertEquals(null, viewHelper.getName());
        assertEquals(null, viewHelper.getOwnerName());
        assertEquals(null, viewHelper.getStatus());
        assertEquals(null, viewHelper.getStatusImageURL());
        assertEquals(null, viewHelper.getObjectKey());
        assertEquals(0, viewHelper.getNumSteps());
    }
}
