package vincenthudry.organizer.View;

import android.support.test.InstrumentationRegistry;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import vincenthudry.organizer.R;
import vincenthudry.organizer.View.mainActivity.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.*;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NoteTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Test
    public void noteTest() {
        //create
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.saveNote)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editTextNoteTitle)).perform(typeText("title1"));
        onView(withId(R.id.textInputNote)).perform(typeText("content1"));
        onView(withId(R.id.saveNote)).perform(click());
        //read
        onView(withId(R.id.notesList)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(allOf(withText("title1"))).check(matches(isDisplayed()));
        onView(withId(R.id.notesList)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.editTextNoteTitle)).check(matches(withText("title1")));
        onView(withId(R.id.textInputNote)).check(matches(withText("content1")));
        Espresso.pressBack();
        //update
        onView(allOf(withId(R.id.notesList))).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.notesList)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.editTextNoteTitle)).perform(replaceText("new title"));
        onView(withId(R.id.saveNote)).perform(click());
        onView(allOf(withText("new title"))).check(matches(isDisplayed()));
        //delete
        onView(allOf(withId(R.id.notesList))).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.notesList)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext());
        onView(allOf(withText("Delete"))).perform(click());
        onView(allOf(withId(android.R.id.button1))).perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}

