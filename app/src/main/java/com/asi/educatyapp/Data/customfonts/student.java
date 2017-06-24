package com.asi.educatyapp.Data.customfonts;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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
    FirebaseUser user;
    String emailS,passwS;


//
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystudentlogin);

        mAuth = FirebaseAuth.getInstance();
         email = (MyEditText) findViewById(R.id.emailInputStudent);
         password = (MyEditText) findViewById(R.id.passwordinputstudent);

        remember = (CheckBox) findViewById(R.id.stremember);
//
//

        fonts1 = Typeface.createFromAsset(student.this.getAssets(),
                "fonts/Lato-Light.ttf");

        TextView t4 = (TextView) findViewById(R.id.stremember);
        t4.setTypeface(fonts1);
        user = mAuth.getCurrentUser();

        MyTextView loginStudent = (MyTextView) findViewById(R.id.signin1);
        loginStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        emailS = email.getText().toString();
                        passwS = password.getText().toString();
                        authStateListener = new FirebaseAuth.AuthStateListener() {
                            @Override
                            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                if (!TextUtils.isEmpty(emailS)&&!TextUtils.isEmpty(passwS)){
                                    if (user != null) {

                                        signIn(emailS,passwS);
                                        Toast.makeText(student.this,"signed in ",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        createAccount(emailS,passwS);
                                        Toast.makeText(student.this,"created account, check your mail",Toast.LENGTH_SHORT).show();
//                                sendEmailVerification(user);
                                    }
                                }
                                else {

                                    Toast.makeText(student.this,"fill your email and password",Toast.LENGTH_SHORT).show();
                                }
                            }
                    };
                };





                });





                    }
        });


    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(student.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);


//        animationView.playAnimation();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(student.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
//                            mStatusTextView.setText(R.string.auth_failed);
                        }
//                        hideProgressDialog();
//                        animationView.pauseAnimation();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();

    }

    private void sendEmailVerification(final FirebaseUser user) {
        // Disable button
        // Send verification email
        // [START send_email_verification]

        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button


                        if (task.isSuccessful()) {
                            Toast.makeText(student.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(student.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

}
