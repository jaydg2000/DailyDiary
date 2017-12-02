package com.gdc.diary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class DiaryAlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            DiaryNotificationManager diaryAlarmManager = new DiaryNotificationManager();
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int minute = Calendar.getInstance().get(Calendar.MINUTE)+1;
            diaryAlarmManager.createAlarmForNotification(context, hour, minute);
            return;
        }

        if (intent.hasExtra(DiaryNotificationManager.EXTRA_DIARY_ALARM_ID)) {
            DiaryNotificationManager diaryAlarmManager = new DiaryNotificationManager();
            diaryAlarmManager.createNotificationForDiaryEntryReminder(context);
            diaryAlarmManager.createAlarmForNotification(context, 5);
        }
    }
}
