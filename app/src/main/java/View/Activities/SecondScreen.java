package com.asi.educatyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.asi.educatyapp.RegisterDir.AdminRegisterTwo;
import com.asi.educatyapp.RegisterDir.ParentRegister;
import com.asi.educatyapp.RegisterDir.StudentRegister;
import com.asi.educatyapp.RegisterDir.TeacherRegisterTwo;
import com.asi.educatyapp.loginDir.LoginActivity;
import com.asi.educatyapp.loginDir.ParentAndStudentLogin;

public class SecondScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second_screen);
       //Button SelectAdmin = (Button) findViewById(R.id.select_admin);

    }

    public void MoveToRegister(View view) {
        if (getIntent().getStringExtra("type").equals("login"))
        {
            startActivity(new Intent(SecondScreen.this,LoginActivity.class).putExtra("type","teacher"));
        }else {
            startActivity(new Intent(SecondScreen.this,TeacherRegisterTwo.class));
        }

    }

    public void MoveToRegisterAdmin(View view) {
        if (getIntent().getStringExtra("type").equals("login"))
        {
            startActivity(new Intent(SecondScreen.this,LoginActivity.class).putExtra("type","admin"));
        }else {
            startActivity(new Intent(SecondScreen.this,AdminRegisterTwo.class));
        }

    }

    public void MoveToRegisterStudent(View view) {
        if (getIntent().getStringExtra("type").equals("login"))
        {
            startActivity(new Intent(SecondScreen.this,ParentAndStudentLogin.class).putExtra("type","student"));
        }else {
            startActivity(new Intent(SecondScreen.this,StudentRegister.class));
        }

    }

    public void MoveToParent(View view) {
        if (getIntent().getStringExtra("type").equals("login"))
        {
            startActivity(new Intent(SecondScreen.this,ParentAndStudentLogin.class).putExtra("type","parent"));
        }else {
            startActivity(new Intent(SecondScreen.this,ParentRegister.class));
        }

    }
}
