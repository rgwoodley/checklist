package com.codeapes.checklist.domain;

import java.util.List;

import com.codeapes.checklist.util.AppLogger;

public final class StepTimeCalculator {

    private static final AppLogger logger = new AppLogger(StepTimeCalculator.class); // NOSONAR
    
    private StepTimeCalculator() { }
    
    protected static void recalculatePreconditionTime(Step step) {
        logger.debug("recalculating precondition time for step %s (%s).", step.getObjectKey(), step.getName());
        final int preconditionTotalTime = step.getPreconditionTotalDuration();
        final int newPreconditionTotalTime = calculatePreconditionTotalTime(step.getPreConditionsList());
        if (preconditionTotalTime != newPreconditionTotalTime) {
            step.setPreconditionTotalDuration(calculatePreconditionTotalTime(step.getPreConditionsList()));
            if (step.getExecutionInfo() != null) {
                recalculatePreconditionTimeRemaining(step);
            }
            notifyDependencies(step);
        }
    }
    
    private static int calculatePreconditionTotalTime(List<Step> preConditions) {
        if (preConditions == null) {
            return 0;
        }
        int totalTime = 0;
        for (Step precondition : preConditions) {
            final int time = precondition.getExpectedDurationInMinutes() 
                    + precondition.getPreconditionTotalDuration();
            if (time > totalTime) {
                totalTime = time;
            }
        }
        return totalTime;
    }
    
    protected static void recalculatePreconditionTimeRemaining(Step step) {
        final StepExecutionInfo executionInfo = step.getExecutionInfo();
        executionInfo.setPreconditionTimeRemaining(calculatePreconditionTimeRemaining(step)); 
    }
    
    private static int calculatePreconditionTimeRemaining(Step step) {
        if (step.getPreConditionsList() == null) {
            return 0;
        }
        int timeRemaining = 0;
        for (Step precondition : step.getPreConditionsList()) {
            if (isRunningOrNotStarted(precondition)) {
                final int time = precondition.getExpectedDurationInMinutes() 
                        + precondition.getExecutionInfo().getPreconditionTimeRemaining();
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
    
    private static void notifyDependencies(Step step) {
        if (step.getDependencyOf() != null) {
            for (Step dependency : step.getDependencyOf()) {
                StepTimeCalculator.recalculatePreconditionTime(dependency);
            }
        } else {
            ChecklistTimeCalculator.recalculateTime(step.getChecklist());
        }
    }
}
