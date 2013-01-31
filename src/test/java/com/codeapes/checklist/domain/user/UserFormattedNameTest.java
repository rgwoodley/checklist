package com.codeapes.checklist.domain.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class UserFormattedNameTest {

    private static final String FIRST_NAME = "Roger";
    private static final String LAST_NAME = "Smith";
    
    @Test
    public void testNameNullFirstName() {
        final User user = new User();
        user.setLastName(LAST_NAME);
        assertEquals(LAST_NAME, user.getName());
    }
    
    @Test
    public void testNameNullLastName() {
        final User user = new User();
        user.setFirstName(FIRST_NAME);
        assertEquals(FIRST_NAME, user.getName());
    }
    
    @Test
    public void testNameNotNullFirstAndLastName() {
        final User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        assertEquals(LAST_NAME + ", " + FIRST_NAME, user.getName());
    }
    
    @Test
    public void testNameNullFirstAndLastName() {
        final User user = new User();
        assertNull(user.getName());
    }

}
