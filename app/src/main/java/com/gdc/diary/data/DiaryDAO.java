package com.gdc.diary.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;

@Dao
public interface DiaryDAO {

    @Query("SELECT * FROM diary_entry ORDER BY create_date DESC")
    List<DiaryEntryEntity> findAllEntries();

    @Query("SELECT * FROM diary_entry WHERE reading_date = :searchDateStr LIMIT 1")
    DiaryEntryEntity findForDate(String searchDateStr);

    @Query("SELECT * FROM diary_entry WHERE _id = :id")
    DiaryEntryEntity findById(long id);

    @Insert
    long createEntry(DiaryEntryEntity entry);

    @Update
    void updateEntry(DiaryEntryEntity entry);

    @Query("DELETE FROM diary_entry")
    int deleteAll();
}
