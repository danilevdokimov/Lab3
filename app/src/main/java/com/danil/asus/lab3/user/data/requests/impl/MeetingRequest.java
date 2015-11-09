package com.danil.asus.lab3.user.data.requests.impl;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.danil.asus.lab3.MeetingInfoActivity;
import com.danil.asus.lab3.user.data.requests.AbstractRequestTask;
import com.danil.asus.shared.Meeting;
import com.danil.asus.shared.gson.GsonHelper;
import com.danil.asus.shared.service.RestApi;
import com.danil.asus.shared.service.ServiceResponse;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 10/29/2015.
 */
public class MeetingRequest extends AbstractRequestTask<String, Void, ServiceResponse<Meeting>> {
    public MeetingRequest(AppCompatActivity activity) {
        super(activity);
        dataType = new TypeToken<ServiceResponse<Meeting>>() {
        }.getType();
    }

    @Override
    protected ServiceResponse<Meeting> handleResponse(InputStream stream) {
        try {
            return GsonHelper.read(dataType, stream);
        } catch (JsonParseException e) {
            Log.i("Lab3", "Parse response error", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.RESPONSE_ERROR_MASSAGE);
        } catch (IOException e) {
            Log.i("Lab3", "Could not read response", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.RESPONSE_ERROR_MASSAGE);
        }
    }

    @Override
    protected ServiceResponse<Meeting> doInBackground(String... params) {
        String meetingTitle = params[0].replace(" ", "+");
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("title", meetingTitle);
        try {
            HttpURLConnection connection = getConnection(RestApi.GET_MEETING, paramsMap, "POST");
            return handleResponse(connection.getInputStream());
        } catch (IOException e) {
            Log.i("Lab3", "Connection error", e);
            return new ServiceResponse<>(ServiceResponse.FAIL, ServiceResponse.CONNECTION_ERROR_MASSAGE);
        }
    }

    @Override
    protected void onPostExecute(ServiceResponse<Meeting> meeting) {
        if (meeting.getStatus().equals(ServiceResponse.SUCCESS)) {
            ((MeetingInfoActivity) activity).showMeetingInfo(meeting.getData());
        } else {
            showToast(meeting.getMassage());
        }
    }
}
