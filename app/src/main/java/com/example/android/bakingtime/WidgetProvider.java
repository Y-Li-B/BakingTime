package com.example.android.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent = new Intent(context,IngredientsService.class);
        intent.setAction(IngredientsService.ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    static void updateWidgets(Context context,String ingredients){
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget);
        Intent intent = new Intent(context, IngredientsService.class);
        intent.setAction(IngredientsService.ACTION_UPDATE_WIDGET);
        PendingIntent pintent = PendingIntent.getService(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.next_recipe,pintent);
        views.setTextViewText(R.id.widget_ingredients_tv,ingredients);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(new ComponentName(context,WidgetProvider.class),
                views);

    }

}
