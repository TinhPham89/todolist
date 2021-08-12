package com.btcsc.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class AlarmReceiver1 extends BroadcastReceiver {
    int ID;
    String subject , plan , time , date;
    Planning planning;
    Database database;
    int status ;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ABC","hello , i am RECEIVER 1");

        ID = intent.getIntExtra("ID",0);
        Log.d("ABC",String.valueOf(ID));

        Intent intent1 = new Intent(context,Action1.class);
        intent1.putExtra("id",ID);
        context.startForegroundService(intent1);


    }
}
