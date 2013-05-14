package com.codeapes.checklist.domain.search.lucene.mapper;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import com.codeapes.checklist.domain.persistence.Persistent;
import com.codeapes.checklist.domain.search.SearchFields;
import com.codeapes.checklist.domain.template.Checklist;

public class ChecklistMapper extends AbstractMapper {

    public static final String FIELD_OWNER = "owner";

    public Document mapObjectToDocument(Persistent object, Document document) {
        final Checklist checklist = (Checklist) object;
        document.add(new TextField(SearchFields.NAME.toString(), checklist.getName(), Field.Store.YES));
        document.add(new TextField(SearchFields.DESCRIPTION.toString(), StringUtils.defaultString(checklist
                .getDescription()), Field.Store.YES));
        document.add(new TextField(FIELD_OWNER, checklist.getOwner().getName(), Field.Store.YES));
        return document;
    }

}
