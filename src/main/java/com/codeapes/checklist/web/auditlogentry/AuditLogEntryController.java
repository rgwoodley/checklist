package com.codeapes.checklist.web.auditlogentry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeapes.checklist.domain.audit.AuditLogEntry;
import com.codeapes.checklist.service.PersistenceService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.web.util.WebSecurityConstants;
import com.codeapes.checklist.web.util.WebUtility;

@Controller
public class AuditLogEntryController {

    private static final String ADD_LOG_ENTRY_VIEW = "addLogEntry";
    private static final String VIEW_LOG_ENTRY_VIEW = "viewLogEntry";
    private static final String FORM_NAME = "form";

    private static final AppLogger logger = new AppLogger(AuditLogEntryController.class); // NOSONAR

    @Autowired
    private Validator validator;

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private WebUtility webUtility;

    @RequestMapping(method = RequestMethod.GET, value = "/createLogEntry")
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    public String initLogEntryForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        final AuditLogEntryForm form = new AuditLogEntryForm();
        model.addAttribute(FORM_NAME, form);
        logger.debug("rendering entry form page");
        return ADD_LOG_ENTRY_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createLogEntry")
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    public String createLogEntry(@ModelAttribute("form") AuditLogEntryForm form, HttpServletRequest request,
        HttpServletResponse response, BindingResult result, Model model) {

        validator.validate(form, result);

        performAdditionalValidation(form, result);

        if (!result.hasErrors()) {
            final AuditLogEntry entry = new AuditLogEntry();
            entry.setDetail(form.getDetail());
            entry.setAction(form.getAction());
            entry.setType(form.getType());
            entry.setModifiedBy(form.getDetail());
            persistenceService.saveObject(entry, webUtility.getLoggedInUsername());
        }

        return ADD_LOG_ENTRY_VIEW;
    }

    private void performAdditionalValidation(AuditLogEntryForm form, BindingResult result) {

        if ("TESTG".equalsIgnoreCase(form.getAction())) {
            final FieldError error = new FieldError(FORM_NAME, "action", "Action cannot be TEST");
            result.addError(error);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/viewLogEntry")
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    public String initLogEntryView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return VIEW_LOG_ENTRY_VIEW;
    }

}
