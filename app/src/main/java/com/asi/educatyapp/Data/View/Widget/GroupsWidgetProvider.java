package com.asi.educatyapp.Data.View.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.asi.educatyapp.Data.View.Activities.Groups;
import com.asi.educatyapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class GroupsWidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.groups_widget);
        Intent intent = new Intent(context, Groups.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.image_viewwidget, pendingIntent);
        // Instruct the widget manager to update the widget
        Intent intent1 = new Intent(context,WidgetRemoteViewFactory.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context,0,intent1,0);
        views.setOnClickPendingIntent(R.id.sync_widget,pendingIntent1);
        views.setRemoteAdapter(R.id.widgetGrid,intent1);
        views = getGardenGridRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
        Intent GroupServiceIntent = new Intent(context, GroupsService.class);
        GroupServiceIntent.setAction(GroupsService.ACTION_GROUPS);
        PendingIntent servicePendingIntent = PendingIntent.getService(
                context,
                0,
                GroupServiceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setRemoteAdapter(R.id.sync_widget, GroupServiceIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            views.setRemoteAdapter(R.id.widgetGrid,
                    new Intent(context, RemoteWidgetServiceAdapter.class).putExtra("id",appWidgetId));
        } else {
            views.setRemoteAdapter(0, R.id.widgetGrid,
                    new Intent(context, RemoteWidgetServiceAdapter.class).putExtra("id",appWidgetId));
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Intent serIntent = new Intent(context,RemoteWidgetServiceAdapter.class);
        context.startService(serIntent);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getGardenGridRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.groups_widget);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, WidgetRemoteViewFactory.class);
        views.setRemoteAdapter(R.id.widgetGrid, intent);
        // Set the PlantDetailActivity intent to launch when clicked
        Intent appIntent = new Intent(context, Groups.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widgetGrid, appPendingIntent);
        // Handle empty gardens
//        views.setEmptyView(R.id.widgetGrid, R.id.empty_view);
        return views;
    }


}

