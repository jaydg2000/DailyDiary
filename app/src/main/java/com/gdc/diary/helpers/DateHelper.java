package com.gdc.diary.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jaydg on 11/12/2017.
 */

public final class DateHelper {
    public static String getDisplayableDateStr(Date theDate) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy" );
        return dateFormat.format(theDate);
    }

    public static Date addDaysToDate(Date theDate, int days) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(theDate);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

}
