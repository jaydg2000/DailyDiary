package com.gdc.diary.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = DiaryEntryEntity.class, version = 1)
public abstract class DiaryDatabase extends RoomDatabase {
    abstract DiaryDAO getDiaryDAO();
}
