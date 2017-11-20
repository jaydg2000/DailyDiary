package com.gdc.diary;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdc.diary.domain.DiaryEntry;
import com.gdc.diary.helpers.DateHelper;
import com.gdc.diary.repository.DiaryRepository;

import java.util.Date;

public class ActivityEntryDetails extends AppCompatActivity {

    public static final long NEW_ENTRY_ID = -1;
    public static final long TODAY_ENTRY_ID = -2;
    public static final String EXTRAS_KEY_ENTRY_ID = "entry_id";

    private DiaryEntry diaryEntry;

    private TextView textViewDate;
    private EditText editTextReading;
    private EditText editTextWeight;
    private CheckBox checkBoxMorning;
    private CheckBox checkBoxEvening;

    public static void start(Context context, long id) {
        context.startActivity(getStartIntent(context, id));
    }

    public static Intent getStartIntent(Context context, long id) {
        Intent starter = new Intent(context, ActivityEntryDetails.class);
        starter.putExtra(EXTRAS_KEY_ENTRY_ID, id);
        return starter;
    }

    public void populateDiaryEntry(DiaryEntry diaryEntry) {
        this.diaryEntry = diaryEntry;
        bindData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

        textViewDate = (TextView) findViewById(R.id.textViewDetailsReadingDate);
        editTextReading = (EditText) findViewById(R.id.editTextGlucoseReading);
        editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        checkBoxMorning = (CheckBox) findViewById(R.id.checkBoxTookMorningMeds);
        checkBoxEvening = (CheckBox) findViewById(R.id.checkBoxTookEveningMeds);

        loadDiaryEntry();
    }

    private void bindData() {
        textViewDate.setText(DateHelper.getDisplayableDateStr(diaryEntry.getReadingDate()));
        editTextReading.setText(formatReading(diaryEntry.getMorningReading()));
        editTextWeight.setText(formatReading(diaryEntry.getWeight()));
        checkBoxMorning.setChecked(diaryEntry.hasTakenMorningMeds());
        checkBoxEvening.setChecked(diaryEntry.hasTakenEveningMeds());
    }

    private String formatReading(int reading) {
        if (reading < 1) {
            return "";
        }
        return Integer.toString(reading);
    }

    private void loadDiaryEntry() {
        long entryId = getPassedEntryId();
        if (entryId == TODAY_ENTRY_ID) {
            new LoadTodaysDiaryEntryAsyncTask(getApplicationContext(), this).execute();
        } else {
            new LoadDiaryEntryAsyncTask(getApplicationContext(), this).execute(entryId);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDiaryEntry();
    }

    private void saveDiaryEntry() {
        updateDiaryEntry();
        new SaveDiaryEntryAsyncTask(getApplicationContext()).execute(diaryEntry);
        // TODO: Figure out how to handle failure.
        Toast.makeText(this, "Entry saved.", Toast.LENGTH_SHORT);
    }

    private void updateDiaryEntry() {
        long id = diaryEntry.getId();
        Date createDate = diaryEntry.getCreateDate();
        Date readingDate = diaryEntry.getReadingDate();
        int morningReading = Integer.parseInt(editTextReading.getText().toString());
        int weight = Integer.parseInt(editTextWeight.getText().toString());
        boolean hasTakenMorningMeds = checkBoxMorning.isChecked();
        boolean hasTakenEveningMeds = checkBoxEvening.isChecked();
        diaryEntry = new DiaryEntry(
                id,
                readingDate,
                morningReading,
                weight,
                hasTakenMorningMeds,
                hasTakenEveningMeds,
                createDate);
    }

    private long getPassedEntryId() {
        return getIntent().getExtras().getLong(EXTRAS_KEY_ENTRY_ID, NEW_ENTRY_ID);
    }

    private static class LoadDiaryEntryAsyncTask extends AsyncTask<Long, Void, DiaryEntry> {

        private final Context context;
        private final ActivityEntryDetails activity;

        public LoadDiaryEntryAsyncTask(Context appContext, ActivityEntryDetails activity) {
            this.context = appContext;
            this.activity = activity;
        }

        @Override
        protected DiaryEntry doInBackground(Long... entryIds) {
            DiaryEntry diaryEntry = null;
            long entryId = entryIds[0];
            if (entryId == NEW_ENTRY_ID) {
                diaryEntry = new DiaryEntry(new Date());
            } else {
                DiaryRepository repository = new DiaryRepository(context);
                diaryEntry = repository.findById(entryId);
            }
            return diaryEntry;
        }

        @Override
        protected void onPostExecute(DiaryEntry diaryEntry) {
            activity.populateDiaryEntry(diaryEntry);
        }
    }

    private static class LoadTodaysDiaryEntryAsyncTask extends AsyncTask<Void, Void, DiaryEntry> {

        private final Context context;
        private final ActivityEntryDetails activity;

        public LoadTodaysDiaryEntryAsyncTask(Context appContext, ActivityEntryDetails activity) {
            this.context = appContext;
            this.activity = activity;
        }

        @Override
        protected DiaryEntry doInBackground(Void... voids) {
            DiaryEntry diaryEntry = null;
            DiaryRepository repository = new DiaryRepository(context);
            diaryEntry = repository.findForDate(new Date());
            return diaryEntry;
        }

        @Override
        protected void onPostExecute(DiaryEntry diaryEntry) {
            activity.populateDiaryEntry(diaryEntry);
        }
    }

    private static class SaveDiaryEntryAsyncTask extends AsyncTask<DiaryEntry, Void, Void> {

        private final Context context;

        public SaveDiaryEntryAsyncTask(Context appContext) {
            this.context = appContext;
        }

        @Override
        protected Void doInBackground(DiaryEntry... entries) {
            DiaryEntry diaryEntry = entries[0];
            DiaryRepository repository = new DiaryRepository(context);
            repository.saveEntry(diaryEntry);
            return null;
        }
    }
}
