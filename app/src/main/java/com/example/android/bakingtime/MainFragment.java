package com.example.android.bakingtime;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

    Recipe[] recipes;
    final static String TAG = MainFragment.class.getSimpleName();
    RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_main,container,false);
        mRecyclerView = fragmentView.findViewById(R.id.recipe_list_RV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        if(savedInstanceState==null) {
            new FetchRecipesTask(this).execute();
        }
        else{
            Recipe[] recipes = (Recipe[]) savedInstanceState.getParcelableArray(Recipe.TAG);
            updateData(recipes);
        }
        return fragmentView;
        }


    void updateData(Recipe[] recipes){
        this.recipes= recipes;
        if (recipes != null) {
            RecipeAdapter adapter = (RecipeAdapter) mRecyclerView.getAdapter();
            if (adapter == null) {
                mRecyclerView.setAdapter(new RecipeAdapter(recipes));
            } else {
                adapter.setRecipeData(recipes);
                adapter.notifyDataSetChanged();
            }
            mRecyclerView.setVisibility(View.VISIBLE);

    }
}

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(Recipe.TAG, recipes);
    }
    }
