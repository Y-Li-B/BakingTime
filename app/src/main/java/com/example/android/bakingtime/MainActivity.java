package com.example.android.bakingtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingtime.fragments.MainFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //Only commit transaction if this is the first time.
            if (savedInstanceState==null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_recipe_list_fragment,
                                new MainFragment(),
                                MainFragment.TAG).commit();
            }
    }

}
