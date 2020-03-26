package com.ludovic.mareiu.model;

import java.io.Serializable;

/**
 * Model object representing a Meeting ; implements also Serializable
 */
public class Meeting implements Serializable {

    /**
     * Subject
     */
    private String topic;

    /**
     * Place
     */
    private String place;

    /**
     * Start
     */
    private Integer start;

    /** Minutes */
    private Integer minute;

    /**
     * Participant
     */
    private String participant;

    /**
     * Constructor
     *  @param topic
     * @param place
     * @param start
     * @param minute
     * @param participant
     */
    public Meeting(String topic, String place, Integer start, Integer minute, String participant) {
        this.topic = topic;
        this.place = place;
        this.start = start;
        this.minute = minute;
        this.participant = participant;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}

