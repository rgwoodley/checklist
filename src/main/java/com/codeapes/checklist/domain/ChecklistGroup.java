package com.codeapes.checklist.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.codeapes.checklist.domain.persistence.impl.PersistentEntity;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.OwnerExecutorImpl;

/**
 * This class defines a groups that a checklist can belong to.  An example
 * might be 'Saturday Checklists'.
 * 
 * @author jkuryla
 */
@Entity
@Table(name = "cl_checklist_group") // NOSONAR
public class ChecklistGroup extends PersistentEntity {

    private static final long serialVersionUID = -6532998511081609672L;

    private String name;
    private String description;
    private ChecklistGroupType type;
    private OwnerExecutor owner;
    private List<Checklist> checklists;

    @Column(name = "name", length = 50, nullable = false, unique = true) // NOSONAR
    public String getName() {
        return this.name;
    }

    @Column(name = "type", length = 25, nullable = false) // NOSONAR
    @Enumerated(EnumType.STRING)
    public ChecklistGroupType getType() {
        return this.type;
    }

    @Column(name = "description", length = 500) // NOSONAR
    public String getDescription() {
        return this.description;
    }

    @OneToMany(mappedBy = "group") // NOSONAR
    public List<Checklist> getChecklists() {
        return this.checklists;
    }

    @ManyToOne (targetEntity = OwnerExecutorImpl.class)
    @JoinColumn(name = "owner_key") // NOSONAR
    public OwnerExecutor getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(ChecklistGroupType type) {
        this.type = type;
    }

    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    public void setOwner(OwnerExecutor owner) {
        this.owner = owner;
    }

}
