package com.codeapes.checklist.service.checklist.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ChecklistTotalTimeTest {

    private static final String FILE_PATH_ROOT = "com/codeapes/checklist/service/checklist/impl/total_time/";
    private StepFileLoader fileLoader = StepFileLoader.getInstance();

    @Test
    public void testNoSteps() {
        test("no_steps.data");
    }
    
    @Test
    public void testOneStep() {
        test("one_step.data");
    }
    
    @Test
    public void testFiveStepsNoPrecondition() {
        test("five_steps_no_precondition.data");
    }
    
    @Test
    public void testTwoStepsPrecondition() {
        test("two_steps_precondition.data");
    }
    
    @Test
    public void testSixStepsWherePreconditionDoesntMatter() {
        test("six_steps_long_one.data");
    }
    
    @Test
    public void testSixStepsWherePreconditionDoesMatter() {
        test("six_steps_long_one_precondition.data");
    }
    
    private void test(String filename) {
        final StepData stepData = fileLoader
                .loadStepFile(FILE_PATH_ROOT + filename);  
        
        final int expectedTotalTime = stepData.getExpectedTotalTime();
        final int calculatedTotalTime = stepData.getChecklist().getExpectedDurationInMinutes();
        assertEquals(expectedTotalTime, calculatedTotalTime);       
    }
}
