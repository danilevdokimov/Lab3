package com.danil.asus.service;

import com.danil.asus.shared.Meeting;
import com.danil.asus.shared.Participant;

import java.util.Collection;

/**
 * Created by Asus on 10/20/2015.
 */
public interface RestApi {
    String AUTHENTICATION = "authentication";
    String GET_MEETINGS = "meetings";
    String GET_MEETING = "meeting";
    String ACCEPT_MEETING = "accept";
    String CREATE_MEETING = "create";
    String FIND_MEETING = "find";
    String REMOVE_MEETING = "remove";

    Collection<String> getMeetings();

    Meeting acceptMeeting(Participant participant, String meetingTitle);

    Meeting getMeeting(String title);

    Meeting removeMeeting(String title);

    void addMeeting(Meeting meeting);

    Meeting getMeetingByDescriptionFragment(String fragment);

    boolean checkPassord(String userPassword);
}
