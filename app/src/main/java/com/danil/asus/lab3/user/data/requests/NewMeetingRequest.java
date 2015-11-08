package com.danil.asus.lab3.user.data.requests;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.danil.asus.lab3.MainActivity;
import com.danil.asus.shared.Meeting;
import com.danil.asus.shared.gson.GsonHelper;
import com.danil.asus.shared.service.ServiceResponse;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by Asus on 11/8/2015.
 */
public class NewMeetingRequest extends AbstractRequestTask<Meeting, Void, ServiceResponse> {
    public NewMeetingRequest(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    protected ServiceResponse handleResponse(InputStream stream) {
        try {
            return GsonHelper.read(ServiceResponse.class, stream);
        } catch (JsonParseException e) {
            Log.i("Lab3", "Parse response error", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.RESPONSE_ERROR_MASSAGE);
        } catch (IOException e) {
            Log.i("Lab3", "Could not read response", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.RESPONSE_ERROR_MASSAGE);
        }
    }

    @Override
    protected ServiceResponse doInBackground(Meeting... params) {
        Meeting newMeeting = params[0];
        try {
            HttpURLConnection connection = getConnection("?query=create", "PUT",
                    GsonHelper.toJson(newMeeting));
            return handleResponse(connection.getInputStream());
        } catch (IOException e) {
            Log.i("Lab3", "Connection error", e);
            return new ServiceResponse<>(ServiceResponse.FAIL, ServiceResponse.CONNECTION_ERROR_MASSAGE);
        }
    }

    @Override
    protected void onPostExecute(ServiceResponse response) {
        Toast.makeText(activity, response.getMassage(), Toast.LENGTH_SHORT).show();
        if (response.getStatus().equals(ServiceResponse.SUCCESS)) {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        }
    }
}
