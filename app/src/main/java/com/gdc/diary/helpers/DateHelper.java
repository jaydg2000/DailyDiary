package com.gdc.diary.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jaydg on 11/12/2017.
 */

public final class DateHelper {
    public static String getDisplayableDateStr(Date theDate) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy" );
        return dateFormat.format(theDate);
    }
}
