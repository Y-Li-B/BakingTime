package com.example.android.bakingtime.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingtime.adapters.CookingStepAdapter;
import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.CookingStep;
import com.example.android.bakingtime.CookingStepDetailsActivity;
import com.example.android.bakingtime.model.Recipe;

public class CookingStepsFragment extends Fragment {

    //Will be used when adding this fragment through the fragment manager.
    public final static String TAG = CookingStepsFragment.class.getSimpleName();

    //Used to hold of a boolean value of the requested layout orientation.
    final static String IS_TABLET_TAG = "is_tablet";

    //Recycler View to display cooking cookingSteps.
    RecyclerView mRecyclerView;

   public static CookingStepsFragment newInstance(boolean isTabletLayout) {
        CookingStepsFragment frag = new CookingStepsFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_TABLET_TAG, isTabletLayout);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_cooking_steps, container, false);
        mRecyclerView = fragmentView.findViewById(R.id.recipe_details_RV);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        Recipe recipe = Recipe.getRecipe(getActivity());

        boolean isTabletLayout = getArguments().getBoolean(IS_TABLET_TAG);

        mRecyclerView.setAdapter(makeOrientedAdapter(recipe.getCookingSteps(),isTabletLayout));

        return fragmentView;
    }


    //Constructs a suitable adapter based on the orientation.
    CookingStepAdapter makeOrientedAdapter(CookingStep[] cookingSteps, boolean isTabletLayout) {
        CookingStepAdapter.OnItemClickListener listener = isTabletLayout ?
                new TabletClickListener() : new PhoneClickListener();

        return new CookingStepAdapter(cookingSteps, listener);

    }


    //Starts a new activity, while passing the cookingSteps and the cookingStepPosition of the clicked one as extras.
    class PhoneClickListener implements CookingStepAdapter.OnItemClickListener {
        @Override
        public void onClick(View v, CookingStep[] steps, int AdapterPosition) {
            Context context = v.getContext();
            Intent intent = new Intent(context, CookingStepDetailsActivity.class);
            intent.putExtra(CookingStep.TAG, steps);
            intent.putExtra(CookingStep.POSITION_TAG, AdapterPosition);
            intent.putExtra(Recipe.NAME_TAG,getActivity().getTitle());
            context.startActivity(intent);
        }
    }

    //Replaces the cookingStepDetail fragment, while passing the cookingSteps and the cookingStepPosition of the clicked one.
    class TabletClickListener implements CookingStepAdapter.OnItemClickListener {
        @Override
        public void onClick(View v, CookingStep[] steps, int AdapterPosition) {
            FragmentManager manager = getFragmentManager();
            if (manager != null) {
                manager.beginTransaction()
                        .replace(R.id.container_cooking_step_details_fragment,
                                CookingStepDetailsFragment.newInstance(steps, AdapterPosition),
                                CookingStepDetailsFragment.TAG).commit();
            }
        }
    }
}
