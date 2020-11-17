package com.chris.mtgdecksapp;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.chris.mtgdecksapp.database.MTGAppRepository;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddCardToDeckTests {
    @Rule
    public ActivityTestRule<DecksActivity> mAddCardToDeckTestsTestRule =
            new ActivityTestRule<>(DecksActivity.class);
    @Test
    public void A_addFirstCardToGenericDeck() throws Exception{
        onView(withText("Generic Deck")).perform(click());
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Jace")).perform(click());
        onView(withId(R.id.dialogCardQuantityField)).perform(typeText("1")).perform(closeSoftKeyboard());
        onView(withText("Add")).perform(click());
        onView(isRoot()).perform(pressBack());
        //check the card list for the deck contains "Jace" with quantity "x1"
        onView(withId(R.id.recyclerView)).check(matches(atPosition(0,hasDescendant(withText("Jace")))));
        onView(withId(R.id.recyclerView)).check(matches(atPosition(0,hasDescendant(withText("x1")))));
    }
    @Test
    public void B_addSecondCardToGenericDeck() throws Exception{
        onView(withText("Generic Deck")).perform(click());
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Jace")).perform(click());
        onView(withId(R.id.dialogCardQuantityField)).perform(typeText("1")).perform(closeSoftKeyboard());
        onView(withText("Add")).perform(click());
        onView(isRoot()).perform(pressBack());
        //check the card list for the deck contains "Jace" with quantity "x2"
        onView(withId(R.id.recyclerView)).check(matches(atPosition(0,hasDescendant(withText("Jace")))));
        onView(withId(R.id.recyclerView)).check(matches(atPosition(0,hasDescendant(withText("x2")))));
    }
    @Test
    public void C_addMultipleNonBasicCardToGenericDeck() throws Exception{
        onView(withText("Generic Deck")).perform(click());
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Some Card")).perform(click());
        onView(withId(R.id.dialogCardQuantityField)).perform(typeText("2"));
        onView(withText("Add")).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.recyclerView)).check(matches(atPosition(1,hasDescendant(withText("Some Card")))));
        onView(withId(R.id.recyclerView)).check(matches(atPosition(1,hasDescendant(withText("x2")))));
    }

    @Test
    public void D_addMultipleNonBasicCardToCommanderDeck() throws Exception{
        onView(withText("Commander Deck")).perform(click());
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Some Card")).perform(click());
        onView(withId(R.id.dialogCardQuantityField)).perform(typeText("2")).perform(closeSoftKeyboard());
        onView(withText("Add")).perform(click());
        //check that the warning message is displayed
        onView(withText("Commander deck should only have one of each nonbasic card.")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Cancel")).perform(click());
    }

    @Test
    public void E_addFirstNonBasicCardToCommanderDeck() throws Exception{
        onView(withText("Commander Deck")).perform(click());
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Jace")).perform(click());
        onView(withId(R.id.dialogCardQuantityField)).perform(typeText("1")).perform(closeSoftKeyboard());
        onView(withText("Add")).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.recyclerView)).check(matches(atPosition(0,hasDescendant(withText("Jace")))));
        onView(withId(R.id.recyclerView)).check(matches(atPosition(0,hasDescendant(withText("x1")))));
    }

    @Test
    public void F_addSecondNonBasicCardToCommanderDeck() throws Exception{
        onView(withText("Commander Deck")).perform(click());
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Jace")).perform(click());
        onView(withId(R.id.dialogCardQuantityField)).perform(typeText("1")).perform(closeSoftKeyboard());
        onView(withText("Add")).perform(click());
        onView(withText("Commander deck should only have one of each nonbasic card.")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("Cancel")).perform(click());
    }



    @Test
    public void G_addFirstBasicCardToCommanderDeck() throws Exception{
        onView(withText("Commander Deck")).perform(click());
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Forest")).perform(click());
        onView(withId(R.id.dialogCardQuantityField)).perform(typeText("1")).perform(closeSoftKeyboard());
        onView(withText("Add")).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.recyclerView)).check(matches(atPosition(1,hasDescendant(withText("Forest")))));
        onView(withId(R.id.recyclerView)).check(matches(atPosition(1,hasDescendant(withText("x1")))));
    }

    @Test
    public void H_addSecondBasicCardToCommanderDeck() throws Exception{
        onView(withText("Commander Deck")).perform(click());
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Forest")).perform(click());
        onView(withId(R.id.dialogCardQuantityField)).perform(typeText("1")).perform(closeSoftKeyboard());
        onView(withText("Add")).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.recyclerView)).check(matches(atPosition(1,hasDescendant(withText("Forest")))));
        onView(withId(R.id.recyclerView)).check(matches(atPosition(1,hasDescendant(withText("x2")))));
    }

    @Test
    public void I_addMultipleBasicCardToCommanderDeck() throws Exception{
        onView(withText("Commander Deck")).perform(click());
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Forest")).perform(click());
        onView(withId(R.id.dialogCardQuantityField)).perform(typeText("2")).perform(closeSoftKeyboard());
        onView(withText("Add")).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.recyclerView)).check(matches(atPosition(1,hasDescendant(withText("Forest")))));
        onView(withId(R.id.recyclerView)).check(matches(atPosition(1,hasDescendant(withText("x4")))));
    }


    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
