package com.danil.asus.service;

import com.danil.asus.shared.Meeting;
import com.danil.asus.shared.Participant;
import com.danil.asus.shared.gson.GsonHelper;
import com.danil.asus.shared.service.RestApi;
import com.danil.asus.shared.service.ServiceResponse;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Asus on 10/22/2015.
 */
public class Server extends HttpServlet {

    private RestApi restService = new RestService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        switch (query) {
            case RestApi.AUTHENTICATION:
                String userPassword = req.getParameter("password");
                boolean result = restService.checkPassord(userPassword);
                ServiceResponse response;
                if (result) {
                    response = new ServiceResponse(ServiceResponse.SUCCESS, "Success connect");
                } else {
                    response = new ServiceResponse(ServiceResponse.FAIL, "Authentication error");
                }
                GsonHelper.write(response, resp.getOutputStream());
                break;
            case RestApi.GET_MEETINGS:
                ServiceResponse<Collection<String>> meetingsResponse = new ServiceResponse<>(restService.getMeetings());
                GsonHelper.write(meetingsResponse, new TypeToken<ServiceResponse<Collection<String>>>() {
                }.getType(), resp.getOutputStream());
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        ServiceResponse response;
        switch (query) {
            case RestApi.CREATE_MEETING:
                Meeting newMeeting = GsonHelper.read(Meeting.class, req.getInputStream());
                restService.addMeeting(newMeeting);
                response = new ServiceResponse(ServiceResponse.SUCCESS, "Meeting created");
                GsonHelper.write(response, resp.getOutputStream());
                break;
            case RestApi.ACCEPT_MEETING:
                String title = req.getParameter("title");
                Participant participant = GsonHelper.read(Participant.class, req.getInputStream());
                Meeting acceptMeeting = restService.acceptMeeting(participant, title);
                if (acceptMeeting != null) {
                    response = new ServiceResponse(ServiceResponse.SUCCESS, "You accept meeting");
                    GsonHelper.write(response, resp.getOutputStream());
                } else {
                    response = new ServiceResponse(ServiceResponse.FAIL, "Accept error: "
                            + ServiceResponse.MEETING_NOT_FOUND_MASSAGE);
                    GsonHelper.write(response, resp.getOutputStream());
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        ServiceResponse<Meeting> response;
        Type responseType = new TypeToken<ServiceResponse<Meeting>>() {
        }.getType();
        switch (query) {
            case RestApi.GET_MEETING:
                String title = req.getParameter("title");
                Meeting meeting = restService.getMeeting(title);
                if (meeting != null) {
                    response = new ServiceResponse<>(meeting);
                    GsonHelper.write(response, responseType, resp.getOutputStream());
                } else {
                    response = new ServiceResponse<>(ServiceResponse.FAIL, ServiceResponse.MEETING_NOT_FOUND_MASSAGE);
                    GsonHelper.write(response, resp.getOutputStream());
                }
                break;
            case RestApi.FIND_MEETING:
                String fragment = req.getParameter("fragment");
                Meeting findMeeting = restService.getMeetingByDescriptionFragment(fragment);
                if (findMeeting != null) {
                    response = new ServiceResponse<>(findMeeting);
                    GsonHelper.write(response, responseType, resp.getOutputStream());
                } else {
                    response = new ServiceResponse<>(ServiceResponse.FAIL, ServiceResponse.MEETING_NOT_FOUND_MASSAGE);
                    GsonHelper.write(response, resp.getOutputStream());
                }
                break;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        ServiceResponse response;
        switch (query) {
            case RestApi.REMOVE_MEETING:
                String title = req.getParameter("title");
                Meeting meeting = restService.removeMeeting(title);
                if (meeting != null) {
                    response = new ServiceResponse(ServiceResponse.SUCCESS, "Meeting deleted");
                } else {
                    response = new ServiceResponse(ServiceResponse.FAIL, ServiceResponse
                            .MEETING_NOT_FOUND_MASSAGE);
                }
                GsonHelper.write(response, resp.getOutputStream());
                break;
        }
    }
}
