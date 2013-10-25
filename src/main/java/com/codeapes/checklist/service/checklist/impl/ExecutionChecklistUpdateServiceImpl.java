package com.codeapes.checklist.service.checklist.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.codeapes.checklist.domain.execution.ExecutionChecklist;
import com.codeapes.checklist.domain.execution.ExecutionStep;
import com.codeapes.checklist.domain.execution.StepExecutionStatus;
import com.codeapes.checklist.service.checklist.ExecutionChecklistUpdateService;
import com.codeapes.checklist.util.constants.DateTimeConstants;

public class ExecutionChecklistUpdateServiceImpl implements ExecutionChecklistUpdateService {
    
    @Override
    public long updateCompletionTimeEstimate(ExecutionChecklist executionChecklist) {
        long timeLeft = 0;
        if (executionChecklist != null) {

            timeLeft = getTimeRemaining(executionChecklist.getSteps());
        }
        return timeLeft;
    }

    private long getTimeRemaining(List<ExecutionStep> steps) {
        final List<Long> remainingTimePerStep = new ArrayList<Long>();
        long timeLeft = 0;
        if (steps != null) {
            for (ExecutionStep step : steps) {
                timeLeft = getTimeLeftForStep(step);
                remainingTimePerStep.add(timeLeft);
            }
            Collections.sort(remainingTimePerStep);
            timeLeft = remainingTimePerStep.get(remainingTimePerStep.size() - 1);
        }
        return timeLeft;
    }
    
    private long getTimeLeftForStep(ExecutionStep step) {
        long timeLeft = 0;
        final List<ExecutionStep> preconditions = step.getPreConditions();
        if (preconditions != null) {
            timeLeft = getTimeRemaining(preconditions);
        }
        if (StepExecutionStatus.IN_PROGRESS == step.getStatus()) {
            final long startTimeMilliseconds = step.getExecutionStart().getTime();
            final long currentTimeMilliseconds = Calendar.getInstance().getTimeInMillis();
            final long elapsedTimeMilliseconds = currentTimeMilliseconds - startTimeMilliseconds;
            final long elapsedTimeSeconds = elapsedTimeMilliseconds / DateTimeConstants.MILLISECONDS_IN_SECOND;
            final long expectedDuration = step.getExpectedDurationInMinutes();
            timeLeft += expectedDuration - elapsedTimeSeconds;

        } else if (StepExecutionStatus.NOT_STARTED == step.getStatus()) {
            timeLeft += step.getExpectedDurationInMinutes();
        }
        return timeLeft;
    }

    
}
