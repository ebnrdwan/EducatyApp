package com.asi.educatyapp.Data.View.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.Data.Data.Models.StudentModel;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.Data.View.Activities.ProfileActivity;
import com.asi.educatyapp.Data.View.Activities.TheGroup;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheGroupStudents extends Fragment {

    ArrayList<StudentModel> StudentList;
    RecyclerView myrecylcer;
    FirebaseRecyclerAdapter claasStudentAdapterFirebase;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    public static String sTag;

    public TheGroupStudents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_class_room_students, container, false);
        sTag = getActivity().getString(R.string.sTag);
        //inistialize database reference
        firebaseDatabase = firebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child(FirebaseUtil.groupsObject).child(TheGroup.groupName).child(FirebaseUtil.studentObject);

        myrecylcer = (RecyclerView) rootView.findViewById(R.id.recyclerStudentClassroom);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        Log.d(getString(R.string.recycler_teacher_log), myrecylcer.toString());
        myrecylcer.setLayoutManager(layoutManager);


        claasStudentAdapterFirebase = new FirebaseRecyclerAdapter<StudentModel, GroupsHolder>(StudentModel.class, R.layout.student_item_group, GroupsHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(GroupsHolder viewHolder, final StudentModel model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setImage(getActivity(), model.getImage());
                viewHolder.StudentCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra(sTag, model.getKey()));
                    }
                });
            }
        };


        myrecylcer.setAdapter(claasStudentAdapterFirebase);
        myrecylcer.setHasFixedSize(true);

        return rootView;
    }

    public static class GroupsHolder extends RecyclerView.ViewHolder {
        CardView StudentCard;
        TextView nameText;
        ImageView picture;

        public GroupsHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.nameStudentClasroom);
            picture = (ImageView) itemView.findViewById(R.id.imageStudentClassroom);
            StudentCard = (CardView) itemView.findViewById(R.id.student_cardView);
        }

        public void setName(String name) {
            nameText.setText(name);
        }

        public void setImage(Context context, String imagePath) {
            Glide.with(context).load(Uri.parse(imagePath)).error(R.drawable.back).into(picture);
        }
    }


}
