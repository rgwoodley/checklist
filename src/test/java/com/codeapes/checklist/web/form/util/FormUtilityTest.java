package com.codeapes.checklist.web.form.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.codeapes.checklist.util.ChecklistException;
import com.codeapes.checklist.web.form.TestForm;
import com.codeapes.checklist.web.form.TestObject;

public class FormUtilityTest {
    
    private static final String FORM_NAME_VALUE = " form name";
    private static final int FORM_AGE_VALUE = 17;
    private static final Long FORM_OBJECT_KEY_VALUE = Long.parseLong("11");
    private static final String FORM_NOT_IN_TARGET_VALUE = "not in target";
    private static final String NAME_VALUE = "name";
    private static final int AGE_VALUE = 7;
    private static final Long OBJECT_KEY_VALUE = Long.parseLong("10");
    private static final String NOT_IN_FORM_VALUE = "not in form";

    @Test(expected = ChecklistException.class)
    public void testNewInstanceNullForm() {
        FormUtility.copyStateToNewInstance(null, TestObject.class);
    }
    
    @Test(expected = ChecklistException.class)
    public void testNewInstanceNullTargetType() {
        FormUtility.copyStateToNewInstance(new TestForm(), null);
    }
    
    @Test(expected = ChecklistException.class)
    public void testCopyInstanceNullForm() {
        FormUtility.copyStateToTargetInstance(null, new TestObject());
    }
    
    @Test(expected = ChecklistException.class)
    public void testCopyInstanceNullTargetType() {
        FormUtility.copyStateToTargetInstance(new TestForm(), null);
    }
    
    @Test
    public void testNewInstance() {
        final TestForm form = createAndPopulateTestForm();
        final TestObject testObject = (TestObject)FormUtility.copyStateToNewInstance(form, TestObject.class);
        assertNotNull(testObject);
        assertEquals(FORM_OBJECT_KEY_VALUE, testObject.getObjectKey());
        assertEquals(FORM_AGE_VALUE, testObject.getAge());
        assertEquals(FORM_NAME_VALUE, testObject.getName());
        assertNull(testObject.getNotInForm());        
    }
    
    @Test
    public void testCopyToTarget() {
        final TestForm form = createAndPopulateTestForm();
        TestObject testObject = createAndPopulateTestObject();
        testObject = (TestObject)FormUtility.copyStateToTargetInstance(form, testObject);
        assertEquals(FORM_OBJECT_KEY_VALUE, testObject.getObjectKey());
        assertEquals(FORM_AGE_VALUE, testObject.getAge());
        assertEquals(FORM_NAME_VALUE, testObject.getName());
        assertEquals(NOT_IN_FORM_VALUE, testObject.getNotInForm());      
    }
    
    private TestForm createAndPopulateTestForm() {
        final TestForm form = new TestForm();
        form.setName(FORM_NAME_VALUE);
        form.setObjectKey(FORM_OBJECT_KEY_VALUE);
        form.setAge(FORM_AGE_VALUE);
        form.setNotInTarget(FORM_NOT_IN_TARGET_VALUE);
        return form;
    }
    
    private TestObject createAndPopulateTestObject() {
        final TestObject testObject = new TestObject();
        testObject.setName(NAME_VALUE);
        testObject.setObjectKey(OBJECT_KEY_VALUE);
        testObject.setAge(AGE_VALUE);
        testObject.setNotInForm(NOT_IN_FORM_VALUE);
        return testObject;
    }
    
}
