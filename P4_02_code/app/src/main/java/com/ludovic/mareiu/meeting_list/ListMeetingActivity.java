package com.ludovic.mareiu.meeting_list;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ludovic.mareiu.R;
import com.ludovic.mareiu.di.DI;
import com.ludovic.mareiu.events.DeleteMeetingEvent;
import com.ludovic.mareiu.model.Meeting;
import com.ludovic.mareiu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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
        mMeetings = mApiService.getMeetings();
        mAdapter = new MeetingRecyclerViewAdapter(mMeetings);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void refresh() {
        mMeetings = new ArrayList<>(mApiService.getMeetings());
        mAdapter.notifyDataSetChanged();
        mAdapter.update(mMeetings);
    }

    public void addMeeting(View view) { // when floating action button is select,  go to Add Meeting Activity
        AddMeetingActivity.navigate(this);
    }

    /*--------Menu----------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.Filter);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Filter by room or day (dd/MM)"); // display into the searchView
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE); // transform keyboard's search into function enter
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Filter:
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /*----------------------*/


    @Override
    public void onResume() {
        super.onResume();
        refresh();
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

    /*-----------delete button-----------*/
    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {

        mApiService.deleteMeeting(event.meeting);
        refresh();
    }
    /*-----------------------------------*/

}