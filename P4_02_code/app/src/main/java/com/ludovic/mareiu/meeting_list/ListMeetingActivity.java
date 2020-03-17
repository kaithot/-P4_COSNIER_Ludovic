package com.ludovic.mareiu.meeting_list;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ludovic.mareiu.R;
import com.ludovic.mareiu.di.DI;
import com.ludovic.mareiu.events.DeleteMeetingEvent;
import com.ludovic.mareiu.model.Meeting;
import com.ludovic.mareiu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ListMeetingActivity extends AppCompatActivity {

    private MeetingRecyclerViewAdapter mAdapter = new MeetingRecyclerViewAdapter(DI.getMeetingApiService().getMeetings());
    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        mApiService = DI.getMeetingApiService();
        mRecyclerView = (RecyclerView) findViewById(R.id.list_meetings);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
    public void addMeeting(View view) {
                    AddMeetingActivity.navigate(this);
            }


    /**
     * Init the list of meetings
     */

    private void initList(){
        mMeetings = mApiService.getMeetings();
        mRecyclerView.setAdapter(new MeetingRecyclerViewAdapter(mMeetings));
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {

        mApiService.deleteMeeting(event.meeting);
        initList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_sort_by_topic) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}