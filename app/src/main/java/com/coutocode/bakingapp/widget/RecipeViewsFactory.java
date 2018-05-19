package com.coutocode.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.coutocode.bakingapp.R;
import com.coutocode.bakingapp.api.RecipeService;
import com.coutocode.bakingapp.recipe.Recipe;
import com.coutocode.bakingapp.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RecipeViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Recipe> items;
    private RecipeService service;

    private Context mContext = null;

    public RecipeViewsFactory(Context ctxt, Intent intent) {
        this.mContext = ctxt;
    }

    @Override
    public void onCreate() {
        service = new RecipeService();
    }

    @Override
    public void onDestroy() {
        if (items != null) {
            items.clear();
        }
    }

    @Override
    public int getCount() {
        if (items != null) {
            return (items.get(0).ingredients.size());
        }else{
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(),
                R.layout.row);
        row.setTextViewText(android.R.id.text1, items.get(0).ingredients.get(position).ingredient);

        Bundle extras = new Bundle();
        extras.putInt(Constants.EXTRA_ITEM, position);
        Intent i = new Intent();
        i.putExtra(Constants.EXTRA_WIDGET, extras);
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.row);
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
        requestData(service.listRecipes());
    }

    private void requestData(Call<List<Recipe>> call){
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()){
                    items  = response.body();
                    AppWidgetManager mgr = AppWidgetManager.getInstance(mContext);
                    int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(mContext, WidgetProvider.class));
                    mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listIngredients);
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