package com.ludovic.mareiu.service;

import com.ludovic.mareiu.model.Meeting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Dummy mock for Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void sortTopic() {

        Collections.sort(meetings, new Comparator<Meeting>() {
            public int compare(Meeting a, Meeting b) {
                return a.getTopic().compareTo(b.getTopic());
            }
        });
    }

    @Override
    public void sortStart() {

        Collections.sort(meetings, new Comparator<Meeting>() {
            public int compare(Meeting a, Meeting b) {
                return a.getStart().compareTo(b.getStart());
            }
        });

    }


}


