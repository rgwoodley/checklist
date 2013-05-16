package com.codeapes.checklist.web.viewhelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.codeapes.checklist.test.viewhelper.FourthTestObject;
import com.codeapes.checklist.test.viewhelper.SecondTestObject;
import com.codeapes.checklist.test.viewhelper.SecondTestViewHelper;
import com.codeapes.checklist.test.viewhelper.TestObject;
import com.codeapes.checklist.test.viewhelper.TestViewHelper;
import com.codeapes.checklist.test.viewhelper.ThirdTestObject;
import com.codeapes.checklist.util.ChecklistException;

public class AbstractViewHelperTest {

    private static final int AGE_VALUE = 21;
    private static final String NAME_VALUE = "The Name";
    private static final Long KEY_VALUE = new Long("1");
    private static final String COMMENT_VALUE = "A Comment";
    private static final String NOT_IN_VIEW_HELPER_VALUE = "Not in VH";
    private static final String FOURTH_OBJECT_NAME = "I am the fourth!";

    private static final int AGE_VALUE_2 = 22;
    private static final String NAME_VALUE_2 = "The Name 2";
    private static final Long KEY_VALUE_2 = new Long("2");
    private static final String NOT_IN_VIEW_HELPER_VALUE_2 = "Not in VH 2";

    @Test(expected = ChecklistException.class)
    public void testInputObjectNull() {
        final TestViewHelper testViewHelper = new TestViewHelper(TestObject.class);
        testViewHelper.populate(null);
    }
    
    @Test(expected = ChecklistException.class)
    public void testSourceTypeNull() {
        final TestViewHelper testViewHelper = new TestViewHelper(null);
        testViewHelper.populate(null);
    }
    
    @Test(expected = ChecklistException.class)
    public void testTypeMismatch() {
        final TestObject testObject = createAndPopulateTestObject();
        final TestViewHelper testViewHelper = new TestViewHelper(String.class);
        testViewHelper.populate(testObject);
    }

    @Test
    public void testFullMapping() {
        final TestObject testObject = createAndPopulateTestObject();
        final TestViewHelper testViewHelper = createAndPopulateTestviewHelper();
        testViewHelper.populate(testObject);
        assertEquals(AGE_VALUE, testViewHelper.getAge());
        assertEquals(NAME_VALUE, testViewHelper.getName());
        assertEquals(KEY_VALUE, testViewHelper.getObjectKey());
        assertNull(testViewHelper.getAnObject());
    }

    @Test
    public void testMappingWithIgnore() {
        final TestObject testObject = createAndPopulateTestObject();
        final TestViewHelper testViewHelper = createAndPopulateTestviewHelper();
        testViewHelper.populate(testObject);
        assertNull(testViewHelper.getTestData());
    }

    @Test
    public void testTwoMappings() {
        final TestObject testObject = createAndPopulateTestObject();
        final TestViewHelper testViewHelper = createAndPopulateTestviewHelper();
        final SecondTestObject testObject2 = createAndPopulateSecondTestObject();
        final SecondTestViewHelper testViewHelper2 = createAndPopulateSecondTestviewHelper();
        testViewHelper2.populate(testObject2);
        assertEquals(AGE_VALUE_2, testViewHelper2.getAge());
        assertEquals(NAME_VALUE_2, testViewHelper2.getName());
        assertEquals(KEY_VALUE_2, testViewHelper2.getObjectKey());
        assertNull(testViewHelper2.getAnObject());
        testViewHelper.populate(testObject);
        assertEquals(AGE_VALUE, testViewHelper.getAge());
        assertEquals(NAME_VALUE, testViewHelper.getName());
        assertEquals(KEY_VALUE, testViewHelper.getObjectKey());
        assertNull(testViewHelper.getAnObject());
    }
    
    @Test
    public void testDifferentSourceName() {
        final TestObject testObject = createAndPopulateTestObject();
        final TestViewHelper testViewHelper = createAndPopulateTestviewHelper();
        testViewHelper.populate(testObject);
        assertEquals(COMMENT_VALUE, testViewHelper.getTheComment());
    }
    
    @Test
    public void testNestedObjects() {
        final TestObject testObject = createAndPopulateTestObject();
        final ThirdTestObject third = new ThirdTestObject();
        final FourthTestObject fourth = new FourthTestObject();
        fourth.setName(FOURTH_OBJECT_NAME);
        third.setFourthTestObject(fourth);
        testObject.setThirdTestObject(third);
        final TestViewHelper testViewHelper = createAndPopulateTestviewHelper();
        testViewHelper.populate(testObject);
        assertEquals(FOURTH_OBJECT_NAME, testViewHelper.getFourthName());
    }
    
    @Test
    public void testPropertyDoesNotExistInSource() {
        final TestObject testObject = createAndPopulateTestObject();
        final TestViewHelper testViewHelper = createAndPopulateTestviewHelper();
        testViewHelper.populate(testObject);
        assertNull(testViewHelper.getMappedButDoesNotExistInTarget());
    }

    private TestObject createAndPopulateTestObject() {
        final TestObject testObject = new TestObject();
        testObject.setName(NAME_VALUE);
        testObject.setAge(AGE_VALUE);
        testObject.setAnObject(new Object());
        testObject.setNotInViewHelper(NOT_IN_VIEW_HELPER_VALUE);
        testObject.setObjectKey(KEY_VALUE);
        testObject.setaComment(COMMENT_VALUE);
        return testObject;
    }

    private TestViewHelper createAndPopulateTestviewHelper() {
        final TestViewHelper testViewHelper = new TestViewHelper(TestObject.class);
        return testViewHelper;
    }

    private SecondTestObject createAndPopulateSecondTestObject() {
        final SecondTestObject testObject = new SecondTestObject();
        testObject.setName(NAME_VALUE_2);
        testObject.setAge(AGE_VALUE_2);
        testObject.setAnObject(new Object());
        testObject.setNotInViewHelper(NOT_IN_VIEW_HELPER_VALUE_2);
        testObject.setObjectKey(KEY_VALUE_2);
        return testObject;
    }

    private SecondTestViewHelper createAndPopulateSecondTestviewHelper() {
        final SecondTestViewHelper testViewHelper = new SecondTestViewHelper(
            SecondTestObject.class);
        return testViewHelper;
    }
}
