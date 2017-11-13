package com.gdc.diary.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jaydg on 11/12/2017.
 */

public class DiaryRepository {

    public static final int NEW_ENTRY_ID = -1;
    private List<DiaryEntry> entries;

    public DiaryRepository() {
        entries = new ArrayList<>();

        Date date = new Date();

        entries.add(new DiaryEntry(1, addDaysToDate(date, 1), 100, 290, true, true));
        entries.add(new DiaryEntry(2, addDaysToDate(date, 2), 112, 289, false, true));
        entries.add(new DiaryEntry(3, addDaysToDate(date, 3),165, 289, true, true));
        entries.add(new DiaryEntry(4, addDaysToDate(date, 4),99, 288, true, false));
        entries.add(new DiaryEntry(5, addDaysToDate(date, 5),134, 289, true, true));
        entries.add(new DiaryEntry(6, addDaysToDate(date, 6),160, 290, true, true));
        entries.add(new DiaryEntry(7, addDaysToDate(date, 7),159, 289, false, true));
        entries.add(new DiaryEntry(8, addDaysToDate(date, 8),98, 289, true, true));
        entries.add(new DiaryEntry(9, addDaysToDate(date, 9),99, 288, true, false));
        entries.add(new DiaryEntry(10, addDaysToDate(date, 10),134, 289, true, true));
    }

    public List<DiaryEntry> findAllEntries() {
        return entries;
    }

    public DiaryEntry findForDate(Date searchDate) {
        for (DiaryEntry entry : entries) {
            if (entry.getReadingDate().equals(searchDate)) {
                return entry;
            }
        }

        // TODO: Don't like nulls.
        return null;
    }

    public DiaryEntry findById(int id) {
        for (DiaryEntry entry : entries) {
            if (entry.getId() == id) {
                return entry;
            }
        }

        // TODO: Don't like nulls.
        return null;
    }

    public void saveEntry(DiaryEntry entry) {
        int id = entry.getId();
        if (id == NEW_ENTRY_ID) {
            int newId = entries.get(entries.size() - 1).getId() + 1;
            entry.setId(newId);
            entries.add(entry);
        }
    }

    public static Date addDaysToDate(Date theDate, int days) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(theDate);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
