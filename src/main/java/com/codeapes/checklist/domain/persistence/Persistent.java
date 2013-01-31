package com.codeapes.checklist.domain.persistence;

import java.io.Serializable;
import java.sql.Timestamp;

public interface Persistent extends Serializable {

    Long getObjectKey();

    void setObjectKey(Long objectKey);

    Timestamp getModifiedTimestamp();

    void setModifiedTimestamp(Timestamp time);

    String getModifiedBy();

    void setModifiedBy(String modifiedBy);

    Timestamp getCreatedTimestamp();

    void setCreatedTimestamp(Timestamp time);

    String getCreatedBy();

    void setCreatedBy(String createdBy);
}
