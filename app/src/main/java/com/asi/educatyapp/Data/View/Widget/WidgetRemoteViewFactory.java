package com.asi.educatyapp.Data.View.Widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class WidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    ArrayList<GroupsModel> groupsModels = new ArrayList<>();
    Context context;
    int appwidgetId;


    public WidgetRemoteViewFactory(Context context, int appwidgetId ) {
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
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.group_item_widget);

        GroupsModel model = groupsModels.get(position);
        remoteViews.setTextViewText(R.id.tvGname, model.getName());
//        remoteViews.setImageViewResource(R.id.ivGroupitem,R.drawable.borderblack);

//        AppWidgetTarget appWidgetTarget = new AppWidgetTarget(context, remoteViews, R.id.ivGroupitem, appwidgetId);
//
//        Glide
//                .with( context.getApplicationContext() ) // safer!
//                .load( model.getPath() )
//                .asBitmap()
//                .error(R.drawable.back)
//                .into( appWidgetTarget );
//        Picasso picasso = Picasso.with(context);
//        picasso.load(model.getPath()) //
//                .error(R.drawable.back) //
//                .into(remoteViews, R.id.image, new int[]{appwidgetId});
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
//        GroupsModel model = new GroupsModel("id", "tid", "name", "https://firebasestorage.googleapis.com/v0/b/educaty-9304b.appspot.com/o/Profile_photo%2Fstudentsample.jpg?alt=media&token=2a970b70-1b7f-4b27-b4b7-9805cc8f348e");
//        groupsModels.add(model);
//        groupsModels.add(model);
//        groupsModels.add(model);

        return groupsModels;
    }
}
