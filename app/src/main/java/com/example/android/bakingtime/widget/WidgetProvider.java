package com.example.android.bakingtime.widgets;

import android.app.Application;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingtime.R;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //update each widget that we have
            Log.v("VVV", "update for widget requested");
            //Populate the widget
            Intent intent = new Intent(context, WidgetJobService.class);
            intent.setAction(WidgetJobService.ACTION_UPDATE_WIDGET);
            //This will start a service which will fetch the data and then populate the widget
            JobIntentService.enqueueWork(context, WidgetJobService.class,
                    WidgetJobService.JOB_ID, intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    static void populateWidget(Context context, int appWidgetId, String ingredients){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        //make an intent which will start updating.
        Intent intent = new Intent(context, WidgetProvider.class);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(
                new ComponentName(context,AppWidgetProvider.class)
        );
        Bundle bundleIds = new Bundle();

        bundleIds.putIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        intent.putExtras(bundleIds);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        //wrap it in a pending intent.
        PendingIntent pintent = PendingIntent.getBroadcast(context,0,intent,0);
        //set pending intent to be used when button is clicked.
        views.setOnClickPendingIntent(R.id.next_recipe,pintent);
        //set the ingredients text.
        views.setTextViewText(R.id.widget_ingredients_tv,ingredients);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);

        manager.updateAppWidget(appWidgetId,
                views);

    }

}
