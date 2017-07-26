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
import com.asi.educatyapp.Data.Utility.itemclickforRecycler;
import com.asi.educatyapp.Data.View.Activities.TheEvaluationCustomDialog;
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
public class ClassRoomStudents extends Fragment {

    ArrayList<StudentModel> StudentList;
    RecyclerView myrecylcer;
    FirebaseRecyclerAdapter claasStudentAdapterFirebase;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;

    public ClassRoomStudents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_class_room_students, container, false);

        //inistialize database reference
        firebaseDatabase = firebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child(FirebaseUtil.studentObject);

        myrecylcer = (RecyclerView) rootView.findViewById(R.id.recyclerStudentClassroom);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        Log.d(getString(R.string.recycler_teacher_log_class), myrecylcer.toString());
        myrecylcer.setLayoutManager(layoutManager);
        itemclickforRecycler.addTo(myrecylcer).setOnItemClickListener(new itemclickforRecycler.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                startActivity(new Intent(getActivity(), TheEvaluationCustomDialog.class));
            }
        });


        claasStudentAdapterFirebase = new FirebaseRecyclerAdapter<StudentModel, GroupsHolder>(StudentModel.class, R.layout.student_item, GroupsHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(GroupsHolder viewHolder, StudentModel model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setImage(getActivity(), model.getImage());
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
