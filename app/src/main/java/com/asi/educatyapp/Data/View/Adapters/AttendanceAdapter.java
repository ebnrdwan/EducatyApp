package com.asi.educatyapp.Data.View.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.Data.Data.Models.StudentModel;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AttendanceAdapter extends BaseAdapter {
//// TODO: 05/07/2017 not used for now 
    private static LayoutInflater layoutInflater;
    static FirebaseDatabase firebaseDatabase;
    static DatabaseReference databaseReference;
    static ArrayList<StudentModel> studentModelArrayList;


    public AttendanceAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        studentModelArrayList = new ArrayList<>();
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(FirebaseUtil.studentObject);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                StudentModel model = dataSnapshot.getValue(StudentModel.class);
                studentModelArrayList.add(model);
                studentModelArrayList.size();
                notifyDataSetChanged();


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
        };
        databaseReference.addChildEventListener(childEventListener);

    }

    @Override
    public int getCount() {
        if (studentModelArrayList.size() > 10)
            return studentModelArrayList.size();
        else
            return 4;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;


        if (view == null) {

            view = layoutInflater.inflate(R.layout.simple_grid_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Context context = parent.getContext();
        viewHolder.textView.setText(String.valueOf(studentModelArrayList.size()));
        Glide.with(context).load(Uri.parse(studentModelArrayList.get(position).getImage())).into(viewHolder.imageView);
        viewHolder.imageView.setImageResource(R.drawable.mypic22);
        return view;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
