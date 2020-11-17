package com.chris.mtgdecksapp;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AddCardTests {
    @Rule
    public ActivityTestRule<CardAddActivity> mCardAddActivityTestRule =
            new ActivityTestRule<>(CardAddActivity.class);

    @Test
    public void clickSaveTest() throws Exception{
        onView(withId(R.id.editTextName)).perform(typeText("TestCard"));
        onView(withId(R.id.editTextManaCost)).perform(typeText("{W}{U}{B}{R}{G}"));
        onView(withId(R.id.editTextText)).perform(ViewActions.scrollTo()).perform(typeText("TEXT"));
        onView(withId(R.id.nacho_text_view_type)).perform(ViewActions.scrollTo()).perform(typeText("Equipment\n"));
        onView(withId(R.id.nacho_text_view_supertype)).perform(ViewActions.scrollTo()).perform(typeText("Legendary\n"));
        onView(withId(R.id.floatingActionButton)).perform(click());
    }
}
