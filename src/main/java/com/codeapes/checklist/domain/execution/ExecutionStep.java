package com.codeapes.checklist.domain.execution;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.codeapes.checklist.domain.persistence.PersistentImpl;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.OwnerExecutorImpl;
import com.codeapes.checklist.domain.user.User;

@Entity
@Table(name = "cl_execution_step") // NOSONAR
public class ExecutionStep extends PersistentImpl {

    private static final long serialVersionUID = 8031885454795882054L;

    private String name;
    private String description;
    private String comments;
    private long expectedDurationInMinutes;
    private Date executionStart;
    private Date executionEnd;
    private StepExecutionStatus status;
    private ExecutionChecklist executionChecklist;
    private OwnerExecutor executor;
    private User executedBy;
    private List<ExecutionStep> preConditions;

    @Column(name = "name", length = 50, nullable = false) // NOSONAR
    public String getName() {
        return name;
    }

    @ManyToMany
    @JoinTable(name = "cl_exec_step_precondition", joinColumns = { @JoinColumn(name = "exec_step_key") },
        inverseJoinColumns = { @JoinColumn(name = "exec_step_precondition_key") }) // NOSONAR
    public List<ExecutionStep> getPreConditions() {
        return preConditions;
    }

    @Column(name = "description", length = 500, nullable = true) // NOSONAR
    public String getDescription() {
        return description;
    }

    @Column(name = "status", length = 25, nullable = false) // NOSONAR
    @Enumerated(EnumType.STRING)
    public StepExecutionStatus getStatus() {
        return status;
    }

    @Column(name = "exp_duration_min", nullable = false) // NOSONAR
    public long getExpectedDurationInMinutes() {
        return expectedDurationInMinutes;
    }

    @Column(name = "execution_end") // NOSONAR
    public Date getExecutionEnd() {
        return executionEnd;
    }

    @Column(name = "comments", length = 1500) // NOSONAR
    public String getComments() {
        return comments;
    }

    @Column(name = "execution_start") // NOSONAR
    public Date getExecutionStart() {
        return executionStart;
    }

    @OneToOne
    @JoinColumn(name = "executed_by_key") // NOSONAR
    public User getExecutedBy() {
        return executedBy;
    }

    @ManyToOne
    @JoinColumn(name = "execution_checklist_key") // NOSONAR
    public ExecutionChecklist getExecutionChecklist() {
        return executionChecklist;
    }

    @ManyToOne (targetEntity = OwnerExecutorImpl.class)
    @JoinColumn(name = "executor_key")
    public OwnerExecutor getExecutor() {
        return executor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpectedDurationInMinutes(long expectedDurationInMinutes) {
        this.expectedDurationInMinutes = expectedDurationInMinutes;
    }

    public void setExecutionStart(Date executionStart) {
        this.executionStart = executionStart;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setExecutionEnd(Date executionEnd) {
        this.executionEnd = executionEnd;
    }

    public void setPreConditions(List<ExecutionStep> preConditions) {
        this.preConditions = preConditions;
    }

    public void setExecutedBy(User executedBy) {
        this.executedBy = executedBy;
    }

    public void setStatus(StepExecutionStatus status) {
        this.status = status;
    }

    public void setExecutor(OwnerExecutor executor) {
        this.executor = executor;
    }

    public void setExecutionChecklist(ExecutionChecklist executionChecklist) {
        this.executionChecklist = executionChecklist;
    }

    public void setName(String name) {
        this.name = name;
    }

}
