package com.example.androiduitesting;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void setup() {
        init();
    }

    @After
    public void tearDown() {
        release();
    }

    @Test
    public void testAddCity(){
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity(){
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        onView(withId(R.id.button_clear)).perform(click());
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView(){
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0).check(matches((withText("Edmonton"))));
    }

    @Test
    public void testActivitySwitch() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Edmonton")))
                .inAdapterView(withId(R.id.city_list))
                .perform(click());

        /*
            ChatGPT provided me with the information to use intented to check if an activity
            switch had occured when I asked it how to check if an activity switch occured
            in testing with JUnit.
         */
        intended(hasComponent(ShowActivity.class.getName()));
    }

    @Test
    public void testCityNameConsistency() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Calgary")))
                .inAdapterView(withId(R.id.city_list))
                .perform(click());

        onView(withText("Calgary")).check(matches(isDisplayed()));
    }

    @Test
    public void testReturnToMain() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Toronto"));
        onView(withId(R.id.button_confirm)).perform(click());

        onData(allOf(is(instanceOf(String.class)), is("Toronto")))
                .inAdapterView(withId(R.id.city_list))
                .perform(click());

        onView(withId(R.id.back_button)).perform(click());

        /*
            ChatGPT was used to help generate this assertion and test for returning to
            the MainActivity. My prompt involved providing my previous incorrect
            implementation and asking why it failed the test and it provided this version
        */
        scenario.getScenario().onActivity(activity -> {
            assertTrue(activity instanceof MainActivity);
        });
    }
}