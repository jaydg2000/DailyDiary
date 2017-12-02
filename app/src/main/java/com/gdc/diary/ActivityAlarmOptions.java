package com.gdc.diary;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class ActivityAlarmOptions extends AppCompatActivity {

    public static final int SNOOZE_SHORT = 15;
    public static final int SNOOZE_MEDIUM = 30;
    public static final int SNOOZE_LONG = 60;

    private Button buttonSnooze;
    private Button buttonTakeReadings;
    private RadioButton radioButtonSnoozeShort;
    private RadioButton radioButtonSnoozeMedium;
    private RadioButton radioButtonSnoozeLong;

    public static Intent getStartIntent(Context context) {
        Intent starter = new Intent(context, ActivityAlarmOptions.class);
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_options);

        buttonSnooze = findViewById(R.id.buttonSnooze);
        buttonTakeReadings = findViewById(R.id.buttonTakeReadings);
        radioButtonSnoozeShort = findViewById(R.id.radioButtonSnoozeShort);
        radioButtonSnoozeMedium = findViewById(R.id.radioButtonSnoozeMedium);
        radioButtonSnoozeLong = findViewById(R.id.radioButtonSnoozeLong);

        initSnoozeButton();
        initNevermindButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cancelExistingDiaryNotification();
    }

    private void cancelExistingDiaryNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(DiaryNotificationManager.DIARY_ENTRY_NOTIFICATION_ID);
    }

    private void initSnoozeButton() {
        buttonSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int snoozeMinutes = getSnoozeMinutes();
                new DiaryNotificationManager()
                        .createAlarmForNotification(ActivityAlarmOptions.this, snoozeMinutes);
                finish();
            }
        });
    }

    private void initNevermindButton() {
        buttonTakeReadings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DiaryNotificationManager()
                        .createAlarmForNotification(ActivityAlarmOptions.this);
                ActivityEntryDetails.start(
                        ActivityAlarmOptions.this,
                        ActivityEntryDetails.TODAY_ENTRY_ID);
                finish();
            }
        });
    }

    private int getSnoozeMinutes() {
        if (radioButtonSnoozeShort.isChecked())
            return SNOOZE_SHORT;
        if (radioButtonSnoozeMedium.isChecked())
            return SNOOZE_MEDIUM;

        return SNOOZE_LONG;
    }
}
