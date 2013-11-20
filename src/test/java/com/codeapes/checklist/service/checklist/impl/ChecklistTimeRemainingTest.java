package com.codeapes.checklist.service.checklist.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ChecklistTimeRemainingTest {

    private static final String FILE_PATH_ROOT = "com/codeapes/checklist/service/checklist/impl/time_remaining/";
    private StepFileLoader fileLoader = StepFileLoader.getInstance();

    @Test
    public void testNoSteps() {
        test("no_steps.data");
    }
    
    @Test
    public void testOneStepComplete() {
        test("one_step_complete.data");
    }
    
    @Test
    public void testOneStepNotStarted() {
        test("one_step_not_started.data");
    }
    
    @Test
    public void testManyStepsNoPreconditionComplete() {
        test("many_steps_no_precondition_complete.data");
    }
    
    @Test
    public void testManyStepsNoPreconditionNotStarted() {
        test("many_steps_no_precondition_not_started.data");
    }
    
    @Test
    public void testManyStepsNoPreconditionMix() {
        test("many_steps_no_precondition_mix.data");
    }
    
    @Test
    public void testManyStepsPreconditionComplete() {
        test("many_steps_precondition_complete.data");
    }
        
    private void test(String filename) {
        final StepData stepData = fileLoader
                .loadStepFile(FILE_PATH_ROOT + filename);  
        
        final int expectedTimeLeft = stepData.getExpectedTimeRemaining();
        final int calculatedTimeLeft = stepData.getChecklist()
                .getExecutionInfo()
                .getMinutesLeft();                
        assertEquals(expectedTimeLeft, calculatedTimeLeft);
    }

}
