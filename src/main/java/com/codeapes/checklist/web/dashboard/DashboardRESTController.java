package com.codeapes.checklist.web.dashboard;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.ChecklistStatus;
import com.codeapes.checklist.domain.CompletionStatus;
import com.codeapes.checklist.service.checklist.ChecklistSearchService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ApplicationProperties;
import com.codeapes.checklist.util.constants.DateTimeConstants;
import com.codeapes.checklist.util.query.PagingQueryCriteria;
import com.codeapes.checklist.util.query.ResultPage;
import com.codeapes.checklist.util.query.SortOrder;
import com.codeapes.checklist.web.util.SinglePageResult;
import com.codeapes.checklist.web.util.WebConstants;
import com.codeapes.checklist.web.util.WebSecurityConstants;
import com.codeapes.checklist.web.util.WebUtil;

@Controller
public class DashboardRESTController {

    private static final AppLogger logger = new AppLogger(DashboardRESTController.class); // NOSONAR
        
    @Autowired
    private ChecklistSearchService checklistSearchService;
    
    @Autowired
    private WebUtil webUtility;
    
    @Autowired
    private ApplicationProperties appProperties;

    @RequestMapping(method = RequestMethod.GET, value = "/myChecklists")
    @ResponseBody
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    public SinglePageResult getUserChecklists(@RequestParam final int perPage, @RequestParam final int page,
            @RequestParam final String orderBy, @RequestParam final String sortOrder, final HttpSession session,
            final Model model) {

        logger.debug("Retrieve owned checklists for user %s", webUtility.getLoggedInUsername());
        final PagingQueryCriteria pageCriteria = new PagingQueryCriteria(page, perPage, orderBy,
                SortOrder.fromString(sortOrder));
        final ResultPage resultPage = checklistSearchService.getOwnedChecklists(webUtility.getLoggedInUserKey(session),
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
        final ResultPage resultPage = checklistSearchService
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
        final ResultPage resultPage = checklistSearchService
                .getRecentlyCompletedChecklists(webUtility.getLoggedInUserKey(session), pageCriteria);
        return populateSinglePageExecutionChecklistResult(resultPage);
    }

    @SuppressWarnings("unchecked")
    private SinglePageResult populateSinglePageChecklistResult(ResultPage resultPage) {
        final List<Checklist> checklists = (List<Checklist>) resultPage.getResults();
        final List<DashboardChecklistDTO> viewHelpers = createChecklistViewHelpers(checklists);
        resultPage.setResults(viewHelpers);
        logger.debug("Found %s checklists.", viewHelpers.size());
        return new SinglePageResult(resultPage);
    }
    
    @SuppressWarnings("unchecked")
    private SinglePageResult populateSinglePageExecutionChecklistResult(ResultPage resultPage) {
        final List<Checklist> checklists = (List<Checklist>) resultPage.getResults();
        final List<DashboardChecklistDTO> viewHelpers = createExecutionChecklistViewHelpers(checklists);
        resultPage.setResults(viewHelpers);
        logger.debug("Found %s execution checklists.", viewHelpers.size());
        return new SinglePageResult(resultPage);
    }

    private List<DashboardChecklistDTO> createChecklistViewHelpers(List<Checklist> checklists) {
        final List<DashboardChecklistDTO> viewHelpers = new ArrayList<DashboardChecklistDTO>();
        for (Checklist checklist : checklists) {
            final DashboardChecklistDTO viewHelper = createChecklistViewHelper(checklist);
            viewHelpers.add(viewHelper);
        }
        return viewHelpers;
    }
    
    private List<DashboardChecklistDTO> createExecutionChecklistViewHelpers(
            List<Checklist> checklists) {
        final List<DashboardChecklistDTO> viewHelpers = new ArrayList<DashboardChecklistDTO>();
        for (Checklist checklist : checklists) {
            final DashboardChecklistDTO viewHelper = createChecklistViewHelper(checklist);
            viewHelpers.add(viewHelper);
        }
        return viewHelpers;
    }

    protected DashboardChecklistDTO createChecklistViewHelper(Checklist checklist) {
        final DashboardChecklistDTO checklistDTO = new DashboardChecklistDTO();
        if (checklist != null) {
            checklistDTO.setName(checklist.getName());
            checklistDTO.setNumSteps(checklist.getNumSteps());
            checklistDTO.setObjectKey(checklist.getObjectKey());
            checklistDTO.setOwnerName(checklist.getOwner().getName());
            checklistDTO.setDuration(checklist.getExpectedDurationInMinutes() + " min");
            if (checklist.getExecutionInfo() != null) {
                populateExecutionInfo(checklist, checklistDTO);
            }
        }
        return checklistDTO;
    }
    
    protected void populateExecutionInfo(Checklist checklist, DashboardChecklistDTO viewHelper) {
        if (checklist != null) {
            final ChecklistStatus status = checklist.getStatus();
            String imageUrl = getCheckmarkImage(status);
            if (status == ChecklistStatus.IN_PROGRESS) {
                imageUrl = getCheckmarkImage(checklist.getExecutionInfo()
                        .getCompletionStatus(appProperties.getPercentCompleteThreshold()));
            }
            viewHelper.setStatusImageURL(imageUrl);
            viewHelper.setCurrentStep("NONE-- GOT TO FIX THIS!");
            viewHelper.setStatus(checklist.getStatus().toString());
            viewHelper.setEstimatedCompletionTime(DateFormatUtils
                    .format(checklist.getExecutionInfo().getEstimatedCompletion()
                            , DateTimeConstants.FULL_DATE_TIME_FORMAT));
            viewHelper.setActualCompletionTime(DateFormatUtils
                    .format(checklist.getExecutionInfo().getExecutionEnd()
                            , DateTimeConstants.FULL_DATE_TIME_FORMAT));
        }
    }
        
    private String getCheckmarkImage(ChecklistStatus status) {
        String imageName = WebConstants.CHECKMARK_BLACK_IMG;
        if (status == ChecklistStatus.COMPLETE) {
            imageName = WebConstants.CHECKMARK_GREEN_IMG;
        }
        return imageName;
    }
        
    private String getCheckmarkImage(CompletionStatus status) {
        String imageName = WebConstants.CHECKMARK_RED_IMG;
        if (status == CompletionStatus.LATE) {
            imageName = WebConstants.CHECKMARK_YELLOW_IMG;
        } else if (status == CompletionStatus.ON_TIME) {
            imageName = WebConstants.CHECKMARK_GREEN_IMG;
        }
        return imageName;
    }
}
