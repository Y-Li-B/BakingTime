package com.example.android.bakingtime;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingtime.model.CookingStep;

public class RecipeStepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        Intent intent = getIntent();
        Parcelable[] steps = intent.getParcelableArrayExtra(CookingStep.TAG);
        int position = intent.getIntExtra(CookingStep.POSITION_TAG, 0);

       if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_recipe_step_details_fragment,
                            CookingStepDetailsFragment.newInstance(steps, position),
                            CookingStepDetailsFragment.TAG).commit();

        }


    }
}
