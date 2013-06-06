package com.codeapes.checklist.service.job.impl;

public final class JobServiceQueries {

    public static final String FIND_ALL_JOBS_QUERY = "from ScheduledJob as sj where sj.active = true";

    private JobServiceQueries() {
        super();
    }
}
