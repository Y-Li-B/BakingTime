package com.example.android.bakingtime;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.android.bakingtime.model.Recipe;

import org.json.JSONException;

import java.io.IOException;


public class IngredientsService extends android.app.IntentService {

    public static final String ACTION_UPDATE_WIDGET =
            "com.example.android.bakingtime.action.update_widget";



    public IngredientsService() {
        super(IngredientsService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(ACTION_UPDATE_WIDGET)) {
                    handleUpdateWidget();
                }
            }
        }
    }

    void handleUpdateWidget(){
        Recipe[] recipes = getRecipes();
        String ingredients = null;
        if(recipes!=null) {
            int position = getPosition(recipes.length - 1);
            Recipe recipe = recipes[position];
            ingredients = recipe.getName() + " " + RecipeUtils.getIngredients(recipe);
        }
        else{
            ingredients = "Network error...";
        }
        WidgetProvider.updateWidgets(this, ingredients);

    }

    Recipe[] getRecipes(){
        Recipe[] recipes = null;
        try{
            recipes= NetworkUtils.getRecipeData();
        }
        catch(IOException | JSONException e){
            e.printStackTrace();
        }
        return recipes;
    }

    int getPosition(int finalPosition){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int position = prefs.getInt(Recipe.RECIPE_WIDGET_POSITION,0);
        SharedPreferences.Editor editor = prefs.edit();
        int nextPosition = position + 1 > finalPosition ? 0 : position+1;
        editor.putInt(Recipe.RECIPE_WIDGET_POSITION,nextPosition);
        editor.apply();
        return position;

    }



}