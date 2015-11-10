package com.danil.asus.lab3.user.data.requests.impl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.danil.asus.lab3.Constants;
import com.danil.asus.lab3.MainActivity;
import com.danil.asus.lab3.R;
import com.danil.asus.lab3.service.UpdateService;
import com.danil.asus.shared.service.RestApi;
import com.danil.asus.shared.service.ServiceResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Asus on 11/10/2015.
 */
public class MeetingsServiceRequest extends MeetingsRequest {

    private UpdateService service;

    public MeetingsServiceRequest(AppCompatActivity activity) {
        super(activity);
    }

    public MeetingsServiceRequest(UpdateService service) {
        super(null);
        this.service = service;
    }

    @Override
    protected ServiceResponse<List<String>> doInBackground(Void... params) {
        try {
            HttpURLConnection connection = getConnection(RestApi.GET_MEETINGS, new HashMap<String, String>(), "GET");
            ServiceResponse<List<String>> response = handleResponse(connection.getInputStream());
            if (response.getStatus().equals(ServiceResponse.SUCCESS)) {
                List<String> actualMeetings = service.getActualMeetings();
                List<String> serverMeetings = response.getData();
                if (serverMeetings.size() == actualMeetings.size()) {
                    for (String meeting : actualMeetings) {
                        if (!serverMeetings.contains(meeting)) {
                            // TODO: 11/10/2015 notify
                            notifyUser();
                            service.setActualMeetings(serverMeetings);
                            break;
                        }
                    }
                } else {
                    // TODO: 11/10/2015 notify
                    notifyUser();
                    service.setActualMeetings(serverMeetings);
                }
            }
            return response;
        } catch (IOException e) {
            Log.i(Constants.LOG_TAG, "Connection error", e);
            return new ServiceResponse<>(ServiceResponse.FAIL, ServiceResponse.CONNECTION_ERROR_MASSAGE);
        }
    }

    private void notifyUser() {
        NotificationManager notificationManager = (NotificationManager) service
                .getSystemService(Service.NOTIFICATION_SERVICE);
        Intent intent = new Intent(service, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(service, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(service).setContentInfo("Your meetings updated")
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pIntent).setAutoCancel(true).build();
        notificationManager.notify(1, notification);
    }

    @Override
    protected void onPostExecute(ServiceResponse<List<String>> response) {
    }
}
