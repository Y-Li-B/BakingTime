package com.example.android.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.CookingStepViewHolder> {

    private RecipeStep[] steps;
    private OnItemClickListener listener;

    RecipeStepAdapter(RecipeStep[] steps, OnItemClickListener listener) {
        this.listener = listener;
        this.steps = steps;
    }

    interface OnItemClickListener {
        void onClick(View v,RecipeStep[] steps,int AdapterPosition);
    }

    @NonNull
    @Override
    public CookingStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recipe,
                parent,
                false);

        return new CookingStepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CookingStepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return steps.length;
    }


    class CookingStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemView;

        CookingStepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.itemView = (TextView) itemView;
        }

        void bind(int position){
            itemView.setText(steps[position].getShortDescription());
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,steps,getAdapterPosition());

        }
    }
}
