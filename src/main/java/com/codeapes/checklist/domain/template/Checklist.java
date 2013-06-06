package com.codeapes.checklist.domain.template;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.codeapes.checklist.domain.annotation.Searchable;
import com.codeapes.checklist.domain.persistence.PersistentImpl;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.OwnerExecutorImpl;

@Entity
@Table(name = "cl_checklist") // NOSONAR
@Searchable(mapperClass = "com.codeapes.checklist.domain.search.lucene.mapper.ChecklistMapper") // NOSONAR
public class Checklist extends PersistentImpl {

    private static final long serialVersionUID = 4189418186231193325L;

    private String name;
    private String description;
    private long expectedDurationInMinutes;
    private ChecklistGroup group;
    private List<Step> steps;
    private OwnerExecutor owner;

    @ManyToOne
    @JoinColumn(name = "group_key") // NOSONAR
    public ChecklistGroup getGroup() {
        return this.group;
    }

    @OneToMany(mappedBy = "checklist") // NOSONAR
    public List<Step> getSteps() {
        return steps;
    }

    @Column(name = "name", length = 50, nullable = false, unique = true) // NOSONAR
    public String getName() {
        return name;
    }

    @Column(name = "description", length = 500) // NOSONAR
    public String getDescription() {
        return description;
    }

    @Column(name = "exp_duration_min", nullable = false) // NOSONAR
    public long getExpectedDurationInMinutes() {
        return expectedDurationInMinutes;
    }

    @ManyToOne(targetEntity = OwnerExecutorImpl.class)
    @JoinColumn(name = "owner_key") // NOSONAR
    public OwnerExecutor getOwner() {
        return owner;
    }

    public void setGroup(ChecklistGroup group) {
        this.group = group;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void setOwner(OwnerExecutor owner) {
        this.owner = owner;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpectedDurationInMinutes(long expectedDurationInMinutes) {
        this.expectedDurationInMinutes = expectedDurationInMinutes;
    }

}
