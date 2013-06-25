package com.codeapes.checklist.util;


public class ApplicationProperties {

    private static final AppLogger logger = new AppLogger(ApplicationProperties.class); // NOSONAR
    
    private boolean testMode;
    private boolean searchIndexInMemory;
    private boolean auditLogEnabled;
    private String searchIndexDirectoryLocation;

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
        logger.info("TestMode property set to %s", this.testMode);
    }

    public boolean isSearchIndexInMemory() {
        return searchIndexInMemory;
    }

    public void setSearchIndexInMemory(boolean searchIndexInMemory) {
        this.searchIndexInMemory = searchIndexInMemory;
        logger.info("SearchIndexInMemory property set to %s", this.searchIndexInMemory);
    }

    public String getSearchIndexDirectoryLocation() {
        return searchIndexDirectoryLocation;
    }

    public void setSearchIndexDirectoryLocation(String searchIndexDirectoryLocation) {
        this.searchIndexDirectoryLocation = searchIndexDirectoryLocation;
        logger.info("SearchIndexDirectoryLocation property set to %s", this.searchIndexDirectoryLocation);
    }

    public boolean isAuditLogEnabled() {
        return auditLogEnabled;
    }

    public void setAuditLogEnabled(boolean auditLogEnabled) {
        this.auditLogEnabled = auditLogEnabled;
        logger.info("AuditLogEnabled property set to %s", this.auditLogEnabled);
    }

}
