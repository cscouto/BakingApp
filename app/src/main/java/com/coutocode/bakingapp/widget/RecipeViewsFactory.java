package com.coutocode.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.coutocode.bakingapp.R;
import com.coutocode.bakingapp.api.RecipeService;
import com.coutocode.bakingapp.recipe.Recipe;
import com.coutocode.bakingapp.recipe.RecipeListActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    List<Recipe> items;
    RecipeService service;

    private Context mContext = null;
    private int appWidgetId;

    public RecipeViewsFactory(Context ctxt, Intent intent) {
        this.mContext = ctxt;
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        service = new RecipeService();
        requestData(service.listRecipes());
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        if (items != null) {
            return (items.size());
        }else{
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(),
                R.layout.row);

        row.setTextViewText(android.R.id.text1, items.get(0).ingredients.get(position).ingredient);

        Intent i = new Intent();
        row.setOnClickFillInIntent(android.R.id.text1, i);

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
        // no-op
    }

    private void requestData(Call<List<Recipe>> call){
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()){
                    items  = response.body();
                }else{
                    Toast.makeText(mContext,
                            response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(mContext,
                        R.string.request_failed, Toast.LENGTH_LONG).show();
            }
        });
    }
}