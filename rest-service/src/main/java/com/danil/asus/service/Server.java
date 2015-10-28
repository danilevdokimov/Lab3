package com.danil.asus.service;

import com.danil.asus.shared.Meeting;
import com.danil.asus.shared.Participant;
import com.danil.asus.shared.gson.GsonHelper;
import com.danil.asus.shared.service.ServiceResponse;

import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;

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
            case "authentication":
                String userPassword = req.getParameter("password");
                boolean result = restService.checkPassord(userPassword);
                ServiceResponse response;
                if (result) {
                    response = new ServiceResponse(ServiceResponse.SUCCESS, "Success connect");
                    GsonHelper.write(response, resp.getOutputStream());
                } else {
                    response = new ServiceResponse(ServiceResponse.FAIL, "Authentication error");
                    GsonHelper.write(response, resp.getOutputStream());
                }
                break;
            case "meetings":
                GsonHelper.write(restService.getMeetings(), resp.getOutputStream());
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        ServiceResponse response;
        switch (query) {
            case "create":
                Meeting newMeeting = GsonHelper.read(Meeting.class, req.getInputStream());
                restService.addMeeting(newMeeting);
                response = new ServiceResponse(ServiceResponse.SUCCESS, "Meeting created");
                GsonHelper.write(response, resp.getOutputStream());
                break;
            case "accept":
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
        ServiceResponse response;
        switch (query) {
            case "meeting":
                String title = req.getParameter("title");
                Meeting meeting = restService.getMeeting(title);
                if (meeting != null) {
                    GsonHelper.write(meeting, resp.getOutputStream());
                } else {
                    response = new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.MEETING_NOT_FOUND_MASSAGE);
                    GsonHelper.write(response, resp.getOutputStream());
                }
                break;
            case "find":
                String fragment = req.getParameter("fragment");
                Meeting findMeeting = restService.getMeetingByDescriptionFragment(fragment);
                if (findMeeting != null) {
                    GsonHelper.write(findMeeting, resp.getOutputStream());
                } else {
                    response = new ServiceResponse(ServiceResponse.FAIL, ServiceResponse.MEETING_NOT_FOUND_MASSAGE);
                    GsonHelper.write(response, resp.getOutputStream());
                }
                break;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        switch (query) {
            case "remove":
                String title = req.getParameter("title");
                Meeting meeting = restService.removeMeeting(title);
                if (meeting == null) {
                    ServiceResponse response = new ServiceResponse(ServiceResponse.FAIL, ServiceResponse
                            .MEETING_NOT_FOUND_MASSAGE);
                    GsonHelper.write(response, resp.getOutputStream());
                }
                break;
        }
    }
}
