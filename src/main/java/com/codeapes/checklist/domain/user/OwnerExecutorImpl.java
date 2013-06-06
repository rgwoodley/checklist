package com.codeapes.checklist.domain.user;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.codeapes.checklist.domain.persistence.PersistentImpl;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "cl_owner_executor") // NOSONAR
public abstract class OwnerExecutorImpl extends PersistentImpl implements OwnerExecutor {

    private static final long serialVersionUID = 8378835944648575788L;

    @Transient
    public abstract String getName();
    
}
