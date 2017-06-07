package com.asi.educatyapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import Adapters.MyPagerAdapter;
import fragment.Admin_inactive_2;
import fragment.Groups;

public class theEvaluationCustomDialog extends AppCompatActivity {

    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;
    MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_evaluation_custom_dialog);

        tabLayout = (TabLayout) findViewById(R.id.mytablayout);
        viewPager = (ViewPager) findViewById(R.id.myviewpager);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragments(new Groups(), "Positive behavior ");
        pagerAdapter.addFragments(new Admin_inactive_2(), "To work in");
        pagerAdapter.addFragments(new Groups(), "Grades");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
