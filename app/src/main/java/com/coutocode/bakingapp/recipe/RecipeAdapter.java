package com.coutocode.bakingapp.recipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coutocode.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private final List<Recipe> mValues;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };

    RecipeAdapter(List<Recipe> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.onBind(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivRecipe)
        ImageView ivRecipe;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.btnExpand)
        Button btnExpand;
        @BindView(R.id.rvIngredients)
        RecyclerView rvIngredients;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(Recipe recipe){

            if (!recipe.image.isEmpty()) {
                Picasso.with(itemView.getContext())
                        .load(recipe.image)
                        .placeholder(R.drawable.ingredients)
                        .error(R.drawable.ingredients)
                        .into(ivRecipe);
            }

            tvTitle.setText(recipe.name);
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(recipe.servings))
                    .append(" ")
                    .append(itemView.getResources().getString(R.string.servings));
            tvDescription.setText(sb);
        }
    }
}
