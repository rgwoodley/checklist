package com.codeapes.checklist.web.load;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeapes.checklist.domain.execution.ChecklistExecutionStatus;
import com.codeapes.checklist.domain.execution.ExecutionChecklist;
import com.codeapes.checklist.domain.job.ScheduledJob;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.template.ChecklistGroup;
import com.codeapes.checklist.domain.template.ChecklistGroupType;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.Role;
import com.codeapes.checklist.domain.user.User;
import com.codeapes.checklist.domain.user.UserGroup;
import com.codeapes.checklist.job.JobConstants;
import com.codeapes.checklist.service.PersistenceService;
import com.codeapes.checklist.service.checklist.ChecklistService;
import com.codeapes.checklist.service.checklist.ExecutionChecklistService;
import com.codeapes.checklist.service.search.SearchService;
import com.codeapes.checklist.service.user.UserService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ApplicationProperties;

@Controller
public class TestDataController {

    private static final AppLogger logger = new AppLogger(TestDataController.class); // NOSONAR

    private static final String TARGET_VIEW = "generalMessage";
    private static final String MODIFIED_BY = "tester";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String MODEL_MESSAGE = "message";
    private static final String CHECKLIST_PREFIX = "checklist";
    private static final String DESCRIPTION_PREFIX = "Test Checklist #";
    private static final int DEFAULT_CHECKLIST_DURATION = 60;
    private static final int NUMBER_OF_CHECKLISTS_TO_GENERATE = 30;
    private static final int NUMBER_OF_EXEC_CHECKLIST_STATES = 5;
    private static final int THREE = 3;
    private static final int EXEC_CL_PER_STATE = NUMBER_OF_CHECKLISTS_TO_GENERATE / NUMBER_OF_EXEC_CHECKLIST_STATES;
    private static final String REFRESH_JOB_CRON_INFO = "0 0/5 * * * ?";
    private static final String REFRESH_JOB_GROUP = "Search";

    @Autowired
    private UserService userService;

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private ExecutionChecklistService executionChecklistService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @RequestMapping(method = RequestMethod.GET, value = "/generateTestData")
    public String createAllTestData(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        if (applicationProperties.isTestMode()) {
            final UserGroup userGroup = createUserGroup();
            final User user = createTestUsers(userGroup);
            final ChecklistGroup[] groups = createTestChecklistGroups(user);
            createTestChecklists(user, groups[0], groups[1], userGroup);
            searchService.refreshIndexSearcherBlocking();
            createSearchCacheRefreshJob();
            model.put(MODEL_MESSAGE, "Test Data Generated.  Have fun!");
        } else {

            model.put(MODEL_MESSAGE, "The application is not running in test mode.  Test Data generation failed.");
        }
        return TARGET_VIEW;
    }

    private UserGroup createUserGroup() {
        final UserGroup group = new UserGroup();
        group.setName("group1");
        userService.createUserGroup(group, MODIFIED_BY);
        logger.info("UserGroup test data generation complete");
        return group;
    }

    private User createTestUsers(UserGroup userGroup) {
        logger.info("Generating user test data");
        final User adminUser = userService.createUser("admin", "Admin", "Administrator", true, DEFAULT_PASSWORD,
                MODIFIED_BY);
        adminUser.addUserRole(Role.ADMIN);
        userService.updateUser(adminUser, MODIFIED_BY);
        final User user = userService.createUser("user", "Normal", "User", true, DEFAULT_PASSWORD, MODIFIED_BY);
        user.addGroup(userGroup);
        userService.updateUser(user, MODIFIED_BY);
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
        final ChecklistGroup[] groups = new ChecklistGroup[2];
        groups[0] = group1;
        groups[1] = group2;
        return groups;
    }

