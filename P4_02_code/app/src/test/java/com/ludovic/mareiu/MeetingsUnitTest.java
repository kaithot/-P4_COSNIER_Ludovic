package com.ludovic.mareiu;

import com.ludovic.mareiu.di.DI;
import com.ludovic.mareiu.model.Meeting;
import com.ludovic.mareiu.service.DummyMeetingGenerator;
import com.ludovic.mareiu.service.MeetingApiService;
import com.ludovic.mareiu.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class MeetingsUnitTest {

    private MeetingApiService mApiService;

    @Before
    public void setup() {
        mApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void getDummyMeetingsWithSuccess(){
        //GIVEN
        List<Meeting> meetingsExpected = DummyMeetingGenerator.DUMMY_MEETINGS;
        //WHEN
        List<Meeting> meetingsActual = mApiService.getMeetings();
        //THEN
        assertEquals(meetingsExpected.size(), meetingsActual.size());
        assertEquals(meetingsExpected.get(1), meetingsActual.get(1));
    }

    @Test
    public void deleteMeetingWithSuccess(){
        //GIVEN
        Meeting meetingToDelete = mApiService.getMeetings().get(1);
        //WHEN
        mApiService.deleteMeeting(meetingToDelete);
        //THEN
        assertFalse(mApiService.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addNewMeetingWithSuccess(){
        //GIVEN
        int meetingsSize = mApiService.getMeetings().size();
        Meeting newMeeting = new Meeting ("Demo 4", Utils.getDate(2020,4,15),
                Utils.getTheTime(17, 0),
                Utils.getTheTime(18,0),"Luigi","maxime@lamzone.fr, alexandra@lamzone.fr");
        //WHEN
        mApiService.createMeeting(newMeeting);
        //THEN
        assertEquals(mApiService.getMeetings().size(), meetingsSize + 1);
    }

    @Test
    public void filterMeetingByDateWithSuccess(){

        Date date = Utils.getDate(2020,4,13);
        List<Meeting>filteredMeeting = mApiService.getMeetingsFilteredByDate(date);
        // check if meetings only contains required date

    }

    @Test
    public void filterMeetingByRoomWithSuccess(){

        String room ="Mario";
        List<Meeting>filteredMeeting = mApiService.getMeetingsFilteredByRoom(room);
        // check if meetings only contains required room
        for (Meeting meeting : filteredMeeting)
        {
            assertTrue(meeting.getPlace()=="Mario");
        }

    }
}
