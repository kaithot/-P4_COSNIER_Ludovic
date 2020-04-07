package com.ludovic.mareiu;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.ludovic.mareiu.di.DI;
import com.ludovic.mareiu.meeting_list.ListMeetingActivity;
import com.ludovic.mareiu.service.MeetingApiService;
import com.ludovic.mareiu.utils.RecyclerViewUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.ludovic.mareiu.utils.RecyclerViewUtils.clickChildView;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingsInstrumentedTest {

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule = new ActivityTestRule<>(ListMeetingActivity.class);

    private int currentMeetingsSize = -1;
    private MeetingApiService mApiService;

    @Before
    public void setup(){
        mApiService = DI.getMeetingApiService();
        mActivityRule.getActivity();
        currentMeetingsSize = 3;
    }

    @Test
    public void checkRecyclerViewItemCount(){
        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize));
    }

    @Test
    public void checkIfDeletingMeetingIsWorking(){
        onView(withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildView(R.id.item_list_delete_button)));
        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize - 1));
    }

    //TODO Le problème venait du clavier virtuel qui restait affiché PROBLEME SUR LE FORMULAIRE
    @Test
    public void checkIfAddingMeetingIsWorking(){
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minutes = rightNow.get(Calendar.MINUTE);

        //Adding a meeting in our AddMeetingFragment
        onView(withId(R.id.add_meeting)).perform(click());
        onView(withId(R.id.topic)).perform(typeText("DEMO AUTO"),closeSoftKeyboard());
        onView(withId(R.id.participants)).perform(typeText("zaza@free.fr"),closeSoftKeyboard());

        onView(withId(R.id.create)).perform(click());

        //Check Meetings recyclerView counts one more
        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize + 1));
    }
}
