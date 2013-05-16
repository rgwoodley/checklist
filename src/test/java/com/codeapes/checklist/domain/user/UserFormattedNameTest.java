package com.codeapes.checklist.domain.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class UserFormattedNameTest {

    private static final String FIRST_NAME = "Roger";
    private static final String LAST_NAME = "Smith";
    private static final String USERNAME = "rsmith";
    
    @Test
    public void testNameNullFirstName() {
        final User user = new User();
        user.setUsername(USERNAME);
        user.setLastName(LAST_NAME);
        assertEquals(USERNAME, user.getName());
    }
    
    @Test
    public void testNameNullLastName() {
        final User user = new User();
        user.setUsername(USERNAME);
        user.setFirstName(FIRST_NAME);
        assertEquals(USERNAME, user.getName());
    }
    
    @Test
    public void testNameNotNullFirstAndLastName() {
        final User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setUsername(USERNAME);
        assertEquals(FIRST_NAME + " " + LAST_NAME, user.getName());
    }
    
    @Test
    public void testNameNullFirstAndLastName() {
        final User user = new User();
        user.setUsername(USERNAME);
        assertEquals(USERNAME, user.getName());
    }
    
    @Test
    public void testNameNullFirstAndLastAndUserName() {
        final User user = new User();
        assertNull(user.getName());
    }

}
