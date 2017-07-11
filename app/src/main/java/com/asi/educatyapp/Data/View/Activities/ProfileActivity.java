package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.educatyapp.Data.View.Fragments.ClassRoomStudents;
import com.asi.educatyapp.Data.View.Fragments.Skills;
import com.asi.educatyapp.Data.chat.chatActivity;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth.AuthStateListener fAuthStateListener;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ImageView teacherImage;
    TextView Desc;
    TextView userName;
    LinearLayout ConnectionLayout;
    LinearLayout Badges;
    LinearLayout Skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        firebaseAuth = FirebaseAuth.getInstance();
        Desc = (TextView) findViewById(R.id.tvDesc);
        teacherImage = (ImageView) findViewById(R.id.teacherimage);
        ConnectionLayout = (LinearLayout) findViewById(R.id.connectionLyout);
        Badges = (LinearLayout) findViewById(R.id.Badges);
        Skills = (LinearLayout) findViewById(R.id.Skills);


        //set listenrers
        ConnectionLayout.setOnClickListener(this);
        Badges.setOnClickListener(this);
        Skills.setOnClickListener(this);
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        ClassRoomStudents fragment = new ClassRoomStudents();
        trans.add(R.id.fragment_frame, fragment).commit();

        userName = (TextView) findViewById(R.id.name);


        TextView message = (TextView) findViewById(R.id.messageTeacher);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this, chatActivity.class));
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


                            Uri uri = user.getPhotoUrl();

                            userName.setText(user.getDisplayName());

                            if (uri == null) {
                                uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/educaty-9304b.appspot.com/o/Profile_photo%2Fstudentsample.jpg?alt=media&token=2a970b70-1b7f-4b27-b4b7-9805cc8f348e");
                            }

                            Glide
                                    .with(ProfileActivity.this)
                                    .load(uri)
                                    .error(R.drawable.mypic22)
                                    .centerCrop()
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(teacherImage);

                        } else {
                            Toast.makeText(ProfileActivity.this, "you are not logined ", Toast.LENGTH_SHORT).show();

                        }
                    }

                };
                firebaseAuth.addAuthStateListener(fAuthStateListener);

            }
        }, 10);

    }

    public void BackTOMAin(View view) {
        startActivity(new Intent(ProfileActivity.this, Home.class));
    }

    public void ChangeBack() {
        final ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(this, R.color.backConnection));
        final ColorDrawable transparent = new ColorDrawable(ContextCompat.getColor(this, android.R.color.transparent));
        final Animation myAnim = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.milkshake);
        ConnectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Badges.setBackground(transparent);
                Skills.setBackground(transparent);
                ConnectionLayout.setBackground(cd);
                ConnectionLayout.setAnimation(myAnim);
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                ClassRoomStudents fragment = new ClassRoomStudents();
                ClassRoomStudents fragments = new ClassRoomStudents();
                trans.add(R.id.fragment_frame, fragment).commit();


            }
        });

        Badges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionLayout.setBackground(transparent);
                Skills.setBackground(transparent);
                Badges.setBackground(cd);
                Badges.setAnimation(myAnim);
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                com.asi.educatyapp.Data.View.Fragments.Skills skills = new Skills();
                trans.replace(R.id.fragment_frame, skills).commit();

            }
        });

        Skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Badges.setBackground(transparent);
                ConnectionLayout.setBackground(transparent);
                Skills.setBackground(cd);
                Skills.setAnimation(myAnim);
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                com.asi.educatyapp.Data.View.Fragments.Skills skills = new Skills();
                trans.replace(R.id.fragment_frame, skills).commit();

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connectionLyout:
                ChangeBack();
                Animation myAnim = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.milkshake);
                ConnectionLayout.setAnimation(myAnim);
            case R.id.Badges:
                ChangeBack();
                myAnim = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.milkshake);
                ConnectionLayout.setAnimation(myAnim);
            case R.id.Skills:
                ChangeBack();
                myAnim = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.milkshake);
                ConnectionLayout.setAnimation(myAnim);

        }
    }
}