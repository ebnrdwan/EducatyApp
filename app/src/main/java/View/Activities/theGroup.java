package com.asi.educatyapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import Adapters.MyPagerAdapter;
import fragment.HomeF;
import fragment.ProgressF;
import fragment.Skills;
import fragment.classRoomStudents;

public class theGroup extends AppCompatActivity {

    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;
    MyPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_group);
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);


        //// TODO: [in the previous line] --->  i can't set method of DisplayAsUpEnabled()  to make my activity navigate back to the main, please tell me the reason
        tabLayout = (TabLayout) findViewById(R.id.mytablayout);
        viewPager = (ViewPager) findViewById(R.id.myviewpager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new HomeF(), "Home ");
        pagerAdapter.addFragments(new classRoomStudents(), "Students");
        pagerAdapter.addFragments(new Skills(), "Quizzes");
        pagerAdapter.addFragments(new ProgressF(), "Progress");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
