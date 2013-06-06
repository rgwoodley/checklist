package com.codeapes.checklist.service.job.impl;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.domain.job.ScheduledJob;
import com.codeapes.checklist.service.job.JobService;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ChecklistException;
import com.codeapes.checklist.util.PropertyConstants;

@Service(value = "jobService")
@Transactional
public class QuartzJobServiceImpl implements JobService, InitializingBean, DisposableBean {

    private static final AppLogger logger = new AppLogger(QuartzJobServiceImpl.class); // NOSONAR

    @Autowired
    private PersistenceDAO persistenceDAO;

    @Value (PropertyConstants.STARTUP_SCHEDULEDJOBS_ENABLED_REPLACEMENT_STRING)
    private boolean initializeAtStartup;

    private Scheduler scheduler;

    @Override
    public void afterPropertiesSet() {
        if (initializeAtStartup) {
            logger.info("Initializing Quartz Job Scheduler");
            initialize();
            logger.info("Quartz Job Scheduler Initialization Complete.");
        } else {
            logger.info("Quartz Job Scheduler not initialized by configuration.");
        }
    }

    @Override
    public void initialize() {

        startJobScheduler();

        @SuppressWarnings("unchecked")
        final List<ScheduledJob> jobs = (List<ScheduledJob>) persistenceDAO.find(JobServiceQueries.FIND_ALL_JOBS_QUERY);
        if (jobs != null) {
            for (ScheduledJob scheduledJob : jobs) {
                logger.info("Attempting to schedule job: %s", scheduledJob.getName());
                scheduleJob(scheduledJob);
            }
        }
    }

    private void startJobScheduler() {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException se) {
            logger.error("failure starting Quartz Job Scheduler.");
            throw new ChecklistException(se.getMessage(), se);
        }
    }

    @Override
    public void scheduleJob(ScheduledJob scheduledJob) {
        final String triggerName = generateTriggerName(scheduledJob.getName());
        final JobDetail jobDetail = newJob(QuartzJob.class)
                .withIdentity(scheduledJob.getName(), scheduledJob.getGroup())
                .usingJobData(QuartzJob.JOB_BEAN_NAME, scheduledJob.getJobBeanName())
                .usingJobData(QuartzJob.JOB_NAME, scheduledJob.getName()).build();
        final Trigger trigger = newTrigger().withIdentity(triggerName, scheduledJob.getGroup()).startNow()
                .withSchedule(cronSchedule(scheduledJob.getCronInfo())).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("  Job %s scheduled.  Schedule: %s", scheduledJob.getName(), scheduledJob.getCronInfo());
        } catch (SchedulerException se) {
            logger.error("Error starting scheduled job: %s", scheduledJob.getName());
            logger.error("Scheduling Failure error message: %s", se.getMessage());
        }
    }

    private String generateTriggerName(String jobName) {
        final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        final StringBuilder triggerName = new StringBuilder();
        triggerName.append(jobName);
        triggerName.append(dateFormatter.format(new Date()));
        return triggerName.toString();
    }

    @Override
    public void destroy() {
        shutdown();
    }

    private void shutdown() {
        try {
            if (scheduler != null && scheduler.isStarted()) {
                scheduler.shutdown();
            }
        } catch (SchedulerException se) {
            logger.error("failure stopping/shutting down Quartz Job Scheduler.");
            throw new ChecklistException(se.getMessage(), se);
        }
    }
}
