package com.codeapes.checklist.dao.persistence.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * This class is necessary because there are some situations in subclasses
 * where access to an instance of the underlying Hibernate session 
 * is needed.
 * 
 * @author jkuryla
 *
 */
public abstract class AbstractHibernateDAO extends HibernateDaoSupport {
    
    @Autowired
    public void obtainSession(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
}
