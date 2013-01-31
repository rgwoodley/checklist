package com.codeapes.checklist.test.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DBSessionUtility {

    private SessionFactory sessionFactory;
    
    public void configureSession(ApplicationContext appContext) {

        sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
        final Session s = sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
    }
    
    public void endSession() {
        final Session s = getCurrentSession();
        s.flush();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
        SessionFactoryUtils.closeSession(s);
    }
    
    public void flushSession() {
        getCurrentSession().flush();
    }
    
    public void clearSession() {
        getCurrentSession().clear();
    }
    
    public void flushAndClearSession() {
        getCurrentSession().flush();
        getCurrentSession().clear();
    }
    
    public Session getCurrentSession() {
        final SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        final Session s = holder.getSession(); 
        return s;
    }
}
