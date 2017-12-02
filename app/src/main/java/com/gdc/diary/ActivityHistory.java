package com.gdc.diary;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        createDefaultDiaryEntryAlarm();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menuItemHistoryReport).setEnabled(false);
        menu.findItem(R.id.menuItemHistorySettings).setEnabled(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menuItemHistoryDeleteAll:
                deleteAllWithConfirmation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAllWithConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("WARNING!")
                .setMessage("Are you sure you want to delete all entries?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAllHistory();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteAllHistory() {
        new DeleteAllHistoryAsyncTask(getApplicationContext(), this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayContent();
    }

    private void createDefaultDiaryEntryAlarm() {
        new DiaryNotificationManager()
                .createAlarmForNotification(
                        this,
                        DiaryNotificationManager.DEFAULT_ALARM_HOUR,
                        DiaryNotificationManager.DEFAULT_ALARM_MINUTE);
    }

    private void setHistoryListVisibility(boolean isVisible) {
        recyclerView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        textViewEmptyHistory.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
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

    public void displayContent() {
        loadHistoricalReadings();
    }

    private void loadHistoricalReadings() {
        // TODO: Don't pass weak reference, let the receiver wrap the activity ref.
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

    private static class DeleteAllHistoryAsyncTask extends AsyncTask<Void, Void, Integer> {

        private final Context context;
        private WeakReference<ActivityHistory> activityRef;

        public DeleteAllHistoryAsyncTask(Context appContext, ActivityHistory activity) {
            this.context = appContext;
            this.activityRef = new WeakReference<ActivityHistory>(activity);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            DiaryRepository repository = new DiaryRepository(context);
            return repository.deleteHistory();
        }

        @Override
        protected void onPostExecute(Integer rowsAffected) {
            super.onPostExecute(rowsAffected);
            activityRef.get().displayContent();
            Toast.makeText(
                    context,
                    "Removed all " + rowsAffected.toString() + " entries.",
                    Toast.LENGTH_LONG);
        }
    }
}
