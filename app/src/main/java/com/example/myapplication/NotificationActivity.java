package com.example.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NotificationActivity extends AppCompatActivity {
    private NotificationCompat.Builder mNotifyBuilder;

    private NotificationManager mNotifyManager;

    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    public void showNotification(View view) {
        switch (view.getId()){
            case R.id.button:
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelid")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("my title")
                        .setContentText("my content")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                mNotifyManager.notify(NOTIFICATION_ID, builder.build());
                break;
            case R.id.button3:
                Intent dialIntent = new Intent(NotificationActivity.this,MainActivity.class);
                PendingIntent notifyPendingIntent =
                        PendingIntent.getActivity(this,007,dialIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager =
                        (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setInexactRepeating(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime()+ 30*1000,//trigger time
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES, // repeating interval
                        notifyPendingIntent);

                break;
        }




       /* mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Notification title")
                .setContentText(" notification content.")
                .setSmallIcon(android.R.drawable.stat_notify_chat);
        Notification myNotification = mNotifyBuilder.build();
        mNotifyManager.notify(NOTIFICATION_ID,  myNotification);*/

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "mychannel";
                    //getString(R.string.channel_name);
            String description = "channel description";
                    //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channelid", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
