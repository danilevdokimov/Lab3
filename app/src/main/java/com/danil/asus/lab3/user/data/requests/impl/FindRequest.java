package com.danil.asus.lab3.user.data.requests.impl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.danil.asus.lab3.Constants;
import com.danil.asus.lab3.MainActivity;
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
 * Created by Asus on 11/4/2015.
 */
public class FindRequest extends AbstractRequestTask<String, Void, ServiceResponse<Meeting>> {
    public FindRequest(AppCompatActivity activity) {
        super(activity);
        dataType = new TypeToken<ServiceResponse<Meeting>>() {
        }.getType();
    }

    @Override
    protected ServiceResponse<Meeting> handleResponse(InputStream stream) {
        try {
            return GsonHelper.read(dataType, stream);
        } catch (JsonParseException e) {
            Log.i(Constants.LOG_TAG, "Parse response error", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.RESPONSE_ERROR_MASSAGE);
        } catch (IOException e) {
            Log.i(Constants.LOG_TAG, "Could not read response", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.RESPONSE_ERROR_MASSAGE);
        }
    }

    @Override
    protected ServiceResponse<Meeting> doInBackground(String... params) {
        String fragment = params[0].replace(" ", "+");
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("fragment", fragment);
        try {
            HttpURLConnection connection = getConnection(RestApi.FIND_MEETING, paramsMap, "POST");
            return handleResponse(connection.getInputStream());
        } catch (IOException e) {
            Log.i(Constants.LOG_TAG, "Connection error", e);
            return new ServiceResponse<>(ServiceResponse.FAIL, ServiceResponse.CONNECTION_ERROR_MASSAGE);
        }
    }

    @Override
    protected void onPostExecute(ServiceResponse<Meeting> meetingServiceResponse) {
        if (meetingServiceResponse.getStatus().equals(ServiceResponse.SUCCESS)) {
            ((MeetingInfoActivity) activity).showMeetingInfo(meetingServiceResponse.getData());
        } else {
            showToast(meetingServiceResponse.getMassage());
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        }
    }
}
