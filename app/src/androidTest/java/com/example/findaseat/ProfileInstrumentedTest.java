package com.example.findaseat;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;


import android.content.Context;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

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


    @Test
    public void Test1_loginlogout_ValidUser() {
        onView(withText("Profile")).perform(click());
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
        onView(withText("Profile")).perform(click());
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
        onView(withText("Profile")).perform(click());
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.loginTip)).check(matches(withText("Please enter your email and password!")));
    }

    @Test
    public void Test4_registerReserveCancel_Success() {
        onView(withText("Profile")).perform(click());
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.registerLink)).perform(click());
        onView(withId(R.id.register_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.enterUsername)).perform(typeText("blackbox"));
        onView(withId(R.id.enterPassword)).perform(typeText("123456"));
        onView(withId(R.id.reenterPassword)).perform(typeText("123456"));
        onView(withId(R.id.enterFullName)).perform(typeText("Blackbox Dummy"));
        onView(withId(R.id.enterEmail)).perform(typeText("blackbox_dummy@usc.edu"));
        onView(withId(R.id.enterUscId)).perform(typeText("0000000000"), closeSoftKeyboard());
        while (InstrumentationRegistry.getInstrumentation().getTargetContext() == null) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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
}