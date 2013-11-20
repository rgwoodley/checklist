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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.codeapes.checklist.domain.annotation.Searchable;
import com.codeapes.checklist.domain.persistence.impl.PersistentEntity;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.OwnerExecutorImpl;
import com.codeapes.checklist.util.AppLogger;

/**
 * This class defines a checklist, and contains methods that automatically
 * calculate total time and time remaining as steps are added/removed/modified/completed.
 * 
 * @author jkuryla
 */
@Entity
@Table(name = "cl_checklist")  // NOSONAR
@Searchable(mapperClass = "com.codeapes.checklist.domain.search.lucene.mapper.ChecklistMapper") // NOSONAR
public class Checklist extends PersistentEntity {

    private static final long serialVersionUID = 4189418186231193325L;
    private static final AppLogger logger = new AppLogger(Checklist.class); // NOSONAR
    private static final String ASTERISK_SEPARATOR = "************************";
    
    private ChecklistType type;
    private String name;
    private String description;
    private String comments;
    private int expectedDurationInMinutes;
    private ChecklistGroup group;
    private List<Step> steps;
    private int numSteps;
    private OwnerExecutor owner;
    private ChecklistStatus status;
    private ChecklistExecutionInfo executionInfo;
        
    private Checklist() { super(); }
    
    public Checklist(ChecklistType type) {
        this();
        this.type = type;
        if (type == ChecklistType.TEMPLATE) {
            this.status = ChecklistStatus.TEMPLATE;
        } else {
            this.status = ChecklistStatus.NOT_STARTED;
            final ChecklistExecutionInfo executionInfoObject = new ChecklistExecutionInfo();
            this.executionInfo = executionInfoObject;
            this.executionInfo.setChecklist(this);
        }
    }
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "execution_info_key") // NOSONAR
    public ChecklistExecutionInfo getExecutionInfo() {
        return executionInfo;
    }

    @ManyToOne
    @JoinColumn(name = "group_key") // NOSONAR
    public ChecklistGroup getGroup() {
        return this.group;
    }

    @OneToMany(mappedBy = "checklist") // NOSONAR
    @OrderColumn(name = "checklist_order")
    private List<Step> getSteps() {
        return steps;
    }
    
    @Transient
    public List<Step> getStepList() {
        if (steps == null) {
            return null;
        }
        return Collections.unmodifiableList(getSteps());
    }

    @Column(name = "num_steps") // NOSONAR
    public int getNumSteps() {
        return numSteps;
    }

    @Column(name = "name", length = 30, nullable = false, unique = true) // NOSONAR
    public String getName() {
        return name;
    }

    @Column(name = "description", length = 500) // NOSONAR
    public String getDescription() {
        return description;
    }

    @Column(name = "exp_duration_min", nullable = false) // NOSONAR
    public int getExpectedDurationInMinutes() {
        return expectedDurationInMinutes;
    }

    @ManyToOne(targetEntity = OwnerExecutorImpl.class)
    @JoinColumn(name = "owner_key") // NOSONAR
    public OwnerExecutor getOwner() {
        return owner;
    }
    
    @Column(name = "type", length = 25, nullable = false) // NOSONAR
    @Enumerated(EnumType.STRING)
    public ChecklistType getType() {
        return type;
    }
    
    @Column(name = "comments", length = 500) // NOSONAR
    public String getComments() {
        return comments;
    }
        
    @Column(name = "status", length = 25, nullable = false) // NOSONAR
    @Enumerated(EnumType.STRING)
    public ChecklistStatus getStatus() {
        return status;
    }
    
    public void setStatus(ChecklistStatus status) {
        this.status = status;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setType(ChecklistType type) {
        this.type = type;
    }

    public void setGroup(ChecklistGroup group) {
        this.group = group;
    }
    
    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    @SuppressWarnings("unused")
    private void setSteps(List<Step> steps) {
        this.steps = steps;
        int numberOfSteps = 0;
        if (steps != null) {
            numberOfSteps = steps.size();
        }
        setNumSteps(numberOfSteps);
    }
    
    public void addStep(Step step) {
        if (step == null) {
            return;
        }
        if (steps == null) {
            steps = new ArrayList<Step>();
        }
        steps.add(step);
        setNumSteps(steps.size());
        logger.debug("Step key %s (%s) added to checklist key %s (%s)", 
                step.getObjectKey(), this.getName(), this.getObjectKey(), this.getName());
        ChecklistTimeCalculator.recalculateTime(this);
    }
    
    public void removeStep(Step step) {
        if (step == null || steps == null) {
            return;
        }
        steps.remove(step);
        setNumSteps(steps.size());
        logger.debug("Step key %s (%s) removed from checklist key %s (%s)", 
                step.getObjectKey(), step.getName(), this.getObjectKey(), this.getName());
        ChecklistTimeCalculator.recalculateTime(this);
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

    public void setExpectedDurationInMinutes(int expectedDurationInMinutes) {
        this.expectedDurationInMinutes = expectedDurationInMinutes;
    }
    
    public void setExecutionInfo(ChecklistExecutionInfo executionInfo) {
        this.executionInfo = executionInfo;
    }
    
    public void logChecklistAndSteps() {
        logger.info(ASTERISK_SEPARATOR);
        logger.info("Checklist id: %s" , this.getObjectKey());
        logger.info("   Checklist Name: %s", this.getName());
        logger.info("   Checklist Status: %s", this.getStatus());
        final List<Step> listOfSteps = this.getStepList();
        if (listOfSteps != null) {
            for (Step step : listOfSteps) {
                logger.info("   Step id: %s", step.getObjectKey());
                logger.info("      Name %s", step.getName());
                logger.info("      Status: %s", step.getStatus());
                logger.info("      Step Duration: %s", step.getExpectedDurationInMinutes());
                logger.info("      Precondition Duration: %s", step.getPreconditionTotalDuration());
                if (step.getExecutionInfo() != null) {
                    logger.info("      Precondition Time Remaining: %s", 
                            step.getExecutionInfo().getPreconditionTimeRemaining());
                }
                logPreconditions(step);
            }
        } else {
            logger.info("  checklist has no steps.");
        }
        logger.info("total duration: %s", this.getExpectedDurationInMinutes());
        if (this.getExecutionInfo() != null) {
            logger.info("time left: %s", this.getExecutionInfo().getMinutesLeft());
        }
        logger.info(ASTERISK_SEPARATOR);
    }
    
    private void logPreconditions(Step step) {
        if (step.getPreConditionsList() != null) {
            logger.info("      Preconditions:  (id)  (name)");
            for (Step precondition : step.getPreConditionsList()) {
                logger.info("                      (%s)  (%s)", precondition.getObjectKey(), precondition.getName());
            }
        }
    }
}