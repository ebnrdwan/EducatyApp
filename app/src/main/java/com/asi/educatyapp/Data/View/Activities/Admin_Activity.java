package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.asi.educatyapp.R;

import com.asi.educatyapp.Data.View.Adapters.MyPagerAdapter;
import com.asi.educatyapp.Data.View.Fragments.AdminGroups;
import com.asi.educatyapp.Data.View.Fragments.Admin_active_1;
import com.asi.educatyapp.Data.View.Fragments.Admin_inactive_2;
import com.asi.educatyapp.Data.View.Fragments.ProgressF;
import com.asi.educatyapp.Data.Data.Local.SQLiteHandler;
import com.asi.educatyapp.Data.Data.helper.SessionManager;

public class Admin_Activity extends AppCompatActivity {
    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;
    MyPagerAdapter pagerAdapter;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
//        getSupportActionBar().setDisplayHomeAsUpEnabled(tru);
        tabLayout = (TabLayout) findViewById(R.id.mytablayout);
        viewPager = (ViewPager) findViewById(R.id.myviewpager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new Admin_active_1(), "Active");
        pagerAdapter.addFragments(new Admin_inactive_2(), "Waiting");
        pagerAdapter.addFragments(new AdminGroups(), "Groups");
        pagerAdapter.addFragments(new ProgressF(), "Progress");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        session =new SessionManager(Admin_Activity.this);
        if (!session.isLoggedInAdmin()) {


            startActivity(new Intent(Admin_Activity.this,firstscreen.class));

        }




    }

    public void AddGroup(View view) {
        startActivity(new Intent(Admin_Activity.this,AddNewGroup.class));

    }

    private void logoutUser() {
        session.setLoginAdmin(false);


        new SQLiteHandler(getApplicationContext()).deleteUsers();
        startActivity(new Intent(Admin_Activity.this,firstscreen.class));

    }

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.menu_logout:
               logoutUser();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
