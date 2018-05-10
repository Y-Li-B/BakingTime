package com.example.android.bakingtime;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)


public class ItemClickTest {
    @Rule public IntentsTestRule<MainActivity> mainActivityActivityTestRule
            = new IntentsTestRule<>(MainActivity.class);

    private static final String DESCRIPTION = "2. Combine the cake flour" +
            ", 400 grams (2 cups) of sugar, baking powder, and 1 teaspoon of salt in the bowl of" +
            " a stand mixer. Using the paddle attachment, beat at low speed until the dry " +
            "ingredients are mixed together, about one minute";
    @Test
    public void clickItem_LaunchesActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.recipe_list_RV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));

        Intents.intended(IntentMatchers.hasComponent(RecipeDetailActivity.class.getName()));

    }

    @Test
    public void clickItem_matchesText(){
        Espresso.onView(ViewMatchers.withId(R.id.recipe_list_RV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.recipe_details_RV)).perform(
                RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()));

        Espresso.onView(ViewMatchers.withId(R.id.step_description_TV)).check(
                ViewAssertions.matches(ViewMatchers.withText(DESCRIPTION))
        );

    }


}
