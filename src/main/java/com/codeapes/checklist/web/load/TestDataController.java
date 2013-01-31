package com.codeapes.checklist.web.load;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeapes.checklist.domain.group.ChecklistGroup;
import com.codeapes.checklist.domain.group.ChecklistGroupType;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.user.Role;
import com.codeapes.checklist.domain.user.User;
import com.codeapes.checklist.service.ChecklistService;
import com.codeapes.checklist.service.user.UserService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ApplicationProperties;

@Controller
public class TestDataController {

    private static final AppLogger logger = new AppLogger(TestDataController.class);

    private static final String TARGET_VIEW = "generalMessage";
    private static final String MODIFIED_BY = "tester";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String MODEL_MESSAGE = "message";
    private static final String CHECKLIST_PREFIX = "checklist";
    private static final String DESCRIPTION_PREFIX = "Test Checklist #";
    private static final int DEFAULT_CHECKLIST_DURATION = 60;
    private static final int NUMBER_OF_CHECKLISTS_TO_GENERATE = 30;

    @Autowired
    private UserService userService;

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @RequestMapping(method = RequestMethod.GET, value = "/generateTestData")
    public String createAllTestData(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (applicationProperties.isTestMode()) {
            final User user = createTestUsers();
            final ChecklistGroup[] groups = createTestChecklistGroups(user);
            createTestChecklists(user, groups[0], groups[1]);
            model.put(MODEL_MESSAGE, "Test Data Generated.  Have fun!");
        } else {

            model.put(MODEL_MESSAGE, "The application is not running in test mode.  Test Data generation failed.");
        }
        return TARGET_VIEW;
    }

    private User createTestUsers() {
        logger.info("Generating user test data");
        final User adminUser = userService.createUser("admin", "Admin", "Administrator", true, DEFAULT_PASSWORD,
            MODIFIED_BY);
        adminUser.addUserRole(Role.ADMIN);
        userService.updateUser(adminUser, MODIFIED_BY);
        final User user = userService.createUser("user", "Normal", "User", true, DEFAULT_PASSWORD, MODIFIED_BY);
        logger.info("User test data generation complete");
        return user;
    }

    private ChecklistGroup[] createTestChecklistGroups(User owner) {
        logger.info("Generating checklist group data");
        final ChecklistGroup group1 = createChecklistGroup("Public Checklists", "A Public Test Group", owner,
            ChecklistGroupType.PUBLIC);
        final ChecklistGroup group2 = createChecklistGroup("Private Checklists", "A Private Test Group", owner,
            ChecklistGroupType.PRIVATE);
        checklistService.saveOrUpdateChecklistGroup(group1, owner.getUsername());
        checklistService.saveOrUpdateChecklistGroup(group2, owner.getUsername());
        logger.info("Checklist group test data generation complete.");
        final ChecklistGroup[] groups = { group1, group2 };
        return groups;
    }

    private void createTestChecklists(User owner, ChecklistGroup publicGroup, ChecklistGroup privateGroup) {

        logger.info("Generating checklist data");
        for (int i = 0; i < NUMBER_OF_CHECKLISTS_TO_GENERATE; i++) {
            final Checklist checklist = createChecklist(CHECKLIST_PREFIX + (i + 1), DESCRIPTION_PREFIX + (i + 1),
                owner, publicGroup, DEFAULT_CHECKLIST_DURATION);
            checklistService.saveOrUpdateChecklist(checklist, owner.getUsername());
        }
        for (int i = NUMBER_OF_CHECKLISTS_TO_GENERATE; i < (NUMBER_OF_CHECKLISTS_TO_GENERATE * 2); i++) {
            final Checklist checklist = createChecklist(CHECKLIST_PREFIX + (i + 1), DESCRIPTION_PREFIX + (i + 1),
                owner, privateGroup, DEFAULT_CHECKLIST_DURATION);
            checklistService.saveOrUpdateChecklist(checklist, owner.getUsername());
        }
        logger.info("Checklist test data generation complete");
    }

    private ChecklistGroup createChecklistGroup(String name, String description, User owner, ChecklistGroupType type) {
        final ChecklistGroup group = new ChecklistGroup();
        group.setName(name);
        group.setDescription(description);
        group.setOwner(owner);
        group.setType(type);
        return group;
    }

    private Checklist createChecklist(String name, String description, User owner, ChecklistGroup grp, long duration) {
        final Checklist checklist = new Checklist();
        checklist.setName(name);
        checklist.setDescription(description);
        checklist.setOwner(owner);
        checklist.setGroup(grp);
        checklist.setSteps(null);
        checklist.setExpectedDurationInMinutes(duration);
        return checklist;
    }
}
