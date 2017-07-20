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
public ArrayList<GroupsModel> groupList =new ArrayList<>();
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
if (ACTION_GROUPS.equals(action)){
    handleUpdateWidget(getApplicationContext());
}
//



        }
    }

    private ArrayList<GroupsModel> handleUpdateWidget(final Context context){


//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.groupsObject);
//        FirebaseListAdapter adapter = new FirebaseListAdapter<GroupsModel>(GroupsIntentService.this,GroupsModel.class, R.layout.groupsitem,databaseReference) {
//
//
//            @Override
//            protected void populateView(View v, GroupsModel model, int position) {
//             if (groupList.size()<20){
//                 groupList.add(model);
//             }
//            }
//        };

        GroupsModel model = new GroupsModel("id","tid","name","https://firebasestorage.googleapis.com/v0/b/educaty-9304b.appspot.com/o/Profile_photo%2Fstudentsample.jpg?alt=media&token=2a970b70-1b7f-4b27-b4b7-9805cc8f348e");
        groupList.add(model);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, GroupsWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetGrid);
        //Now update all widgets


            GroupsWidgetProvider.updatewholeAppWidget(this,appWidgetManager,appWidgetIds);

        return groupList;
    }
}
