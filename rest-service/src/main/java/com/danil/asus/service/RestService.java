package com.danil.asus.service;

import com.danil.asus.shared.Meeting;
import com.danil.asus.shared.Participant;
import com.danil.asus.shared.service.RestApi;

import java.util.ArrayList;
import java.util.Calendar;
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
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        Collection<String> dayMeetings = new ArrayList<>();
        for (Meeting meeting : meetings.values()) {
            if (meeting.getStartDate().after(todayStart.getTime())
                    && meeting.getStartDate().before(todayEnd.getTime())) {
                dayMeetings.add(meeting.getTitle());
            }
        }
        return dayMeetings;
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
    public boolean checkPassword(String userPassword) {
        return PASSWORD.equals(userPassword);
    }
}
