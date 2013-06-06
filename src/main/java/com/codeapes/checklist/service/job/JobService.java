package com.codeapes.checklist.service.job;

import com.codeapes.checklist.domain.job.ScheduledJob;

public interface JobService {

    void initialize();
    
    void scheduleJob(ScheduledJob scheduledJob);
}
