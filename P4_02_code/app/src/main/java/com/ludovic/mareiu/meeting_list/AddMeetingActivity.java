package com.ludovic.mareiu.meeting_list;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ludovic.mareiu.R;
import com.ludovic.mareiu.di.DI;
import com.ludovic.mareiu.model.Meeting;
import com.ludovic.mareiu.service.MeetingApiService;

import java.util.Calendar;

public class AddMeetingActivity extends AppCompatActivity {

    ImageView mAlert;
    EditText mTopic;
    Spinner mSpinnerRoom;
    TimePicker mSpinnerSchedule;
    EditText mParticipants;
    Button mAddButton;

    private MeetingApiService mApiService;
    private String mMeetingAlert;

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
     //@RequiresApi(api = Build.VERSION_CODES.M)
     //TODO en ajoutant getHour, j'ai cette ligne qui indique une API minimum de 23, le projet doit être sur 21 ...
    public void addMeeting(View button) {
        Meeting meeting = new Meeting(
                System.currentTimeMillis(),
                mTopic.getText().toString(),
                mSpinnerRoom.getSelectedItem().toString(),
                //mSpinnerSchedule.getHour(),
                mSpinnerSchedule.getCurrentHour(),
                mParticipants.getText().toString()
        );
        mApiService.createMeeting(meeting);
        finish();
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