package com.asi.educatyapp.Data.View.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.R;

import java.util.ArrayList;

/**
 * Created by Abdulrhman on 18/07/2017.
 */

public class GroupsIntentService extends IntentService {
    public static final String ACTION_GROUPS = " com.asi.educatyapp.Data.View.Widget.GroupsIntentService.groups_action";
    public ArrayList<GroupsModel> groupList = new ArrayList<>();
    Context context;

    public GroupsIntentService() {
        //empty constructor required by the AndroidManifest
        super(GroupsIntentService.class.getName());
    }

    public static void startActionGroupsService(Context context) {
        Intent intent = new Intent(context, GroupsIntentService.class);
        intent.setAction(ACTION_GROUPS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_GROUPS.equals(action)) {
                handleUpdateWidget();
            }
        }
    }

    private void handleUpdateWidget() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, GroupsWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetList);
        //Now update all widgets
        GroupsWidgetProvider.updatewholeAppWidget(this, appWidgetManager, appWidgetIds);
    }
}
