package com.example.notificationscheduler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

public class NotificationJobService extends JobService {

    private NotificationManager mNotification;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public boolean onStartJob(JobParameters params) {
        createNotificationChannel();
        PendingIntent contentPending = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuild = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Notifcation")
                .setContentText("This is notitification")
                .setContentIntent(contentPending)
                .setSmallIcon(R.drawable.ic_job_runing)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotification.notify(0, notificationBuild.build());

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    public void createNotificationChannel() {
        mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new
                    NotificationChannel(PRIMARY_CHANNEL_ID, "Job Service Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification Job Service");

            mNotification.createNotificationChannel(notificationChannel);
        }
    }
}
