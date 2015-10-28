package com.danil.asus.lab3.user.data;

/**
 * Created by Asus on 10/27/2015.
 */
public class UserData {

    public static final String DEFAULT_USER_FIO = "Unknown user";
    public static final String DEFAULT_USER_POST = "Unknown post";

    private static String userFio = DEFAULT_USER_FIO;
    private static String userPost = DEFAULT_USER_POST;

    public static String getUserPost() {
        return userPost;
    }

    public static void setUserPost(String userPost) {
        UserData.userPost = userPost;
    }

    public static String getUserFio() {
        return userFio;
    }

    public static void setUserFio(String userFio) {
        UserData.userFio = userFio;
    }
}
