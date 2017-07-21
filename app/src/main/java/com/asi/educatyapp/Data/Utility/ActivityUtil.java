package com.asi.educatyapp.Data.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Abdulrhman on 08/06/2017.
 */

public class ActivityUtil {
    public static void addFragmentToActivity (@Nullable FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


    public static void pickImage(Activity a, int RC_PHOTO_PICKER){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        a.startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    public static void  widgetUpdate(final Context context){

        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedLayout= inflater.inflate(R.layout.groups_widget, null, false);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.groupsObject);
        FirebaseListAdapter adapter = new FirebaseListAdapter<GroupsModel>((Activity) context,GroupsModel.class, R.layout.groupsitem,databaseReference) {


            @Override
            protected void populateView(View v, GroupsModel model, int position) {
                ImageView imageView= (ImageView) v.findViewById(R.id.ivGroupitem);
                Glide.with(context).load(model.getPath()).error(R.drawable.back).into(imageView);
                TextView textView = (TextView) v.findViewById(R.id.tvGname);
                textView.setText(model.getName());
            }
        };
        GridView gridView = (GridView) inflatedLayout.findViewById(R.id.widgetGrid);
        gridView.setAdapter(adapter);
    }

}
