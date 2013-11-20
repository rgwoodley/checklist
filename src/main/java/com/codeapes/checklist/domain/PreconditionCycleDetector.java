package com.codeapes.checklist.domain;

import java.util.List;

import com.codeapes.checklist.exception.PreconditionCycleException;
import com.codeapes.checklist.util.AppLogger;

public final class PreconditionCycleDetector {

    private static final AppLogger logger = new AppLogger(PreconditionCycleDetector.class); // NOSONAR
    private PreconditionCycleDetector() { }
    
    public static void checkPreconditionAddForCycle(Step step, Step stepBeingAdded) {
        logger.debug("Checking precondition add for cyclic dependency.");
        logger.debug("   Step: %s (%s), Precondition: %s (%s) ", step.getObjectKey(), step.getName(),
                stepBeingAdded.getObjectKey(), stepBeingAdded.getName());
        if (stepsAreEqual(step, stepBeingAdded)) {
            logger.debug("Steps are the same.  Obvious cyclic dependency.");
            throw new PreconditionCycleException("Error adding step %s (%s) as a precondition to step %s (%s).  "
                    + "These two steps are the same!", stepBeingAdded.getObjectKey(), stepBeingAdded.getName(), 
                    step.getObjectKey(), step.getName());
        }
        checkIfStepExistsInPreconditions(step, stepBeingAdded, step.getPreConditionsList());
        logger.debug("No cyclic dependency found.");
    }
    
    private static void checkIfStepExistsInPreconditions(Step step, Step stepBeingAdded, List<Step> preconditions) {
        if (preconditions != null && !preconditions.isEmpty()) {
            logger.debug("  Step has preconditions.  Checking them");
            for (Step precondition : preconditions) {
                logger.debug("  Checking Precondition: %s (%s) ", precondition.getObjectKey(), precondition.getName());
                if (stepsAreEqual(stepBeingAdded, precondition)) {
                    throw new PreconditionCycleException("Cannot add step %s (%s) as a precondition to step %s (%s).  "
                            + "This operation results in a cyclic dependency!", 
                            stepBeingAdded.getObjectKey(), stepBeingAdded.getName(), 
                            step.getObjectKey(), step.getName());
                }
                logger.debug("   Checking the preconditions of step: %s (%s) ", 
                        precondition.getObjectKey(), precondition.getName());
                checkIfStepExistsInPreconditions(step, stepBeingAdded, precondition.getPreConditionsList());
            }
        }
    }
    
    private static boolean stepsAreEqual(Step step1, Step step2) {
        return step1.equals(step2);
    }
}
