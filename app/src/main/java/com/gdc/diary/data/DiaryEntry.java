package com.gdc.diary.data;

import java.util.Date;

/**
 * Created by jaydg on 11/10/2017.
 */

public class DiaryEntry {
    private int id;
    private Date readingDate;
    private int morningReading;
    private int weight;
    private boolean hasTakenMorningMeds;
    private boolean hasTakenEveningMeds;

    public DiaryEntry() {
    }

    public DiaryEntry(
            int id,
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(Date readingDate) {
        this.readingDate = readingDate;
    }

    public int getMorningReading() {
        return morningReading;
    }

    public void setMorningReading(int morningReading) {
        this.morningReading = morningReading;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean hasTakenMorningMeds() {
        return hasTakenMorningMeds;
    }

    public void setHasTakenMorningMeds(boolean hasTakenMorningMeds) {
        this.hasTakenMorningMeds = hasTakenMorningMeds;
    }

    public boolean hasTakenEveningMeds() {
        return hasTakenEveningMeds;
    }

    public void setHasTakenEveningMeds(boolean hasTakenEveningMeds) {
        this.hasTakenEveningMeds = hasTakenEveningMeds;
    }

    public boolean isSugarReadingGood() {
        return morningReading < 160;
    }

}
