package com.gdc.diary.mapper;

import com.gdc.diary.data.DiaryEntryEntity;
import com.gdc.diary.domain.DiaryEntry;
import com.gdc.diary.helpers.DateHelper;

public class DiaryEntryToEntityMapper implements Mapper<DiaryEntry, DiaryEntryEntity> {
    @Override
    public DiaryEntryEntity map(DiaryEntry diaryEntry) {
        DiaryEntryEntity entity = new DiaryEntryEntity();
        entity.id = diaryEntry.getId() == -1 ? 0 : diaryEntry.getId();
        entity.readingDate = DateHelper.getSQLDateStr(diaryEntry.getReadingDate());
        entity.hasTakenEveningMeds = diaryEntry.hasTakenEveningMeds();
        entity.hasTakenMorningMeds = diaryEntry.hasTakenMorningMeds();
        entity.morningReading = diaryEntry.getMorningReading();
        entity.weight = diaryEntry.getWeight();

        return entity;
    }
}
