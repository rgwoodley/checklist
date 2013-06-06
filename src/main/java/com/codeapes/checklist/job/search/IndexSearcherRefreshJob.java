package com.codeapes.checklist.job.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codeapes.checklist.job.JobConstants;
import com.codeapes.checklist.job.JobControllable;
import com.codeapes.checklist.job.JobResult;
import com.codeapes.checklist.job.JobStatus;
import com.codeapes.checklist.service.search.SearchService;

@Component(value = JobConstants.SEARCH_INDEX_REFRESH_JOB)
public class IndexSearcherRefreshJob implements JobControllable {

    @Autowired
    private SearchService searchService;
    
    @Override
    public JobResult execute() {
        searchService.refreshIndexSearcher();
        final JobResult result = new JobResult();
        result.setDescription(JobStatus.SUCCEEDED.toString());
        result.setStatus(JobStatus.SUCCEEDED);
        return result;
    }

}
