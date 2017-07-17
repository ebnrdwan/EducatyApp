package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.Data.Utility.Constants;
import com.asi.educatyapp.Data.Utility.SharedPreferencesUtils;
import com.asi.educatyapp.Data.View.Fragments.GroupsF;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Groups extends AppCompatActivity {

    ArrayList<GroupsModel> grouplist;
    FirebaseAuth.AuthStateListener fAuthStateListener;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ImageView imageUser;
    TextView username;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        firebaseAuth = FirebaseAuth.getInstance();

        imageUser = (ImageView) findViewById(R.id.groupsImage);
        username = (TextView) findViewById(R.id.name);


        fab = (FloatingActionButton) findViewById(R.id.fab);

        if (SharedPreferencesUtils.getTypeOfCurrentUser(Groups.this).equals(Constants.T_STUDENT)) {
            fab.setVisibility(View.GONE);
        }
        GroupsF groupsF = new GroupsF();
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        manager.add(R.id.groufrag, groupsF);
        manager.commit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                fAuthStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        user = firebaseAuth.getCurrentUser();
                        if (user != null) {


                            Uri uri = user.getPhotoUrl();

                            username.setText(user.getDisplayName());

                            if (uri == null) {
                                uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/educaty-9304b.appspot.com/o/Profile_photo%2Fstudentsample.jpg?alt=media&token=2a970b70-1b7f-4b27-b4b7-9805cc8f348e");
                            }

                            Glide
                                    .with(Groups.this)
                                    .load(uri)
                                    .error(R.drawable.mypic22)
                                    .centerCrop()
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imageUser);

                        } else {
                            Toast.makeText(Groups.this, "you are not logined ", Toast.LENGTH_SHORT).show();

                        }
                    }

                };
                firebaseAuth.addAuthStateListener(fAuthStateListener);

            }
        }, 10);
        View view = findViewById(R.id.backtoolbar);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Groups.this, ProfileActivity.class));
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Groups.this, AddNewGroup.class));
            }
        });

    }
}
