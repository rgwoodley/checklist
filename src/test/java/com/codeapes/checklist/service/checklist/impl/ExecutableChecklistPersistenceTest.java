package com.codeapes.checklist.service.checklist.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.user.User;
import com.codeapes.checklist.service.checklist.ChecklistService;
import com.codeapes.checklist.service.user.UserService;
import com.codeapes.checklist.test.util.DBSessionUtil;
import com.codeapes.checklist.test.util.TestConfiguration;

public class ExecutableChecklistPersistenceTest {

    private static final String CREATED_BY = "test";
    
    private ChecklistService checklistService;
    private UserService userService;
    private DBSessionUtil sessionUtility;

    @Before
    public void initializeChecklistService() {
        final ApplicationContext appContext = TestConfiguration.getInstance().getApplicationContext();
        sessionUtility = new DBSessionUtil();
        sessionUtility.configureSession(appContext);
        checklistService = (ChecklistService) appContext.getBean("checklistService");
        userService = (UserService) appContext.getBean("userService");
    }
    
    @Test
    public void testExecutableChecklistSave() {
        final User user = TestChecklistCreator.createExecutor();
        final Checklist checklist = TestChecklistCreator.createChecklistTemplate(user);
        TestChecklistCreator.createChecklistSteps(checklist, user);
        TestChecklistCreator.addChecklistPreconditions(checklist);
        final Checklist executableChecklist = checklistService
                .createExecutableChecklist(checklist, user, CREATED_BY);
        userService.saveOrUpdateUser(user, CREATED_BY);
        checklistService.saveChecklist(executableChecklist, CREATED_BY);
        checklistService.deleteChecklist(executableChecklist, CREATED_BY);
        userService.deleteUser(user, CREATED_BY);
    }
    
    @After
    public void tearDown() {
        sessionUtility.endSession();
    }

}
