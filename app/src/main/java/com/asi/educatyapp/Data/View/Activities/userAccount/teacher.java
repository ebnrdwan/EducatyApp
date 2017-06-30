package com.asi.educatyapp.Data.View.Activities.userAccount;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.educatyapp.Data.customfonts.MyEditText;
import com.asi.educatyapp.Data.customfonts.MyTextView;
import com.asi.educatyapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class teacher extends AppCompatActivity {


    private static final String TAG = "STAG";
    Typeface fonts1;
    CheckBox remember;
    MyEditText password, email;
    String emailS, passwS;
    MyTextView loginTeacher;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myteacherlogin);

        mAuth = FirebaseAuth.getInstance();
        email = (MyEditText) findViewById(R.id.emailInputteacher);
        password = (MyEditText) findViewById(R.id.passwordinputteacher);
        remember = (CheckBox) findViewById(R.id.stremember);
        fonts1 = Typeface.createFromAsset(teacher.this.getAssets(),
                "fonts/Lato-Light.ttf");
        TextView t4 = (TextView) findViewById(R.id.stremember);
        t4.setTypeface(fonts1);

        loginTeacher = (MyTextView) findViewById(R.id.signin1);
        loginTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailS = email.getText().toString();
                passwS = password.getText().toString();

                if (!TextUtils.isEmpty(emailS) && !TextUtils.isEmpty(passwS)) {

                    signIn(emailS, passwS);
                    Toast.makeText(teacher.this, "signed in ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(teacher.this, "fill your email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        MyTextView register = (MyTextView) findViewById(R.id.orrigestteacher);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(teacher.this, TeacherRegister.class));
            }
        });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

//        animationView.playAnimation();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // [START_EXCLUDE]
                            if (!task.isSuccessful()) {
                                Toast.makeText(teacher.this, "check your email and password", Toast.LENGTH_SHORT).show();
                            }
//                        hideProgressDialog();
//                        animationView.pauseAnimation();

                        }
                    }
                });
    }

}
