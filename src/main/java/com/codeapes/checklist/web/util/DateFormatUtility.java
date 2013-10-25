package com.codeapes.checklist.web.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.codeapes.checklist.util.constants.DateTimeConstants;

public final class DateFormatUtility {

    private DateFormatUtility() { }
    
    public static String formatDate(Timestamp dateTime) {
        final DateFormat dateFormat = new SimpleDateFormat(DateTimeConstants.FULL_DATE_TIME_FORMAT);
        return dateFormat.format(dateTime);
    }
    
}
