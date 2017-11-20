package com.gdc.diary.domain;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jaydg on 11/14/2017.
 */

public class DiaryEntry {

    private static final int NEW_ID = -1;
    private long id;
    private Date createDate;
    private Date readingDate;
    private int morningReading;
    private int weight;
    private boolean hasTakenMorningMeds;
    private boolean hasTakenEveningMeds;

    public DiaryEntry(Date readingDate) {
        this(NEW_ID, new Date(), 0, 0, false, false, new Date());
    }

    public DiaryEntry(
            long id,
            Date readingDate,
            int morningReading,
            int weight,
            boolean hasTakenMorningMeds,
            boolean hasTakenEveningMeds,
            Date createDate) {

        this.id = id;
        setReadingDate(readingDate);
        this.morningReading = morningReading;
        this.weight = weight;
        this.hasTakenMorningMeds = hasTakenMorningMeds;
        this.hasTakenEveningMeds = hasTakenEveningMeds;
        this.createDate = createDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getReadingDate() {
        return readingDate;
    }

    public int getMorningReading() {
        return morningReading;
    }

    public int getWeight() {
        return weight;
    }

    public boolean hasTakenMorningMeds() {
        return hasTakenMorningMeds;
    }

    public boolean hasTakenEveningMeds() {
        return hasTakenEveningMeds;
    }

    public boolean isSugarReadingGood() {
        return morningReading < 160;
    }

    public boolean isNew() {
        return id == NEW_ID;
    }

    private void setReadingDate(Date readingDate) {
        Date newDate = readingDate == null ? new Date() : readingDate;
        this.readingDate = getDateWithoutTime(newDate);
    }

    private Date getDateWithoutTime(Date theDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(theDate);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
