package com.codeapes.checklist.util;

public final class StringUtil {

    public static final String EMPTY_STRING = "";

    private StringUtil() {
    }

    public static boolean isNullOrEmpty(String value) {
        boolean nullOrEmpty = false;
        if (value == null || "".equals(value.trim())) {
            nullOrEmpty = true;
        }
        return nullOrEmpty;
    }

    public static boolean isNotEmpty(String value) {
        return !isNullOrEmpty(value);
    }

}
