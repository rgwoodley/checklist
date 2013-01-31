package com.codeapes.checklist.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.codeapes.checklist.dao.PagingQueryCriteria;
import com.codeapes.checklist.dao.PersistenceDAO;
import com.codeapes.checklist.dao.ResultPage;
import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.util.ChecklistException;

public class HibernateDAOImpl extends HibernateDaoSupport implements PersistenceDAO {

    private static final String FIND_METHOD_VALIDATION_ERROR_MSG = "Query/Parameters cannot be null, "
        + "and parameters must not be empty.";

    public SessionFactory getHibernateSessionFactory() {

        return this.getSessionFactory();
    }

    public Persistent saveObject(Persistent persistentObj, final String createdBy) {

        persistentObj.setCreatedBy(createdBy);
        persistentObj.setCreatedTimestamp(new Timestamp(new Date().getTime()));
        persistentObj.setModifiedBy(createdBy);
        getHibernateTemplate().save(persistentObj);
        return persistentObj;
    }

    public Persistent update(Persistent persistentObj, final String modifiedBy) {

        persistentObj.setModifiedBy(modifiedBy);
        getHibernateTemplate().update(persistentObj);
        return persistentObj;
    }

    public Persistent saveOrUpdate(Persistent persistentObj, String modifiedBy) {

        if (persistentObj.getObjectKey() == null) {

            persistentObj.setCreatedBy(modifiedBy);
            persistentObj.setCreatedTimestamp(new Timestamp(new Date().getTime()));
        }
        persistentObj.setModifiedBy(modifiedBy);
        getHibernateTemplate().saveOrUpdate(persistentObj);
        return persistentObj;
    }

    public void delete(Persistent persistentObj) {

        getHibernateTemplate().delete(persistentObj);
    }

    public Persistent findObjectByKey(Class<? extends Persistent> objectClass, Long key) {

        return getHibernateTemplate().get(objectClass, key);
    }

    public List<?> find(String query) {
        if (query == null) {
            throw new ChecklistException(FIND_METHOD_VALIDATION_ERROR_MSG);
        }
        List<?> results = getHibernateTemplate().find(query);
        if (results == null) {
            results = new ArrayList<Persistent>();
        }
        return results;
    }

    public List<?> find(String query, Object parameterValue) {
        if (query == null || parameterValue == null) {
            throw new ChecklistException(FIND_METHOD_VALIDATION_ERROR_MSG);
        }
        List<?> results = getHibernateTemplate().find(query, parameterValue);
        if (results == null) {
            results = new ArrayList<Persistent>();
        }
        return results;
    }

    public List<?> find(String query, Map<String, Object> parameters) {
        if (query == null || parameters == null || parameters.isEmpty()) {
            throw new ChecklistException(FIND_METHOD_VALIDATION_ERROR_MSG);
        }
        final Object[] paramData = getParamNamesAndValues(parameters);
        List<?> results = getHibernateTemplate().findByNamedParam(query, (String[]) paramData[0],
            (Object[]) paramData[1]);
        if (results == null) {
            results = new ArrayList<Persistent>();
        }
        return results;
    }

    private Object[] getParamNamesAndValues(Map<String, Object> parameters) {
        final Set<String> keys = parameters.keySet();
        final String[] names = new String[keys.size()];
        final Object[] values = new Object[keys.size()];
        int i = 0;
        for (String key : keys) {
            names[i] = key;
            values[i++] = parameters.get(key);
        }
        final Object[] paramNamesAndValues = new Object[2];
        paramNamesAndValues[0] = names;
        paramNamesAndValues[1] = values;
        return paramNamesAndValues;
    }

    public ResultPage getPageOfResults(final PagingQueryCriteria pageCriteria) {
        validatePageCriteria(pageCriteria);
        final long totalCnt = executeQueryForTotalRowCount(pageCriteria);
        final List<?> results = executeResultsPageQuery(pageCriteria);
        final ResultPage result = new ResultPageImpl(totalCnt, pageCriteria.getResultsPerPage(),
            pageCriteria.getPageNumber(), results);
        return result;
    }

    private void validatePageCriteria(PagingQueryCriteria pageCriteria) {
        validatePageCriteriaNullValues(pageCriteria);
        validatePageCriteriaNumberValues(pageCriteria);
    }

    private void validatePageCriteriaNullValues(PagingQueryCriteria pageCriteria) {
        if (pageCriteria == null) {
            throw new ChecklistException("Invalid pageCriteria instance.  The pageCriteria instance is null.");
        } else if (pageCriteria.getQuery() == null) {
            throw new ChecklistException("Invalid pageCriteria instance.  Attribute queryString cannot be null.");
        } else if (pageCriteria.getCountQuery() == null) {
            throw new ChecklistException("Invalid pageCriteria instance.  Attribute countQuery cannot be null.");
        }
    }

    private void validatePageCriteriaNumberValues(PagingQueryCriteria pageCriteria) {
        if (pageCriteria.getPageNumber() < 0) {
            throw new ChecklistException(
                "Invalid pageCriteria instance.  Attribute pageNumber = %d.  This value cannot be negative.",
                pageCriteria.getPageNumber());
        } else if (pageCriteria.getResultsPerPage() < 1) {
            throw new ChecklistException(
                "Invalid pageCriteria instance.  Attribute resultsPerPage = %d.  This value must be greater than zero.",
                pageCriteria.getResultsPerPage());
        }
    }

    private long executeQueryForTotalRowCount(PagingQueryCriteria pageCriteria) {
        List<?> queryResult = null;
        final Map<String, Object> parameters = pageCriteria.getParameters();
        if (parameters == null || parameters.isEmpty()) {
            queryResult = find(pageCriteria.getCountQuery());
        } else {
            queryResult = find(pageCriteria.getCountQuery(), parameters);
        }
        validateCountQueryResults(queryResult);
        return ((Long) queryResult.get(0)).intValue();
    }
    
    private void validateCountQueryResults(List<?> queryResult) {
        if (queryResult == null) {
            throw new ChecklistException("result of count query for paging is null.");   
        } else if (queryResult.size() != 1) {
            throw new ChecklistException("result of count query for paging must contain one element.");
        } else if (!(queryResult.get(0) instanceof Integer  || queryResult.get(0) instanceof Long)) {
            throw new ChecklistException("total count from count query for paging must be an integer.");
        }
    }

    private List<?> executeResultsPageQuery(PagingQueryCriteria pageCriteria) {

        final String queryString = pageCriteria.getQuery();
        final int pageNumber = pageCriteria.getPageNumber();
        final int resultsPerPage = pageCriteria.getResultsPerPage();
        final Map<String, Object> parameters = pageCriteria.getParameters();

        final List<?> results = getHibernateTemplate().executeFind(new HibernateCallback<List<?>>() {
            public List<?> doInHibernate(Session session) throws SQLException {
                final Query query = session.createQuery(queryString);
                setQueryParameters(query, parameters);
                query.setFirstResult(pageNumber * resultsPerPage);
                query.setMaxResults(resultsPerPage);
                return query.list();
            }
        });
        return results;
    }

    private Query setQueryParameters(Query query, Map<String, Object> parameters) {
        if (parameters != null) {
            final Set<String> keys = parameters.keySet();
            for (String paramName : keys) {
                final Object paramValue = parameters.get(paramName);
                query.setParameter(paramName, paramValue);
            }
        }
        return query;
    }
}
