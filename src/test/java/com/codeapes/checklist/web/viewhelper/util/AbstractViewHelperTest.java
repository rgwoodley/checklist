package com.codeapes.checklist.web.viewhelper.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.codeapes.checklist.test.viewhelper.SecondTestObject;
import com.codeapes.checklist.test.viewhelper.SecondTestViewHelper;
import com.codeapes.checklist.test.viewhelper.TestObject;
import com.codeapes.checklist.test.viewhelper.TestViewHelper;
import com.codeapes.checklist.util.ChecklistException;

public class AbstractViewHelperTest {

    private static final int AGE_VALUE = 21;
    private static final String NAME_VALUE = "The Name";
    private static final Long KEY_VALUE = new Long("1");
    
    private static final int AGE_VALUE_2 = 22;
    private static final String NAME_VALUE_2 = "The Name 2";
    private static final Long KEY_VALUE_2 = new Long("2");
    
    @Test(expected = ChecklistException.class)
    public void testDerivedClassNotSet() {
        final TestObject testObject = new TestObject();
        final TestViewHelper testViewHelper = new TestViewHelper();
        testViewHelper.populate(testObject);
    }
    
    @Test(expected = ChecklistException.class)
    public void testInputObjectNull() {
        final TestViewHelper testViewHelper = new TestViewHelper();
        testViewHelper.setClassDerivedFrom(TestObject.class);
        testViewHelper.populate(null);
    }
    
    @Test(expected = ChecklistException.class)
    public void testInputObjectAndType() {
        final TestObject testObject = new TestObject();
        final TestViewHelper testViewHelper = new TestViewHelper();
        testViewHelper.setClassDerivedFrom(TestViewHelper.class);
        testViewHelper.populate(testObject);
    }
    
    @Test
    public void testFullMapping() {
        final TestObject testObject = createAndPopulateTestObject();
        final TestViewHelper testViewHelper = createAndPopulateTestviewHelper();
        testViewHelper.setClassDerivedFrom(TestObject.class);
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
        testViewHelper.setClassDerivedFrom(TestObject.class);
        final Set<String> propertiesToExclude = new HashSet<String>();
        propertiesToExclude.add("name");
        testViewHelper.setPropertiesToExclude(propertiesToExclude);
        testViewHelper.populate(testObject);
        assertEquals(AGE_VALUE, testViewHelper.getAge());
        assertEquals(KEY_VALUE, testViewHelper.getObjectKey());
        assertNull(testViewHelper.getAnObject());
        assertNull(testViewHelper.getExtra());
        assertNull(testViewHelper.getName());
    }
    
    @Test
    public void testTwoMappings() {
        final TestObject testObject = createAndPopulateTestObject();
        final TestViewHelper testViewHelper = createAndPopulateTestviewHelper();
        testViewHelper.setClassDerivedFrom(TestObject.class);
        final SecondTestObject testObject2 = createAndPopulateSecondTestObject();
        final SecondTestViewHelper testViewHelper2 = createAndPopulateSecondTestviewHelper();
        testViewHelper2.setClassDerivedFrom(SecondTestObject.class);
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
    
    
    private TestObject createAndPopulateTestObject() {
        final TestObject testObject = new TestObject();
        testObject.setName(NAME_VALUE);
        testObject.setAge(AGE_VALUE);
        testObject.setAnObject(new Object());
        testObject.setNotInViewHelper("not in vh");
        testObject.setObjectKey(KEY_VALUE);
        return testObject;
    }
    
    private TestViewHelper createAndPopulateTestviewHelper() {
        final TestViewHelper testViewHelper = new TestViewHelper();
        return testViewHelper;
    }
    
    private SecondTestObject createAndPopulateSecondTestObject() {
        final SecondTestObject testObject = new SecondTestObject();
        testObject.setName(NAME_VALUE_2);
        testObject.setAge(AGE_VALUE_2);
        testObject.setAnObject(new Object());
        testObject.setNotInViewHelper("not in vh 2");
        testObject.setObjectKey(KEY_VALUE_2);
        return testObject;
    }
    
    private SecondTestViewHelper createAndPopulateSecondTestviewHelper() {
        final SecondTestViewHelper testViewHelper = new SecondTestViewHelper();
        return testViewHelper;
    }
}
