package com.codeapes.checklist.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.domain.audit.AuditLogEntry;
import com.codeapes.checklist.test.util.DBSessionUtility;
import com.codeapes.checklist.test.util.TestConfiguration;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.paging.PagingQueryCriteria;
import com.codeapes.checklist.util.paging.ResultPage;

public class ResultsPageDAOTest {

    private static final AppLogger logger = new AppLogger(ResultsPageDAOTest.class);

    private static final String DEFAULT_CREATED_BY = "test_user";
    private static final String DATA_QUERY = "from AuditLogEntry as a where a.type = :type";
    private static final String COUNT_QUERY = "select count(*) " + DATA_QUERY;

    private PersistenceDAO persistenceDAO;
    private DBSessionUtility sessionUtility;

    @Before
    public void initializeUserService() {
        final ApplicationContext appContext = TestConfiguration.getInstance().getApplicationContext();
        sessionUtility = new DBSessionUtility();
        sessionUtility.configureSession(appContext);
        persistenceDAO = (PersistenceDAO) appContext.getBean("persistenceDAO");
    }

    @Test
    public void testPagingEvenlyDivisible() {
        final String testName = "pagingEvenlyDivisible";
        final List<AuditLogEntry> entries = createTestData(testName);
        final PagingQueryCriteria queryCriteria = createQueryCriteria(testName, 20);

        for (int i = 0; i < 5; i++) {
            logTestIteration(testName, i);
            queryCriteria.setPageNumber(i);
            final ResultPage results = persistenceDAO.getPageOfResults(queryCriteria);
            assertEquals(100, results.getTotalResults());
            assertEquals(5, results.getTotalNumberOfPages());
            assertEquals(20, results.getResults().size());
            assertEquals(i + 1, results.getCurrentPageForDisplay());

            if (i == 0) {
                assertTrue(results.isNextEnabled());
                assertFalse(results.isPrevEnabled());
            } else if (i == 4) {
                assertFalse(results.isNextEnabled());
                assertTrue(results.isPrevEnabled());
            } else {
                assertTrue(results.isNextEnabled());
                assertTrue(results.isPrevEnabled());
            }
        }

        removeTestData(entries);
    }
    
    @Test
    public void testPagingNotEvenlyDivisible() {
        final String testName = "pagingNotEvenlyDivisible";
        final List<AuditLogEntry> entries = createTestData(testName);
        final PagingQueryCriteria queryCriteria = createQueryCriteria(testName, 15);

        for (int i = 0; i < 7; i++) {
            logTestIteration(testName, i);
            queryCriteria.setPageNumber(i);
            final ResultPage results = persistenceDAO.getPageOfResults(queryCriteria);
            assertEquals(100, results.getTotalResults());
            assertEquals(7, results.getTotalNumberOfPages());
            assertEquals(i + 1, results.getCurrentPageForDisplay());

            if (i == 0) {
                assertEquals(15, results.getResults().size());
                assertTrue(results.isNextEnabled());
                assertFalse(results.isPrevEnabled());
            } else if (i == 6) {
                assertEquals(10, results.getResults().size());
                assertFalse(results.isNextEnabled());
                assertTrue(results.isPrevEnabled());
            } else {
                assertEquals(15, results.getResults().size());
                assertTrue(results.isNextEnabled());
                assertTrue(results.isPrevEnabled());
            }
        }

        removeTestData(entries);        
    }

    @After
    public void tearDown() {
        sessionUtility.endSession();
    }

    private PagingQueryCriteria createQueryCriteria(String testName, int resultsPerPage) {
        final PagingQueryCriteria queryCriteria = new PagingQueryCriteria();
        queryCriteria.setCountQuery(COUNT_QUERY);
        queryCriteria.setQuery(DATA_QUERY);
        queryCriteria.setResultsPerPage(resultsPerPage);
        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("type", testName);
        queryCriteria.setParameters(parameters);
        return queryCriteria;
    }
    
    private void logTestIteration(String testName, int iteration) {
        logger.info("Test: %s - Iteration: %d", testName, iteration);
    }
    private List<AuditLogEntry> createTestData(String testName) {
        final List<AuditLogEntry> logEntries = new ArrayList<AuditLogEntry>();
        for (int i = 0; i < 100; i++) {
            final AuditLogEntry entry = createAuditLogEntry(testName, i);
            persistenceDAO.saveObject(entry, DEFAULT_CREATED_BY);
            logEntries.add(entry);
        }
        sessionUtility.flushSession();
        return logEntries;
    }

    private AuditLogEntry createAuditLogEntry(String testName, int counter) {
        final AuditLogEntry entry = new AuditLogEntry();
        entry.setAction("created:" + testName + counter);
        entry.setType(testName);
        entry.setDetail("Audit Log Entry for object: " + testName + counter);
        return entry;
    }

    private void removeTestData(List<AuditLogEntry> logEntries) {
        for (AuditLogEntry entry : logEntries) {
            persistenceDAO.delete(entry, DEFAULT_CREATED_BY);
        }
    }
}
