package com.codeapes.checklist.domain.execution;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.codeapes.checklist.domain.persistence.PersistentImpl;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.OwnerExecutorImpl;

@Entity
@Table(name = "cl_execution_checklist")
public class ExecutionChecklist extends PersistentImpl {

    private static final long serialVersionUID = 1459216950038011721L;

    private String name;
    private String description;
    private long expectedDurationInMinutes;
    private String comments;
    private Date executionStart;
    private Date executionEnd;
    private ChecklistExecutionStatus status;
    private Checklist checklist;
    private OwnerExecutor executor;
    private List<ExecutionStep> steps;

    @Column(name = "name", length = 50, nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    @Column(name = "execution_start")
    public Date getExecutionStart() {
        return executionStart;
    }

    @Column(name = "comments", length = 1500)
    public String getComments() {
        return comments;
    }

    @Column(name = "exp_duration_min", nullable = false)
    public long getExpectedDurationInMinutes() {
        return expectedDurationInMinutes;
    }

    @Column(name = "execution_end")
    public Date getExecutionEnd() {
        return executionEnd;
    }

    @ManyToOne
    @JoinColumn(name = "checklist_key")
    public Checklist getChecklist() {
        return checklist;
    }

    @Column(name = "status", length = 25)
    @Enumerated(EnumType.STRING)
    public ChecklistExecutionStatus getStatus() {
        return status;
    }

    @OneToMany(mappedBy = "executionChecklist")
    public List<ExecutionStep> getSteps() {
        return steps;
    }

    @ManyToOne (targetEntity = OwnerExecutorImpl.class)
    @JoinColumn(name = "owner_key")
    public OwnerExecutor getExecutor() {
        return executor;
    }

    public void setExpectedDurationInMinutes(long expectedDurationInMinutes) {
        this.expectedDurationInMinutes = expectedDurationInMinutes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setExecutionStart(Date executionStart) {
        this.executionStart = executionStart;
    }

    public void setExecutionEnd(Date executionEnd) {
        this.executionEnd = executionEnd;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public void setExecutor(OwnerExecutor executor) {
        this.executor = executor;
    }

    public void setStatus(ChecklistExecutionStatus status) {
        this.status = status;
    }

    public void setSteps(List<ExecutionStep> steps) {
        this.steps = steps;
    }
}
