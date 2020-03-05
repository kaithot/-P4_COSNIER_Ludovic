package com.ludovic.mareiu.service;

import com.ludovic.mareiu.model.Meeting;

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
     * Deletes a meeting
     */

    void deleteMeeting (Meeting meeting);
}
