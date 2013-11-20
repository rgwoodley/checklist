package com.codeapes.checklist.dao.search.lucene.impl;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ApplicationProperties;

/**
 * The IndexingContext contains references to the objects necessary to work
 * with the Lucene search index.  It is used by the LuceneIndexer as well
 * as the LuceneSearchDAO and LuceneSearcher.
 * 
 * @author jkuryla
 */
public class IndexingContext {

    public static final Version LUCENE_VERSION = Version.LUCENE_42;
    private static final AppLogger logger = new AppLogger(IndexingContext.class); // NOSONAR
    
    private Analyzer analyzer = new StandardAnalyzer(LUCENE_VERSION);
    private Directory indexDirectory;
    private IndexWriter indexWriter;
    private SearcherManager searcherManager;

    public void initialize(ApplicationProperties applicationProperties) throws IOException {
        this.setIndexDirectory(LuceneContextInitializer.createDirectory(applicationProperties));
        final IndexWriterConfig config = new IndexWriterConfig(LUCENE_VERSION, analyzer);
        this.setIndexWriter(new IndexWriter(indexDirectory, config));
        indexWriter.commit();
        this.setSearcherManager(new SearcherManager(indexDirectory, new SearcherFactory()));
    }
    
    public void close() throws IOException {
        logger.info("Performing Lucene Cleanup.");
        if (indexWriter != null) {
            indexWriter.close();
            logger.info("Lucene IndexWriter closed.");
        }
        if (searcherManager != null) {
            searcherManager.close();
            logger.info("Lucene SearcherManager closed.");
        }
        if (indexDirectory != null) {
            indexDirectory.close();
            logger.info("Lucene Index Directory closed.");
        }
        if (analyzer != null) {
            analyzer.close();
        }
    }
    
    public Directory getIndexDirectory() {
        return indexDirectory;
    }

    public void setIndexDirectory(Directory indexDirectory) {
        this.indexDirectory = indexDirectory;
    }

    public IndexWriter getIndexWriter() {
        return indexWriter;
    }

    public void setIndexWriter(IndexWriter indexWriter) {
        this.indexWriter = indexWriter;
    }

    public SearcherManager getSearcherManager() {
        return searcherManager;
    }

    public void setSearcherManager(SearcherManager searcherManager) {
        this.searcherManager = searcherManager;
    }
    
    public Analyzer getAnalyzer() {
        return analyzer;
    }
    
    public void commit() throws IOException {
        this.indexWriter.commit();
    }

}
