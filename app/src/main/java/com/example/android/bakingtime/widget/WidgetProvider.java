package com.example.android.bakingtime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.JobIntentService;
import android.widget.RemoteViews;

import com.example.android.bakingtime.R;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //update each widget that we have
        for (int id : appWidgetIds) {
            //Make a new intent to start a service.
            Intent intent = new Intent(context, WidgetJobService.class);
            intent.setAction(WidgetJobService.ACTION_UPDATE_WIDGET);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,id);
            //This will start a service which will fetch the data and complete
            //the update of the widget.
            JobIntentService.enqueueWork(context, WidgetJobService.class,
                    WidgetJobService.JOB_ID, intent);
        }
    }


    static void completeUpdate(Context context, int appWidgetId, String ingredients){
        //The remote views for our widget.
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        //This intent will be wrapped in a pending intent and used to update the
        //widget on click.
        Intent intent = new Intent(context, WidgetProvider.class);
        //We need to put the id in a bundle and set action.
        //See implementation of super class onReceive method for details.
        Bundle bundleIds = new Bundle();
        bundleIds.putIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS,new int[]{appWidgetId});
        intent.putExtras(bundleIds);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        //Wrap it in a pending intent.
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
