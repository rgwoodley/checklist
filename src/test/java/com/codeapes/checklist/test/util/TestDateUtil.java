package com.codeapes.checklist.test.util;

import java.util.Date;

import com.ibm.icu.util.Calendar;

public final class TestDateUtil {

    private static final int YEAR = 2000;
    private TestDateUtil() { }
    
    public static Date getDateForTesting() {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.YEAR, YEAR);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 1);
        return cal.getTime();
    }
}
