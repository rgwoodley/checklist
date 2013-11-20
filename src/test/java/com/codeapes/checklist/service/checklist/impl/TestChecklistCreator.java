package com.codeapes.checklist.service.checklist.impl;

import java.util.Random;

import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.ChecklistType;
import com.codeapes.checklist.domain.Step;
import com.codeapes.checklist.domain.user.User;

public final class TestChecklistCreator {

    private static final String CHECKLIST_EXECUTOR = "Test Executor";
    private static final String PASSWORD_STRING = "password";
    private static final Long CHECKLIST_1_KEY = Long.parseLong("3");
    private static final Long STEP_1_KEY = Long.parseLong("4");
    private static final int NUM_STEPS = 15;
    private static final int RANDOM_DURATION_MAX = 100;
    
    private TestChecklistCreator() { }
    
    protected static User createExecutor() {
        final User executor = new User();
        executor.setUsername(CHECKLIST_EXECUTOR);
        executor.setPassword(PASSWORD_STRING);
        return executor;
    }
        
    protected static Checklist createChecklistTemplate(User owner) {
        final Checklist checklist = new Checklist(ChecklistType.TEMPLATE);
        checklist.setName("Test Checklist");
        checklist.setObjectKey(CHECKLIST_1_KEY);
        checklist.setOwner(owner);
        return checklist;
    }
    
    private static Step createTemplateStep(Checklist checklist, User executor, long stepKey) {
        final Step step = new Step();
        step.setName("Test Step " + stepKey);
        step.setObjectKey(stepKey);
        step.setExecutor(executor);
        step.setExpectedDurationInMinutes(generateRandomDuration());
        checklist.addStep(step);
        step.setChecklist(checklist); 
        return step;
    }
    
    private static int generateRandomDuration() {
        return new Random().nextInt(RANDOM_DURATION_MAX);
    }
    
    protected static void createChecklistSteps(Checklist checklist, User executor) {
        long currentStepKey = STEP_1_KEY;
        for (int i = 0; i < NUM_STEPS; i++) {
            createTemplateStep(checklist, executor, currentStepKey++);
        }
    }
    
    protected static void addChecklistPreconditions(Checklist checklist) { 
        final Step step2 = checklist.getStepList().get(1);
        final Step step4 = checklist.getStepList().get(3);
        final Step step6 = checklist.getStepList().get(5);
        final Step step7 = checklist.getStepList().get(6);
        final Step step8 = checklist.getStepList().get(7);
        step6.addPreCondition(step4);
        step4.addPreCondition(step2);
        step8.addPreCondition(step7);
        step8.addPreCondition(step4);
    }
    
}
