package com.example.android.bakingtime.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingtime.util.NetworkUtils;
import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Recipe;
import com.example.android.bakingtime.adapters.RecipeAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class MainFragment extends Fragment {


    private Recipe[] dataSet;

    public final static String TAG = MainFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private TextView mErrorMsgTextView;

    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        mErrorMsgTextView= fragmentView.findViewById(R.id.error_message_TV);

        mProgressBar= fragmentView.findViewById(R.id.progress_bar);
        mRecyclerView = fragmentView.findViewById(R.id.recipe_list_RV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        //If this is the first time loading then fetch data.
        if (savedInstanceState == null) {
            new FetchRecipesTask(this).execute();
            //Else just reload existing data.
        } else {
            //get old data from saveInstanceState bundle and send it to the display.
            dataSet = (Recipe[]) savedInstanceState.getParcelableArray(Recipe.TAG);
            sendDataToDisplay();
        }
        return fragmentView;
    }

    void setData(Recipe[] dataSet) {
        this.dataSet = dataSet;
    }

    //Performs null check and array length check,
    //if all is ok then it displays the data by calling displayData()
    void sendDataToDisplay(){
        if (dataSet==null){
            stopWithErrorMessage(getString(R.string.connection_error));
        }
        else if (dataSet.length==0){
            stopWithErrorMessage(getString(R.string.no_results_found));
        }
        else{
            displayData();
        }
    }

    //Displays the current data in the recycler view.
    void displayData() {
        RecipeAdapter adapter = (RecipeAdapter) mRecyclerView.getAdapter();
            if (adapter == null) {
                mRecyclerView.setAdapter(new RecipeAdapter(dataSet));
            } else {
                adapter.setRecipeData(dataSet);
                adapter.notifyDataSetChanged();
            }
            mRecyclerView.setVisibility(View.VISIBLE);
        }


    //Hides the progress bar,Sets text of error msg TV, and show's it.
    //not orthogonal, consider redesigning
    void stopWithErrorMessage(String msg){
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMsgTextView.setText(msg);
        mErrorMsgTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(Recipe.TAG, dataSet);
    }

    static class FetchRecipesTask extends AsyncTask<Void, Void, Recipe[]> {
        //Weak ref to avoid leaks,
        //in this program all of the loading will occur in the MainFragment.
        private WeakReference<MainFragment> callerFragment;

        FetchRecipesTask(MainFragment callerFragment) {
            this.callerFragment = new WeakReference<>(callerFragment);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v("VVVV","REQUESTING NEW DATA");
            MainFragment frag = this.callerFragment.get();
            frag.mRecyclerView.setVisibility(View.INVISIBLE);
            frag.mErrorMsgTextView.setVisibility(View.INVISIBLE);
            frag.mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Recipe[] doInBackground(Void... voids) {
            Recipe[] recipes = null;
            //Try to get recipe data...
            try {
                recipes = NetworkUtils.getRecipesFromURL(NetworkUtils.RECIPES_URL);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return recipes;
        }

        @Override
        protected void onPostExecute(Recipe[] dataSet) {
            MainFragment mainFragment = callerFragment.get();
            if (mainFragment != null) {
                mainFragment.setData(dataSet);
                mainFragment.sendDataToDisplay();
            }
        }
    }
}
