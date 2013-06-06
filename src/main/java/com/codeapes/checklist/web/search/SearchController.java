package com.codeapes.checklist.web.search;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeapes.checklist.domain.search.SearchResult;
import com.codeapes.checklist.service.search.SearchService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.web.util.WebSecurityConstants;
import com.codeapes.checklist.web.viewhelper.ViewHelperUtility;
import com.codeapes.checklist.web.viewhelper.search.SearchResultViewHelper;

@Controller
public class SearchController {

    private static final AppLogger logger = new AppLogger(SearchController.class); // NOSONAR
    
    @Autowired
    private SearchService searchService;

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    @PreAuthorize(WebSecurityConstants.USER_ROLE)
    public String processSearch(HttpServletRequest request,
            HttpServletResponse response, Model model) {

        logger.debug("Search controller invoked.");
        final String searchText = (String)request.getParameter("searchText");
        logger.debug("Search text: %s", searchText);
        final int resultsPerPage = 25;
        final List<SearchResult> results = searchService.search(searchText, resultsPerPage);
        @SuppressWarnings("unchecked")
        final List<SearchResultViewHelper> resultsViewHelper = (List<SearchResultViewHelper>) ViewHelperUtility
                .convertList(results, SearchResultViewHelper.class);
        model.addAttribute("searchResults", resultsViewHelper);
        return "searchresult";
    }

}
