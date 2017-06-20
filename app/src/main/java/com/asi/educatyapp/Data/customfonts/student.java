package com.asi.educatyapp.Data.customfonts;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.asi.educatyapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class student extends AppCompatActivity {


    private static final String TAG ="STAG" ;
    Typeface fonts1;
    CheckBox remember;
    MyEditText password, email;
    LottieAnimationView animationView;

FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystudentlogin);

      TextInputLayout  password = (TextInputLayout) findViewById(R.id.studentpassword);
        TextInputLayout    email = (TextInputLayout) findViewById(R.id.studentemail);

        password.getEditText();
        

        remember = (CheckBox)findViewById(R.id.stremember);
//        animationView = (LottieAnimationView) findViewById(R.id.studentanimat);
//        animationView.setAnimation("glowloading.json");
//      animationView.playAnimation();

        fonts1 =  Typeface.createFromAsset(student.this.getAssets(),
                "fonts/Lato-Light.ttf");

        TextView t4 =(TextView)findViewById(R.id.stremember);
        t4.setTypeface(fonts1);


    }
    // [START create_user_with_email]

//    private void createAccount(String email, String password) {
//        Log.d(TAG, "createAccount:" + email);
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//        @Override
//        public void onComplete(@NonNull Task<AuthResult> task) {
//            if (task.isSuccessful()) {
//                // Sign in success, update UI with the signed-in user's information
//                Log.d(TAG, "createUserWithEmail:success");
//                FirebaseUser user = mAuth.getCurrentUser();
//
//            } else {
//                // If sign in fails, display a message to the user.
//                Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                Toast.makeText(student.this, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//
//
//        }
//    });
//    // [END create_user_with_email]
//}
//
//    private void signIn(String email, String password) {
//        Log.d(TAG, "signIn:" + email);
//
//
// animationView.playAnimation();
//
//        // [START sign_in_with_email]
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(student.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        // [START_EXCLUDE]
//                        if (!task.isSuccessful()) {
////                            mStatusTextView.setText(R.string.auth_failed);
//                        }
////                        hideProgressDialog();
////                        animationView.pauseAnimation();
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END sign_in_with_email]
//    }
//
//    private void signOut() {
//        mAuth.signOut();
//
//    }
//
//    private void sendEmailVerification( final FirebaseUser user) {
//        // Disable button
//        // Send verification email
//        // [START send_email_verification]
//
//        user.sendEmailVerification()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // [START_EXCLUDE]
//                        // Re-enable button
//
//
//                        if (task.isSuccessful()) {
//                            Toast.makeText(student.this,
//                                    "Verification email sent to " + user.getEmail(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Log.e(TAG, "sendEmailVerification", task.getException());
//                            Toast.makeText(student.this,
//                                    "Failed to send verification email.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END send_email_verification]
//    }

}
