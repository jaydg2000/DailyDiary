package com.gdc.diary.mapper;

import com.gdc.diary.data.DiaryEntryEntity;
import com.gdc.diary.domain.DiaryEntry;

public class DiaryEntryToEntityMapper implements Mapper<DiaryEntry, DiaryEntryEntity> {
    @Override
    public DiaryEntryEntity map(DiaryEntry diaryEntry) {
        DiaryEntryEntity entity = new DiaryEntryEntity();
        entity.id = diaryEntry.getId();
        entity.hasTakenEveningMeds = diaryEntry.hasTakenEveningMeds();
        entity.hasTakenMorningMeds = diaryEntry.hasTakenMorningMeds();
        entity.morningReading = diaryEntry.getMorningReading();
        entity.weight = diaryEntry.getWeight();

        return entity;
    }
}
