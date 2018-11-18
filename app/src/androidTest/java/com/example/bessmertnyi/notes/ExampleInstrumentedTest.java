package com.example.bessmertnyi.notes;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.bessmertnyi.notes", appContext.getPackageName());
    }

    public void createNote(String text) {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.new_note)).perform(click());

        onView(withId(R.id.mainTextEditText))
                .perform(typeText(text));

        onView(withId(R.id.backImageButton)).perform(click());
        onView(withText(text)).check(matches(isDisplayed()));
    }

    @Test
    public void createNote() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.new_note)).perform(click());

        onView(withId(R.id.mainTextEditText))
                .check(matches(isDisplayed()))
                .check(matches(withText("")))
                .perform(typeText("Hello World!!!"));

        onView(withId(R.id.backImageButton)).perform(click());

        onView(withText("Hello World!!!")).check(matches(isDisplayed()));
    }

    @Test
    public void editNote() {
        String text = "text";
        createNote(text);

        onView(withText(text)).perform(click());

        onView(withId(R.id.mainTextEditText))
                .check(matches(isDisplayed()))
                .check(matches(withText(text)))
                .perform(typeText("EDITED!"));

        onView(withId(R.id.backImageButton)).perform(click());

        onView(withText(text + "EDITED!")).check(matches(isDisplayed()));


    }

    @Test
    public void rotateScreen() {
        String text1 = "Note1";
        String text2 = "Note2";
        createNote(text1);
        createNote(text2);

        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withText(text1)).check(matches(isDisplayed()));
        onView(withText(text2)).check(matches(isDisplayed()));
        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        onView(withText(text1)).check(matches(isDisplayed()));
        onView(withText(text2)).check(matches(isDisplayed()));
    }
}
