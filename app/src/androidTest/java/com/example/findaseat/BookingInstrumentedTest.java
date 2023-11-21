package com.example.findaseat;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.viewpager2.widget.ViewPager2;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static java.lang.Thread.sleep;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
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
public class BookingInstrumentedTest {

    /**
     * Use {@link ActivityScenarioRule} to create and launch the activity under test, and close it
     * after test completes.
     */

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setup() {
        /* LOGIN */
        onView(withText("Profile")).perform(click());
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.enterEmail)).perform(typeText("sxfan@usc.edu"));
        onView(withId(R.id.enterPassword)).perform(typeText("123456"));
        onView(withId(R.id.loginButton)).perform(click());

        /* RETURN TO MAP */
        onView(withText("Map")).perform(click());
    }


    @Test
    public void Test1_cancelReservationButton() {
        /* START BOOKING PAGE */
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.startBooking(1);
        });

        /* Add Reservation to 'shopping cart' */
        try {
            onData(anything())
                    .inAdapterView(withId(R.id.intervalListView))
                    .atPosition(2)
                    .onChildView(withId(R.id.addButton))
                    .perform(click());
        } catch (NoMatchingDataException e) {
        // in case add button isn't there
        }
        /* Click BOOK RESERVATION Button */
        try {
            onView(withId(R.id.bookButton)).perform(click());
        } catch (NoMatchingViewException e) {
            // Active Reservation already existed
        }
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /* Click Yes */
        try {
            onView(withText("Yes"))
                    .inRoot(isDialog())
                    .perform(click());
        } catch (NoMatchingViewException e) {
            // Active Reservation already existed
        }

        /* Return to Profile to check for Reservation */
        onView(withText("Profile")).perform(click());
        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.reservationView)).atPosition(0).
                onChildView(withId(R.id.cancelButton)).
                perform(click());
        /* Check that Cancel confirmation pop up shows up */
        onView(withText("Cancel"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
        /* Click Yes */
        onView(withText("Yes"))
                .inRoot(isDialog())
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.reservationView)).atPosition(0).
                onChildView(withId(R.id.status)).
                check(matches(withText("CANCELLED")));
    }

    @Test
    public void Test2_bookOneReservation() {
        /* START BOOKING PAGE */
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.startBooking(1);
        });
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
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
        /* Click Yes */
        onView(withText("Yes"))
                .inRoot(isDialog())
                .perform(click());

        /* Return to Profile to check for Reservation */
        onView(withText("Profile")).perform(click());
        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.reservationView)).atPosition(0).
                onChildView(withId(R.id.buildingName)).
                check(matches(withText("Leavey Library")));

        /* CANCEL RESERVATION SO FOLLOWING TESTS CAN RUN */
        /* Return to Profile to cancel reservation */

        onView(withText("Profile")).perform(click());
        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        try {
            onData(anything()).inAdapterView(withId(R.id.reservationView)).atPosition(0).
                    onChildView(withId(R.id.cancelButton)).
                    perform(click());
        } catch (NoMatchingDataException e) {
            // in case reservation isn't there
        }
        /* Click Yes */
        try {
            onView(withText("Yes"))
                    .inRoot(isDialog())
                    .perform(click());
        } catch (NoMatchingViewException e) {
            // in case reservation isn't there
        }
    }

    @Test
    public void Test3_BookFiveReservations() {
        /* START BOOKING PAGE */
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.startBooking(1);
        });
        /* Add 5 Reservation to 'shopping cart' */
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(2)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(3)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(4)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(5)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(6)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        /* Click BOOK RESERVATION Button */
        onView(withId(R.id.bookButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.intervalTip)).check(matches(withText("Select up to 4 consecutive intervals!")));

    }

    @Test
    public void Test4_bookNonConsecutiveReservations() {
        /* START BOOKING PAGE */
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.startBooking(1);
        });
        /* Add Non Consecutive Reservations to 'shopping cart' */
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(2)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(4)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        /* Click BOOK RESERVATION Button */
        onView(withId(R.id.bookButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.intervalTip)).check(matches(withText("Select up to 4 consecutive intervals!")));
    }



}

