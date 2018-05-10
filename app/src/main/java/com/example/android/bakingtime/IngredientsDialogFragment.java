package com.example.android.bakingtime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IngredientsDialogFragment extends DialogFragment {
    static final String INGREGIENTS_TAG = "ingredients";
    static IngredientsDialogFragment newInstance(String ingredients){
        IngredientsDialogFragment frag = new IngredientsDialogFragment();
        Bundle args = new Bundle();
        args.putString(INGREGIENTS_TAG,ingredients);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_ingredients,container,false);
        TextView ingredientsTextView = v.findViewById(R.id.dialog_ingredients_TV);
        ingredientsTextView.setText(getArguments().getString(INGREGIENTS_TAG));
        return v;
    }
}
