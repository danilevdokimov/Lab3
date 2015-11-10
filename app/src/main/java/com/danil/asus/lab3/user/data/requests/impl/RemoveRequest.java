package com.danil.asus.lab3.user.data.requests.impl;

import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.danil.asus.lab3.Constants;
import com.danil.asus.lab3.MainActivity;
import com.danil.asus.lab3.user.data.requests.AbstractRequestTask;
import com.danil.asus.shared.gson.GsonHelper;
import com.danil.asus.shared.service.RestApi;
import com.danil.asus.shared.service.ServiceResponse;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 11/3/2015.
 */
public class RemoveRequest extends AbstractRequestTask<String, Void, ServiceResponse> {
    public RemoveRequest(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    protected ServiceResponse handleResponse(InputStream stream) {
        try {
            return GsonHelper.read(ServiceResponse.class, stream);
        } catch (JsonParseException e) {
            Log.i(Constants.LOG_TAG, "Parse response error", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.RESPONSE_ERROR_MASSAGE);
        } catch (IOException e) {
            Log.i(Constants.LOG_TAG, "Could not read response", e);
            return new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.RESPONSE_ERROR_MASSAGE);
        }
    }

    @Override
    protected ServiceResponse doInBackground(String... params) {
        String removeMeetingTitle = params[0].replace(" ", "+");
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("title", removeMeetingTitle);
        try {
            HttpURLConnection connection = getConnection(RestApi.REMOVE_MEETING, paramsMap, "DELETE");
            return handleResponse(connection.getInputStream());
        } catch (IOException e) {
            Log.i(Constants.LOG_TAG, "Connection error", e);
            return new ServiceResponse<>(ServiceResponse.FAIL, ServiceResponse.CONNECTION_ERROR_MASSAGE);
        }
    }

    @Override
    protected void onPostExecute(ServiceResponse response) {
        showToast(response.getMassage());
        if (response.getStatus().equals(ServiceResponse.SUCCESS)) {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        }
    }
}
