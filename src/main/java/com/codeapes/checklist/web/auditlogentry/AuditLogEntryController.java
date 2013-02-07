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

import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.domain.audit.AuditLogEntry;
import com.codeapes.checklist.web.util.WebUtility;

@Controller
public class AuditLogEntryController {

    private static final String LOG_ENTRY_VIEW = "logEntryForm";
    private static final String FORM_NAME = "form";
    
    @Autowired
    private Validator validator;

    @Autowired
    private PersistenceDAO persistenceDAO;

    @Autowired
    private WebUtility webUtility;

    @RequestMapping(method = RequestMethod.GET, value = "/createLogEntry")
    @PreAuthorize("hasRole('USER')")
    public String initLogEntryForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        final AuditLogEntryForm form = new AuditLogEntryForm();
        model.addAttribute(FORM_NAME, form);
        return LOG_ENTRY_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createLogEntry")
    @PreAuthorize("hasRole('USER')")
    public String createLogEntry(@ModelAttribute("form") AuditLogEntryForm form, HttpServletRequest request,
        HttpServletResponse response, BindingResult result, Model model) {

        validator.validate(form, result);
        
        performAdditionalValidation(form, result);

        if (!result.hasErrors()) {
            final AuditLogEntry entry = new AuditLogEntry();
            entry.setAction(form.getAction());
            entry.setType(form.getType());
            entry.setModifiedBy(form.getDetail());
            persistenceDAO.saveObject(entry, webUtility.getLoggedInUsername());
        }

        return LOG_ENTRY_VIEW;
    }

    private void performAdditionalValidation(AuditLogEntryForm form, BindingResult result) {

        if ("TESTINGTESTING".equalsIgnoreCase(form.getAction())) {
            final FieldError error = new FieldError(FORM_NAME, "action", "Action cannot be TEST");
            result.addError(error);
        }

    }

    // Autowire a validator
    // Autowire service
    // Get & Post methods

    // BindingResult necessary for validate
    // show extending validation
    // create FieldError instance
    // add FieldError to result

    // Sample validation: NotEmpty (message="Not empty.")
    // Length (max=50, message="Max of 50 characters.")

    // <form:form commandName="form" name="form" id="form" action="url"
    // method="POST">

    // <spring:hasBindErrors name="form">
    // <span class="error">general message</span>
    // </spring:hasBindErrors>

    // <form:errors path="field" cssClass="error"/>

    // <form:input id="field" path="field" tabIndex="1" maxLength="50"/>

    // </form:form>
}
