package com.gdc.diary.mapper;

import com.gdc.diary.data.DiaryEntryEntity;
import com.gdc.diary.domain.DiaryEntry;

public class EntityToDiaryEntryMapper implements Mapper<DiaryEntryEntity, DiaryEntry> {
    @Override
    public DiaryEntry map(DiaryEntryEntity entity) {
        return new DiaryEntry(
                entity.id,
                entity.readingDate,
                entity.morningReading,
                entity.weight,
                entity.hasTakenMorningMeds,
                entity.hasTakenEveningMeds);
    }
}
