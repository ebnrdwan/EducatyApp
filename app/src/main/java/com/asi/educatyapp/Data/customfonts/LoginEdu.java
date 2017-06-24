package com.asi.educatyapp.Data.customfonts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.asi.educatyapp.Data.View.Activities.SecondScreen;
import com.asi.educatyapp.R;

;

public class LoginEdu extends AppCompatActivity  {
    private static final String TAG = "TAGG";
    MyTextView student, teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myloginedu);

        student = (MyTextView) findViewById(R.id.studentLogin);
        teacher = (MyTextView) findViewById(R.id.teacherLogin);
        TextView signup = (TextView) findViewById(R.id.signuptext);
        SpannableString content = new SpannableString("Sign Up?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        signup.setText(content);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginEdu.this, SecondScreen.class));
            }
        });


        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginEdu.this, teacher.class);
                startActivity(it);

            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginEdu.this, student.class);
                startActivity(it);


            }
        });



    }




    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();


    }


}

