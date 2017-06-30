package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asi.educatyapp.Data.Data.Models.HomeModel;
import com.asi.educatyapp.Data.View.Adapters.ConnenctionAdpter;
import com.asi.educatyapp.Data.chat.chatActivity;
import com.asi.educatyapp.R;

import java.util.ArrayList;

import static com.asi.educatyapp.R.id.rvHome;

public class TeacherA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        TextView Desc = (TextView) findViewById(R.id.tvDesc);

        TextView userName = (TextView) findViewById(R.id.name);
//        ImageView imageView = (ImageView) findViewById(R.id.ProfilePic);
//        Glide
//                .with(TeacherA.this)
//                .load(Constants.BASEURL + new SQLiteHandler(getApplicationContext()).getUserDetails().get("path"))
//                .centerCrop()
//                .placeholder(R.drawable.mypic)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imageView);
//        ChangeBack();
        RecyclerView rvConnenctions = (RecyclerView) findViewById(rvHome);
        rvConnenctions.setLayoutManager(new LinearLayoutManager(TeacherA.this, LinearLayoutManager.VERTICAL, false));
        ArrayList<HomeModel> homeModels = new ArrayList<>();
        homeModels.add(new HomeModel("ASI"));
        homeModels.add(new HomeModel("Ali"));
        homeModels.add(new HomeModel("Ali"));
        homeModels.add(new HomeModel("Ali"));
        rvConnenctions.setNestedScrollingEnabled(false);

        ConnenctionAdpter homeAdpter = new ConnenctionAdpter(TeacherA.this, homeModels);
        rvConnenctions.setAdapter(homeAdpter);

        TextView message = (TextView) findViewById(R.id.messageTeacher);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherA.this, chatActivity.class));
            }
        });
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
