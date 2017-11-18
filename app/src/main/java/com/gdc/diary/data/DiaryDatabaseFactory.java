package com.gdc.diary.data;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.gdc.diary.repository.DiaryRepository;

public final class DiaryDatabaseFactory {

    private static DiaryDatabase db = null;


    public static DiaryDAO getDiaryDAO(Context appContext) {
        if (db == null) {
            db = Room.databaseBuilder(appContext, DiaryDatabase.class, "diary.db").build();
        }

        return db.getDiaryDAO();
    }
}
