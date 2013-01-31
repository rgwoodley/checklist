package com.codeapes.checklist.web.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.codeapes.checklist.test.util.TestConfiguration;
import com.codeapes.checklist.util.ChecklistException;
import com.codeapes.checklist.web.util.viewhelper.ViewHelper;
import com.codeapes.checklist.web.util.viewhelper.ViewHelperMapper;

public class ViewHelperMapperTest {

    private static final String TEST_MAPPING_NAME = "testMapping";
    private static final String[] attributes = { "name", "description", "anInteger", "listOfStrings" };
    private static final String DEFAULT_NAME = "Name";
    private static final String DEFAULT_DESCRIPTION = "Description";
    private static final int DEFAULT_INTEGER = 10;
    private static final List<String> DEFAULT_STRING_LIST = new ArrayList<String>();

    private ViewHelperMapper viewHelperMapper;

    static {
        for (int i = 0; i < 10; i++) {
            DEFAULT_STRING_LIST.add("item" + i);
        }
    }

    @Before
    public void initializeSpringContext() {
        final ApplicationContext appContext = TestConfiguration.getInstance().getApplicationContext();
        viewHelperMapper = (ViewHelperMapper) appContext.getBean("viewHelperMapper");
    }

    @Test(expected = ChecklistException.class)
    public void testNoMappingDefined() {
        viewHelperMapper.convertToViewMapper(TEST_MAPPING_NAME, null);
    }

    @Test
    public void testValidMapping() {
        final TestClass testObject = createInputObject();
        viewHelperMapper.addMapping(TEST_MAPPING_NAME, TestClass.class, TestViewHelper.class, attributes);
        final ViewHelper<TestClass> viewHelper = viewHelperMapper.convertToViewMapper(TEST_MAPPING_NAME, testObject);
        assertNotNull(viewHelper);
        assertTrue(viewHelper instanceof TestViewHelper);
        final TestViewHelper testViewHelper = (TestViewHelper) viewHelper;
        assertEquals(DEFAULT_NAME, testObject.getName());
        assertEquals(testObject.getName(), testViewHelper.getName());
        assertEquals(DEFAULT_DESCRIPTION, testObject.getDescription());
        assertEquals(testObject.getDescription(), testViewHelper.getDescription());
        assertEquals(DEFAULT_INTEGER, testObject.getAnInteger());
        assertEquals(testObject.getDescription(), testViewHelper.getDescription());
        assertEquals(10, testObject.getListOfStrings().size());
        assertEquals(10, testViewHelper.getListOfStrings().size());
    }

    private TestClass createInputObject() {
        final TestClass testObject = new TestClass();
        testObject.setName(DEFAULT_NAME);
        testObject.setDescription(DEFAULT_DESCRIPTION);
        testObject.setAnInteger(DEFAULT_INTEGER);
        testObject.setListOfStrings(DEFAULT_STRING_LIST);
        return testObject;
    }
}
