package com.codeapes.checklist.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.codeapes.checklist.exception.ChecklistException;


public final class DateUtil {

    private DateUtil() { }
    
    public static Timestamp createTimestampFromString(String dateTimeString, String format) {
        if (dateTimeString == null || format == null) {
            throw new ChecklistException("cannot create timestamp from string; "
                    + "either dateTimeString or format was null.");
        }
        final DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            final Date theDate = dateFormat.parse(dateTimeString);
            return new Timestamp(theDate.getTime());
        } catch (ParseException pe) {
            throw new ChecklistException(pe.getMessage(), pe);
        }     
    }
}
