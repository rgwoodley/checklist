package com.codeapes.checklist.util;

public class ApplicationProperties {
    
    private boolean testMode;
    private boolean searchIndexInMemory;
    private String searchIndexDirectoryLocation;

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public boolean isSearchIndexInMemory() {
        return searchIndexInMemory;
    }

    public void setSearchIndexInMemory(boolean searchIndexInMemory) {
        this.searchIndexInMemory = searchIndexInMemory;
    }

    public String getSearchIndexDirectoryLocation() {
        return searchIndexDirectoryLocation;
    }

    public void setSearchIndexDirectoryLocation(String searchIndexDirectoryLocation) {
        this.searchIndexDirectoryLocation = searchIndexDirectoryLocation;
    }
    
}
