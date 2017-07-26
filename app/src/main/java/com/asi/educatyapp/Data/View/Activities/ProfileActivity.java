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

import com.asi.educatyapp.Data.Data.Models.StudentModel;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.Data.View.Fragments.ClassRoomStudents;
import com.asi.educatyapp.Data.View.Fragments.Skills;
import com.asi.educatyapp.Data.View.Fragments.TheGroupStudents;
import com.asi.educatyapp.Data.chat.ChatActivity;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    String myuser;

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        ClassRoomStudents fragment = new ClassRoomStudents();
        trans.replace(R.id.fragment_frame, fragment).commit();
    }

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


        userName = (TextView) findViewById(R.id.name);


        TextView message = (TextView) findViewById(R.id.messageTeacher);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this, ChatActivity.class));
            }
        });


        Intent intent = getIntent();
        myuser = intent.getStringExtra(TheGroupStudents.sTag);
        if (myuser == null) {
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
                                    uri = Uri.parse(FirebaseUtil.fakeImageProfile);
                                }

                                Glide
                                        .with(getApplicationContext())
                                        .load(uri)
                                        .error(R.drawable.mypic22)
                                        .centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(teacherImage);

                            } else {
                                Toast.makeText(ProfileActivity.this, R.string.printNotLogined, Toast.LENGTH_SHORT).show();

                            }
                        }

                    };
                    firebaseAuth.addAuthStateListener(fAuthStateListener);

                }
            }, 10);
        } else {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child(FirebaseUtil.studentObject).child(myuser);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    StudentModel model = dataSnapshot.getValue(StudentModel.class);
                    userName.setText(model.getName().toString());
                    Desc.setText(model.getSchool().toString());
                    Glide.with(ProfileActivity.this).load(Uri.parse(model.getImage())).error(R.drawable.student).into(teacherImage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


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
                trans.replace(R.id.fragment_frame, fragment).commit();


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
                Toast.makeText(ProfileActivity.this, R.string.not_imp_yet, Toast.LENGTH_SHORT);

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