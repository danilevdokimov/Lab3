package com.danil.asus.lab3.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.danil.asus.lab3.Constants;

/**
 * Created by Asus on 11/10/2015.
 */
public class UpdateBroadcastReceiver extends BroadcastReceiver {

    private static final long REPEATING_INTERVAL = 600_000;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Constants.LOG_TAG, "Start broadcast receiver");
        initializeAlarmService(context);
        //context.startService(new Intent(context, UpdateService.class));
    }

    private void initializeAlarmService(Context context) {
        Log.i(Constants.LOG_TAG, "Initialize alarm service");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC, 0, REPEATING_INTERVAL, pendingIntent);
    }
}
