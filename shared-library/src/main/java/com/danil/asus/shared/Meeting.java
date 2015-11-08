package com.danil.asus.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class Meeting {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Collection<Participant> participants;
    private String priority;

    public static final String URGENT_PRIORITY = "Urgent";
    public static final String PLANNED_PRIORITY = "Planned";
    public static final String POSSIBLE_PRIORITY = "Possible";


    public Meeting() {
        participants = new ArrayList<>();
    }

    public Meeting(String title, String description, Date startDate, Date endDate, String priority) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        participants = Collections.emptyList();
    }

    public Meeting(String title, String description, Date startDate, Date endDate, String priority,
                   Collection<Participant> participants) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.participants = participants;
    }

    public String getParticipantsAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Participant participant : participants) {
            stringBuilder.append(participant.getFio()).append(" (").append(participant.getPost()).append(")")
                    .append("; ");
        }
        return stringBuilder.toString();
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Collection<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Collection<Participant> participants) {
        this.participants = participants;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
