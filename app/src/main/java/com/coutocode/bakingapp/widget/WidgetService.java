package com.coutocode.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new RecipeViewsFactory(this.getApplicationContext(),
                intent));
    }
}
