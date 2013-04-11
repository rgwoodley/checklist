package com.codeapes.checklist.web.viewhelper.checklist;

import java.sql.Timestamp;
import java.util.List;

import com.codeapes.checklist.domain.group.ChecklistGroup;
import com.codeapes.checklist.domain.template.Checklist;
import com.codeapes.checklist.domain.template.Step;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.util.ChecklistException;
import com.codeapes.checklist.web.viewhelper.AbstractViewHelper;

public class ChecklistSummaryViewHelper extends AbstractViewHelper {

    private Long objectKey;
    private String name;
    private String description;
    private long expectedDurationInMinutes;
    private String groupName;
    private Long groupObjectKey;
    private Timestamp modifiedTimestamp;
    private String ownerName;
    private String ownerType;
    private Long ownerObjectKey;
    private int numberOfSteps;

    public ChecklistSummaryViewHelper(Class<?> parameterizedType) {
        super(parameterizedType);
    }

    public void populate(Object inputObject) {
        if (!(inputObject instanceof Checklist)) {
            throw new ChecklistException("Expecting type of Checklist.class, but got type %s", inputObject.getClass()
                .getSimpleName());
        }
        final Checklist checklist = (Checklist) inputObject;
        this.objectKey = checklist.getObjectKey();
        this.name = checklist.getName();
        this.description = checklist.getDescription();
        this.expectedDurationInMinutes = checklist.getExpectedDurationInMinutes();
        this.modifiedTimestamp = checklist.getModifiedTimestamp();
        final ChecklistGroup group = checklist.getGroup();
        if (group != null) {
            this.groupName = checklist.getGroup().getName();
            this.groupObjectKey = checklist.getGroup().getObjectKey();
        }
        final OwnerExecutor owner = checklist.getOwner();
        if (owner != null) {
            this.ownerName = checklist.getOwner().getName();
            this.ownerType = checklist.getOwner().getClass().getSimpleName();
            this.ownerObjectKey = checklist.getOwner().getObjectKey();
        }
        final List<Step> steps = checklist.getSteps();
        if (steps != null) {
            this.numberOfSteps = checklist.getSteps().size();
        }
    }

    public Long getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(Long objectKey) {
        this.objectKey = objectKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getExpectedDurationInMinutes() {
        return expectedDurationInMinutes;
    }

    public void setExpectedDurationInMinutes(long expectedDurationInMinutes) {
        this.expectedDurationInMinutes = expectedDurationInMinutes;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getGroupObjectKey() {
        return groupObjectKey;
    }

    public void setGroupObjectKey(Long groupObjectKey) {
        this.groupObjectKey = groupObjectKey;
    }

    public Timestamp getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public void setModifiedTimestamp(Timestamp modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public Long getOwnerObjectKey() {
        return ownerObjectKey;
    }

    public void setOwnerObjectKey(Long ownerObjectKey) {
        this.ownerObjectKey = ownerObjectKey;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

}
