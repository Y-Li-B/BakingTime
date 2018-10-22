package com.example.android.bakingtime.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.CookingStepsActivity;
import com.example.android.bakingtime.model.Recipe;
import com.squareup.picasso.Picasso;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Recipe[] recipes;

    public RecipeAdapter(Recipe[] recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recipe,
                parent,
                false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return recipes.length;
    }

    public void setRecipeData(Recipe[] recipes) {
        this.recipes = recipes;
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView= itemView.findViewById(R.id.recipe_IV);
            imageView.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),R.drawable.food_placeholder));
            textView= itemView.findViewById(R.id.recipe_TV);
        }

        void bind(int position){
            Recipe recipe = recipes[position];
            final String imageUrl = recipe.getImageUrl();
            if(!imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).placeholder(R.drawable.food_placeholder).into(imageView);
            }
            textView.setText(recipe.getName());
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context,CookingStepsActivity.class);
            intent.putExtra(Recipe.TAG,recipes[getAdapterPosition()]);
            context.startActivity(intent);
        }
    }
}
