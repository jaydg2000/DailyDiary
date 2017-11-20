package com.gdc.diary.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class DateHelper {

    private static Locale _locale;          // The locale to use for date calculations.
    private static String[] _monthNames;    // Names of the months.
    private static String[] _dayNames;      // Names of the days of the week.


    /**
     * Static initializer that creates month name list and day name list.
     */
    static {
        _monthNames = new String[12];
        _monthNames[0] = "January";
        _monthNames[1] = "February";
        _monthNames[2] = "March";
        _monthNames[3] = "April";
        _monthNames[4] = "May";
        _monthNames[5] = "June";
        _monthNames[6] = "July";
        _monthNames[7] = "August";
        _monthNames[8] = "September";
        _monthNames[9] = "October";
        _monthNames[10] = "November";
        _monthNames[11] = "December";

        _dayNames = new String[7];
        _dayNames[0] = "Sunday";
        _dayNames[1] = "Monday";
        _dayNames[2] = "Tuesday";
        _dayNames[3] = "Wednesday";
        _dayNames[4] = "Thursday";
        _dayNames[5] = "Friday";
        _dayNames[6] = "Saturday";

        _locale = Locale.US;
    }


    /**
     * Returns the current locale.
     *
     * @return
     */
    public static Locale getLocale() {
        return _locale;
    }

    public static String getDisplayableDateStr(Date theDate) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy" );
        return dateFormat.format(theDate);
    }

    /**
     * Sets the locale.
     *
     * @param locale
     */
    public static void setLocale(Locale locale) {
        _locale = locale;
    }


    /**
     * Returns the year of the supplied date as an integer.
     *
     * @param theDate
     * @return
     */
    public static int getYear(Date theDate) {
        SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy", _locale);
        //java.util.Date nowDate = new java.util.Date();
        return Integer.valueOf(formatNowYear.format(theDate));
    }


    /**
     * Returns the month of the supplied date as an integer.
     *
     * @param theDate
     * @return
     */
    public static int getMonth(Date theDate) {
        SimpleDateFormat formatNowYear = new SimpleDateFormat("MM", _locale);
        //java.util.Date nowDate = new java.util.Date();
        return Integer.valueOf(formatNowYear.format(theDate));
    }


    /**
     * Returns the day of month of the supplied date as an integer.
     *
     * @param theDate
     * @return
     */
    public static int getDayOfMonth(Date theDate) {
        SimpleDateFormat formatNowYear = new SimpleDateFormat("dd", _locale);
        //java.util.Date nowDate = new java.util.Date();
        return Integer.valueOf(formatNowYear.format(theDate));
    }


    /**
     * Returns the day of week of the supplied date as an integer.
     *
     * @param theDate
     * @return
     */
    public static int getDayOfWeek(Date theDate) {
        Calendar cal = Calendar.getInstance(_locale);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        return weekday;
    }


    /**
     * Returns the hour of the supplied date as an integer.
     *
     * @param theDate
     * @return
     */
    public static int getHour(Date theDate) {
        SimpleDateFormat formatNowYear = new SimpleDateFormat("HH");
        //java.util.Date nowDate = new java.util.Date();
        return Integer.valueOf(formatNowYear.format(theDate));
    }


    /**
     * Returns the minutes of the supplied date as an integer.
     *
     * @param theDate
     * @return
     */
    public static int getMinute(Date theDate) {
        SimpleDateFormat formatNowYear = new SimpleDateFormat("mm");
        //java.util.Date nowDate = new java.util.Date();
        return Integer.valueOf(formatNowYear.format(theDate));
    }


    /**
     * Returns the seconds of the supplied date as an integer.
     *
     * @param theDate
     * @return
     */
    public static int getSecond(Date theDate) {
        SimpleDateFormat formatNowYear = new SimpleDateFormat("ss");
        //java.util.Date nowDate = new java.util.Date();
        return Integer.valueOf(formatNowYear.format(theDate));
    }


    /**
     * Returns a String representation of the supplied date in the
     * format MM/dd/yyyy HH:mm:ss.
     *
     * @param theDate
     * @return
     */
    public static String getDateTimeStr(Date theDate) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return dateFormat.format(theDate);
    }


    /**
     * Returns a String representation suitable for SQLite storage of the
     * supplied date.
     *
     * @param theDate
     * @return
     */
    public static String getSQLDateStr(Date theDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(theDate);
    }


    /**
     * Returns a String representation suitable for SQLite storage of the
     * supplied date and time.
     *
     * @param theDate
     * @return
     */
    public static String getSQLDateTimeStr(Date theDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(theDate);
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * Returns a String representation of the supplied date without the time.
     *
     * @param theDate
     * @return
     */
    public static String getDateStr(Date theDate) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(theDate);
    }


    /**
     * Returns a String representation of the supplied date without the date.  Includes
     * time only.
     *
     * @param theDate
     * @return
     */
    public static String getTimeStr(Date theDate) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(theDate);
    }


    /**
     * Returns a Date created from the supplied String.  Date only, time is not included.
     *
     * @param strDate
     * @return
     */
    public static Date getDateFromString(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }


    /**
     * Returns a Date created from the supplied String formatted for SQLite.  Time is not included.
     *
     * @param strDate
     * @return
     */
    public static Date getDateFromSQLString(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }


    /**
     * Returns a Date with time from the supplied String formatted for SQLite.
     *
     * @param strDate
     * @return
     */
    public static Date getDateTimeFromSQLString(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }


    /**
     * Returns a Date from integer values for year, month and day.
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDateFromValues(int year, int month, int day) {
        Calendar cal = Calendar.getInstance(_locale);
        cal.set(year, month, day);
        return cal.getTime();
    }

    public static Date getDateFromValues(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance(_locale);
        cal.set(year, month, day, hour, minute, second);
        return cal.getTime();
    }

    /**
     * Returns a Date with time from integer values for year, month, day,
     * hour, and minutes.
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return
     */
    public static Date getDateTimeFromValues(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance(_locale);
        cal.set(year, month, day, hour, minute);
        return cal.getTime();
    }


    /**
     * Returns a Date with time from integer values for year, month, day,
     * hour, minutes, and seconds.
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getDateTimeFromValues(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance(_locale);
        cal.set(year, month, day, hour, minute, second);
        return cal.getTime();
    }


    /**
     * Returns a Date without time from a supplied Date that may or may not have time.
     *
     * @param theDate
     * @return
     */
    public static Date getDateWithoutTime(Date theDate) {
        String dateStr = getDateStr(theDate);
        Date newDate = getDateFromString(dateStr);
        return newDate;
    }


    /**
     * Returns a Date using the current date and adding the specified number of days.
     *
     * @param days
     * @return
     */
    public static Date CalcRelativeDate(int days) {
        Calendar cal = Calendar.getInstance(_locale);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }


    /**
     * Returns the full length name of the month from the supplied date.
     *
     * @param theDate
     * @return
     */
    public static String getMonthName(Date theDate) {
        String monthName = "";

        int ndx = DateHelper.getMonth(theDate);
        if (ndx > 0) {
            monthName = _monthNames[ndx - 1];
        }

        return monthName;
    }


    /**
     * Returns the abbreviated month name from the supplied date.
     *
     * @param theDate
     * @return
     */
    public static String getShortMonthName(Date theDate) {
        String monthName = getMonthName(theDate);
        int length = monthName.length();

        return length > 0 ? monthName.substring(0, 3) : "";
    }


    /**
     * Returns the day of week name from the supplied date.
     *
     * @param theDate
     * @return
     */
    public static String getDayOfWeekName(Date theDate) {
        String dayOfWeekName = "";

        int ndx = DateHelper.getDayOfWeek(theDate);
        if (ndx > -1) {
            dayOfWeekName = _dayNames[ndx];
        }

        return dayOfWeekName;
    }


    /**
     * Returns the abbreviated day of week name from the supplied date.
     *
     * @param theDate
     * @return
     */
    public static String getShortDayOfWeekName(Date theDate) {
        String dayName = getDayOfWeekName(theDate);
        int length = dayName.length();

        return length > 0 ? dayName.substring(0, 2) : "";
    }


    /**
     * Returns a new Date from the supplied date, adding the specified number of days.
     *
     * @param theDate
     * @param days
     * @return
     */
    public static Date AddDaysToDate(Date theDate, int days) {
        Calendar cal = Calendar.getInstance(_locale);
        cal.setTime(theDate);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }


    /**
     * Returns a new Date from the supplied date, adding the specified number of months.
     *
     * @param theDate
     * @param months
     * @return
     */
    public static Date AddMonthsToDate(Date theDate, int months) {
        Calendar cal = Calendar.getInstance(_locale);
        cal.setTime(theDate);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }


    /**
     * Returns a new Date from the supplied date, adding the specified number of years.
     *
     * @param theDate
     * @param years
     * @return
     */
    public static Date AddYearsToDate(Date theDate, int years) {
        Calendar cal = Calendar.getInstance(_locale);
        cal.setTime(theDate);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }


    /**
     * Returns the supplied Date as milliseconds.
     *
     * @param theDate
     * @return
     */
    public static long GetDateInMilliseconds(Date theDate) {
        Calendar cal = Calendar.getInstance(_locale);
        cal.setTime(theDate);
        return cal.getTimeInMillis();
    }


    /**
     * Returns the current Date.
     *
     * @return
     */
    public static Date getCurrentDate() {
        return getDateWithoutTime(new Date());
    }


    /**
     * Returns the current Date and time.
     *
     * @return
     */
    public static Date getCurrentDateTime() {
        return new Date();
    }


    /**
     * Returns the difference in days between two dates.
     *
     * @param d1
     * @param d2
     * @return
     */
    public static long getDifferenceInDays(Date d1, Date d2) {
        long diff = Math.abs(d1.getTime() - d2.getTime());
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }
}
