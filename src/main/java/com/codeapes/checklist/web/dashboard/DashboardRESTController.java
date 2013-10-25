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

import com.codeapes.checklist.domain.execution.ChecklistExecutionStatus;
import com.codeapes.checklist.domain.execution.EstimatedCompletionStatus;
import com.codeapes.checklist.domain.execution.ExecutionChecklist;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.service.checklist.ChecklistService;
import com.codeapes.checklist.service.checklist.ExecutionChecklistSearchService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.query.PagingQueryCriteria;
import com.codeapes.checklist.util.query.ResultPage;
import com.codeapes.checklist.util.query.SortOrder;
import com.codeapes.checklist.web.util.DateFormatUtility;
import com.codeapes.checklist.web.util.SinglePageResult;
import com.codeapes.checklist.web.util.WebConstants;
import com.codeapes.checklist.web.util.WebSecurityConstants;
import com.codeapes.checklist.web.util.WebUtility;

@Controller
public class DashboardRESTController {

    private static final AppLogger logger = new AppLogger(DashboardRESTController.class); // NOSONAR

    @Autowired
    private ChecklistService checklistService;
        
    @Autowired
    private ExecutionChecklistSearchService executionChecklistSearchService;
    
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
        return populateSinglePageChecklistResult(resultPage);
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
        final ResultPage resultPage = executionChecklistSearchService
                .getActiveChecklists(webUtility.getLoggedInUserKey(session), pageCriteria);
        return populateSinglePageExecutionChecklistResult(resultPage);
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
        final ResultPage resultPage = executionChecklistSearchService
                .getRecentlyCompletedChecklists(webUtility.getLoggedInUserKey(session), pageCriteria);
        return populateSinglePageExecutionChecklistResult(resultPage);
    }

    @SuppressWarnings("unchecked")
    private SinglePageResult populateSinglePageChecklistResult(ResultPage resultPage) {
        final List<Checklist> checklists = (List<Checklist>) resultPage.getResults();
        final List<DashboardChecklistViewHelper> viewHelpers = createChecklistViewHelpers(checklists);
        resultPage.setResults(viewHelpers);
        logger.debug("Found %s checklists.", viewHelpers.size());
        return new SinglePageResult(resultPage);
    }
    
    @SuppressWarnings("unchecked")
    private SinglePageResult populateSinglePageExecutionChecklistResult(ResultPage resultPage) {
        final List<ExecutionChecklist> checklists = (List<ExecutionChecklist>) resultPage.getResults();
        final List<DashboardChecklistViewHelper> viewHelpers = createExecutionChecklistViewHelpers(checklists);
        resultPage.setResults(viewHelpers);
        logger.debug("Found %s execution checklists.", viewHelpers.size());
        return new SinglePageResult(resultPage);
    }


    private List<DashboardChecklistViewHelper> createChecklistViewHelpers(List<Checklist> checklists) {
        final List<DashboardChecklistViewHelper> viewHelpers = new ArrayList<DashboardChecklistViewHelper>();
        for (Checklist checklist : checklists) {
            final DashboardChecklistViewHelper viewHelper = createChecklistViewHelper(checklist);
            viewHelpers.add(viewHelper);
        }
        return viewHelpers;
    }
    
    private List<DashboardChecklistViewHelper> createExecutionChecklistViewHelpers(
            List<ExecutionChecklist> checklists) {
        final List<DashboardChecklistViewHelper> viewHelpers = new ArrayList<DashboardChecklistViewHelper>();
        for (ExecutionChecklist checklist : checklists) {
            final DashboardChecklistViewHelper viewHelper = createExecutionChecklistViewHelper(checklist);
            viewHelpers.add(viewHelper);
        }
        return viewHelpers;
    }

    private DashboardChecklistViewHelper createChecklistViewHelper(Checklist checklist) {
        final DashboardChecklistViewHelper viewHelper = new DashboardChecklistViewHelper();
        viewHelper.setName(checklist.getName());
        viewHelper.setNumSteps(checklist.getNumSteps());
        viewHelper.setObjectKey(checklist.getObjectKey());
        viewHelper.setOwnerName(checklist.getOwner().getName());
        viewHelper.setDuration(checklist.getExpectedDurationInMinutes() + " min");
        return viewHelper;
    }
    
    private DashboardChecklistViewHelper createExecutionChecklistViewHelper(ExecutionChecklist checklist) {
        final DashboardChecklistViewHelper viewHelper = new DashboardChecklistViewHelper();
        viewHelper.setName(checklist.getName());
        viewHelper.setNumSteps(checklist.getChecklist().getNumSteps());
        viewHelper.setObjectKey(checklist.getObjectKey());
        viewHelper.setOwnerName(checklist.getExecutor().getName());
        final ChecklistExecutionStatus status = checklist.getStatus();
        String imageUrl = getCheckmarkImage(status);
        if (status == ChecklistExecutionStatus.IN_PROGRESS) {
            imageUrl = getCheckmarkImage(checklist.getCompletionStatus());
        }
        viewHelper.setStatusImageURL(imageUrl);
        viewHelper.setCurrentStep(checklist.getCurrentExecutionStepDescription());
        viewHelper.setStatus(checklist.getStatus().toString());
        viewHelper.setEstimatedCompletionTime(DateFormatUtility.formatDate(checklist.getEstimatedCompletion()));
        viewHelper.setActualCompletionTime(DateFormatUtility.formatDate(checklist.getExecutionEnd()));
        return viewHelper;
    }
        
    private String getCheckmarkImage(ChecklistExecutionStatus status) {
        String imageName = WebConstants.CHECKMARK_RED_IMG;
        if (status == ChecklistExecutionStatus.PASSED) {
            imageName = WebConstants.CHECKMARK_GREEN_IMG;
        }
        return imageName;
    }
        
    private String getCheckmarkImage(EstimatedCompletionStatus status) {
        String imageName = WebConstants.CHECKMARK_RED_IMG;
        if (status == EstimatedCompletionStatus.LATE) {
            imageName = WebConstants.CHECKMARK_YELLOW_IMG;
        } else if (status == EstimatedCompletionStatus.ON_TIME) {
            imageName = WebConstants.CHECKMARK_GREEN_IMG;
        }
        return imageName;
    }
}
