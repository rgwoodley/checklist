package com.codeapes.checklist.dao.search.lucene.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import com.codeapes.checklist.exception.ChecklistException;
import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ApplicationProperties;

/**
 * The purpose of this class is to initialize Lucene.  The system supports
 * both in-memory and disk-persistent modes for the search index.
 * 
 * @author jkuryla
 */
public final class LuceneContextInitializer {

    private static final AppLogger logger = new AppLogger(LuceneContextInitializer.class); // NOSONAR
    
    private LuceneContextInitializer() {
        super();
    }
    
    protected static Directory createDirectory(ApplicationProperties applicationProperties) throws IOException {
        if (applicationProperties.isSearchIndexInMemory()) {
            logger.info("Using in-memory storage for search index");
            return new RAMDirectory();
        } else {
            final String directoryLocation = applicationProperties.getSearchIndexDirectoryLocation();
            if (StringUtils.isBlank(directoryLocation)) {
                throw new ChecklistException(
                        "Search Index Directory Location is null or empty!  Check the application properties.");
            }
            logger.info("Search Index Directory set to: %s", directoryLocation);
            return FSDirectory.open(new File(directoryLocation));
        }
    }
}
