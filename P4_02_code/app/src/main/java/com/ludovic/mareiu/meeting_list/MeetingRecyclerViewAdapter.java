package com.ludovic.mareiu.meeting_list;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ludovic.mareiu.R;
import com.ludovic.mareiu.events.DeleteMeetingEvent;
import com.ludovic.mareiu.model.Meeting;
import com.ludovic.mareiu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    private MeetingApiService meetingApiService;
    private final List<Meeting> mMeetings;

    public MeetingRecyclerViewAdapter(List<Meeting> items){
        mMeetings = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from((parent.getContext()))
                .inflate(R.layout.fragment_meeting,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Meeting meeting = mMeetings.get(position);
        holder.mMeetingSubject.setText(meeting.getSubject());
        holder.mMeetingSchedule.setText(meeting.getStart());
        holder.mMeetingPlace.setText(meeting.getPlace());
        holder.mMeetingParticipant.setText(meeting.getParticipant());

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.findViewById(R.id.item_list_delete_button).setVisibility(View.INVISIBLE);
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mMeetingSubject;
        public TextView mMeetingSchedule;
        public TextView mMeetingPlace;
        public TextView mMeetingParticipant;
        public ImageButton mDeleteButton;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mMeetingSubject = ((TextView) itemView.findViewById(R.id.item_list_subject));
            mMeetingSchedule = ((TextView) itemView.findViewById(R.id.item_list_schedule));
            mMeetingPlace = ((TextView) itemView.findViewById(R.id.item_list_place));
            mMeetingParticipant = ((TextView) itemView.findViewById(R.id.item_list_participant));
            mDeleteButton = ((ImageButton) itemView.findViewById(R.id.item_list_delete_button));


        }
    }
}
