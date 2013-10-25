package com.codeapes.checklist.util.query;

public enum SortOrder {
    ASC, DESC;
    public static SortOrder fromString(String order) {
        SortOrder sortOrder = SortOrder.ASC;
        if (order != null 
                && ("desc".equalsIgnoreCase(order) || "dsc".equalsIgnoreCase(order))) {
            sortOrder = SortOrder.DESC;
        }
        return sortOrder;
    }
}
