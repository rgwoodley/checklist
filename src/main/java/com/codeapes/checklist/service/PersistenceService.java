package com.codeapes.checklist.service;

import java.util.List;

import com.codeapes.checklist.domain.persistence.Persistent;

public interface PersistenceService {

    Persistent saveObject(Persistent persistentObj, String createdBy);

    Persistent update(Persistent persistentObj, String modifiedBy);

    Persistent saveOrUpdate(Persistent persistentObj, String modifiedBy);

    void delete(Persistent persistentObj);

    Persistent findObjectByKey(Class<? extends Persistent> objectClass, Long key);
    
    List<?> fetchAllObjectsByType(Class<? extends Persistent> objectClass);
}
