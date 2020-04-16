package com.ludovic.mareiu.meeting_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.ludovic.mareiu.R;
import com.ludovic.mareiu.di.DI;
import com.ludovic.mareiu.model.Meeting;
import com.ludovic.mareiu.service.MeetingApiService;
import com.ludovic.mareiu.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddMeetingActivity extends AppCompatActivity {

    EditText mTopic;
    TextView mDateSelected;
    Spinner mSpinnerRoom;
    TextView mStartTime;
    TextView mEndTime;
    EditText mParticipants;
    Button mAddButton;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Calendar mCalendar;
    Calendar mStart;
    Calendar mEnd;

    private SimpleDateFormat mDateFormat;
    private SimpleDateFormat mTimeFormat;

    private MeetingApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getMeetingApiService();
        mTopic = findViewById(R.id.topic);
        mDateSelected = findViewById(R.id.date_selected);
        mSpinnerRoom = findViewById(R.id.spinnerMeetingRooms);
        mStartTime = findViewById(R.id.start_time);
        mEndTime = findViewById(R.id.end_time);

        mParticipants = findViewById(R.id.participants);
        mAddButton = findViewById(R.id.create);

        initCalendars();
        dateSelected();
        startTime();
        endTime();
    }

    /**
     * Init start calendar to next hour
     */


    private void initCalendars() {
        mCalendar = Calendar.getInstance();
        mStart = Calendar.getInstance();
        mEnd = Calendar.getInstance();
        mCalendar.add(Calendar.HOUR, 1);
        mCalendar.add(Calendar.MINUTE, 0);

        mYear = mCalendar.get(mCalendar.YEAR);
        mMonth = mCalendar.get(mCalendar.MONTH);
        mDay = mCalendar.get(mCalendar.DAY_OF_MONTH);
        mHour = mCalendar.get(mCalendar.HOUR);
        mMinute = mCalendar.get(mCalendar.MINUTE);
        mDateFormat = new SimpleDateFormat((String) getText(R.string.date_format));
        mTimeFormat = new SimpleDateFormat("HH:mm");
    }

    private void dateSelected() {
        mDateSelected.setText(mDateFormat.format(mCalendar.getTime()));
        mDateSelected.setClickable(true);
        mDateSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMeetingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                mCalendar.set(year, month, day);
                                mDateSelected.setText(mDateFormat.format(mCalendar.getTime()));
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

    }

    private void startTime() {
        mStartTime.setText(mTimeFormat.format(mStart.getTime()));
        mStartTime.setClickable(true);
        mStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        mStart.set(mYear, mMonth, mDay, hourOfDay, minutes);
                        mStartTime.setText(mTimeFormat.format(Utils.getTheTime(hourOfDay, minutes)));

                    }
                }, mHour, 0, true);
                timePickerDialog.show();
            }
        });

    }

    private void endTime() {

        mEndTime.setText(mTimeFormat.format(mCalendar.getTime()));
        mEndTime.setClickable(true);
        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        mEnd.set(mYear, mMonth, mDay, hourOfDay, minutes);
                        mEndTime.setText(mTimeFormat.format(Utils.getTheTime(hourOfDay, minutes)));
                    }
                }, mHour + 1, 0, true);
                timePickerDialog.show();
            }
        });

    }

    public void addMeeting(View button) {

        String topic = mTopic.getText().toString();
        Date date = mCalendar.getTime();
        String room = mSpinnerRoom.getSelectedItem().toString();
        Date start = mStart.getTime();
        Date end = mEnd.getTime();
        String participants = mParticipants.getText().toString();
        Meeting meeting = new Meeting(topic, date, start, end, room, participants);
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