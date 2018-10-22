package com.example.android.bakingtime.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.Recipe;


//This fragment will show the user the ingredients of the recipe.
public class IngredientsDialogFragment extends DialogFragment {

    public static final String  TAG = IngredientsDialogFragment.class.getSimpleName();


    public static IngredientsDialogFragment newInstance(String ingredients){
        IngredientsDialogFragment frag = new IngredientsDialogFragment();
        Bundle args = new Bundle();
        args.putString(Recipe.INGREDIENTS_TAG,ingredients);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ingredients,container,false);
        TextView ingredientsTextView = v.findViewById(R.id.dialog_ingredients_TV);
        ingredientsTextView.setText(getArguments().getString(Recipe.INGREDIENTS_TAG));
        return v;
    }
}
