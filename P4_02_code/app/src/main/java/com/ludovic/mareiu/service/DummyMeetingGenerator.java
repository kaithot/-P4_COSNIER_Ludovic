package com.ludovic.mareiu.service;


import com.ludovic.mareiu.model.Meeting;
import com.ludovic.mareiu.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(

            new Meeting ("Demo 1", Utils.getDate(2020, 4,12),Utils.getTheTime(16, 0),
                    Utils.getTheTime(17,0),"Mario","francis@lamzone.fr, alexandra@lamzone.fr"),
            new Meeting ("Demo 3", Utils.getDate(2020,4,13),Utils.getTheTime(17, 0),
                    Utils.getTheTime(18,0),"Luigi","maxime@lamzone.fr, alexandra@lamzone.fr"),
            new Meeting ("Demo 2", Utils.getDate(2020,4,14),Utils.getTheTime(18, 0),
                    Utils.getTheTime(19,0),"Peach","francis@lamzone.fr, maxime@lamzone.fr")
    );

    static List<Meeting>generateMeetings(){return new ArrayList<>(DUMMY_MEETINGS);    }
}
