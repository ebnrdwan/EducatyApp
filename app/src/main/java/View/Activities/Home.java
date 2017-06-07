package com.asi.educatyapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.educatyapp.loginDir.LoginActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import Utility.CustomTypefaceSpan;
import fragment.*;
import fragment.Groups;
import helper.SQLiteHandler;
import helper.SessionManager;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class Home extends AppCompatActivity {
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    NavigationView navigationView;
    String type="";

    MenuItem item;


    private Menu m;
    private ImageView nofIcon;
    private SessionManager session;
    private SQLiteHandler db;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        session =new SessionManager(Home.this);
        db=new SQLiteHandler(Home.this);
        //TypefaceUtil.overrideFont(MainActivity.this, "SERIF", "fonts/Font-Bold.otf");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = setupDrawerToggle();
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //to show icon with originaal color
        navigationView.setItemIconTintList(null);
        setupDrawerContent(navigationView);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_home, null);
        navigationView.addHeaderView(header);
        ImageView ImageProfile = (ImageView) header.findViewById(R.id.imageProfile);
        TextView name= (TextView) header.findViewById(R.id.nameProfile);
        name.setText(new SQLiteHandler(getApplicationContext()).getUserDetails().get("name"));
        TextView email= (TextView) header.findViewById(R.id.emailtv);
        email.setText(new SQLiteHandler(getApplicationContext()).getUserDetails().get("email"));
        ImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,TeacherA.class));
            }
        });

        Glide
                .with(Home.this)
                .load(Constants.BASEURL+new SQLiteHandler(getApplicationContext()).getUserDetails().get("path"))
                .centerCrop()
                .placeholder(R.drawable.logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ImageProfile);
        if (savedInstanceState == null) {
            Fragment homeFragment = new HomeF();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contaner, homeFragment, null);
            fragmentTransaction.commit();
            toolbar.setTitle("Home");
        }


        floatingActionButton= (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (new SQLiteHandler(getApplicationContext()).getUserDetails().get("type").equals("teacher")) {
                    startActivity(new Intent(Home.this, AddHomePosts.class));
                }else
                {
                    Toast.makeText(getApplicationContext(),"Only Teacher Allowed to Add Posts",Toast.LENGTH_LONG).show();
                }
            }
        });

        /**  to change font for app*/
        m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);

                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }


//        item = m.findItem(R.id.nav_login);
//        if (session.isLoggedIn()) {
//            item.setTitle("Log out");
//            applyFontToMenuItem(item);
//
//
//        } else {
//            item.setTitle("Log in");
//            applyFontToMenuItem(item);
//
//        }



    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    public void selectDrawerItem(MenuItem menuItem) {
        int id = menuItem.getItemId();
       // FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left);

        if (id == R.id.nav_main) {
            Fragment homeFragment = new HomeF();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contaner, homeFragment, null);
            fragmentTransaction.commit();
            toolbar.setTitle("Home");
        }
        else if (id == R.id.nav_friends) {
//            Fragment homeFragment = new TimeLine();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.contaner, homeFragment, null);
//            fragmentTransaction.commit();
//            toolbar.setTitle("Teacher");
            startActivity(new Intent(Home.this,TeacherA.class));

        }

        else if (id == R.id.nav_groups) {
            Fragment homeFragment = new Groups();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contaner, homeFragment, null);
            fragmentTransaction.commit();
            toolbar.setTitle("Teacher");
        }
        else if (id == R.id.nav_progress) {
            Fragment homeFragment = new TimeLine();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contaner, homeFragment, null);
            fragmentTransaction.commit();
            toolbar.setTitle("Time Line");

        }
        else if (id == R.id.nav_library) {
           // startActivity(new Intent(MainActivity.this,Healthy.class));
        }
        else if (id == R.id.nav_timeline) {
           // startActivity(new Intent(MainActivity.this,foodAndDrinks.class));
        }

        else if (id == R.id.nav_setting) {
           // startActivity(new Intent(MainActivity.this,Offers.class));
        } else if (id == R.id.nav_login) {
//            if (!session.isLoggedIn()) {
//                item.setTitle("Log in");
//                applyFontToMenuItem(item);
//               // startActivity(new Intent(MainActivity.this, LoginMahall.class));
//
//            } else {
//                item.setTitle("Log out" +
//                        "");
//                applyFontToMenuItem(item);
//                logoutUser();
//            }
            logoutUser();

        }


        //Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        // Close the navigation drawer
        drawer.closeDrawers();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Font-Bold.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


//    private void logoutUser() {
//        session.setLogin(false);
//        db.deleteUsers();
//        Toast.makeText(MainActivity.this, "تم تسجيل الخروج", Toast.LENGTH_LONG).show();
//        this.finish();
//
//    }


    private void logoutUser() {
        session.setLogin(false);


        db.deleteUsers();
        startActivity(new Intent(Home.this,firstscreen.class));
        finish();

    }
}