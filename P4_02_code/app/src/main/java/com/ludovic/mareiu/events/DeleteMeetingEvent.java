package com.ludovic.mareiu.events;

import com.ludovic.mareiu.model.Meeting;

/**
 * Event fired when someone deletes a meeting
 */
public class DeleteMeetingEvent {
    /**
     * Meeting to delete
     */

    public Meeting meeting;

    /**
     * Constructor
     * @param meeting
     */

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
