package com.codeapes.checklist.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.codeapes.checklist.util.AppLogger;

@Controller
public class LoginController {

    private static final AppLogger logger = new AppLogger(LoginController.class); // NOSONAR

    @RequestMapping("/login")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("forwarding to login page.");
        return new ModelAndView("login", null);
    }
}
