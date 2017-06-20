package com.asi.educatyapp.Data.customfonts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

