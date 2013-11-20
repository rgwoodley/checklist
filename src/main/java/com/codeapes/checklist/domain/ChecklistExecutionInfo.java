package com.codeapes.checklist.domain;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.codeapes.checklist.domain.persistence.impl.PersistentEntity;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.OwnerExecutorImpl;
import com.codeapes.checklist.exception.ChecklistException;

/**
 * The class contains attributes and behavior that only pertain
 * to a checklist that can be, or is being, executed.
 * 
 * @author jkuryla
 */
@Entity
@Table(name = "cl_checklist_exec_info")  // NOSONAR
public class ChecklistExecutionInfo extends PersistentEntity {

    private static final long serialVersionUID = 4053405406470971000L;
    private static final int ONE_THOUSAND = 1000;
    
    private Checklist checklist;
    private OwnerExecutor executor; 
    private int minutesLeft;
    private Timestamp executionStart;
    private Timestamp executionEnd;
    
    @OneToOne (cascade = CascadeType.ALL, mappedBy = "executionInfo") // NOSONAR
    public Checklist getChecklist() {
        return checklist;
    }

    @Column(name = "execution_end", nullable = true) // NOSONAR
    public Timestamp getExecutionEnd() {
        validateExecutable();
        return executionEnd;
    }
    
    @ManyToOne (targetEntity = OwnerExecutorImpl.class)
    @JoinColumn(name = "executor_key") // NOSONAR
    public OwnerExecutor getExecutor() {
        validateExecutable();
        return executor;
    }
    
    @Column(name = "minutes_left", nullable = false) // NOSONAR
    public int getMinutesLeft() {
        validateExecutable();
        return minutesLeft;
    }
    
    @Column(name = "execution_start", nullable = true) // NOSONAR
    public Timestamp getExecutionStart() {
        validateExecutable();
        return executionStart;
    }
    
    @Transient
    public CompletionStatus getCompletionStatus(double threshold) {
        CompletionStatus completionStatus = CompletionStatus.ON_TIME;
        final long currentTimeMilliseconds = Calendar.getInstance().getTimeInMillis();
        final long estimatedCompletionMilliseconds = this.getEstimatedCompletion().getTime();
        final long differenceInSeconds = (estimatedCompletionMilliseconds - currentTimeMilliseconds) / ONE_THOUSAND;
        if (differenceInSeconds > 0) {
            final double percentOfTotal = differenceInSeconds / this.getChecklist().getExpectedDurationInMinutes();
            if (percentOfTotal <= threshold) {
                completionStatus = CompletionStatus.LATE;
            } else {
                completionStatus = CompletionStatus.PAST_THRESHOLD;
            }
        }  
        return completionStatus;
    }
        
    @Transient
    public Timestamp getEstimatedCompletion() {
        validateExecutable();
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, this.getMinutesLeft());
        return new Timestamp(cal.getTimeInMillis());
    }
    
    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public void setExecutor(OwnerExecutor executor) {
        validateExecutable();
        this.executor = executor;
    }

    public void setMinutesLeft(int minutesLeft) {
        validateExecutable();
        this.minutesLeft = minutesLeft;
    }

    public void setExecutionStart(Timestamp executionStart) {
        validateExecutable();
        this.executionStart = executionStart;
    }

    public void setExecutionEnd(Timestamp executionEnd) {
        validateExecutable();
        this.executionEnd = executionEnd;
    }
    
    private void validateExecutable() {
        if (checklist != null && checklist.getType() != ChecklistType.EXECUTABLE) {
            throw new ChecklistException("Error accessing executable field for checklist id: %s",
                    checklist.getObjectKey());
        }
    }
       
}
