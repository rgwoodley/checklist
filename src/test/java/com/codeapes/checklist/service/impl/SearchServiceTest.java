package com.codeapes.checklist.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.ChecklistType;
import com.codeapes.checklist.domain.search.SearchResult;
import com.codeapes.checklist.domain.user.User;
import com.codeapes.checklist.service.PersistenceService;
import com.codeapes.checklist.service.search.SearchService;
import com.codeapes.checklist.service.user.UserService;
import com.codeapes.checklist.test.util.DBSessionUtil;
import com.codeapes.checklist.test.util.TestConfiguration;

@Transactional
public class SearchServiceTest {

    private static final String CREATOR_USERNAME = "creator";

    private PersistenceService persistenceService;
    private SearchService searchService;
    private UserService userService;
    private DBSessionUtil sessionUtility;

    @Before
    public void initializeSearchService() {
        final ApplicationContext appContext = TestConfiguration.getInstance().getApplicationContext();
        sessionUtility = new DBSessionUtil();
        sessionUtility.configureSession(appContext);
        persistenceService = (PersistenceService) appContext.getBean("persistenceService");
        searchService = (SearchService) appContext.getBean("searchService");
        userService = (UserService) appContext.getBean("userService");
        searchService.removeAllObjectsFromIndex();
    }

    @After
    public void closeSession() {
        sessionUtility.endSession();
    }

    @Test
    public void testBasicIndexAndRetrieval() throws Exception {
        final String checklist1Id = "1";
        final Checklist checklist = createChecklist(checklist1Id);
        persistenceService.saveObject(checklist, CREATOR_USERNAME);
        final Long objectKey = checklist.getObjectKey();
        searchService.refreshIndexSearcherBlocking();
        final List<SearchResult> results = searchService.search(checklist1Id, 1);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertNotNull(objectKey);
        final SearchResult result = results.get(0);
        assertEquals(objectKey, result.getObjectKey());
        assertEquals(checklist.getClass().getName(), result.getObjectType());
        persistenceService.delete(checklist, CREATOR_USERNAME);
    }

    @Test
    public void testRemoveFromIndex() throws Exception {
        final String checklist2Id = "2";
        final Checklist checklist = createChecklist(checklist2Id);
        persistenceService.saveObject(checklist, CREATOR_USERNAME);
        searchService.refreshIndexSearcherBlocking();
        List<SearchResult> results = searchService.search(checklist2Id, 1);
        assertEquals(1, results.size());
        persistenceService.delete(checklist, CREATOR_USERNAME);
        searchService.refreshIndexSearcherBlocking();
        results = searchService.search(checklist2Id, 1);
        assertEquals(0, results.size());
    }

    @Test
    public void testUpdateObjectInIndex() throws Exception {
        final String checklist3Id = "3";
        final String checklist4Id = "4";
        final Checklist checklist = createChecklist(checklist3Id);
        persistenceService.saveObject(checklist, CREATOR_USERNAME);
        searchService.refreshIndexSearcherBlocking();
        List<SearchResult> results = searchService.search(checklist3Id, 5);
        assertNotNull(results);
        assertEquals(1, results.size());
        SearchResult result = results.get(0);
        assertEquals(generateChecklistName(checklist3Id), result.getName());
        assertEquals(generateChecklistDescription(checklist3Id), result.getDescription());
        checklist.setName(generateChecklistName(checklist4Id));
        checklist.setDescription(generateChecklistDescription(checklist4Id));
        persistenceService.update(checklist, CREATOR_USERNAME);
        searchService.refreshIndexSearcherBlocking();
        results = searchService.search(checklist4Id, 5);
        assertNotNull(results);
        assertEquals(1, results.size());
        result = results.get(0);
        assertEquals(generateChecklistName(checklist4Id), result.getName());
        assertEquals(generateChecklistDescription(checklist4Id), result.getDescription());
    }

    private User createUserIfNotExists() {
        User user = userService.findUserByUsername(CREATOR_USERNAME);
        if (user == null) {
            user = new User();
            user.setUsername(CREATOR_USERNAME);
            user.setFirstName("FirstName");
            user.setLastName("LastName");
            user.setPassword("password");
            userService.createUser(user, CREATOR_USERNAME);
        }
        return user;
    }

    private Checklist createChecklist(String id) {
        final Checklist checklist = new Checklist(ChecklistType.TEMPLATE);
        checklist.setName(generateChecklistName(id));
        checklist.setDescription(generateChecklistDescription(id));
        checklist.setOwner(createUserIfNotExists());
        return checklist;
    }

    private String generateChecklistName(String id) {
        return "Checklist Number " + id + " - General";
    }

    private String generateChecklistDescription(String id) {
        return "This is the description for checklist number " + id + ".";
    }
}
