package com.codeapes.checklist.domain.template;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.codeapes.checklist.domain.persistence.PersistentImpl;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.OwnerExecutorImpl;

@Entity
@Table(name = "cl_step")
public class Step extends PersistentImpl {

    private static final long serialVersionUID = -6032664071847503025L;

    private String name;
    private String description;
    private long expectedDurationInMinutes;
    private List<Step> preConditions;
    private OwnerExecutor defaultExecutor;
    private Checklist checklist;

    @Column(name = "name", length = 50, nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "description", length = 500, nullable = true)
    public String getDescription() {
        return description;
    }

    @ManyToOne
    @JoinColumn(name = "checklist_key")
    public Checklist getChecklist() {
        return checklist;
    }

    @Column(name = "exp_duration_min", nullable = false)
    public long getExpectedDurationInMinutes() {
        return expectedDurationInMinutes;
    }

    @ManyToMany
    @JoinTable(name = "cl_step_precondition", joinColumns = { @JoinColumn(name = "step_key") },
            inverseJoinColumns = { @JoinColumn(name = "step_precondition_key") })
    public List<Step> getPreConditions() {
        return preConditions;
    }

    @ManyToOne (targetEntity = OwnerExecutorImpl.class)
    @JoinColumn(name = "default_executor_key")
    public OwnerExecutor getDefaultExecutor() {
        return defaultExecutor;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public void setPreConditions(List<Step> preConditions) {
        this.preConditions = preConditions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpectedDurationInMinutes(long expectedDurationInMinutes) {
        this.expectedDurationInMinutes = expectedDurationInMinutes;
    }

    public void setDefaultExecutor(OwnerExecutor defaultExecutor) {
        this.defaultExecutor = defaultExecutor;
    }

}
