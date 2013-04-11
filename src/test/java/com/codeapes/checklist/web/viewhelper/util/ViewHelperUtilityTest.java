package com.codeapes.checklist.web.viewhelper.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.codeapes.checklist.test.viewhelper.TestObject;
import com.codeapes.checklist.test.viewhelper.TestViewHelper;
import com.codeapes.checklist.web.viewhelper.ViewHelperUtility;

public class ViewHelperUtilityTest {

    private static final String NAME_VALUE = "name";

    @Test
    public void testListConvert() {
        final List<TestObject> objectList = createTestObjects();
        @SuppressWarnings("unchecked")
        final List<TestViewHelper> viewHelpers = (List<TestViewHelper>) ViewHelperUtility.convertList(objectList,
            TestViewHelper.class);
        assertNotNull(viewHelpers);
        assertEquals(100, viewHelpers.size());
        for (int i = 0; i < 100; i++) {
            final String name = viewHelpers.get(i).getName();
            final int age = viewHelpers.get(i).getAge();
            assertEquals(NAME_VALUE + i, name);
            assertEquals(i, age);
        }
    }
    
    @Test
    public void testNullList() {
        final List<TestObject> objectList = null;
        @SuppressWarnings("unchecked")
        final List<TestViewHelper> viewHelpers = (List<TestViewHelper>) ViewHelperUtility.convertList(objectList,
            TestViewHelper.class);
        assertNotNull(viewHelpers);
        assertEquals(0, viewHelpers.size());
    }
    
    @Test
    public void testEmptyList() {
        final List<TestObject> objectList = new ArrayList<TestObject>();
        @SuppressWarnings("unchecked")
        final List<TestViewHelper> viewHelpers = (List<TestViewHelper>) ViewHelperUtility.convertList(objectList,
            TestViewHelper.class);
        assertNotNull(viewHelpers);
        assertEquals(0, viewHelpers.size());
    }

    private List<TestObject> createTestObjects() {
        final List<TestObject> objectList = new ArrayList<TestObject>();
        for (int i = 0; i < 100; i++) {
            final TestObject object = new TestObject();
            object.setName(NAME_VALUE + i);
            object.setAge(i);
            objectList.add(object);
        }
        return objectList;
    }
}
