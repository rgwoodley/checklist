package com.codeapes.checklist.service.checklist.impl;

import java.util.ArrayList;
import java.util.List;

import com.codeapes.checklist.domain.Checklist;
import com.codeapes.checklist.domain.ChecklistStatus;
import com.codeapes.checklist.domain.CompletionStatus;
import com.codeapes.checklist.domain.Step;

public class StepData {

    private Checklist checklist;
    private List<Step> steps = new ArrayList<Step>();
    private double thresholdPercent;
    private int expectedTimeRemaining;
    private int expectedTotalTime;
    private ChecklistStatus expectedStatus;
    private CompletionStatus completionStatus;

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public double getThresholdPercent() {
        return thresholdPercent;
    }

    public void setThresholdPercent(double thresholdPercent) {
        this.thresholdPercent = thresholdPercent;
    }

    public int getExpectedTimeRemaining() {
        return expectedTimeRemaining;
    }

    public void setExpectedTimeRemaining(int expectedTimeRemaining) {
        this.expectedTimeRemaining = expectedTimeRemaining;
    }

    public int getExpectedTotalTime() {
        return expectedTotalTime;
    }

    public void setExpectedTotalTime(int expectedTotalTime) {
        this.expectedTotalTime = expectedTotalTime;
    }

    public ChecklistStatus getExpectedStatus() {
        return expectedStatus;
    }

    public void setExpectedStatus(ChecklistStatus expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(CompletionStatus completionStatus) {
        this.completionStatus = completionStatus;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

}
