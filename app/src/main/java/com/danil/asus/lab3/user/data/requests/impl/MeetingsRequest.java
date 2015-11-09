package com.danil.asus.lab3.user.data.requests.impl;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.danil.asus.lab3.MainActivity;
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
import java.util.List;

/**
 * Created by Asus on 10/28/2015.
 */
public class MeetingsRequest extends AbstractRequestTask<Void, Void, ServiceResponse<List<String>>> {
    public MeetingsRequest(AppCompatActivity activity) {
        super(activity);
        dataType = new TypeToken<ServiceResponse<List<String>>>() {
        }.getType();
    }

    @Override
    protected ServiceResponse<List<String>> handleResponse(InputStream stream) {
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
    protected ServiceResponse<List<String>> doInBackground(Void... params) {
        try {
            HttpURLConnection connection = getConnection(RestApi.GET_MEETINGS, new HashMap<String, String>(), "GET");
            return handleResponse(connection.getInputStream());
        } catch (IOException e) {
            Log.i("Lab3", "Connection error", e);
            return new ServiceResponse<>(ServiceResponse.FAIL, ServiceResponse.CONNECTION_ERROR_MASSAGE);
        }
    }

    @Override
    protected void onPostExecute(ServiceResponse<List<String>> response) {
        if (response.getStatus().equals(ServiceResponse.SUCCESS)) {
            ((MainActivity) activity).initializeList(response.getData());
        } else {
            showToast(response.getMassage());
        }
    }
}
