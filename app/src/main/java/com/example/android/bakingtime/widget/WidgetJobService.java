package com.example.android.bakingtime.widgets;


import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.util.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;


public class WidgetJobService extends JobIntentService {

    public static final String ACTION_UPDATE_WIDGET =
            "com.example.android.bakingtime.action.update_widget";

    public static final int JOB_ID =  3413;

    @Override
    protected void onHandleWork(@Nullable Intent intent) {
        Log.v("VVVV","HANDLING WORK");
        if (intent != null) {
            String action = intent.getAction();
            if (action != null && action.equals(ACTION_UPDATE_WIDGET)) {
                    handleUpdateWidget(intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,0));
            }
        }
    }

    void handleUpdateWidget(int widgetID){
        Recipe[] recipes = getRecipes();
        String ingredients = null;
        if(recipes!=null) {
            int position = getPosition(recipes.length - 1);
            Recipe recipe = recipes[position];
            ingredients = recipe.getName() + " " + recipe.getIngredients();
        }
        else{
            ingredients = "Network error...";
        }
        WidgetProvider.populateWidget(this, widgetID, ingredients);

    }

    Recipe[] getRecipes(){
        Recipe[] recipes = null;
        try{
            recipes= NetworkUtils.getRecipesFromURL(NetworkUtils.RECIPES_URL);
        }
        catch(IOException | JSONException e){
            e.printStackTrace();
        }
        return recipes;
    }

    //returns the current position which still hasn't been displayed, and advances a step and
    //stores it in the shared prefs so that a new position is returned when this method is called
    //again
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