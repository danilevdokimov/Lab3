package com.danil.asus.lab3.user.data.requests;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.danil.asus.shared.gson.GsonHelper;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Asus on 10/27/2015.
 */
public abstract class AbstractRequestTask<T, E, K> extends AsyncTask<T, E, K> {

    private static final String SERVER_URL = "http://192.168.1.166:8585/rest-service/Server";
    private static final String EMPTY_BODY = "";

    protected Type dataType;
    protected AppCompatActivity activity;

    public AbstractRequestTask(AppCompatActivity activity) {
        super();
        this.activity = activity;
    }

    protected HttpURLConnection getConnection(String query, String requestType) throws IOException {
        return getConnection(query, requestType, EMPTY_BODY);
    }

    protected HttpURLConnection getConnection(String query, String requestType, String body) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(SERVER_URL + query).openConnection();
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setRequestProperty("Content-Type", "text/xml");
        connection.setRequestMethod(requestType);
        if (!EMPTY_BODY.equals(body)) {
            connection.setDoOutput(true);
            connection.getOutputStream().write(body.getBytes());
        }
        return connection;
    }

    protected abstract K handleResponse(InputStream stream);
}
