package com.example.testingexample;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testReverseButton_displaysReversedText() {
        onView(withId(R.id.inputField)).perform(typeText("hello"), closeSoftKeyboard());
        onView(withId(R.id.reverseButton)).perform(click());
        onView(withId(R.id.resultView)).check(matches(withText("olleh")));
    }
}