    private void createTestChecklists(User user, ChecklistGroup publicGroup, ChecklistGroup privateGroup,
            UserGroup userGroup) {

        logger.info("Generating checklist data");
        int count = 0;
        createSetOfChecklists(count++, user, publicGroup, user.getUsername());
        createSetOfChecklists(NUMBER_OF_CHECKLISTS_TO_GENERATE, user, privateGroup, user.getUsername());
        createSetOfChecklists(NUMBER_OF_CHECKLISTS_TO_GENERATE * ++count, userGroup, publicGroup, user.getUsername());
        createSetOfChecklists(NUMBER_OF_CHECKLISTS_TO_GENERATE * ++count, userGroup, privateGroup, user.getUsername());
        logger.info("Checklist test data generation complete");
    }

    private void createSetOfChecklists(int start, OwnerExecutor owner, ChecklistGroup group, String createdBy) {
        for (int i = start; i < (start + NUMBER_OF_CHECKLISTS_TO_GENERATE); i++) {
            final Checklist checklist = createChecklist(CHECKLIST_PREFIX + (i + 1), DESCRIPTION_PREFIX + (i + 1),
                    owner, group, DEFAULT_CHECKLIST_DURATION + (i + 1));
            checklistService.saveOrUpdateChecklist(checklist, createdBy);
            createExecutionChecklist(checklist, owner, createdBy, i - start);
        }
    }

    private void createExecutionChecklist(Checklist checklist, OwnerExecutor executor, String createdBy, int i) {

        final ExecutionChecklist executionChecklist = executionChecklistService.createExecutionChecklist(checklist,
                executor, createdBy);
        executionChecklist.setStatus(determineExecutionStatus(i));
        final Timestamp currentDateTime = new Timestamp(new Date().getTime());
        executionChecklist.setExecutionStart(currentDateTime);
        executionChecklist.setEstimatedCompletion(currentDateTime);
        executionChecklist.setExecutionEnd(currentDateTime);
        executionChecklistService.updateExecutionChecklist(executionChecklist, createdBy);
    }

    private ChecklistExecutionStatus determineExecutionStatus(int i) {
        
        final int position = i / EXEC_CL_PER_STATE;
        ChecklistExecutionStatus status = null;
        switch (position) {
            case 0:
                status = ChecklistExecutionStatus.NOT_STARTED;
                break;
            case 1:
                status = ChecklistExecutionStatus.CANCELLED;
                break;
            case 2:
                status = ChecklistExecutionStatus.FAILED;
                break;
            case THREE:
                status = ChecklistExecutionStatus.PASSED;
                break;
            default:
                status = ChecklistExecutionStatus.IN_PROGRESS;
        }
        return status;
    }

    private ChecklistGroup createChecklistGroup(String name, String description, OwnerExecutor owner,
            ChecklistGroupType type) {
        final ChecklistGroup group = new ChecklistGroup();
        group.setName(name);
        group.setDescription(description);
        group.setOwner(owner);
        group.setType(type);
        return group;
    }

    private Checklist createChecklist(String name, String description, OwnerExecutor owner, ChecklistGroup grp,
            long duration) {
        final Checklist checklist = new Checklist();
        checklist.setName(name);
        checklist.setDescription(description);
        checklist.setOwner(owner);
        checklist.setGroup(grp);
        checklist.setSteps(null);
        checklist.setExpectedDurationInMinutes(duration);
        return checklist;
    }

    private ScheduledJob createSearchCacheRefreshJob() {
        final ScheduledJob refreshJob = new ScheduledJob();
        refreshJob.setName("Lucene IndexSearcher Refresh Job");
        refreshJob.setDescription("This job refreshes the Lucene IndexSearcher "
                + "on a periodic basis in a separate thread.");
        refreshJob.setCronInfo(REFRESH_JOB_CRON_INFO);
        refreshJob.setGroup(REFRESH_JOB_GROUP);
        refreshJob.setJobBeanName(JobConstants.SEARCH_INDEX_REFRESH_JOB);
        refreshJob.setActive(true);
        persistenceService.saveObject(refreshJob, MODIFIED_BY);
        return refreshJob;
    }
}
