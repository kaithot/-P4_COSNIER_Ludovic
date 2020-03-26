package com.ludovic.mareiu;

import com.ludovic.mareiu.di.DI;
import com.ludovic.mareiu.model.Meeting;
import com.ludovic.mareiu.service.DummyMeetingGenerator;
import com.ludovic.mareiu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;


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
        Meeting newMeeting = new Meeting("Demo4", "Yoshi", 14,"00","Momo, Zaza");
        //WHEN
        mApiService.createMeeting(newMeeting);
        //THEN
        assertEquals(mApiService.getMeetings().size(), meetingsSize + 1);
    }

    @Test
    public void sortMeetingsByTopic(){
        //GIVEN
        Meeting m1 = mApiService.getMeetings().get(0);
        Meeting m2 = mApiService.getMeetings().get(1);
        Meeting m3 = mApiService.getMeetings().get(2);
        //WHEN
        mApiService.sortTopic();
        //THEN
        assertSame(m1, mApiService.getMeetings().get(0));
        assertSame(m3, mApiService.getMeetings().get(1));
        assertSame(m2, mApiService.getMeetings().get(2));
    }

    @Test
    public void sortMeetingsByStart(){
        //GIVEN
        Meeting m1 = mApiService.getMeetings().get(0);
        Meeting m2 = mApiService.getMeetings().get(1);
        Meeting m3 = mApiService.getMeetings().get(2);

        //WHEN
        mApiService.sortStart();
        //THEN
        assertSame(m2, mApiService.getMeetings().get(0));
        assertSame(m1, mApiService.getMeetings().get(1));
        assertSame(m3, mApiService.getMeetings().get(2));

    }
}
