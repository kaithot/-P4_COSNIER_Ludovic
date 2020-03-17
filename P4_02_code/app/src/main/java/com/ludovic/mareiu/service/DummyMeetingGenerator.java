package com.ludovic.mareiu.service;


import com.ludovic.mareiu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList();

    static List<Meeting>generateMeetings(){return new ArrayList<>(DUMMY_MEETINGS);    }
}
