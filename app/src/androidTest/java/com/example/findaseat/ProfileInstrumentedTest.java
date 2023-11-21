package com.example.findaseat;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertNotNull;
import static java.lang.Thread.sleep;


import android.content.Context;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfileInstrumentedTest {

    /**
     * Use {@link ActivityScenarioRule} to create and launch the activity under test, and close it
     * after test completes.
     */

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        onView(withText("Profile")).perform(click());
    }

    @Test
    public void Test1_loginlogout_ValidUser() {
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.enterEmail)).perform(typeText("sxfan@usc.edu"));
        onView(withId(R.id.enterPassword)).perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.displayFullName)).check(matches(withText("Sammie Fan")));

        onView(withId(R.id.logoutLink)).perform(click());
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void Test2_login_InvalidUser() {
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.enterEmail)).perform(typeText("blahblah@usc.edu"));
        onView(withId(R.id.enterPassword)).perform(typeText("blahblah"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.loginTip)).check(matches(withText("Invalid email/password combination.")));
    }

    @Test
    public void Test3_login_Empty() {
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.loginTip)).check(matches(withText("Please enter your email and password!")));
    }

    @Test
    public void Test4_register_Success() {
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.registerLink)).perform(click());
        onView(withId(R.id.register_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.enterUsername)).perform(typeText("blackbox"));
        onView(withId(R.id.enterPassword)).perform(typeText("123456"));
        onView(withId(R.id.reenterPassword)).perform(typeText("123456"));
        onView(withId(R.id.enterFullName)).perform(typeText("Blackbox Dummy"));
        onView(withId(R.id.enterEmail)).perform(typeText("blackbox_dummy@usc.edu"));
        onView(withId(R.id.enterUscId)).perform(typeText("0000000000"), closeSoftKeyboard());

        onView(withId(R.id.registerButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.displayFullName)).check(matches(withText("Blackbox Dummy")));

        FirebaseAuth.getInstance().getCurrentUser().delete();
    }

    @Test
    public void Test5_bookCancel_Success() {
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.enterEmail)).perform(typeText("profile_dummy@usc.edu"));
        onView(withId(R.id.enterPassword)).perform(typeText("aaaaaa"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.displayFullName)).check(matches(withText("Profile Dummy")));

        onView(withText("Map")).perform(click());
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.startBooking(4);
        });
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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

        onView(withText("Profile")).perform(click());
        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onData(anything())
                .inAdapterView(withId(R.id.reservationView))
                .atPosition(0)
                .onChildView(withId(R.id.cancelButton))
                .perform(click());

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /* Check that confirmation pop up shows up */
        onView(withText("Cancel"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        /* Click Yes */
        onView(withText("Yes"))
                .inRoot(isDialog())
                .perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.reservationView))
                .atPosition(0).onChildView(withId(R.id.status)).check(matches(withText("CANCELLED")));
    }

    @Test
    public void Test6_register_EmptyFieldsUser() {
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.registerLink)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.register_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.registerTip)).check(matches(withText("Please correct the following fields.")));
    }

    @Test
    public void Test7_register_NewValidUser() {
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));
        /* Register */
        onView(withId(R.id.registerLink)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.register_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.enterUsername)).perform(typeText("willgranger"));
        onView(withId(R.id.enterPassword)).perform(typeText("blahblah"));
        onView(withId(R.id.reenterPassword)).perform(typeText("blahblah"));
        onView(withId(R.id.enterFullName)).perform(typeText("Will Stewart"));
        onView(withId(R.id.enterEmail)).perform(typeText("wgstewar@usc.edu"));
        onView(withId(R.id.enterUscId)).perform(typeText("1234567899"), closeSoftKeyboard());
        onView(withId(R.id.enterAffiliation)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Undergraduate"))).perform(click());

        onView(withId(R.id.registerButton)).perform(click());

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.displayFullName)).check(matches(withText("Will Stewart")));
    }

    @Test
    public void Test8_register_logout_login() {
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));
        /* Register */
        onView(withId(R.id.registerLink)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.register_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.enterUsername)).perform(typeText("jlo"));
        onView(withId(R.id.enterPassword)).perform(typeText("blahblah"));
        onView(withId(R.id.reenterPassword)).perform(typeText("blahblah"));
        onView(withId(R.id.enterFullName)).perform(typeText("Jennifer Lopez"));
        onView(withId(R.id.enterEmail)).perform(typeText("jlopez@usc.edu"));
        onView(withId(R.id.enterUscId)).perform(typeText("1234567899"), closeSoftKeyboard());
        onView(withId(R.id.enterAffiliation)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Undergraduate"))).perform(click());

        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        /* Logout */
        onView(withId(R.id.logoutLink)).perform(click());
        onView(withText("Profile")).perform(click());
        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));

        /* Login with newly created account */
        onView(withId(R.id.enterEmail)).perform(typeText("jlopez@usc.edu"));
        onView(withId(R.id.enterPassword)).perform(typeText("blahblah"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.displayFullName)).check(matches(withText("Jennifer Lopez")));
    }

}