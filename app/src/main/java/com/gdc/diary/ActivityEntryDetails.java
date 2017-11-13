package com.gdc.diary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.gdc.diary.data.DiaryEntry;
import com.gdc.diary.data.DiaryRepository;
import com.gdc.diary.helpers.DateHelper;

import java.util.Date;

public class ActivityEntryDetails extends AppCompatActivity {

    public static final String EXTRAS_KEY_ENTRY_ID = "entry_id";
    private DiaryEntry diaryEntry;

    private TextView textViewDate;
    private EditText editTextReading;
    private EditText editTextWeight;
    private CheckBox checkBoxMorning;
    private CheckBox checkBoxEvening;

    public static void start(Context context, int id) {
        Intent starter = new Intent(context, ActivityEntryDetails.class);
        starter.putExtra(EXTRAS_KEY_ENTRY_ID, id);
        context.startActivity(starter);
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
        bindData();
    }

    private void bindData() {
        textViewDate.setText(DateHelper.getDisplayableDateStr(diaryEntry.getReadingDate()));
        editTextReading.setText(Integer.toString(diaryEntry.getMorningReading()));
        editTextWeight.setText(Integer.toString(diaryEntry.getWeight()));
        checkBoxMorning.setChecked(diaryEntry.hasTakenMorningMeds());
        checkBoxEvening.setChecked(diaryEntry.hasTakenEveningMeds());
    }

    private void loadDiaryEntry() {
        int entryId = getPassedEntryId();

        if (entryId == DiaryRepository.NEW_ENTRY_ID) {
            createNewEntry();
        } else {
            DiaryRepository repository = new DiaryRepository();
            diaryEntry = repository.findById(entryId);
        }
    }

    private void createNewEntry() {
        diaryEntry = new DiaryEntry();
        diaryEntry.setReadingDate(new Date());
    }

    private int getPassedEntryId() {
        return getIntent().getExtras().getInt(EXTRAS_KEY_ENTRY_ID, DiaryRepository.NEW_ENTRY_ID);
    }
}
