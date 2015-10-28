package com.danil.asus.shared.gson;

import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by Asus on 10/22/2015.
 */
public class GsonHelper {

    public static <T> T read(Class<T> type, InputStream stream) throws IOException {
        String src = IOUtils.toString(stream);
        return gsonToObject(src, type);
    }

    public static void write(Object src, OutputStream stream) throws IOException {
        IOUtils.write(toGson(src), stream);
    }

    public static String toGson(Object src) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().toJson(src);
    }

    public static <T> T gsonToObject(String src, Class<T> type) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(src, type);
    }
}
