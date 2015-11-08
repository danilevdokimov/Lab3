package com.danil.asus.service;

import com.danil.asus.shared.Meeting;
import com.danil.asus.shared.Participant;
import com.danil.asus.shared.service.RestApi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RestService implements RestApi {

    private static final String PASSWORD = "12345";

    private Map<String, Meeting> meetings;

    public RestService() {
        meetings = new HashMap<>();
    }

    @Override
    public Collection<String> getMeetings() {
        return meetings.keySet();
    }

    @Override
    public Meeting acceptMeeting(Participant participant, String meetingTitle) {
        Meeting meeting = meetings.get(meetingTitle);
        if (meeting != null) {
            meeting.addParticipant(participant);
        }
        return meeting;
    }

    @Override
    public Meeting getMeeting(String title) {
        return meetings.get(title);
    }

    @Override
    public Meeting removeMeeting(String title) {
        return meetings.remove(title);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetings.put(meeting.getTitle(), meeting);
    }

    @Override
    public Meeting getMeetingByDescriptionFragment(String fragment) {
        Collection<Meeting> allMeetings = meetings.values();
        for (Meeting meeting : allMeetings) {
            if (meeting.getDescription().contains(fragment)) {
                return meeting;
            }
        }
        return null;
    }

    @Override
    public boolean checkPassord(String userPassword) {
        return PASSWORD.equals(userPassword);
    }
}
