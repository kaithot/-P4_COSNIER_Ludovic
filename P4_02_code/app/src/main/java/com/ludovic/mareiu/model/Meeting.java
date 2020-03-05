package com.ludovic.mareiu.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Model object representing a Meeting ; implements also Serializable
 */
public class Meeting implements Serializable {

    /**
     * Identifier
     */
    private Integer id;

    /**
     * Subject
     */
    private String subject;

    /**
     * Place
     */
    private String place;

    /**
     * Start
     */
    private String start;

    /**
     * Participant
     */
    private String participant;

/**
 * Constructor
 * @param id
 * @param subject
 * @param place
 * @param start
 * @param participant
 */
 public Meeting(Integer id, String subject, String place, String start, String participant){
     this.id = id;
     this.subject = subject;
     this.place = place;
     this.start = start;
     this.participant = participant;
 }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return getId().equals(meeting.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
