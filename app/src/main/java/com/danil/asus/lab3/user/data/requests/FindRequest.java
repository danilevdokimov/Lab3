package com.danil.asus.lab3.user.data.requests;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.danil.asus.lab3.MainActivity;
import com.danil.asus.lab3.MeetingInfoActivity;
import com.danil.asus.shared.Meeting;
import com.danil.asus.shared.gson.GsonHelper;
import com.danil.asus.shared.service.ServiceResponse;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

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
            Log.i("Lab3", "Parse response error", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.RESPONSE_ERROR_MASSAGE);
        } catch (IOException e) {
            Log.i("Lab3", "Could not read response", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.RESPONSE_ERROR_MASSAGE);
        }
    }

    @Override
    protected ServiceResponse<Meeting> doInBackground(String... params) {
        String fragment = params[0].replace(" ", "+");
        try {
            HttpURLConnection connection = getConnection("?query=find&fragment=" + fragment, "POST");
            return handleResponse(connection.getInputStream());
        } catch (IOException e) {
            Log.i("Lab3", "Connection error", e);
            return new ServiceResponse<>(ServiceResponse.FAIL, ServiceResponse.CONNECTION_ERROR_MASSAGE);
        }
    }

    @Override
    protected void onPostExecute(ServiceResponse<Meeting> meetingServiceResponse) {
        if (meetingServiceResponse.getStatus().equals(ServiceResponse.SUCCESS)) {
            ((MeetingInfoActivity) activity).showMeetingInfo(meetingServiceResponse.getData());
        } else {
            Toast.makeText(activity, meetingServiceResponse.getMassage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        }
    }
}
