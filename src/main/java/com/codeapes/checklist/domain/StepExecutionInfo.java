package com.codeapes.checklist.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.codeapes.checklist.domain.persistence.impl.PersistentEntity;
import com.codeapes.checklist.domain.user.User;

@Entity
@Table(name = "cl_step_exec_info") // NOSONAR
public class StepExecutionInfo extends PersistentEntity {

    private static final long serialVersionUID = -8743822181752786990L;

    private Step step;
    private String comments;
    private Date executionStart;
    private Date executionEnd;
    private User executedBy;
    private int preconditionTimeRemaining;
    
    @OneToOne (mappedBy = "executionInfo") // NOSONAR
    public Step getStep() {
        return step;
    }
    
    @Column(name = "comments", length = 500) // NOSONAR
    public String getComments() {
        return comments;
    }

    @Column(name = "execution_start", nullable = true) // NOSONAR
    public Date getExecutionStart() {
        return executionStart;
    }

    @Column(name = "execution_end", nullable = true) // NOSONAR
    public Date getExecutionEnd() {
        return executionEnd;
    }

    @OneToOne
    @JoinColumn(name = "executed_by_key") // NOSONAR
    public User getExecutedBy() {
        return executedBy;
    }

    @Column(name = "precondition_time_remaining", nullable = false) // NOSONAR
    public int getPreconditionTimeRemaining() {
        return preconditionTimeRemaining;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setExecutionStart(Date executionStart) {
        this.executionStart = executionStart;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public void setExecutionEnd(Date executionEnd) {
        this.executionEnd = executionEnd;
    }

    public void setExecutedBy(User executedBy) {
        this.executedBy = executedBy;
    }

    public void setPreconditionTimeRemaining(int preconditionTimeRemaining) {
        this.preconditionTimeRemaining = preconditionTimeRemaining;
    }
    
}
