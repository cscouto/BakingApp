package com.coutocode.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.coutocode.bakingapp.R;
import com.coutocode.bakingapp.recipe.RecipeListActivity;
import com.coutocode.bakingapp.util.Constants;

public class WidgetProvider extends AppWidgetProvider {

    public void onReceive(Context context, Intent intent) {

        AppWidgetManager mgr = AppWidgetManager.getInstance(context);

        int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
        mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listIngredients);

        super.onReceive(context, intent);

    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; ++i) {
            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
            rv.setRemoteAdapter(appWidgetIds[i], R.id.listIngredients, intent);

            Intent startActivityIntent = new Intent(context, RecipeListActivity.class);
            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(
                    context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.listIngredients, startActivityPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

}
