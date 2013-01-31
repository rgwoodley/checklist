package com.codeapes.checklist.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilTest {

    private static final String TEST_STRING = "Test";
    private static final String EMPTY_STRING = "";
    
    @Test
    public void testNullEmptyCheckWithNull() {
        assertEquals(true, StringUtil.isNullOrEmpty(null));
    }
    
    @Test
    public void testNullEmptyCheckWithEmpty() {
        assertEquals(true, StringUtil.isNullOrEmpty(EMPTY_STRING));
    }
    
    @Test
    public void testNullEmptyCheckWithNotEmpty() {
        assertEquals(false, StringUtil.isNullOrEmpty(TEST_STRING));
    }
    
    @Test
    public void testNotEmptyCheckWithNull() {
        assertEquals(false, StringUtil.isNotEmpty(null));
    }
    
    @Test
    public void testNotEmptyCheckWithEmpty() {
        assertEquals(false, StringUtil.isNotEmpty(EMPTY_STRING));
    }
    
    @Test
    public void testNotEmptyCheckWithNotEmpty() {
        assertEquals(true, StringUtil.isNotEmpty(TEST_STRING));
    }

}
