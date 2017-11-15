package com.gdc.diary;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gdc.diary.data.DiaryDAO;
import com.gdc.diary.data.DiaryDatabaseFactory;
import com.gdc.diary.data.DiaryEntryEntity;
import com.gdc.diary.domain.DiaryEntry;
import com.gdc.diary.repository.DiaryRepository;
import com.gdc.diary.ui.DiaryHistoryAdapter;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

import static com.gdc.diary.ActivityEntryDetails.NEW_ENTRY_ID;

public class ActivityHistory extends AppCompatActivity {

    private FloatingActionButton fabEdit;
    private TextView textViewEmptyHistory;
    private RecyclerView recyclerView;

    public void populateHistoryList(List<DiaryEntry> entries) {
        boolean hasItems =  entries.size() > 0;
        setHistoryListVisibility(hasItems);
        if (hasItems) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new DiaryHistoryAdapter(entries));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        fabEdit = (FloatingActionButton) findViewById(R.id.fabEntry);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHistory);
        textViewEmptyHistory = (TextView) findViewById(R.id.textViewEmptyHistory);
        initEditButton();
        displayContent();
    }

    private void setHistoryListVisibility(boolean isVisible) {
        recyclerView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        textViewEmptyHistory.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
    }

    private void makeHistoryListInvisible() {
        recyclerView.setVisibility(View.INVISIBLE);
        textViewEmptyHistory.setVisibility(View.VISIBLE);
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
        ActivityEntryDetails.start(this, ActivityEntryDetails.TODAY_ENTRY_ID);
    }

    private void displayContent() {
        loadHistoricalReadings();
    }

    private void loadHistoricalReadings() {
        new RetrieveHistoryAsyncTask(
                getApplicationContext(),
                new WeakReference<ActivityHistory>(this))
                    .execute();
    }

    private static class RetrieveHistoryAsyncTask extends AsyncTask<Void, Void, List<DiaryEntry>> {

        private WeakReference<ActivityHistory> activity;
        private Context context;

        public RetrieveHistoryAsyncTask(
                Context appContext,
                WeakReference<ActivityHistory> activity) {
            this.activity = activity;
            this.context = appContext;
        }

        @Override
        protected List<DiaryEntry> doInBackground(Void... voids) {
            DiaryRepository repository = new DiaryRepository(context);
            return repository.findAllEntries();
        }

        @Override
        protected void onPostExecute(List<DiaryEntry> diaryEntries) {
            activity.get().populateHistoryList(diaryEntries);
        }
    }
}
