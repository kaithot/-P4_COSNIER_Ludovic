package com.ludovic.mareiu.meeting_list;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Ludovic Cosnier 28/02/2020
 */
public class ListMeetingPagerAdapter extends FragmentPagerAdapter {

    public ListMeetingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MeetingFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
