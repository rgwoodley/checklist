package com.codeapes.checklist.web.dashboard;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.service.ChecklistService;
import com.codeapes.checklist.service.SortOrder;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.paging.PagingQueryCriteria;
import com.codeapes.checklist.util.paging.ResultPage;
import com.codeapes.checklist.web.util.SinglePageResult;
import com.codeapes.checklist.web.util.WebSecurityConstants;
import com.codeapes.checklist.web.util.WebUtility;

@Controller
public class DashboardRESTController {

    private static final AppLogger logger = new AppLogger(DashboardRESTController.class); // NOSONAR

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private WebUtility webUtility;

    @RequestMapping(method = RequestMethod.GET, value = "/myChecklists")
    @ResponseBody
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    public SinglePageResult getUserChecklists(@RequestParam final int perPage, @RequestParam final int page,
            @RequestParam final String orderBy, @RequestParam final String sortOrder, final HttpSession session,
            final Model model) {

        logger.debug("Retrieve owned checklists for user %s", webUtility.getLoggedInUsername());
        final PagingQueryCriteria pageCriteria = new PagingQueryCriteria(page, perPage, orderBy,
                SortOrder.fromString(sortOrder));
        final ResultPage resultPage = checklistService.getOwnedChecklists(webUtility.getLoggedInUserKey(session),
                pageCriteria);
        return populateSinglePageResult(resultPage);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activeChecklists")
    @ResponseBody
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    public SinglePageResult getActiveChecklists(@RequestParam final int perPage, @RequestParam final int page,
            @RequestParam final String orderBy, @RequestParam final String sortOrder, final HttpSession session,
            final Model model) {

        logger.debug("Retrieve actively running checklists for user %s", webUtility.getLoggedInUsername());
        final PagingQueryCriteria pageCriteria = new PagingQueryCriteria(page, perPage, orderBy,
                SortOrder.fromString(sortOrder));
        final ResultPage resultPage = checklistService.getActiveChecklists(webUtility.getLoggedInUserKey(session),
                pageCriteria);
        return populateSinglePageResult(resultPage);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/recentChecklists")
    @ResponseBody
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    public SinglePageResult getRecentChecklists(@RequestParam final int perPage, @RequestParam final int page,
            @RequestParam final String orderBy, @RequestParam final String sortOrder, final HttpSession session,
            final Model model) {

        logger.debug("Retrieve recently executed checklists for user %s", webUtility.getLoggedInUsername());
        final PagingQueryCriteria pageCriteria = new PagingQueryCriteria(page, perPage, orderBy,
                SortOrder.fromString(sortOrder));
        final ResultPage resultPage = checklistService
                .getRecentlyCompletedChecklists(webUtility.getLoggedInUserKey(session), pageCriteria);
        return populateSinglePageResult(resultPage);
    }

    @SuppressWarnings("unchecked")
    private SinglePageResult populateSinglePageResult(ResultPage resultPage) {
        final List<Checklist> checklists = (List<Checklist>) resultPage.getResults();
        final List<DashboardChecklistViewHelper> viewHelpers = createViewHelpers(checklists);
        resultPage.setResults(viewHelpers);
        logger.debug("Found %s checklists.", viewHelpers.size());
        return new SinglePageResult(resultPage);
    }

    private List<DashboardChecklistViewHelper> createViewHelpers(List<Checklist> checklists) {
        final List<DashboardChecklistViewHelper> viewHelpers = new ArrayList<DashboardChecklistViewHelper>();
        for (Checklist checklist : checklists) {
            final DashboardChecklistViewHelper viewHelper = createViewHelper(checklist);
            viewHelpers.add(viewHelper);
        }
        return viewHelpers;
    }

    private DashboardChecklistViewHelper createViewHelper(Checklist checklist) {
        final DashboardChecklistViewHelper viewHelper = new DashboardChecklistViewHelper();
        viewHelper.setName(checklist.getName());
        int numSteps = 0;
        if (checklist.getSteps() != null) {
            numSteps = checklist.getSteps().size();
        }
        viewHelper.setNumSteps(numSteps);
        viewHelper.setObjectKey(checklist.getObjectKey());
        viewHelper.setOwnerName(checklist.getOwner().getName());
        viewHelper.setDuration("" + checklist.getExpectedDurationInMinutes());
        viewHelper.setImageURL("checkmark_green.png");
        viewHelper.setCurrentStep("SAN Snap - DBA Group");
        viewHelper.setStatus("Running");
        viewHelper.setEstimatedCompletionTime("09/30/2013 11:59pm");
        viewHelper.setActualCompletionTime("10/16/2013 06:00am");
        return viewHelper;
    }

}
