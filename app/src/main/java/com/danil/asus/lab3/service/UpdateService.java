package com.danil.asus.lab3.service;

import android.app.NotificationManager;
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
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private static final int TEN_MINUTES = 10;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(Constants.LOG_TAG, "Start update service");
        executor.scheduleAtFixedRate(new TaskExecutor(this), 0, TEN_MINUTES, TimeUnit.MINUTES);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(Constants.LOG_TAG, "Stop update service");
        executor.shutdown();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TaskExecutor implements Runnable {
        private UpdateService service;

        public TaskExecutor(UpdateService parentService) {
            service = parentService;
        }

        @Override
        public void run() {
            Log.i(Constants.LOG_TAG, "Execute service request");
            MeetingsServiceRequest meetingsServiceRequest = new MeetingsServiceRequest(service);
            meetingsServiceRequest.execute();
        }
    }

    public List<String> getActualMeetings() {
        return actualMeetings;
    }

    public void setActualMeetings(List<String> actualMeetings) {
        this.actualMeetings = actualMeetings;
    }
}
