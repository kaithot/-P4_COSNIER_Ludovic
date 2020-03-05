package com.ludovic.mareiu.service;

import com.ludovic.mareiu.model.Meeting;

import java.util.List;

/**
 * Dummy mock for Api
 */
public class DummyMeetingApiService implements MeetingApiService{

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);

    }
}
