package com.codeapes.checklist.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import com.codeapes.checklist.domain.user.User;
import com.codeapes.checklist.service.user.UserService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.web.util.WebConstants;

public class ChecklistDispatcherServlet extends DispatcherServlet {

    private static final long serialVersionUID = 6003897565098681389L;
    private static final AppLogger logger = new AppLogger(ChecklistDispatcherServlet.class);
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        final ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this
            .getServletContext());
        userService = (UserService) context.getBean("userService");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        final HttpSession session = request.getSession();
        final Long userObjectKey = (Long)session.getAttribute(WebConstants.LOGGED_IN_USER_KEY);
        if (userObjectKey == null) {
            final Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (obj != null && obj instanceof org.springframework.security.core.userdetails.User) {
                final String username = ((org.springframework.security.core.userdetails.User) obj).getUsername();
                final User user = userService.findUserByUsername(username);     
                session.setAttribute(WebConstants.LOGGED_IN_USER_KEY, user.getObjectKey());
                logger.info("User %s with id of %d has logged in.", user.getUsername(), user.getObjectKey());
            }
        }
        super.service(request, response);
    }

}
