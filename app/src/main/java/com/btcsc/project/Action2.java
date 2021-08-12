package com.btcsc.project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class Action2 extends Service {
    String subject , plan , time , date,list;
    Planning planning;
    Database database;
    int status ,ID;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ABC","I am Action 2");

        database= new Database(getApplicationContext());
        database.createTable();

        status = 0;
        ID = intent.getIntExtra("id",10);
        Log.d("ABC",String.valueOf(ID));

        planning = database.getOnePlanning(ID);
        Log.d("ABC",planning.toString());

        subject = planning.subject;
        plan = planning.plan;
        time = planning.time;
        date = planning.date;
        list = planning.list;
        status = -1;

        Planning planning1 = new Planning(subject,plan,date,time,ID,status,list);
        Log.d("ABC",planning1.toString());
        database.updatePlanning(planning1);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(subject)
                    .setContentText("Bạn có 1 kế hoạch đã quá hạn : " + plan +
                            "\nThời gian :"+ time + " "+ date ).build();

            startForeground(1, notification);
        }
    }
}
