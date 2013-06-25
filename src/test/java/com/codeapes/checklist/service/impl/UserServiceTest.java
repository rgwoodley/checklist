package com.codeapes.checklist.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.domain.user.Role;
import com.codeapes.checklist.domain.user.User;
import com.codeapes.checklist.service.user.UserService;
import com.codeapes.checklist.test.util.DBSessionUtility;
import com.codeapes.checklist.test.util.TestConfiguration;

@Transactional
public class UserServiceTest {

    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final boolean ACTIVE = true;
    private static final String PASSWORD = "testing123";
    private static final String MODIFIED_BY = "unit_test";

    private UserService userService;
    private DBSessionUtility sessionUtility;

    @Before
    public void initializeUserService() {
        final ApplicationContext appContext = TestConfiguration.getInstance().getApplicationContext();
        sessionUtility = new DBSessionUtility();
        sessionUtility.configureSession(appContext);
        userService = (UserService) appContext.getBean("userService");
    }

    @Test
    public void createUserUsingAttributes() {
        final String testName = "userUsingAttributes";
        final User aUser = userService.createUser(testName, FIRSTNAME, LASTNAME, ACTIVE, PASSWORD, MODIFIED_BY);
        assertNotNull(aUser);
        assertNotNull(aUser.getObjectKey());
        assertNotNull(aUser.getModifiedTimestamp());
        assertNotNull(aUser.getCreatedBy());
        assertNotNull(aUser.getCreatedTimestamp());
        assertNotNull(aUser.getPassword());
        assertNotSame(PASSWORD, aUser.getPassword());
        assertTrue(aUser.getRoles().contains(Role.USER));
        userService.deleteUser(aUser, MODIFIED_BY);
    }

    @Test
    public void findUserById() {

        final String testName = "findById";
        final User aUser = userService.createUser(testName, FIRSTNAME, LASTNAME, ACTIVE, PASSWORD, MODIFIED_BY);
        final Long userKey = aUser.getObjectKey();
        sessionUtility.flushAndClearSession();
        final User bUser = userService.findUserByObjectKey(userKey);
        assertEquals(userKey, bUser.getObjectKey());
        assertEquals(aUser.getUsername(), bUser.getUsername());
        userService.deleteUser(bUser, MODIFIED_BY);
    }

    @Test
    public void findUserByUsernameFound() {

        final String testName = "findByUnameFound";
        final User aUser = userService.createUser(testName, FIRSTNAME, LASTNAME, ACTIVE, PASSWORD, MODIFIED_BY);
        sessionUtility.flushAndClearSession();
        final User bUser = userService.findUserByUsername(testName);
        assertNotNull(bUser);
        assertEquals(aUser.getObjectKey(), bUser.getObjectKey());
        assertEquals(aUser.getUsername(), bUser.getUsername());
        userService.deleteUser(bUser, MODIFIED_BY);
    }

    @Test
    public void findUserByUsernameNotFound() {

        final String testName = "unameNotFound";
        final User bUser = userService.findUserByUsername(testName);
        assertNull(bUser);
    }

    @Test
    public void deleteUser() {

        final String testName = "deleteUser";
        final User aUser = userService.createUser(testName, FIRSTNAME, LASTNAME, ACTIVE, PASSWORD, MODIFIED_BY);
        assertNotNull(aUser.getObjectKey());
        final Long objectKey = aUser.getObjectKey();
        sessionUtility.flushAndClearSession();
        userService.deleteUser(aUser, MODIFIED_BY);
        final User bUser = userService.findUserByObjectKey(objectKey);
        assertNull(bUser);
    }

    @Test
    public void updateUser() {

        final String testName = "updateUser";
        final String updatedUsername = "updateUser1";
        final String updatedFirstname = "testingfname";
        final User aUser = userService.createUser(testName, FIRSTNAME, LASTNAME, ACTIVE, PASSWORD, MODIFIED_BY);
        assertNotNull(aUser.getObjectKey());
        final Long objectKey = aUser.getObjectKey();
        aUser.setUsername(updatedUsername);
        aUser.setFirstName(updatedFirstname);
        aUser.addUserRole(Role.ADMIN);
        sessionUtility.flushAndClearSession();
        userService.updateUser(aUser, MODIFIED_BY);
        final User bUser = userService.findUserByObjectKey(objectKey);
        assertEquals(updatedUsername, bUser.getUsername());
        assertEquals(updatedFirstname, bUser.getFirstName());
        assertTrue(bUser.getRoles().contains(Role.ADMIN));
        userService.deleteUser(bUser, MODIFIED_BY);
    }

    @After
    public void tearDown() {
        sessionUtility.endSession();
    }

}
