package com.gdc.diary;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.gdc.diary.helpers.DateHelper;

import java.util.Calendar;
import java.util.Date;

public class DiaryAlarmReciever extends BroadcastReceiver {
    private static final String EXTRA_DIARY_ALARM_ID = "DIARY_ALARM";
    private static final int DIARY_ENTRY_NOTIFICATION_ID = 2000;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent alarmIntent = new Intent(context, DiaryAlarmReciever.class);
            alarmIntent.putExtra(EXTRA_DIARY_ALARM_ID, 1);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    alarmIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            long alarmTime = getTimeInMillis(
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE)+1);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
            }
            return;
        }

        if (intent.hasExtra(EXTRA_DIARY_ALARM_ID)) {

            PendingIntent diaryEntryPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            1,
                            ActivityEntryDetails.getStartIntent(
                                    context,
                                    ActivityEntryDetails.TODAY_ENTRY_ID),
                            PendingIntent.FLAG_CANCEL_CURRENT);

            RemoteViews contentView = new RemoteViews("com.gdc.diary", R.layout.notification_diary_alarm);
            Notification.Builder builder = new Notification.Builder(context)
                    .setTicker("This is ticker text")
                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                    .setAutoCancel(true)
                    .setContentIntent(diaryEntryPendingIntent)
                    .setContent(contentView);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(DIARY_ENTRY_NOTIFICATION_ID, builder.build());
        }
    }

    private long getTimeInMillis(int hour, int minute) {
        long timeInMillis;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);

        Calendar now = Calendar.getInstance();

        if (now.before(cal)) {
            timeInMillis = cal.getTimeInMillis();
        } else {
            cal.add(Calendar.DATE, 1);
            timeInMillis = cal.getTimeInMillis();
        }

        Log.d("DIARY", "Alarm set for: " + DateHelper.getDateTimeStr(cal.getTime()));

        return timeInMillis;
    }
}
