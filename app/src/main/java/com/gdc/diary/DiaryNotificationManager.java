package com.gdc.diary;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.gdc.diary.helpers.DateHelper;

import java.util.Calendar;
import java.util.Date;

public final class DiaryNotificationManager {

    public static final String EXTRA_DIARY_ALARM_ID = "DIARY_ALARM";
    public static final int DIARY_ENTRY_NOTIFICATION_ID = 2000;
    public static final int DEFAULT_ALARM_HOUR = 6;
    public static final int DEFAULT_ALARM_MINUTE = 0;

    public void createAlarmForNotification(Context context, int snoozeMinutes) {
        Date now = DateHelper.getCurrentDateTime();

        int hour = DateHelper.getHour(now);
        int minutes = DateHelper.getMinute(now) + snoozeMinutes;
        if (minutes > 59) {
            hour++;
            minutes-=60;
        }
        createAlarmForNotification(context, hour, minutes);
    }

    public void createAlarmForNotification(Context context, int hour, int minute) {
        Intent alarmIntent = new Intent(context, DiaryAlarmReciever.class);
        alarmIntent.putExtra(EXTRA_DIARY_ALARM_ID, 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        long alarmTime = getTimeInMillis(hour, minute);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
            Log.d("DIARY:", "Diary entry alarm set for " + hour + ":" + minute);
        }
    }

    public void createNotificationForDiaryEntryReminder(Context context) {
        PendingIntent diaryEntryPendingIntent = createNotificationPendingIntent(context);
        buildNotification(context, diaryEntryPendingIntent);
    }

    private void buildNotification(Context context, PendingIntent diaryEntryPendingIntent) {
        RemoteViews contentView =
                new RemoteViews("com.gdc.diary", R.layout.notification_diary_alarm);

        PendingIntent snoozePendingIntent = createNotificationSnoozePendingIntent(context);
        contentView.setOnClickPendingIntent(R.id.buttonNotificationSnooze, snoozePendingIntent);

        Notification.Builder builder = new Notification.Builder(context)
                .setTicker("This is ticker text")
                .setSmallIcon(R.drawable.ic_diary_notification)
                .setAutoCancel(true)
                .setContentIntent(diaryEntryPendingIntent)
                .setContent(contentView);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(DIARY_ENTRY_NOTIFICATION_ID, builder.build());
    }

    private PendingIntent createNotificationPendingIntent(Context context) {
        return PendingIntent.getActivity(
                context,
                1,
                ActivityEntryDetails.getStartIntent(
                        context,
                        ActivityEntryDetails.TODAY_ENTRY_ID),
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private PendingIntent createNotificationSnoozePendingIntent(Context context) {
        return PendingIntent.getActivity(
                context,
                1,
                ActivityAlarmOptions.getStartIntent(context),
                    PendingIntent.FLAG_CANCEL_CURRENT);
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
