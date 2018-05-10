package com.example.android.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Recipe[] recipes;

    RecipeAdapter(Recipe[] recipes) {
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

    void setRecipeData(Recipe[] recipes) {
        this.recipes = recipes;
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.itemView = (TextView) itemView;
        }

        void bind(int position){
            itemView.setText(recipes[position].getName());
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context,RecipeDetailActivity.class);
            intent.putExtra(Recipe.TAG,recipes[getAdapterPosition()]);
            context.startActivity(intent);
        }
    }
}
