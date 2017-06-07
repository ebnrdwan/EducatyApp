package com.asi.educatyapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import Adapters.ConnenctionAdpter;
import Data.Data.Models.homeModel;
import helper.SQLiteHandler;
import static com.asi.educatyapp.R.id.rvHome;

public class TeacherA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        TextView Desc = (TextView) findViewById(R.id.tvDesc);
        Desc.setText("Hi, i am " + new SQLiteHandler(getApplicationContext()).getUserDetails().get("name") + " you can contact with me ");
        TextView userName = (TextView) findViewById(R.id.tvUserName);
        userName.setText(new SQLiteHandler(getApplicationContext()).getUserDetails().get("name"));
        ImageView imageView = (ImageView) findViewById(R.id.ProfilePic);
        Glide
                .with(TeacherA.this)
                .load(Constants.BASEURL + new SQLiteHandler(getApplicationContext()).getUserDetails().get("path"))
                .centerCrop()
                .placeholder(R.drawable.logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        ChangeBack();
        RecyclerView rvConnenctions = (RecyclerView) findViewById(rvHome);
        rvConnenctions.setLayoutManager(new LinearLayoutManager(TeacherA.this, LinearLayoutManager.VERTICAL, false));
        ArrayList<homeModel> homeModels = new ArrayList<>();
        homeModels.add(new homeModel("ASI"));
        homeModels.add(new homeModel("Ali"));
        homeModels.add(new homeModel("Ali"));
        homeModels.add(new homeModel("Ali"));
        rvConnenctions.setNestedScrollingEnabled(false);

        ConnenctionAdpter homeAdpter = new ConnenctionAdpter(TeacherA.this, homeModels);
        rvConnenctions.setAdapter(homeAdpter);

    }

    public void BackTOMAin(View view) {
        startActivity(new Intent(TeacherA.this, Home.class));
    }

    public void ChangeBack() {
        final ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(this, R.color.backConnection));
        final ColorDrawable transparent = new ColorDrawable(ContextCompat.getColor(this, android.R.color.transparent));
        final LinearLayout ConnectionLayout = (LinearLayout) findViewById(R.id.connectionLyout);
        final LinearLayout Badges = (LinearLayout) findViewById(R.id.Badges);
        final LinearLayout Skills = (LinearLayout) findViewById(R.id.Skills);
        final Animation myAnim = AnimationUtils.loadAnimation(TeacherA.this, R.anim.milkshake);
        ConnectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Badges.setBackground(transparent);
                Skills.setBackground(transparent);
                ConnectionLayout.setBackground(cd);
                ConnectionLayout.setAnimation(myAnim);

            }
        });

        Badges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionLayout.setBackground(transparent);
                Skills.setBackground(transparent);
                Badges.setBackground(cd);
                Badges.setAnimation(myAnim);
            }
        });

        Skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Badges.setBackground(transparent);
                ConnectionLayout.setBackground(transparent);
                Skills.setBackground(cd);
                Skills.setAnimation(myAnim);
            }
        });


    }


}
