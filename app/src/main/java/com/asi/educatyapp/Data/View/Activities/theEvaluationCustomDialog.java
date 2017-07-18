package com.asi.educatyapp.Data.View.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.asi.educatyapp.Data.View.Fragments.GroupsF;
import com.asi.educatyapp.R;

import com.asi.educatyapp.Data.View.Adapters.MyPagerAdapter;

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

        pagerAdapter.addFragments(new GroupsF(), "Positive behavior ");
        pagerAdapter.addFragments(new GroupsF(), "Grades");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
