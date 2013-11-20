package com.codeapes.checklist.web.util;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.codeapes.checklist.domain.user.User;
import com.codeapes.checklist.service.user.UserService;

@Service (value = "webUtility")
public final class WebUtil {

    @Autowired
    private UserService userService;

    public String getLoggedInUsername() {

        final Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((org.springframework.security.core.userdetails.User)obj).getUsername();
    }

    public User getLoggedInUser() {
        return userService.findUserByUsername(getLoggedInUsername());
    }
    
    public Long getLoggedInUserKey(HttpSession session) {
        return (Long)session.getAttribute(WebConstants.LOGGED_IN_USER_KEY);
    }
}
