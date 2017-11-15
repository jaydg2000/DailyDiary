package com.gdc.diary.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Dao
public interface DiaryDAO {

    @Query("SELECT * FROM diary_entry")
    List<DiaryEntryEntity> findAllEntries();

    @Query("SELECT * FROM diary_entry WHERE reading_date = :searchDate LIMIT 1")
    DiaryEntryEntity findForDate(Date searchDate);

    @Query("SELECT * FROM diary_entry WHERE _id = :id")
    DiaryEntryEntity findById(long id);

    @Insert
    long createEntry(DiaryEntryEntity entry);

    @Update
    void updateEntry(DiaryEntryEntity entry);
}
