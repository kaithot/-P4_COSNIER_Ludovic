package com.ludovic.mareiu.service;

import com.ludovic.mareiu.model.Meeting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public List<Meeting> getMeetingsFilteredByDate(Date date) {
        List<Meeting> meetings = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        String meeting_date;
        String filterDate = sdf.format(date);
        for(Meeting meeting : meetings)
        {
            meeting_date =  sdf.format(meeting.getDate());
            /// if(DateUtils.)
            if( meeting_date.compareTo(filterDate)==0)
            {
                meetings.add(meeting);
            }
        }
        return meetings;
    }

    /**
     * Get all Meetings filtered by meeting room
     * @param room
     * @return {@link List}
     */
    @Override
    public List<Meeting> getMeetingsFilteredByRoom(String room) {
        List<Meeting> meetings = new ArrayList<>();

        for(Meeting meeting : meetings)
        {
            if(meeting.getPlace() == room)
            {
                meetings.add(meeting);
            }
        }
        return meetings;
    }

}


