package com.codeapes.checklist.domain.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.ChecklistType;
import com.codeapes.checklist.domain.Step;

public class ChecklistTest {

    private static final int NUM_STEPS = 5;

    @Test
    public void testAddNullStepList() {
        final Checklist checklist = new Checklist(null);
        //checklist.setSteps(null);
        assertEquals(0, checklist.getNumSteps());
    }

    @Test
    public void testAddStepList() {
        final Checklist checklist = new Checklist(ChecklistType.TEMPLATE);
        for (int i = 0; i < NUM_STEPS; i++) {
            checklist.addStep(new Step());
        }
        assertEquals(NUM_STEPS, checklist.getNumSteps());
    }

    @Test
    public void testAddNullStep() {
        final Checklist checklist = new Checklist(ChecklistType.TEMPLATE);
        checklist.addStep(null);
        assertEquals(0, checklist.getNumSteps());
    }
   
    @Test
    public void testAddStepToNullList() {
        final Checklist checklist = new Checklist(ChecklistType.TEMPLATE);
        assertNull(checklist.getStepList());
        assertEquals(0, checklist.getNumSteps());
        final Step step = new Step();
        checklist.addStep(step);
        assertEquals(1, checklist.getNumSteps());
    }
}
