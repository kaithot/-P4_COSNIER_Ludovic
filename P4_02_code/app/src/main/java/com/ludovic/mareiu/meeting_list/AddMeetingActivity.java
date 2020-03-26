package com.ludovic.mareiu.meeting_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.ludovic.mareiu.R;
import com.ludovic.mareiu.di.DI;
import com.ludovic.mareiu.model.Meeting;
import com.ludovic.mareiu.service.MeetingApiService;

public class AddMeetingActivity extends AppCompatActivity {

    EditText mTopic;
    Spinner mSpinnerRoom;
    TimePicker mSpinnerSchedule;
    EditText mParticipants;
    Button mAddButton;

    private MeetingApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getMeetingApiService();
        mTopic = findViewById(R.id.Topic);
        mSpinnerRoom = findViewById(R.id.SpinnerMeetingRooms);
        mSpinnerSchedule = findViewById(R.id.Schedule);
        mParticipants = findViewById(R.id.Participants);
        mAddButton = findViewById(R.id.create);

        /*Convert TimePicker to 24H*/
        mSpinnerSchedule.setIs24HourView(true);
        //mSpinnerSchedule.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        /*------------------------*/


    }

    public void addMeeting(View button) {

        String topic = mTopic.getText().toString();
        String room = mSpinnerRoom.getSelectedItem().toString();
        int hour = mSpinnerSchedule.getCurrentHour();
        int minutes = mSpinnerSchedule.getCurrentMinute();
        String participants = mParticipants.getText().toString();

        int startMinutes = (hour * 60 + minutes); // convert meeting start time to minutes
        boolean exist = false;

        if (!topic.equals("") && !room.equals("") && !participants.equals("")) {

            for (int i = 0; i < mApiService.getMeetings().size(); i++) { // search loop to see if the start time and the room entered exist

                if ((startMinutes < mApiService.getMeetings().get(i).getStart()*60+mApiService.getMeetings().get(i).getMinute()+60) // compare the start time and end time of each meeting in the list
                        && (room.equals(mApiService.getMeetings().get(i).getPlace()))) { // if the start time is the same, check if it's in the same room
                    exist = true; // if the two conditions are ok, show an alert.
                }
            }
            if (!exist) { // if it's not the case add the meeting into the list
                Meeting meeting = new Meeting(topic, room, hour, minutes, participants);
                mApiService.createMeeting(meeting);
                finish();
            }else {
                Toast.makeText(AddMeetingActivity.this, "this room or this time is not available", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AddMeetingActivity.this, "Field missing", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Used to navigate to this activity
     *
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}