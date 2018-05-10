package com.example.android.bakingtime;

import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class FetchRecipesTask extends AsyncTask<Void, Void, Recipe[]> {
    private WeakReference<MainFragment> callerFragment;

     FetchRecipesTask(MainFragment callerFragment) {
        this.callerFragment = new WeakReference<>(callerFragment);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.callerFragment.get().mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected Recipe[] doInBackground(Void... voids) {
        Recipe[] recipes = null;
        try {
            recipes = NetworkUtils.getRecipeData();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    @Override
    protected void onPostExecute(Recipe[] recipes) {
        MainFragment frag = callerFragment.get();
        if (frag != null) {
            frag.updateData(recipes);

            }
        }
    }

