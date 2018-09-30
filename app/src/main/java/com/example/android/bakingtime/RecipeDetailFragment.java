package com.example.android.bakingtime;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.model.Recipe;

public class RecipeDetailFragment extends Fragment {

    final static String TAG = RecipeDetailFragment.class.getSimpleName();
    RecyclerView mRecyclerView;
    TextView mIngredientsTextView;

    static RecipeDetailFragment newInstance(boolean isTabletLayout) {
        RecipeDetailFragment frag = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putBoolean(RecipeDetailActivity.LAYOUT_TAG, isTabletLayout);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        mRecyclerView = fragmentView.findViewById(R.id.recipe_details_RV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mIngredientsTextView = fragmentView.findViewById(R.id.ingredients_TV);
        Recipe recipe = RecipeUtils.getRecipe(getActivity());

        populate(recipe);

        return fragmentView;
    }

    void populate(final Recipe recipe) {
        if (recipe != null) {
            RecipeStep[] steps = RecipeUtils.getCookingSteps(recipe);
            final String ingredients = RecipeUtils.getIngredients(recipe);
            if (steps != null) {
                Bundle args = this.getArguments();
                if (args != null) {
                    if (args.getBoolean(RecipeDetailActivity.LAYOUT_TAG, false))
                        mRecyclerView.setAdapter(new RecipeStepAdapter(steps, new TabletClickListener()));
                    else
                        mRecyclerView.setAdapter(new RecipeStepAdapter(steps, new PhoneClickListener()));
                }
            }
            if (ingredients != null) {
                Log.v("VVV","clicked");
                mIngredientsTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IngredientsDialogFragment.newInstance(ingredients).show(getFragmentManager(),null);
                    }
                });
            }

        }
    }


    class PhoneClickListener implements RecipeStepAdapter.OnItemClickListener {
        @Override
        public void onClick(View v, RecipeStep[] steps, int AdapterPosition) {
            Context context = v.getContext();
            Intent intent = new Intent(context, RecipeStepDetailActivity.class);
            intent.putExtra(RecipeStep.TAG, steps);
            intent.putExtra(RecipeStep.POSITION_TAG, AdapterPosition);
            context.startActivity(intent);
        }
    }

    class TabletClickListener implements RecipeStepAdapter.OnItemClickListener {
        @Override
        public void onClick(View v, RecipeStep[] steps, int AdapterPosition) {
            FragmentManager manager = getFragmentManager();
            if (manager != null) {
                manager.beginTransaction()
                        .replace(R.id.container_recipe_step_details_fragment,
                                RecipeStepDetailFragment.newInstance(steps, AdapterPosition),
                                RecipeStepDetailFragment.TAG).commit();
            }
        }
    }
}
