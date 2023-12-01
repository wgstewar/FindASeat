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
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
            activity.startBooking(4);
        });
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /* Add Reservation to 'shopping cart' */
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(35)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        /* Click BOOK RESERVATION Button */
        onView(withId(R.id.bookButton)).perform(click());

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /* Click Yes */
        onView(withText("Yes"))
                .inRoot(isDialog())
                .perform(click());
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
            activity.startBooking(4);
        });
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /* Add Reservation to 'shopping cart' */
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(35)
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
                check(matches(withText("Olin Hall")));

        /* CANCEL RESERVATION SO FOLLOWING TESTS CAN RUN */
        /* Return to Profile to cancel reservation */

        onView(withText("Profile")).perform(click());
        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void Test3_BookFiveReservations() {
        /* START BOOKING PAGE */
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.startBooking(4);
        });
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /* Add 5 Reservation to 'shopping cart' */
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(30)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(31)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(32)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(33)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(34)
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
            activity.startBooking(4);
        });
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /* Add Non Consecutive Reservations to 'shopping cart' */
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(33)
                .onChildView(withId(R.id.addButton))
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(35)
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

    /*public void Test5_makeReservationFullInterval(){
        //start booking page
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.startBooking(4);
        });
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //

    }*/

    public void Test6_checkBuildSeatsCreate(){
        //start booking page
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.startBooking(4);
        });
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //add a booking to shopping cart
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(1)
                .onChildView(withId(R.id.addButton))
                .perform(click());

        //click book reservation button
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

        /* Return to Map and go to building and check for reservation */
        onView(withText("Map")).perform(click());
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.reservationView)).atPosition(0).
                onChildView(withId(R.id.buildingName)).
                check(matches(withText("Olin Hall")));

        //check that it matches with the number stated in the reservation
        onData(anything()).inAdapterView(withId(R.id.seatsAvailView)).atPosition(0).
                check(matches(withText("# AVAILABLE")));

    }

    public void Test7_checkBuildSeatsCancel(){
        //start booking page
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.startBooking(4);
        });
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //add a booking to shopping cart
        onData(anything())
                .inAdapterView(withId(R.id.intervalListView))
                .atPosition(30)
                .onChildView(withId(R.id.addButton))
                .perform(click());

        //click book reservation button
        onView(withId(R.id.bookButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }



}