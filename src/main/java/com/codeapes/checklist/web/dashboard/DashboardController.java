package com.codeapes.checklist.web.dashboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.web.util.WebSecurityConstants;
import com.codeapes.checklist.web.util.WebUtil;

@Controller
public class DashboardController {

    private static final AppLogger logger = new AppLogger(DashboardController.class); // NOSONAR

    @Autowired
    private WebUtil webUtil;

    @RequestMapping(method = RequestMethod.GET, value = "/dashboard")
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    public String displayDashboard(HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.debug("Rendering Dashboard for user %s", webUtil.getLoggedInUsername());
        return "dashboard";
    }
}
