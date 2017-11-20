package com.gdc.diary;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.gdc.diary.data.DiaryDAO;
import com.gdc.diary.data.DiaryDatabaseFactory;
import com.gdc.diary.data.DiaryEntryEntity;
import com.gdc.diary.domain.DiaryEntry;
import com.gdc.diary.helpers.DateHelper;
import com.gdc.diary.repository.DiaryRepository;

import io.reactivex.Single;

public class DiaryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        final Context context = this;
//
//                DiaryRepository repository = new DiaryRepository(context);
//
//                repository.saveEntry(new DiaryEntry(
//                        -1,
//                        DateHelper.CalcRelativeDate(-5),
//                        132,
//                        289,
//                        false,
//                        true,
//                        DateHelper.CalcRelativeDate(-5)));
//                repository.saveEntry(new DiaryEntry(
//                        -1,
//                        DateHelper.CalcRelativeDate(-4),
//                        167,
//                        290,
//                        true,
//                        true,
//                        DateHelper.CalcRelativeDate(-4)));
//                repository.saveEntry(new DiaryEntry(
//                        -1,
//                        DateHelper.CalcRelativeDate(-3),
//                        156,
//                        288,
//                        true,
//                        false,
//                        DateHelper.CalcRelativeDate(-3)));
//                repository.saveEntry(new DiaryEntry(
//                        -1,
//                        DateHelper.CalcRelativeDate(-2),
//                        128,
//                        287,
//                        true,
//                        true,
//                        DateHelper.CalcRelativeDate(-2)));
//                repository.saveEntry(new DiaryEntry(
//                        -1,
//                        DateHelper.CalcRelativeDate(-1),
//                        98,
//                        286,
//                        true,
//                        true,
//                        DateHelper.CalcRelativeDate(-1)));
//            }
    }
}
