package com.codeapes.checklist.service.job.impl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.codeapes.checklist.job.JobControllable;
import com.codeapes.checklist.job.JobResult;
import com.codeapes.checklist.job.JobStatus;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.SpringApplicationContext;

public class QuartzJob implements Job {

    public static final String JOB_BEAN_NAME = "jobBeanName";
    public static final String JOB_NAME = "jobName";

    private static final AppLogger logger = new AppLogger(QuartzJob.class); // NOSONAR

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        final String jobBeanName = context.getJobDetail().getJobDataMap().getString(JOB_BEAN_NAME);
        final String jobName = context.getJobDetail().getJobDataMap().getString(JOB_NAME);
        logger.info("Starting execution of job: %s", jobName);

        final ApplicationContext springContext = SpringApplicationContext.getInstance().getAppContext();
        final JobControllable controllable = (JobControllable) springContext.getBean(jobBeanName);

        final JobResult jobResult = controllable.execute();
        if (jobResult.getStatus() == JobStatus.FAILED) {
            logger.error("Job Failure in job %s.  Details: %s", jobName, jobResult.getDescription());
        }

        logger.info("Job execution complete for job: %s", jobName);

    }
}
