package com.asi.educatyapp.Data.View.Widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.R;
import com.bumptech.glide.request.target.AppWidgetTarget;

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
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.group_item_widget);

        GroupsModel model = groupsModels.get(position);
        remoteViews.setTextViewText(R.id.tvGname,model.getName());
        AppWidgetTarget  appWidgetTarget = new AppWidgetTarget( context, remoteViews, R.id.ivGroupitem, appwidgetId );

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
        remoteViews.setImageViewResource(R.id.ivGroupitem,R.drawable.back);
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
//        groupsModels.clear();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.groupsObject);
//        FirebaseListAdapter adapter = new FirebaseListAdapter<GroupsModel>((Activity) context, GroupsModel.class, R.layout.groupsitem, databaseReference) {
//
//            @Override
//            protected void populateView(View v, GroupsModel model, int position) {
//               while (groupsModels.size()<=20){
//                   groupsModels.add(model);
//               }
//            }
//        };
        GroupsModel model = new GroupsModel("id","tid","name","https://firebasestorage.googleapis.com/v0/b/educaty-9304b.appspot.com/o/Profile_photo%2Fstudentsample.jpg?alt=media&token=2a970b70-1b7f-4b27-b4b7-9805cc8f348e");
        groupsModels.add(model);
        return groupsModels;
    }
}
