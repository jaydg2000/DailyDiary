package com.gdc.diary;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gdc.diary.data.DiaryEntry;
import com.gdc.diary.data.DiaryRepository;
import com.gdc.diary.ui.DiaryHistoryAdapter;

import java.util.Date;
import java.util.List;

public class ActivityHistory extends AppCompatActivity {

    private FloatingActionButton fabEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        fabEdit = (FloatingActionButton) findViewById(R.id.fabEntry);
        initEditButton();
        displayContent();
    }

    private void initEditButton() {
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTodaysEntry();
            }
        });
    }

    private void editTodaysEntry() {
        DiaryRepository repository = new DiaryRepository();
        DiaryEntry todaysEntry = repository.findForDate(new Date());
        int id = todaysEntry == null ? DiaryRepository.NEW_ENTRY_ID : todaysEntry.getId();
        ActivityEntryDetails.start(this, id);
    }

    private void displayContent() {
        List<DiaryEntry> entries = getDiaryEntries();
        RecyclerView rvHistory = (RecyclerView) findViewById(R.id.recyclerViewHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(new DiaryHistoryAdapter(entries));
    }

    private List<DiaryEntry> getDiaryEntries() {
        DiaryRepository repository = new DiaryRepository();
        return repository.findAllEntries();
    }
}
