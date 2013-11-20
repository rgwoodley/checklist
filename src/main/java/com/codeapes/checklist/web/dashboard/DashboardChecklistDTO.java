package com.codeapes.checklist.web.dashboard;

public class DashboardChecklistDTO {

    private Long objectKey;
    private String name;
    private String ownerName;
    private int numSteps;
    private String duration;
    private String statusImageURL;
    private String status;
    private String estimatedCompletionTime;
    private String actualCompletionTime;
    private String currentStep;

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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getNumSteps() {
        return numSteps;
    }

    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatusImageURL() {
        return statusImageURL;
    }

    public void setStatusImageURL(String imageURL) {
        this.statusImageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstimatedCompletionTime() {
        return estimatedCompletionTime;
    }

    public void setEstimatedCompletionTime(String estimatedCompletionTime) {
        this.estimatedCompletionTime = estimatedCompletionTime;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public String getActualCompletionTime() {
        return actualCompletionTime;
    }

    public void setActualCompletionTime(String actualCompletionTime) {
        this.actualCompletionTime = actualCompletionTime;
    }

}
