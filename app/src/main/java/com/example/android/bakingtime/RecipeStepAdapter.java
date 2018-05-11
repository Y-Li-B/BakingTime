package com.example.android.bakingtime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.CookingStepViewHolder> {

    private RecipeStep[] steps;
    private OnItemClickListener listener;

    RecipeStepAdapter(RecipeStep[] steps, OnItemClickListener listener) {
        this.listener = listener;
        this.steps = steps;
    }

    interface OnItemClickListener {
        void onClick(View v, RecipeStep[] steps, int AdapterPosition);
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
        ViewGroup itemView;
        ImageView imageView;
        TextView textView;

        CookingStepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.itemView = (ViewGroup) itemView;
            imageView = itemView.findViewById(R.id.recipe_IV);
            textView = itemView.findViewById(R.id.recipe_TV);
        }

        void bind(int position) {
            RecipeStep step = steps[position];
            String imageUrl = step.getThumbnailURL();
            if (!imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

                }
                textView.setText(steps[position].getShortDescription());
            }

            @Override
            public void onClick (View v){
                listener.onClick(v, steps, getAdapterPosition());

            }
        }
    }
