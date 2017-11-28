package com.gdc.diary.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "diary_entry")
public class DiaryEntryEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public long id;
    @ColumnInfo(name = "create_date")
    public Date createDate;
    @ColumnInfo(name = "reading_date")
    public String readingDate;
    @ColumnInfo(name = "morning_reading")
    public int morningReading;
    @ColumnInfo(name = "weight")
    public int weight;
    @ColumnInfo(name ="taken_morning_meds")
    public boolean hasTakenMorningMeds;
    @ColumnInfo(name = "taken_evening_meds")
    public boolean hasTakenEveningMeds;
}
