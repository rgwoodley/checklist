package com.codeapes.checklist.web.dashboard;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.service.ChecklistService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.web.util.WebUtility;

@Controller
public class DashboardController {

    private static final AppLogger logger = new AppLogger(DashboardController.class);

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private WebUtility webUtil;

    @RequestMapping(method = RequestMethod.GET, value = "/dashboard")
    @PreAuthorize("hasRole('USER')")
    public ModelAndView displayDashboard(HttpServletRequest request, HttpServletResponse response) {

        final String username = webUtil.getLoggedInUsername();
        logger.debug("Display dashboard for user: %s", username);

        final List<Checklist> checklists = checklistService.getOwnedChecklistsForUser(webUtil
            .getLoggedInUserKey(request.getSession()));
        final DashboardViewHelper dashboardViewHelper = new DashboardViewHelper();
        dashboardViewHelper.setChecklists(checklists);
        logger.debug("Found %d checklists for user %s,", checklists.size(), username);

        return new ModelAndView("dashboard", "model", dashboardViewHelper);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getChecklist/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ChecklistViewHelper getChecklist(@PathVariable final Long id, final Model model) {
        logger.debug("Get checklist id: %s", id);
        final Checklist checklist = checklistService.getChecklistByKey(id);
        final ChecklistViewHelper h = new ChecklistViewHelper();
        h.setName(checklist.getName());
        return h;
    }
}
