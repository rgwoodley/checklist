package com.codeapes.checklist.web.dashboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    private static final String MESSAGE = "message";
    private static final String CONTENT = "content";
    
    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String displayTestPage1(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        model.put(MESSAGE, CONTENT);
        return "generalMessage";
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/test2")
    public String displayTestPage2(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        model.put(MESSAGE, CONTENT);
        return "generalMessage2";
    }
}
