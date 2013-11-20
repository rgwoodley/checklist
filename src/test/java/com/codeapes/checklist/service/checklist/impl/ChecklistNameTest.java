package com.codeapes.checklist.service.checklist.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

import com.codeapes.checklist.exception.ChecklistException;
import com.codeapes.checklist.util.constants.DateTimeConstants;
import com.ibm.icu.util.Calendar;

public class ChecklistNameTest {

    private static final int NAME_MAX_LENGTH = 30;
    private static final String NAME_STRING = "A";
    
    @Test (expected = ChecklistException.class)
    public void testNullName() {
        final ChecklistServiceImpl checklistService = new ChecklistServiceImpl();
        checklistService.generateExecutionChecklistName(null, new Date());
    }
    
    @Test (expected = ChecklistException.class)
    public void testNullCurrentDate() {
        final ChecklistServiceImpl checklistService = new ChecklistServiceImpl();
        checklistService.generateExecutionChecklistName(generateNameString(1), null);
    }
    
    @Test (expected = ChecklistException.class)
    public void testAllInputsNull() {
        final ChecklistServiceImpl checklistService = new ChecklistServiceImpl();
        checklistService.generateExecutionChecklistName(null, null);
    }
    
    @Test
    public void testEmptyStringName() {
        final ChecklistServiceImpl checklistService = new ChecklistServiceImpl();
        final Date theDate = generateDate();
        final String name = checklistService.generateExecutionChecklistName("", theDate);
        assertEquals(generateExpectedSuffix(theDate), name);
    }
    
    @Test
    public void testShortName() {
        final ChecklistServiceImpl checklistService = new ChecklistServiceImpl();
        final Date theDate = generateDate();
        final String inputName = generateNameString(NAME_MAX_LENGTH / 4);
        final String name = checklistService.generateExecutionChecklistName(inputName, theDate);
        assertEquals(inputName + generateExpectedSuffix(theDate), name);
    }
    
    @Test
    public void testMaxLengthName() {
        final ChecklistServiceImpl checklistService = new ChecklistServiceImpl();
        final Date theDate = generateDate();
        final String inputName = generateNameString(NAME_MAX_LENGTH);
        final String name = checklistService.generateExecutionChecklistName(inputName, theDate);
        assertEquals(NAME_MAX_LENGTH, name.length());
        assertTrue(name.endsWith(generateExpectedSuffix(theDate)));
        assertTrue(name.startsWith(NAME_STRING));
    }
    
    @Test
    public void testLengthThatRequiresSplit() {
        final ChecklistServiceImpl checklistService = new ChecklistServiceImpl();
        final Date theDate = generateDate();
        final String inputName = generateNameString(NAME_MAX_LENGTH - 3);
        final String name = checklistService.generateExecutionChecklistName(inputName, theDate);
        assertEquals(NAME_MAX_LENGTH, name.length());
        assertTrue(name.endsWith(generateExpectedSuffix(theDate)));
        assertTrue(name.startsWith(NAME_STRING));
    }
    
    private String generateNameString(int length) {
        final StringBuilder name = new StringBuilder();
        for (int i = 0; i < length; i++) {
            name.append(NAME_STRING);
        }
        return name.toString();
    }
    
    private Date generateDate() {
        final Calendar cal = Calendar.getInstance();
        cal.set(2011, 8, 9, 13, 5);
        return cal.getTime();
    }
    
    private String generateExpectedSuffix(Date date) {
        return " -" + DateFormatUtils.format(date, DateTimeConstants.ABBREVIATED_DATE_TIME_FORMAT);
    }
}
