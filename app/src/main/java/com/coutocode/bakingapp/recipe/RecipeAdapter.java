package com.coutocode.bakingapp.recipe;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coutocode.bakingapp.R;
import com.coutocode.bakingapp.step.StepListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private final List<Recipe> mValues;
    final RecipeListActivity activity;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, StepListActivity.class);
            intent.putParcelableArrayListExtra(context.getString(R.string.extra_recipe),
                    mValues.get((Integer) view.getTag()).steps);
            String transitionName = context.getString(R.string.transition_string);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                            view,
                            transitionName
                    );
            ActivityCompat.startActivity(context, intent, options.toBundle());
        }
    };

    RecipeAdapter(List<Recipe> items, RecipeListActivity activity) {
        mValues = items;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.onBind(mValues.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
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
        @BindView(R.id.layout_list)
        LinearLayout layout_list;

        boolean isColapsed = true;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(mOnClickListener);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            rvIngredients.setLayoutManager(layoutManager);
        }

        public void onBind(Recipe recipe, int position){

            cardView.setTag(position);

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
            rvIngredients.setAdapter(new IngredientAdapter(recipe.ingredients));

            btnExpand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isColapsed){
                        layout_list.setVisibility(View.VISIBLE);
                        btnExpand.setText(R.string.collapse);
                    }else{
                        layout_list.setVisibility(View.GONE);
                        btnExpand.setText(R.string.expand);
                    }
                    isColapsed = !isColapsed;
                }
            });
        }
    }
}
