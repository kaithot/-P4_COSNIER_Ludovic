package com.ludovic.mareiu.service;

import com.ludovic.mareiu.model.Meeting;

import java.util.Date;
import java.util.List;

/**
 * Meeting API client
 */
public interface MeetingApiService {

    /**
     * Get all the meetings
     */
    List<Meeting>getMeetings();

    /**
     * Delete a meeting
     */
    void deleteMeeting (Meeting meeting);

    /**
     * Create a meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Filter the meetings by room and day
     */

    List<Meeting> getMeetingsFilteredByDate(Date date);

    List<Meeting> getMeetingsFilteredByRoom(String room);

}
