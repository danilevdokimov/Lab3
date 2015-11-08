package com.danil.asus.shared.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;


/**
 * Created by Asus on 10/22/2015.
 */
public class GsonHelper {

    public static <T> T read(Class<T> type, InputStream stream) throws IOException {
        String src = IOUtils.toString(stream);
        return jsonToObject(src, type);
    }

    public static <T> T read(Type type, InputStream stream) throws IOException {
        String src = IOUtils.toString(stream);
        return new Gson().fromJson(src, type);
    }

    public static void write(Object src, OutputStream stream) throws IOException {
        IOUtils.write(toJson(src), stream);
    }

    public static void write(Object src, Type type, OutputStream stream) throws IOException {
        String json = new Gson().toJson(src, type);
        IOUtils.write(json, stream);
    }

    public static String toJson(Object src) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().toJson(src);
    }

    public static <T> T jsonToObject(String src, Class<T> type) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(src, type);
    }
}
