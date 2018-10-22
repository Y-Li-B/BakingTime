package com.example.android.bakingtime;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingtime.fragments.CookingStepDetailsFragment;
import com.example.android.bakingtime.model.CookingStep;
import com.example.android.bakingtime.model.Recipe;

public class CookingStepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_step_details);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra(Recipe.NAME_TAG));
        Parcelable[] steps = intent.getParcelableArrayExtra(CookingStep.TAG);
        int position = intent.getIntExtra(CookingStep.POSITION_TAG, 0);

       if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_cooking_step_details_fragment,
                            CookingStepDetailsFragment.newInstance(steps, position),
                            CookingStepDetailsFragment.TAG).commit();

        }


    }
}
