package com.codeapes.checklist.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class AbstractHibernateDAO extends HibernateDaoSupport {
    
    @Autowired
    public void obtainSession(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
}
