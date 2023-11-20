package com.example.findaseat;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;

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


    @Test
    public void Test1_login_ValidUser() {
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

        onView(withId(R.id.profileLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.displayFullName)).check(matches(withText("Sammie Fan")));
    }

    @Test
    public void Test2_login_InvalidUser() {
        onView(withText("Profile")).perform(click());
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.enterEmail)).perform(typeText("blahblah@usc.edu"));
        onView(withId(R.id.enterPassword)).perform(typeText("blahblah"));
        onView(withId(R.id.loginButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.loginTip)).check(matches(withText("Invalid email/password combination.")));
    }

    @Test
    public void Test3_login_EmptyFields() {
        onView(withText("Profile")).perform(click());
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));

        onView(withId(R.id.loginButton)).perform(click());
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.loginTip)).check(matches(withText("Please enter your email and password!")));
    }
}