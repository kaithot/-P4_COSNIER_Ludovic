package com.ludovic.mareiu.service;


import com.ludovic.mareiu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1,"Presentation","Room 1","10h00","toi@etmoi, moi@ettoi"),
            new Meeting(2,"Presentation","Room 1","10h00","toi@etmoi, moi@ettoi")
    );

    static List<Meeting>generateMeetings(){return new ArrayList<>(DUMMY_MEETINGS);    }
}
