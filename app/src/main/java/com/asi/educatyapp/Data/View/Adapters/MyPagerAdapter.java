package com.asi.educatyapp.Data.View.Adapters;

/**
 * Created by Abdulrhman on 20/02/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();

    public void addFragments(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}