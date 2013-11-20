package com.codeapes.checklist.domain.job;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.codeapes.checklist.domain.persistence.impl.PersistentEntity;

@Entity
@Table(name = "cl_scheduled_job") // NOSONAR
public class ScheduledJob extends PersistentEntity {

    private static final long serialVersionUID = 2665027165330699711L;

    private String name;
    private String group;
    private String description;
    private String jobBeanName;
    private String cronInfo;
    private boolean active;

    @Column(name = "name", length = 50, nullable = false) // NOSONAR
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 200, nullable = false) // NOSONAR
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "job_bean_name", length = 200, nullable = false) // NOSONAR
    public String getJobBeanName() {
        return jobBeanName;
    }

    public void setJobBeanName(String jobBeanName) {
        this.jobBeanName = jobBeanName;
    }

    @Column(name = "cron_format_schedule", length = 25, nullable = false) // NOSONAR
    public String getCronInfo() {
        return cronInfo;
    }

    public void setCronInfo(String cronInfo) {
        this.cronInfo = cronInfo;
    }

    @Column(name = "active", nullable = false) // NOSONAR
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Column(name = "group_name", length = 30, nullable = false) // NOSONAR
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
