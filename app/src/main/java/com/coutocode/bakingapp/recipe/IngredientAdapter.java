package com.coutocode.bakingapp.recipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.coutocode.bakingapp.R;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private final List<Ingredient> mValues;

    IngredientAdapter(List<Ingredient> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false);
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

        @BindView(R.id.tvIngredient)
        TextView tvIngredient;
        @BindView(R.id.tvQuantity)
        TextView tvQuantity;
        @BindView(R.id.tvMeasure)
        TextView tvMeasure;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void onBind(Ingredient ingredient){
            tvIngredient.setText(ingredient.ingredient);
            tvQuantity.setText(String.valueOf(ingredient.quantity));
            tvMeasure.setText(String.valueOf(ingredient.measure));
        }
    }
}