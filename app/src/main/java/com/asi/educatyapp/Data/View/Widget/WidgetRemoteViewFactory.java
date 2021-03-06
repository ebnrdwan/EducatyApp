package com.asi.educatyapp.Data.View.Widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.Data.View.Activities.TheGroup;
import com.asi.educatyapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class WidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    ArrayList<GroupsModel> groupsModels = new ArrayList<>();
    Context context;
    int appwidgetId;


    public WidgetRemoteViewFactory(Context context, int appwidgetId) {
        this.context = context;
        this.appwidgetId = appwidgetId;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        groupsModels = getmodels();
    }

    @Override
    public void onDestroy() {
        groupsModels.clear();
    }

    @Override
    public int getCount() {
        return groupsModels.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.group_item_widget);

        final GroupsModel model = groupsModels.get(position);
        remoteViews.setTextViewText(R.id.tvGname, model.getName());


        Intent theGroupIntent = new Intent(context, TheGroup.class);
        PendingIntent theGroupPendingIntent = PendingIntent.getActivity(context, 0, theGroupIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.wholeItemWidget,theGroupPendingIntent);


        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Picasso.with(context)
                        .load(model.getPath())
                        .into(remoteViews, R.id.ivGroupitem, new int[]{appwidgetId});

            }
        });


        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {

        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public ArrayList<GroupsModel> getmodels() {
//        groupsModels.clear();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.groupsObject);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GroupsModel model = dataSnapshot.getValue(GroupsModel.class);
                groupsModels.add(model);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return groupsModels;
    }
}
