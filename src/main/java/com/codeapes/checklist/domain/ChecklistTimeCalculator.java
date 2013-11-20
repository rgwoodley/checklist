package com.codeapes.checklist.domain;

import java.util.List;

import com.codeapes.checklist.util.AppLogger;

public final class ChecklistTimeCalculator {

    private static final AppLogger logger = new AppLogger(ChecklistTimeCalculator.class); // NOSONAR
    
    private ChecklistTimeCalculator() { }
    
    public static void recalculateTime(Checklist checklist) {
        logger.debug("Recalculating expected duration for checklist key %s (%s)", 
                checklist.getObjectKey(), checklist.getName());
        checklist.setExpectedDurationInMinutes(calculateExpectedDuration(checklist.getStepList()));
        final ChecklistExecutionInfo executionInfo = checklist.getExecutionInfo();
        if (executionInfo != null) {
            logger.debug("Recalculating time left for checklist key %s (%s)", 
                    checklist.getObjectKey(), checklist.getName());
            recalculateMinLeft(checklist);      
        }
    }
    
    private static int calculateExpectedDuration(List<Step> steps) {
        if (steps == null) {
            return 0;
        }
        int duration = 0;
        for (Step step : steps) {
            final int time = step.getExpectedDurationInMinutes() 
                        + step.getPreconditionTotalDuration();
            if (time > duration) {
                duration = time;
            }
        }
        return duration;
    }
    
    public static void recalculateMinLeft(Checklist checklist) {
        checklist.getExecutionInfo().setMinutesLeft(calculateTimeRemaining(checklist));
    }
    
    private static int calculateTimeRemaining(Checklist checklist) {
        if (checklist.getStepList() == null) {
            return 0;
        }
        int timeRemaining = 0;
        for (Step step : checklist.getStepList()) {
            if (isRunningOrNotStarted(step)) {
                final int time = step.getExpectedDurationInMinutes() 
                            + step.getExecutionInfo().getPreconditionTimeRemaining();
                if (time > timeRemaining) {
                    timeRemaining = time;
                }
            }
        }
        return timeRemaining;
    }
    
    private static boolean isRunningOrNotStarted(Step step) {
        return StepStatus.IN_PROGRESS == step.getStatus()
                || StepStatus.NOT_STARTED == step.getStatus();
    }
}
