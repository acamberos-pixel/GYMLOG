package com.example.gym3;

import static androidx.test.espresso.Espresso.onView;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.gym3", appContext.getPackageName());
    }


    @Test
    public void testLoginAddLogSignOutAndVerify() {
        // sign in as admin1
        onView(withId(R.id.userNameLoginEditText)).perform(typeText("admin1"), closeSoftKeyboard());
        onView(withId(R.id.passwordLoginEditText)).perform(typeText("admin1"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // add a log entry
        onView(withId(R.id.exerciseInputEditText)).perform(typeText("Pushups"), closeSoftKeyboard());
        onView(withId(R.id.weightInputEditText)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.repInputEditText)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.logButton)).perform(click());

        // sign out
        onView(withId(R.id.logoutMenuItem)).perform(click());
        onView(withText("logout")).perform(click());

        // sign back in and verify log persists
        onView(withId(R.id.userNameLoginEditText)).perform(typeText("admin1"), closeSoftKeyboard());
        onView(withId(R.id.passwordLoginEditText)).perform(typeText("admin1"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.logDisplayRecyclerView)).check(matches(isDisplayed()));
    }
}