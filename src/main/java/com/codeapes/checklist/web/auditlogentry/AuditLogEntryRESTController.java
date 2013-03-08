package com.codeapes.checklist.web.auditlogentry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.codeapes.checklist.domain.audit.AuditLogEntry;
import com.codeapes.checklist.service.PersistenceService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.web.util.WebUtility;
import com.codeapes.checklist.web.viewhelper.audit.AuditLogEntryViewHelper;
import com.codeapes.checklist.web.viewhelper.util.ViewHelperUtility;

@Controller
public class AuditLogEntryRESTController {

    private static final AppLogger logger = new AppLogger(AuditLogEntryRESTController.class);

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private WebUtility webUtility;

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET, value = "/auditLogEntries", produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public List<AuditLogEntryViewHelper> getAllAuditLogEntries(final Model model) {
        logger.debug("Get all audit log entries");
        final List<AuditLogEntry> entries = (List<AuditLogEntry>) persistenceService
            .fetchAllObjectsByType(AuditLogEntry.class);
        final List<AuditLogEntryViewHelper> entryList = (List<AuditLogEntryViewHelper>) ViewHelperUtility.convertList(
            entries, AuditLogEntryViewHelper.class);
        return entryList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/auditLogEntries/search/{query}",
        produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public AuditLogEntryViewHelper searchForLogEntry(@PathVariable final String query, final Model model) {
        logger.debug("Search for audit log entry using query: %s", query);
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/auditLogEntries/{id}", produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public AuditLogEntryViewHelper getAuditLogEntry(@PathVariable final Long id, final Model model) {
        logger.debug("Get audit log entry via request parameter id: %s", id);
        final AuditLogEntry entry = (AuditLogEntry) persistenceService.findObjectByKey(AuditLogEntry.class, id);
        final AuditLogEntryViewHelper auditLogEntryViewHelper = (AuditLogEntryViewHelper) ViewHelperUtility
            .createAndPopulateViewHelper(entry, AuditLogEntryViewHelper.class);
        return auditLogEntryViewHelper;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auditLogEntries", consumes = "application/json",
        produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public AuditLogEntryViewHelper createAuditLogEntry(@RequestBody AuditLogEntryViewHelper entryData) {
        logger.debug("Create new Audit Log Entry");
        AuditLogEntry entry = createEntryObject(entryData);
        entry = (AuditLogEntry) persistenceService.saveObject(entry, webUtility.getLoggedInUsername());
        final AuditLogEntryViewHelper savedEntryData = (AuditLogEntryViewHelper) ViewHelperUtility
            .createAndPopulateViewHelper(entry, AuditLogEntryViewHelper.class);
        return savedEntryData;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/auditLogEntries/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public void updateAuditLogEntry(@RequestBody AuditLogEntryViewHelper entryData, @PathVariable final Long id) {
        logger.debug("Update Audit Log Entry %s:", entryData.getObjectKey());
        final AuditLogEntry entry = (AuditLogEntry) persistenceService.findObjectByKey(AuditLogEntry.class, id);
        setFields(entry, entryData);
        persistenceService.update(entry, webUtility.getLoggedInUsername());
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/auditLogEntries/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public void deleteAuditLogEntry(@PathVariable final Long id) {
        logger.debug("Deleting Audit Log Entry %s:", id);
        final AuditLogEntry entry = (AuditLogEntry) persistenceService.findObjectByKey(AuditLogEntry.class, id);
        persistenceService.delete(entry);
    }

    private AuditLogEntry createEntryObject(AuditLogEntryViewHelper viewHelper) {
        AuditLogEntry entry = new AuditLogEntry();
        entry = setFields(entry, viewHelper);
        return entry;
    }

    private AuditLogEntry setFields(AuditLogEntry entry, AuditLogEntryViewHelper viewHelper) {
        entry.setAction(viewHelper.getAction());
        entry.setDetail(viewHelper.getDetail());
        entry.setType(viewHelper.getType());
        return entry;
    }
}
