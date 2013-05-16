package com.codeapes.checklist.web.search;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeapes.checklist.domain.search.SearchResult;
import com.codeapes.checklist.service.search.SearchService;
import com.codeapes.checklist.web.viewhelper.ViewHelperUtility;
import com.codeapes.checklist.web.viewhelper.search.SearchResultViewHelper;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    @PreAuthorize("hasRole('USER')")
    public String displayDashboard(@ModelAttribute("form") SearchForm form, HttpServletRequest request,
            HttpServletResponse response, BindingResult result, Model model) {

        final String searchText = form.getSearchText();
        final int resultsPerPage = form.getResultsPerPage();
        final List<SearchResult> results = searchService.search(searchText, resultsPerPage);
        @SuppressWarnings("unchecked")
        final List<SearchResultViewHelper> resultsViewHelper = (List<SearchResultViewHelper>) ViewHelperUtility
                .convertList(results, SearchResultViewHelper.class);
        model.addAttribute("searchResults", resultsViewHelper);
        model.addAttribute("searchForm", form);
        return "searchresult";
    }

}
