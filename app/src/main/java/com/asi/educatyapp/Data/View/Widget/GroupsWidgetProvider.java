package com.asi.educatyapp.Data.View.Widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.asi.educatyapp.Data.View.Activities.Groups;
import com.asi.educatyapp.Data.View.Activities.TheGroup;
import com.asi.educatyapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class GroupsWidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    public static void updatewholeAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object

        RemoteViews remoteViews = getGridItemsRemoteView(context, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        GroupsIntentService.startActionGroupsService(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getGridItemsRemoteView(Context context, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.groups_widget);
        Intent groupIntent = new Intent(context, Groups.class);
        PendingIntent groupPendingIntent = PendingIntent.getActivity(context, 0, groupIntent, 0);
        views.setOnClickPendingIntent(R.id.image_viewwidget, groupPendingIntent);
        // Instruct the widget manager to update the widget
        Intent syncIntent = new Intent(context, GroupsIntentService.class);
        PendingIntent syncPendingIntent = PendingIntent.getActivity(context, 0, syncIntent, 0);
        views.setOnClickPendingIntent(R.id.sync_widget, syncPendingIntent);

        Intent theGroupIntent = new Intent(context, TheGroup.class);
        PendingIntent theGroupPendingIntent = PendingIntent.getActivity(context, 0, theGroupIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        views.setOnClickPendingIntent(R.id.widgetList,theGroupPendingIntent);
        views.setPendingIntentTemplate(R.id.widgetList, theGroupPendingIntent);
        views.setOnClickFillInIntent(R.id.widgetList, theGroupIntent);


        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, RemoteWidgetServiceAdapter.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.putExtra("id", appWidgetId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            views.setRemoteAdapter(R.id.widgetList,
                    new Intent(context, RemoteWidgetServiceAdapter.class).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId));
        } else {
            views.setRemoteAdapter(0, R.id.widgetList,
                    new Intent(context, RemoteWidgetServiceAdapter.class).putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId));
        }

        // Set the PlantDetailActivity intent to launch when clicked

        Intent appIntent = new Intent(context, Groups.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widgetList, appPendingIntent);
        // Handle empty gardens
//        views.setEmptyView(R.id.widgetGrid, R.id.empty_view);
        return views;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {

    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widgetList,
                new Intent(context, RemoteWidgetServiceAdapter.class));
    }


}

