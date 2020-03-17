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
    private Long id;

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

    /**
     * Participant
     */
    private String participant;

/**
 * Constructor
 * @param id
 * @param topic
 * @param place
 * @param start
 * @param participant
 */
 public Meeting(Long id, String topic, String place, Integer start, String participant){
     this.id = id;
     this.topic = topic;
     this.place = place;
     this.start = start;
     this.participant = participant;
 }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
