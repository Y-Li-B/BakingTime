package com.example.android.bakingtime;

import android.support.v4.app.FragmentActivity;

import org.json.JSONException;

public class RecipeUtils {

    static Recipe getRecipe(FragmentActivity fragmentActivity) {
        Recipe recipe = null;
        if (fragmentActivity!= null) {
            recipe = fragmentActivity.getIntent().getParcelableExtra(Recipe.TAG);
        }
        return recipe;
    }

    static String getIngredients(Recipe recipe) {
        String ingredients = null;
        try {
            ingredients = NetworkUtils.parseIngredients(recipe.getIngredientsJson());
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return ingredients;
    }

    static RecipeStep[] getCookingSteps(Recipe recipe) {
        RecipeStep[] steps = null;
        try {
            steps = NetworkUtils.parseCookingSteps(recipe.getStepsJson());
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return steps;
    }
}
