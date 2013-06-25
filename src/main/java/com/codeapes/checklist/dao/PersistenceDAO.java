package com.codeapes.checklist.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.codeapes.checklist.domain.persistence.Persistent;

public interface PersistenceDAO {

    SessionFactory getHibernateSessionFactory();

    Persistent saveObject(Persistent persistentObj, String createdBy);

    Persistent update(Persistent persistentObj, String modifiedBy);

    Persistent saveOrUpdate(Persistent persistentObj, String modifiedBy);

    void delete(Persistent persistentObj, String deletedBy);

    Persistent findObjectByKey(Class<? extends Persistent> objectClass, Long key);

    List<?> find(String query);
    
    List<?> find(String query, Object parameterValue);

    List<?> find(String query, Map<String, Object> parameters);

    ResultPage getPageOfResults(PagingQueryCriteria pageCriteria);
}
