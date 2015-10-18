package com.coursera.eugenel.dailyselfie;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by eugenel on 7/17/2015.
 */
public class Alarm extends BroadcastReceiver {

    private final int MY_NOTIFICATION_ID = 11151990;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        final Intent restartMainActivityIntent = new Intent(context,
                MainActivity.class);
        restartMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // If not, create a PendingIntent using
        // the
        // restartMainActivityIntent and set its flags
        // to FLAG_UPDATE_CURRENT
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                restartMainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Uses R.layout.custom_notification for the
        // layout of the notification View. The xml
        // file is in res/layout/custom_notification.xml

        RemoteViews mContentView = new RemoteViews(
                context.getPackageName(),
                R.layout.custom_notification);

        // Set the notification View's text to
        // reflect whether the download completed
        // successfully
        mContentView.setTextViewText(R.id.text, context.getString(R.string.alarm_string));

        // Use the Notification.Builder class to
        // create the Notification. You will have to set
        // several pieces of information. You can use
        // android.R.drawable.stat_sys_warning
        // for the small icon. You should also
        // setAutoCancel(true).
        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.camera)
                .setContent(mContentView)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Send the notification
        // Get the NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Pass the Notification to the NotificationManager:
        mNotificationManager.notify(MY_NOTIFICATION_ID,
                notificationBuilder.build());

    }

    public void SetAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 2, pi); // Millisec * Second * Minute
    }

}
