package com.codeapes.checklist.domain.execution;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.codeapes.checklist.domain.persistence.PersistentImpl;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.OwnerExecutorImpl;

@Entity
@Table(name = "cl_execution_checklist") // NOSONAR
public class ExecutionChecklist extends PersistentImpl {

    private static final long serialVersionUID = 1459216950038011721L;

    private String name;
    private String description;
    private long expectedDurationInMinutes;
    private String comments;
    private Timestamp executionStart;
    private Timestamp executionEnd;
    private ChecklistExecutionStatus status;
    private Checklist checklist;
    private OwnerExecutor executor;
    private List<ExecutionStep> steps;
    private Timestamp estimatedCompletion;
    private EstimatedCompletionStatus completionStatus;
    private ExecutionStep currentlyExecutingStep;

    @Column(name = "name", length = 50, nullable = false) // NOSONAR
    public String getName() {
        return name;
    }

    @Column(name = "description", length = 500) // NOSONAR
    public String getDescription() {
        return description;
    }

    @Column(name = "execution_start") // NOSONAR
    public Date getExecutionStart() {
        return executionStart;
    }

    @Column(name = "comments", length = 1500) // NOSONAR
    public String getComments() {
        return comments;
    }

    @Column(name = "exp_duration_min", nullable = false) // NOSONAR
    public long getExpectedDurationInMinutes() {
        return expectedDurationInMinutes;
    }
    
    @Column(name = "estimated_completion", nullable = true) // NOSONAR
    public Timestamp getEstimatedCompletion() {
        return estimatedCompletion;
    }

    @Column(name = "execution_end") // NOSONAR
    public Timestamp getExecutionEnd() {
        return executionEnd;
    }

    @ManyToOne
    @JoinColumn(name = "checklist_key") // NOSONAR
    public Checklist getChecklist() {
        return checklist;
    }

    @Column(name = "status", length = 25) // NOSONAR
    @Enumerated(EnumType.STRING)
    public ChecklistExecutionStatus getStatus() {
        return status;
    }
    
    @Column(name = "completion_status", length = 25) // NOSONAR
    @Enumerated(EnumType.STRING)
    public EstimatedCompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    @OneToMany(mappedBy = "executionChecklist") // NOSONAR
    public List<ExecutionStep> getSteps() {
        return steps;
    }

    @ManyToOne (targetEntity = OwnerExecutorImpl.class)
    @JoinColumn(name = "owner_key")
    public OwnerExecutor getExecutor() {
        return executor;
    }
   
    @OneToOne
    @JoinColumn(name = "current_executing_step_key") //NOSONAR
    public ExecutionStep getCurrentlyExecutingStep() {
        return currentlyExecutingStep;
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

    public void setExecutionStart(Timestamp executionStart) {
        this.executionStart = executionStart;
    }

    public void setExecutionEnd(Timestamp executionEnd) {
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

    public void setEstimatedCompletion(Timestamp estimatedCompletion) {
        this.estimatedCompletion = estimatedCompletion;
    }

    public void setCompletionStatus(EstimatedCompletionStatus completionStatus) {
        this.completionStatus = completionStatus;
    }

    public void setCurrentlyExecutingStep(ExecutionStep currentlyExecutingStep) {
        this.currentlyExecutingStep = currentlyExecutingStep;
    }
    
    @Transient
    public String getCurrentExecutionStepDescription() {
        String currentStepDescription = null;
        final ExecutionStep step = this.getCurrentlyExecutingStep();
        if (step == null) {
            currentStepDescription  = "None";
        } else {
            final StringBuilder descriptionSB = new StringBuilder(step.getName());
            descriptionSB.append(" - ");
            descriptionSB.append(step.getExecutor().getName());
            currentStepDescription  = descriptionSB.toString();
        }
        return currentStepDescription;
    }
}
