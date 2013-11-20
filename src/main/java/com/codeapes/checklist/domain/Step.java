package com.codeapes.checklist.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.codeapes.checklist.domain.persistence.impl.PersistentEntity;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.OwnerExecutorImpl;
import com.codeapes.checklist.util.AppLogger;

@Entity
@Table(name = "cl_step") // NOSONAR
public class Step extends PersistentEntity {

    private static final long serialVersionUID = -6032664071847503025L;
    private static final AppLogger logger = new AppLogger(Step.class); // NOSONAR
    
    private String name;
    private String description;
    private int expectedDurationInMinutes;
    private List<Step> preConditions;
    private List<Step> dependencyOf;
    private int preconditionTotalDuration;
    private OwnerExecutor executor;
    private Checklist checklist;
    private StepStatus status;
    private StepExecutionInfo executionInfo;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "execution_info_key") // NOSONAR
    public StepExecutionInfo getExecutionInfo() {
        return executionInfo;
    }
    
    @Column(name = "name", length = 50, nullable = false) // NOSONAR
    public String getName() {
        return name;
    }

    @Column(name = "description", length = 500, nullable = true) // NOSONAR
    public String getDescription() {
        return description;
    }

    @ManyToOne
    @JoinColumn(name = "checklist_key") // NOSONAR
    public Checklist getChecklist() {
        return checklist;
    }

    @Column(name = "exp_duration_min", nullable = false) // NOSONAR
    public int getExpectedDurationInMinutes() {
        return expectedDurationInMinutes;
    }

    @ManyToMany
    @JoinTable(name = "cl_step_precondition", joinColumns = { @JoinColumn(name = "step_key") },
            inverseJoinColumns = { @JoinColumn(name = "step_precondition_key") }) // NOSONAR
    @OrderColumn(name = "step_order")
    private List<Step> getPreConditions() {
        return preConditions;
    }
    
    @ManyToMany(mappedBy="preConditions")  // NOSONAR
    protected List<Step> getDependencyOf() {
        return dependencyOf;
    }
    
    @Transient
    public List<Step> getPreConditionsList() {
        if (preConditions == null) {
            return null;
        }
        return Collections.unmodifiableList(getPreConditions());
    }

    @ManyToOne (targetEntity = OwnerExecutorImpl.class)
    @JoinColumn(name = "executor_key") // NOSONAR
    public OwnerExecutor getExecutor() {
        return executor;
    }
    
    @Column(name = "status", length = 25, nullable = false) // NOSONAR
    @Enumerated(EnumType.STRING)
    public StepStatus getStatus() {
        return status;
    }
    
    @Column(name = "precondition_total_duration", nullable = false) // NOSONAR
    public int getPreconditionTotalDuration() {
        return preconditionTotalDuration;
    }
    
    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    @SuppressWarnings("unused")
    private void setPreConditions(List<Step> preConditions) {
        this.preConditions = preConditions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpectedDurationInMinutes(int expectedDurationInMinutes) {
        this.expectedDurationInMinutes = expectedDurationInMinutes;
        StepTimeCalculator.recalculatePreconditionTime(this);
    }

    public void setExecutor(OwnerExecutor executor) {
        this.executor = executor;
    }
    
    public void setExecutionInfo(StepExecutionInfo executionInfo) {
        this.executionInfo = executionInfo;
    }
    
    public void setStatus(StepStatus status) {
        this.status = status;
    }
    
    public void setPreconditionTotalDuration(int preconditionTotalDuration) {
        this.preconditionTotalDuration = preconditionTotalDuration;
    }

    public void addPreCondition(Step step) {
        PreconditionCycleDetector.checkPreconditionAddForCycle(this, step);
        if (preConditions == null) {
            preConditions = new ArrayList<Step>();
        }
        preConditions.add(step);
        step.addDependencyOf(this);
        logger.debug("precondition added to step %s (%s)", this.getObjectKey(), this.getName());
        StepTimeCalculator.recalculatePreconditionTime(this);
    }
    
    @Transient
    public void addDependencyOf(Step step) {
        if (dependencyOf == null) {
            dependencyOf = new ArrayList<Step>();
        }
        dependencyOf.add(step);
        logger.debug("dependency of relationship added to step %s (%s)", step.getObjectKey(), step.getName());
    }
    
    @SuppressWarnings("unused")
    private void setDependencyOf(List<Step> dependencyOf) {
        this.dependencyOf = dependencyOf;
    }
    
    public void removePreCondition(Step step) {
        if (step == null || preConditions == null) {
            return;
        }
        preConditions.remove(step);  
        step.removeDependencyOf(this);
        logger.debug("precondition removed from step %s (%s).", this.getObjectKey(), this.getName());
        StepTimeCalculator.recalculatePreconditionTime(this);
    }
    
    public void removeDependencyOf(Step step) {
        if (step == null || preConditions == null) {
            return;
        }
        dependencyOf.remove(step);
        logger.debug("dependency of replationship removed from step %s (%s).", step.getObjectKey(), step.getName());
    }
           
}
