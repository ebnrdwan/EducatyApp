package com.asi.educatyapp.Data.View.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asi.educatyapp.R;
import com.asi.educatyapp.Data.View.Activities.theEvaluationCustomDialog;

import java.util.ArrayList;


import com.asi.educatyapp.Data.View.Adapters.classroomAdapter;
import com.asi.educatyapp.Data.Data.Models.StudentModel;
import com.asi.educatyapp.Data.Utility.itemclickforRecycler;

/**
 * A simple {@link Fragment} subclass.
 */
public class classRoomStudents extends Fragment  {

    ArrayList<StudentModel>StudentList;
    RecyclerView myrecylcer;
    public classRoomStudents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_class_room_students, container, false);
        StudentList =new ArrayList<>();
        StudentList.add(new StudentModel("Abdulrhman1", R.drawable.my));
        StudentList.add(new StudentModel("Anwar1", R.drawable.mypic));
        StudentList.add(new StudentModel("Abdulrhman2", R.drawable.my));
        StudentList.add(new StudentModel("Anwar2", R.drawable.mypic));
        StudentList.add(new StudentModel("Abdulrhman3", R.drawable.my));
        StudentList.add(new StudentModel("Anwar3", R.drawable.mypic));
        myrecylcer = (RecyclerView) rootView.findViewById(R.id.recyclerStudentClassroom);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        Log.d("Recycler Teacher",myrecylcer.toString());
        myrecylcer.setLayoutManager(layoutManager);
        itemclickforRecycler.addTo(myrecylcer).setOnItemClickListener(new itemclickforRecycler.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                startActivity(new Intent(getActivity(), theEvaluationCustomDialog.class));
            }
        });



       classroomAdapter adapter = new classroomAdapter(getContext(),StudentList);
        myrecylcer.setAdapter(adapter);
        myrecylcer.setHasFixedSize(true);




        return rootView;
    }


}
