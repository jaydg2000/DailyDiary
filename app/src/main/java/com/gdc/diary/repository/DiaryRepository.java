package com.gdc.diary.repository;

import android.content.Context;

import com.gdc.diary.data.DiaryDAO;
import com.gdc.diary.data.DiaryDatabaseFactory;
import com.gdc.diary.data.DiaryEntryEntity;
import com.gdc.diary.domain.DiaryEntry;
import com.gdc.diary.helpers.DateHelper;
import com.gdc.diary.mapper.DiaryEntryToEntityMapper;
import com.gdc.diary.mapper.EntityToDiaryEntryMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class DiaryRepository {

    private Context context;
    private EntityToDiaryEntryMapper domainMapper;
    private DiaryEntryToEntityMapper entityMapper;
    public DiaryRepository(Context appContext) {
        this.context = appContext;
        this.domainMapper = new EntityToDiaryEntryMapper();
        this.entityMapper = new DiaryEntryToEntityMapper();
    }

    public List<DiaryEntry> findAllEntries() {
        DiaryDAO dao = DiaryDatabaseFactory.getDiaryDAO(context);
        List<DiaryEntryEntity> allEntries = dao.findAllEntries();
        List<DiaryEntry> entries = new ArrayList<>();
        for (DiaryEntryEntity entity : allEntries) {
            entries.add(domainMapper.map(entity));
        }

        return entries;
    }

    public DiaryEntry findForDate(Date searchDate) {
        DiaryDAO dao = DiaryDatabaseFactory.getDiaryDAO(context);
        String sqlDateStr = DateHelper.getSQLDateStr(searchDate);
        DiaryEntryEntity entity = dao.findForDate(sqlDateStr);
        if (entity == null) {
            return new DiaryEntry(new Date());
        }
        return domainMapper.map(entity);
    }

    public DiaryEntry findById(long id) {
        DiaryDAO dao = DiaryDatabaseFactory.getDiaryDAO(context);
        DiaryEntryEntity entity = dao.findById(id);
        return domainMapper.map(entity);
    }

    public void saveEntry(DiaryEntry entry) {
        DiaryDAO dao = DiaryDatabaseFactory.getDiaryDAO(context);
        DiaryEntryEntity diaryEntryEntity = entityMapper.map(entry);
        if (entry.isNew()) {
            long newId = dao.createEntry(diaryEntryEntity);
            entry.setId(newId);
        } else {
            dao.updateEntry(diaryEntryEntity);
        }
    }
}
