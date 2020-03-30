package com.ludovic.mareiu.service;


import com.ludovic.mareiu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(

            new Meeting ("Demo 1", "Yoshi", 16,0,"francis@lamzone.fr, alexandra@lamzone.fr"),
            new Meeting ("Demo 3", "Luigi", 15,0,"maxime@lamzone.fr, alexandra@lamzone.fr"),
            new Meeting ("Demo 2", "Mario", 17,50,"francis@lamzone.fr, maxime@lamzone.fr")
    );

    static List<Meeting>generateMeetings(){return new ArrayList<>(DUMMY_MEETINGS);    }
}
