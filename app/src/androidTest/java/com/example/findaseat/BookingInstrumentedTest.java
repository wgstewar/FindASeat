package com.example.findaseat;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileInstrumentedTest {

    /**
     * Use {@link ActivityScenarioRule} to create and launch the activity under test, and close it
     * after test completes.
     */

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void init() {
        /* LOGIN */
        onView(withText("Profile")).perform(click());
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.enterEmail)).perform(typeText("sxfan@usc.edu"));
        onView(withId(R.id.enterPassword)).perform(typeText("123456"));
        onView(withId(R.id.loginButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /* RETURN TO MAP */
        onView(withText("Map")).perform(click());
    }


    @Test
    public void Test1_bookOneReservation() {
        /* START BOOKING PAGE */
        activityScenarioRule.scenario.onActivity { it.startBooking(1) }
        /* Add Reservation to 'shopping cart' */
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(2)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        /* Click BOOK RESERVATION Button */
        onView(withId(R.id.bookButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /* Check that confirmation pop up shows up */
        onView(withText("Make Reservation?"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()));
    }

//    @Test
//    public void Test7_cancelReservationButton() {
//      /* Add Reservation to 'shopping cart' */
//        onData(anything())
//                .inAdapterView(withId(R.id.intervalListView))
//                .atPosition(2)
//                .onChildView(withId(R.id.addButton))
//                .perform(click());
//        /* Click BOOK RESERVATION Button */
//        onView(withId(R.id.bookButton)).perform(click());
//        try {
//            sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        /* Check that confirmation pop up shows up */
//        onView(withText("Make Reservation?"))
//                .inRoot(RootMatchers.isDialog())
//                .check(matches(isDisplayed()));
//
//        /* Click Yes */
//        onView(withText("Yes"))
//                .inRoot(RootMatchers.isDialog())
//                .perform(click());
//
//        onView(withText("Profile")).perform(click());
//
//        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));
//
//        onView(withId(R.id.enterEmail)).perform(typeText("sxfan@usc.edu"));
//        onView(withId(R.id.enterPassword)).perform(typeText("123456"));
//        onView(withId(R.id.loginButton)).perform(click());
//        try {
//            sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
//        onView(withId(R.id.displayFullName)).check(matches(withText("Sammie Fan")));
//    }
}


