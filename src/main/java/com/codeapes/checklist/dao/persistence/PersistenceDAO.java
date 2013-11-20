package com.codeapes.checklist.dao.persistence;

import java.util.List;
import java.util.Map;

import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.util.query.PagingQueryCriteria;
import com.codeapes.checklist.util.query.ResultPage;

/**
 * The purpose of this interface is to define general operations
 * for updating and retrieving persistent data.
 * 
 * @author jkuryla
 */
public interface PersistenceDAO {

    Persistent saveObject(Persistent persistentObj, String createdBy);

    Persistent update(Persistent persistentObj, String modifiedBy);

    Persistent saveOrUpdate(Persistent persistentObj, String modifiedBy);

    void delete(Persistent persistentObj, String deletedBy);

    Persistent findObjectByKey(Class<? extends Persistent> objectClass, Long key);

    List<?> find(String query);
    
    List<?> find(String query, Object parameterValue);

    List<?> find(String query, Map<String, Object> parameters);

    /**
     * This method takes some criteria information, such as the starting
     * page and number of results per page, and returns a page
     * of results.
     * 
     * @param pageCriteria
     * @return ResultPage - a Page of Results.
     */
    ResultPage getPageOfResults(PagingQueryCriteria pageCriteria);
}
