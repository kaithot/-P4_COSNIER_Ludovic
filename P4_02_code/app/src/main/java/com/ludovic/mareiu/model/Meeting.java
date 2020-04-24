package com.ludovic.mareiu.model;


import java.util.Date;

/**
 * Model object representing a Meeting
 */
public class Meeting {

    /**
     * Subject
     */
    private String topic;

    /**
     * Date
     */
    private Date date;

    /**
     * Place
     */
    private String place;

    /**
     * StartTime
     */
    private Date startTime;

    /**
     * EndTime
     */
    private Date endTime;

    /**
     * Participant
     */
    private String participant;

    public Meeting(String topic, Date date, Date startTime, Date endTime,String place, String participant) {
        this.topic = topic;
        this.date = date;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participant = participant;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}