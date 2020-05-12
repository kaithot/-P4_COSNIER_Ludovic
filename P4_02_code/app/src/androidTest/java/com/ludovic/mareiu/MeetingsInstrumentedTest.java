package com.ludovic.mareiu;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.ludovic.mareiu.di.DI;
import com.ludovic.mareiu.meeting_list.ListMeetingActivity;
import com.ludovic.mareiu.service.MeetingApiService;
import com.ludovic.mareiu.utils.RecyclerViewUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ludovic.mareiu.utils.RecyclerViewUtils.childAtPosition;
import static com.ludovic.mareiu.utils.RecyclerViewUtils.clickChildView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;

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
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, clickChildView(R.id.item_list_delete_button)));
        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize - 1));
    }

    @Test
    public void checkIfAddingMeetingIsWorking(){

        //Adding a meeting in our AddMeetingFragment
        onView(withId(R.id.add_meeting)).perform(click());
        onView(withId(R.id.topic)).perform(typeText("DEMO AUTO"),closeSoftKeyboard());
        onView(withId(R.id.spinnerMeetingRooms)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinnerMeetingRooms)).check(matches(withSpinnerText(containsString("Mario"))));
        onView(withId(R.id.participants)).perform(typeText("zaza@free.fr"),closeSoftKeyboard());
        onView(withId(R.id.create)).perform(click());

        //Check Meetings recyclerView counts one more
        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize + 1));

    }

    @Test
    public void checkIfFilteringByRoomIsWorking(){

        // go to the menu
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());

        // click on the Filter
        onView(allOf(withId(R.id.title), withText("Filter by Room or Day"),childAtPosition(
                childAtPosition(withId(R.id.content),0),0),isDisplayed())).perform(click());

        // put "Mario" into the searchView and valid this
        onView(allOf(withId(R.id.search_src_text),childAtPosition(allOf(withId(R.id.search_plate),
                childAtPosition(withId(R.id.search_edit_frame),1)),0),isDisplayed())).perform(replaceText("Mario"), closeSoftKeyboard());

        //check that list_meeting equal at Mario
        ViewInteraction textView = onView(
                allOf(withId(R.id.item_main),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText(endsWith("Mario"))));

        //clear the searchView
        onView(allOf(withId(R.id.search_close_btn), withContentDescription("Clear query"),
                childAtPosition(allOf(withId(R.id.search_plate),childAtPosition(withId(R.id.search_edit_frame),1)),1),isDisplayed())).perform(click());

        // check that list_meeting equal at three
        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize));
    }


    @Test
    public void checkIfFilteringByDateIsWorking(){

        // go to the menu
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());

        // click on the Filter
        onView(allOf(withId(R.id.title), withText("Filter by Room or Day"),childAtPosition(
                childAtPosition(withId(R.id.content),0),0),isDisplayed())).perform(click());

        // put "14/05" into the searchView and valid this
        onView(allOf(withId(R.id.search_src_text),childAtPosition(allOf(withId(R.id.search_plate),
                childAtPosition(withId(R.id.search_edit_frame),1)),0),isDisplayed())).perform(replaceText("14/05"), closeSoftKeyboard());

        //check that list_meeting equal at 14/05
        ViewInteraction textView = onView(
                allOf(withId(R.id.item_main),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.item),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText(containsString("14/05"))));

        //clear the searchView
        onView(allOf(withId(R.id.search_close_btn), withContentDescription("Clear query"),
                childAtPosition(allOf(withId(R.id.search_plate),childAtPosition(withId(R.id.search_edit_frame),1)),1),isDisplayed())).perform(click());

        // check that list_meeting equal at three
        onView(withId(R.id.list_meetings)).check((ViewAssertion) new RecyclerViewUtils.ItemCount(currentMeetingsSize));
    }


}
