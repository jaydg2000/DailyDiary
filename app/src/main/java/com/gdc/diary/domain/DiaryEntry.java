package com.gdc.diary.domain;

import java.util.Date;

/**
 * Created by jaydg on 11/14/2017.
 */

public class DiaryEntry {

    private static final int NEW_ID = -1;
    private long id;
    private Date readingDate;
    private int morningReading;
    private int weight;
    private boolean hasTakenMorningMeds;
    private boolean hasTakenEveningMeds;

    public DiaryEntry(Date readingDate) {
        this(NEW_ID, new Date(), 0, 0, false, false);
    }

    public DiaryEntry(
            long id,
            Date readingDate,
            int morningReading,
            int weight,
            boolean hasTakenMorningMeds,
            boolean hasTakenEveningMeds) {

        this.id = id;
        this.readingDate = readingDate;
        this.morningReading = morningReading;
        this.weight = weight;
        this.hasTakenMorningMeds = hasTakenMorningMeds;
        this.hasTakenEveningMeds = hasTakenEveningMeds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
