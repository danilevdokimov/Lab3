package com.danil.asus.lab3.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.danil.asus.lab3.Constants;

/**
 * Created by Asus on 11/10/2015.
 */
public class UpdateBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Constants.LOG_TAG, "Start broadcast receiver");
        context.startService(new Intent(context, UpdateService.class));
    }
}
