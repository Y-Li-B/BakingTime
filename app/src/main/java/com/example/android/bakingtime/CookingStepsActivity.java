package com.example.android.bakingtime;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.bakingtime.fragments.CookingStepDetailsFragment;
import com.example.android.bakingtime.fragments.CookingStepsFragment;
import com.example.android.bakingtime.fragments.IngredientsDialogFragment;
import com.example.android.bakingtime.model.Recipe;

public class CookingStepsActivity extends AppCompatActivity {

    String ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_steps);

        Recipe recipe = getIntent().getParcelableExtra(Recipe.TAG);
        ingredients = recipe.getIngredients();

        setTitle(recipe.getName());

        if (savedInstanceState==null) {

            FragmentManager manager = getSupportFragmentManager();

            /*If we can find the details fragment in this activity then it means that android
            has loaded the layout resource file for the tablet layout, which means we are running
            on a tablet*/
            boolean isTabletLayout= findViewById(R.id.container_cooking_step_details_fragment) !=null;

            if (isTabletLayout) populateTabletLayout(manager,recipe);
            else populatePhoneLayout(manager);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cooking_steps_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show_ingredients:
                IngredientsDialogFragment.newInstance(ingredients)
                        .show(getSupportFragmentManager(),IngredientsDialogFragment.TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void populateTabletLayout(FragmentManager manager, Recipe recipe){

        manager.beginTransaction()
                .add(R.id.container_cooking_steps_fragment,
                        CookingStepsFragment.newInstance(true),
                        CookingStepsFragment.TAG)
                .add(R.id.container_cooking_step_details_fragment,
                        CookingStepDetailsFragment.newInstance(recipe.getCookingSteps(),0),
                        CookingStepDetailsFragment.TAG)
                .commit();
    }

    void populatePhoneLayout(FragmentManager manager){
        manager.beginTransaction()
                .add(R.id.container_cooking_steps_fragment,
                        CookingStepsFragment.newInstance(false),
                        CookingStepsFragment.TAG).commit();

    }
}
