package com.danil.asus.lab3.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.danil.asus.lab3.Constants;
import com.danil.asus.lab3.user.data.requests.impl.MeetingsServiceRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Asus on 11/10/2015.
 */
public class UpdateService extends Service {

    private List<String> actualMeetings = new ArrayList<>();
    private PendingIntent activityLink;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(Constants.LOG_TAG, "Start update service");
        activityLink = intent.getParcelableExtra(Constants.PENDING_INTENT_KEY);
        MeetingsServiceRequest meetingsServiceRequest = new MeetingsServiceRequest(this);
        meetingsServiceRequest.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(Constants.LOG_TAG, "Stop update service");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public List<String> getActualMeetings() {
        return actualMeetings;
    }

    public void setActualMeetings(List<String> actualMeetings) {
        this.actualMeetings = actualMeetings;
    }

    public PendingIntent getActivityLink() {
        return activityLink;
    }
}
