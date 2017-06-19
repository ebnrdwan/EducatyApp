package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.asi.educatyapp.Data.View.Activities.RegisterDir.StudentRegister;
import com.asi.educatyapp.Data.View.Activities.RegisterDir.TeacherRegisterTwo;
import com.asi.educatyapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondScreen extends AppCompatActivity {
    LinearLayout teacherReg;
    LinearLayout adminReg;
    LinearLayout parentReg;
    LinearLayout studentReg;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    String email, name, number, keyy;
    public static String emailEx = "em";
    public static String nameEx = "nam";
    public static String numberEx = "num";
    public static String userKey = "keyy";
    public static String bundleKey = "bund";

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second_screen);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        email = currentUser.getEmail().toString();
        name = currentUser.getDisplayName();
        number = currentUser.getPhoneNumber();
        keyy = currentUser.getUid();

        teacherReg = (LinearLayout) findViewById(R.id.teacherRegid);
        teacherReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SecondScreen.this, TeacherRegisterTwo.class);
                Bundle bundle = new Bundle();
                bundle.putString(nameEx, name);
                bundle.putString(numberEx, number);
                bundle.putString(nameEx, name);
                bundle.putString(userKey, keyy);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        studentReg = (LinearLayout) findViewById(R.id.studentRegid);
        studentReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondScreen.this, StudentRegister.class);
                Bundle bundle = new Bundle();
                bundle.putString(nameEx, name);
                bundle.putString(numberEx, number);
                bundle.putString(nameEx, name);

                intent.putExtra(bundleKey, bundle);
                startActivity(intent);
            }
        });


    }


}
