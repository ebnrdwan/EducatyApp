package com.asi.educatyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import helper.SessionManager;

public class firstscreen extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstscreen);
        session =new SessionManager(firstscreen.this);
        if (session.isLoggedIn()) {


            startActivity(new Intent(firstscreen.this,Home.class));
            finish();

        }

        if (session.isLoggedInAdmin())
        {
            startActivity(new Intent(firstscreen.this,Admin_Activity.class));
            finish();

        }
    }

    public void goToLogin(View view) {
//        //Creating the instance of PopupMenu
//        PopupMenu popup = new PopupMenu(firstscreen.this,view);
//        //Inflating the Popup using xml file
//        popup.getMenuInflater().inflate(R.menu.choosetype, popup.getMenu());
//
//        MenuPopupHelper menuHelper = new MenuPopupHelper(firstscreen.this, (MenuBuilder) popup.getMenu(), view);
//        menuHelper.setForceShowIcon(true);
//
//        //registering popup with OnMenuItemClickListener
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            public boolean onMenuItemClick(MenuItem item) {
//
//                if(item.getItemId()==R.id.Teacher)
//                {
//                    startActivity(new Intent(firstscreen.this,LoginActivity.class).putExtra("type","teacher"));
//                    finish();
//                }else if(item.getItemId()==R.id.Parent)
//                {
//                    startActivity(new Intent(firstscreen.this,ParentAndStudentLogin.class).putExtra("type","parent"));
//                    finish();
//
//                }else if(item.getItemId()==R.id.Student)
//                {
//                    startActivity(new Intent(firstscreen.this,ParentAndStudentLogin.class).putExtra("type","student"));
//                    finish();
//                }
//                else if(item.getItemId()==R.id.Admin)
//                {
//                    startActivity(new Intent(firstscreen.this,LoginActivity.class).putExtra("type","admin"));
//                    finish();
//                }
//                return true;
//            }
//        });
//
//        menuHelper.show();//showing popup menu

        startActivity(new Intent(firstscreen.this,SecondScreen.class).putExtra("type","login"));
    }

    public void goToSinup(View view) {

//        //Creating the instance of PopupMenu
//        PopupMenu popup = new PopupMenu(firstscreen.this,view);
//        //Inflating the Popup using xml file
//        popup.getMenuInflater().inflate(R.menu.choosetype, popup.getMenu());
//
//        MenuPopupHelper menuHelper = new MenuPopupHelper(firstscreen.this, (MenuBuilder) popup.getMenu(), view);
//        menuHelper.setForceShowIcon(true);
//
//        //registering popup with OnMenuItemClickListener
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            public boolean onMenuItemClick(MenuItem item) {
//
//                if(item.getItemId()==R.id.Teacher)
//                {
//                    startActivity(new Intent(firstscreen.this,TeacherRegisterTwo.class));
//                }else if(item.getItemId()==R.id.Parent)
//                {
//                    startActivity(new Intent(firstscreen.this,ParentRegister.class));
//
//                }else if(item.getItemId()==R.id.Student)
//                {
//                    startActivity(new Intent(firstscreen.this,StudentRegister.class));
//                }
//                else if(item.getItemId()==R.id.Admin)
//                {
//                    startActivity(new Intent(firstscreen.this,AdminRegisterTwo.class));
//                }
//                return true;
//            }
//        });
//
//        menuHelper.show();//showing popup menu

        startActivity(new Intent(firstscreen.this,SecondScreen.class).putExtra("type","signup"));
    }
}
