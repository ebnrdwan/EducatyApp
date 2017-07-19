package com.asi.educatyapp.Data.View.Widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Abdulrhman on 18/07/2017.
 */

public class WidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    ArrayList<GroupsModel> groupsModels = new ArrayList<>();
    Context context;
    int appwidgetId;


    public WidgetRemoteViewFactory(Context context, int appwodgetid) {
        this.context = context;
        this.appwidgetId=appwodgetid;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        getmodels();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return groupsModels.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.groupsitem);

        GroupsModel model = groupsModels.get(position);
        remoteViews.setTextViewText(R.id.tvGname,model.getPath());
        AppWidgetTarget  appWidgetTarget = new AppWidgetTarget( context, remoteViews, R.id.ivGroup, appwidgetId );

        Glide
                .with( context.getApplicationContext() ) // safer!
                .load( model.getPath() )
                .asBitmap()
                .error(R.drawable.back)
                .into( appWidgetTarget );

        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public ArrayList<GroupsModel> getmodels() {
        groupsModels.clear();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.groupsObject);
        FirebaseListAdapter adapter = new FirebaseListAdapter<GroupsModel>((Activity) context, GroupsModel.class, R.layout.groupsitem, databaseReference) {

            @Override
            protected void populateView(View v, GroupsModel model, int position) {
               while (groupsModels.size()<=20){
                   groupsModels.add(model);
               }
            }
        };

        return groupsModels;
    }
}
