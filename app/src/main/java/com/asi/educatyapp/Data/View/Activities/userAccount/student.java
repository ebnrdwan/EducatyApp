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

public class student extends AppCompatActivity {

    private static final String TAG = "STAG";
    Typeface fonts1;
    CheckBox remember;
    MyEditText password, email;
    String emailS, passwS;
    MyTextView loginStudent;

    FirebaseAuth mAuth;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystudentlogin);

        mAuth = FirebaseAuth.getInstance();
        email = (MyEditText) findViewById(R.id.emailInputStudent);
        password = (MyEditText) findViewById(R.id.passwordinputstudent);
        remember = (CheckBox) findViewById(R.id.stremember);
        fonts1 = Typeface.createFromAsset(student.this.getAssets(),
                "fonts/Lato-Light.ttf");
        TextView t4 = (TextView) findViewById(R.id.stremember);
        t4.setTypeface(fonts1);

        loginStudent = (MyTextView) findViewById(R.id.signin1);
        loginStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailS = email.getText().toString();
                passwS = password.getText().toString();

                if (!TextUtils.isEmpty(emailS) && !TextUtils.isEmpty(passwS)) {

                    signIn(emailS, passwS);
                    Toast.makeText(student.this, "signed in ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(student.this, "fill your email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        MyTextView register = (MyTextView) findViewById(R.id.orrigest);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student.this, StudentRegister.class));
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

                            if (!task.isSuccessful()) {
                                Toast.makeText(student.this, "check your email and password", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
    }

}
