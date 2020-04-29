package com.ludovic.mareiu.meeting_list;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ludovic.mareiu.R;
import com.ludovic.mareiu.events.DeleteMeetingEvent;
import com.ludovic.mareiu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<Meeting> mMeetings;
    private List<Meeting> meetingListFull;

    public MeetingRecyclerViewAdapter(List<Meeting> items) {
        this.mMeetings = items;

    }

    public void update(List<Meeting> items) {
        meetingListFull = new ArrayList<>(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext()))
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Meeting meeting = mMeetings.get(position);

        String startMeeting = new SimpleDateFormat("HH:mm").format(meeting.getStartTime()); //CONVERT DATE TO STRING FOR DISPLAY
        String date = new SimpleDateFormat("dd/MM").format(meeting.getDate());

        /*recovery current hour, startTime and currentDay, meetingDay*/
        Calendar calendar = Calendar.getInstance();
        Calendar rightNow = Calendar.getInstance();
        calendar.setTime(meeting.getStartTime());
        calendar.setTime(meeting.getDate());
        int startTime = calendar.get(Calendar.HOUR_OF_DAY);
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentDay = rightNow.get(Calendar.DAY_OF_YEAR);
        int meetingDay = calendar.get(Calendar.DAY_OF_YEAR);
        /*--------------------*/

        //*selected the good alert*/
        int resultDay = meetingDay - currentDay;
        int result = startTime - currentHour;

        if (resultDay == 0) {

            if (result < 2) {
                holder.mAlert.setImageResource(R.drawable.red_alert);
            } else if (result < 3) {
                holder.mAlert.setImageResource(R.drawable.orange_alert);
            } else {
                holder.mAlert.setImageResource(R.drawable.green_alert);
            }
        } else {
            holder.mAlert.setImageResource(R.drawable.green_alert);
        }
        /*-----------------------*/

        holder.mAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), meeting.getTopic(),Toast.LENGTH_LONG).show();
            }
        });

        holder.mMeetingTopicSchedulePlace.setText(MessageFormat.format("{0} - {1} - {2} - {3}",
                meeting.getTopic(), date, startMeeting, meeting.getPlace()));
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Meeting> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(meetingListFull);
            } else {
                for (Meeting item : meetingListFull) {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    String stringDate = new SimpleDateFormat("dd/MM").format(item.getDate());
                    if (item.getPlace().toLowerCase().contains(filterPattern) || (stringDate.contains(filterPattern))) {
                        filteredList.add(item);
                    }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMeetings.clear();
            mMeetings.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mAlert;
        TextView mMeetingTopicSchedulePlace;
        TextView mMeetingParticipant;
        ImageButton mDeleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            mAlert = ((ImageView) itemView.findViewById(R.id.item_alert));
            mMeetingTopicSchedulePlace = ((TextView) itemView.findViewById(R.id.item_main));
            mMeetingParticipant = ((TextView) itemView.findViewById(R.id.item_list_participant));
            mDeleteButton = ((ImageButton) itemView.findViewById(R.id.item_list_delete_button));


        }
    }
}