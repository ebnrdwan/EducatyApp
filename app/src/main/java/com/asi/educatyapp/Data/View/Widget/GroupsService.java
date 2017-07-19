package com.asi.educatyapp.Data.View.Widget;

import android.app.Activity;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.Data.Utility.ActivityUtil;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Abdulrhman on 18/07/2017.
 */

public class GroupsService extends IntentService {
    public static final String ACTION_GROUPS = "GROUPS_ACTION";



    public GroupsService() {
        //empty constructor required by the AndroidManifest
        super(GroupsService.class.getName());
    }
    public static void startActionpostsService(Context context) {
        Intent intent = new Intent(context, GroupsService.class);
        intent.setAction(ACTION_GROUPS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
if (ACTION_GROUPS.equals(action))
                ActivityUtil.widgetUpdate(getApplicationContext());



        }
    }

    private void handleUpdateWidget(final Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedLayout= inflater.inflate(R.layout.groups_widget, null, false);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.groupsObject);
        FirebaseListAdapter adapter = new FirebaseListAdapter<GroupsModel>((Activity) context,GroupsModel.class, R.layout.groupsitem,databaseReference) {


            @Override
            protected void populateView(View v, GroupsModel model, int position) {
                ImageView imageView= (ImageView) v.findViewById(R.id.ivGroup);
                Glide.with(context).load(model.getPath()).error(R.drawable.back).into(imageView);
                TextView textView = (TextView) v.findViewById(R.id.tvGname);
                textView.setText(model.getName());
            }
        };

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, GroupsWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetGrid);
        //Now update all widgets
        for (int appWidgetId : appWidgetIds) {
            GroupsWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetId);
        }
    }
}
