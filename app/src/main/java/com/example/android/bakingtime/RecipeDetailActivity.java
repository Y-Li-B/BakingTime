package com.example.android.bakingtime;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.bakingtime.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    static final String LAYOUT_TAG = RecipeDetailActivity.class.getSimpleName()+"Layout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        if (savedInstanceState==null) {

            FragmentManager manager = getSupportFragmentManager();

            View v = findViewById(R.id.container_recipe_step_details_fragment);

            if (v==null) populatePhoneLayout(manager);
            else populateTabletLayout(manager);
        }
    }

    void populateTabletLayout(FragmentManager manager){
        Recipe recipe = RecipeUtils.getRecipe(this);

        manager.beginTransaction()
                .add(R.id.container_recipe_details_fragment,
                        RecipeDetailFragment.newInstance(true),
                        RecipeDetailFragment.TAG)
                .add(R.id.container_recipe_step_details_fragment,
                        RecipeStepDetailFragment.newInstance(RecipeUtils.getCookingSteps(recipe),0),
                        RecipeStepDetailFragment.TAG)
                .commit();
    }

    void populatePhoneLayout(FragmentManager manager){
        manager.beginTransaction()
                .add(R.id.container_recipe_details_fragment,
                        RecipeDetailFragment.newInstance(false),
                        RecipeDetailFragment.TAG).commit();

    }
}
