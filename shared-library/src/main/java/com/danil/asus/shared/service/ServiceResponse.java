package com.danil.asus.shared.service;

/**
 * Created by Asus on 10/25/2015.
 */
public class ServiceResponse {

    public static final String SUCCESS = "Success";
    public static final String FAIL = "Fail";
    public static final String MEETING_NOT_FOUND_MASSAGE = "Meeting not found";
    public static final String RESPONSE_ERROR_MASSAGE = "Response not correct";
    public static final String CONNECTION_ERROR_MASSAGE = "Connection error";
    private String status;
    private String massage;

    public ServiceResponse() {
    }

    public ServiceResponse(String status, String massage) {
        this.status = status;
        this.massage = massage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}