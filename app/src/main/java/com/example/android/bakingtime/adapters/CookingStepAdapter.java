package com.example.android.bakingtime.adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.CookingStep;
import com.squareup.picasso.Picasso;

public class CookingStepAdapter extends RecyclerView.Adapter<CookingStepAdapter.CookingStepViewHolder> {

    private CookingStep[] steps;

    //On item click, data will be passed and handled by this. It will be provided through
    //the constructor
    private OnItemClickListener listener;

    public CookingStepAdapter(CookingStep[] steps, OnItemClickListener listener) {
        this.listener = listener;
        this.steps = steps;
    }

    public interface OnItemClickListener {
        void onClick(View v, CookingStep[] steps, int AdapterPosition);
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
            imageView.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),R.drawable.cooking_step_placeholder));
            textView = itemView.findViewById(R.id.recipe_TV);
        }

        void bind(int position) {
            CookingStep step = steps[position];
            String imageUrl = step.getThumbnailURL();
            if (!imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).
                        placeholder(R.drawable.cooking_step_placeholder).into(imageView);
                }
                textView.setText(steps[position].getShortDescription());
            }

            @Override
            public void onClick (View v){
                listener.onClick(v, steps, getAdapterPosition());

            }
        }
    }
