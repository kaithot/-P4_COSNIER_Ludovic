package com.ludovic.mareiu;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.ludovic.mareiu.di.DI;
import com.ludovic.mareiu.meeting_list.ListMeetingActivity;
import com.ludovic.mareiu.service.MeetingApiService;
import com.ludovic.mareiu.utils.RecyclerViewUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ludovic.mareiu.meeting_list.ListMeetingActivityTest.childAtPosition;
import static com.ludovic.mareiu.utils.RecyclerViewUtils.clickChildView;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MeetingsInstrumentedTest {

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule = new ActivityTestRule<>(ListMeetingActivity.class);
    private int currentMeetingsSize;
    private MeetingApiService mApiService;

    @Before
    public void setup(){
        mApiService = DI.getMeetingApiService();
        mActivityRule.getActivity();
        currentMeetingsSize = mApiService.getMeetings().size();
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

    @Test
    public void checkIfAddingMeetingIsWorking(){

        //Adding a meeting in our AddMeetingFragment
        onView(withId(R.id.add_meeting)).perform(click());
        onView(withId(R.id.topic)).perform(typeText("DEMO AUTO"),closeSoftKeyboard());
        onView(withId(R.id.participants)).perform(typeText("zaza@free.fr"),closeSoftKeyboard());

        onView(withId(R.id.create)).perform(click());

        //Check Meetings recyclerView counts one more
        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize + 1));

    }

    @Test
    public void checkIfFilteringByRoomIsWorking(){

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        onView(allOf(withId(R.id.title), withText("Filter by Room or Day"),childAtPosition(
                childAtPosition(withId(R.id.content),0),0),isDisplayed())).perform(click());

        onView(allOf(withId(R.id.search_src_text),childAtPosition(allOf(withId(R.id.search_plate),
                childAtPosition(withId(R.id.search_edit_frame),1)),0),isDisplayed())).perform(replaceText("Mario"), closeSoftKeyboard());

        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize -2));

        onView(allOf(withId(R.id.search_close_btn), withContentDescription("Clear query"),
                childAtPosition(allOf(withId(R.id.search_plate),childAtPosition(withId(R.id.search_edit_frame),1)),1),isDisplayed())).perform(click());

        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize));
    }


    @Test
    public void checkIfFilteringByDateIsWorking(){

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        onView(allOf(withId(R.id.title), withText("Filter by Room or Day"),childAtPosition(
                childAtPosition(withId(R.id.content),0),0),isDisplayed())).perform(click());

        onView(allOf(withId(R.id.search_src_text),childAtPosition(allOf(withId(R.id.search_plate),
                childAtPosition(withId(R.id.search_edit_frame),1)),0),isDisplayed())).perform(replaceText("14/05"), closeSoftKeyboard());

        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize -2));

        onView(allOf(withId(R.id.search_close_btn), withContentDescription("Clear query"),
                childAtPosition(allOf(withId(R.id.search_plate),childAtPosition(withId(R.id.search_edit_frame),1)),1),isDisplayed())).perform(click());

        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize));
    }
}
