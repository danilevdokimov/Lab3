package com.danil.asus.lab3.user.data.requests.impl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.danil.asus.lab3.MainActivity;
import com.danil.asus.lab3.StartActivity;
import com.danil.asus.lab3.user.data.requests.AbstractRequestTask;
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
 * Created by Asus on 10/27/2015.
 */
public class PasswordRequest extends AbstractRequestTask<String, Void, ServiceResponse> {

    public PasswordRequest(AppCompatActivity activity) {
        super(activity);
        dataType = new TypeToken<ServiceResponse>() {
        }.getType();
    }

    @Override
    protected ServiceResponse doInBackground(String... params) {
        String userPassword = params[0].replace(" ", "+");
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("password", userPassword);
        try {
            HttpURLConnection connection = getConnection(RestApi.AUTHENTICATION, paramsMap, "GET");
            return handleResponse(connection.getInputStream());
        } catch (IOException e) {
            Log.i("Lab3", "Connection error", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.CONNECTION_ERROR_MASSAGE);
        }
    }

    @Override
    protected void onPostExecute(ServiceResponse response) {
        showToast(response.getMassage());
        if (response.getStatus().equals(ServiceResponse.SUCCESS)) {
            ((StartActivity) activity).saveUserData();
            ((StartActivity) activity).showMeetings();
        }
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
}
