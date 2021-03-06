package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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


import com.asi.educatyapp.Data.Utility.Constants;
import com.asi.educatyapp.Data.Utility.CustomTypefaceSpan;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.Data.Utility.SharedPreferencesUtils;
import com.asi.educatyapp.Data.View.Activities.userAccount.LoginEdu;
import com.asi.educatyapp.Data.View.Fragments.GroupsF;
import com.asi.educatyapp.Data.View.Fragments.HomeF;
import com.asi.educatyapp.Data.View.Fragments.TimeLine;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    NavigationView navigationView;
    String type = "";
    private Menu m;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ImageView ImageProfile;
    FirebaseAuth.AuthStateListener fAuthStateListener;
    ;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();


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
        ImageProfile = (ImageView) header.findViewById(R.id.imageProfile);
        TextView name = (TextView) header.findViewById(R.id.nameProfile);
        TextView email = (TextView) header.findViewById(R.id.emailtv);

        ImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, ProfileActivity.class));
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                fAuthStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        user = firebaseAuth.getCurrentUser();
                        if (user != null) {


                            if (SharedPreferencesUtils.getTypeOfCurrentUser(Home.this).equals(Constants.T_STUDENT)) {
                                Toast.makeText(Home.this, R.string.printSavedStudent, Toast.LENGTH_SHORT).show();
                                SharedPreferencesUtils.setCurrentStudent(Home.this, user.getUid());
                            } else {
                                SharedPreferencesUtils.setCurrentTeacher(Home.this, user.getUid());
                                Toast.makeText(Home.this, R.string.printSavedTeacher, Toast.LENGTH_SHORT).show();
                            }
                            Uri uri = user.getPhotoUrl();


                            if (uri == null) {
                                uri = Uri.parse(FirebaseUtil.fakeImageProfile);
                            }

                            Glide.with(getApplicationContext())
                                    .load(uri)
                                    .error(R.drawable.mypic22)
                                    .centerCrop()
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ImageProfile);

                        } else {
                            Toast.makeText(Home.this, R.string.printNotLogined, Toast.LENGTH_SHORT).show();

                        }
                    }

                };
                firebaseAuth.addAuthStateListener(fAuthStateListener);

            }
        }, 100);


        if (savedInstanceState == null) {
            Fragment homeFragment = new HomeF();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contaner, homeFragment, null);
            fragmentTransaction.commit();
            toolbar.setTitle(R.string.home_bar);
        }


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
            applyFontToMenuItem(mi);
        }


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

        if (id == R.id.nav_main) {
            Fragment homeFragment = new HomeF();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contaner, homeFragment, null);
            fragmentTransaction.commit();
            toolbar.setTitle("Home");
        } else if (id == R.id.nav_friends) {

            startActivity(new Intent(Home.this, ProfileActivity.class));

        } else if (id == R.id.nav_groups) {
            Fragment homeFragment = new GroupsF();

            startActivity(new Intent(Home.this, Groups.class));
        } else if (id == R.id.nav_progress) {
            Fragment homeFragment = new TimeLine();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contaner, homeFragment, null);
            fragmentTransaction.commit();
            toolbar.setTitle("Time Line");

        } else if (id == R.id.nav_library) {
            // startActivity(new Intent(ChatActivity.this,Healthy.class));
        } else if (id == R.id.nav_timeline) {
            // startActivity(new Intent(ChatActivity.this,foodAndDrinks.class));
        } else if (id == R.id.nav_setting) {
            // startActivity(new Intent(ChatActivity.this,Offers.class));
        } else if (id == R.id.nav_login) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            SharedPreferencesUtils.removeCurrentGroupKey(Home.this);
                            SharedPreferencesUtils.removeCurrentStudent(Home.this);
                            SharedPreferencesUtils.removeCurrentTeacher(Home.this);
                            SharedPreferencesUtils.removeTypeOfCurrentUser(Home.this);
                            startActivity(new Intent(Home.this, LoginEdu.class));

                        }
                    });

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
        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.boldfont));
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
}