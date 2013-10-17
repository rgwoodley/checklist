package com.codeapes.checklist.web.dashboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.service.ChecklistService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.web.util.WebSecurityConstants;
import com.codeapes.checklist.web.util.WebUtility;
import com.codeapes.checklist.web.viewhelper.checklist.ChecklistSummaryViewHelper;

@Controller
public class DashboardController {

    private static final AppLogger logger = new AppLogger(DashboardController.class); // NOSONAR

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private WebUtility webUtil;

    @RequestMapping(method = RequestMethod.GET, value = "/dashboard")
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    public String displayDashboard(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "dashboard";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getChecklist/{id}")
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    @ResponseBody
    public ChecklistSummaryViewHelper getChecklist(@PathVariable final Long id, final Model model) {
        logger.debug("Get checklist id: %s", id);
        final Checklist checklist = checklistService.getChecklistByKey(id);
        final ChecklistSummaryViewHelper checklistViewHelper = new ChecklistSummaryViewHelper(Checklist.class);
        checklistViewHelper.populate(checklist);
        return checklistViewHelper;
    }
}
