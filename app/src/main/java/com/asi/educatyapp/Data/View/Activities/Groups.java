package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.Data.View.Adapters.GroupsAdpter;
import com.asi.educatyapp.Data.View.Fragments.GroupsF;
import com.asi.educatyapp.R;

import java.util.ArrayList;

public class Groups extends AppCompatActivity {

    ArrayList<GroupsModel>grouplist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        GroupsF groupsF = new GroupsF();
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        manager.add(R.id.groufrag,groupsF);
        manager.commit();
        View view = findViewById(R.id.backtoolbar);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Groups.this,TeacherA.class));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Groups.this,AddNewGroup.class));
            }
        });

grouplist = new ArrayList<>();
        grouplist.add(new GroupsModel("name",R.drawable.teacher));
        GroupsAdpter groupsAdpter = new GroupsAdpter(this,grouplist);

    }
}
