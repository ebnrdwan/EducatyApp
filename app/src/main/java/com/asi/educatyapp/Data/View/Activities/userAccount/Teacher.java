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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asi.educatyapp.Data.Utility.Constants;
import com.asi.educatyapp.Data.Utility.SharedPreferencesUtils;
import com.asi.educatyapp.Data.View.Activities.Home;
import com.asi.educatyapp.Data.customfonts.MyEditText;
import com.asi.educatyapp.Data.customfonts.MyTextView;
import com.asi.educatyapp.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Teacher extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{


    private static final String TAG = "STAG";
    Typeface fonts1;
    CheckBox remember;
    MyEditText password, email;
    String emailS, passwS;
    MyTextView loginTeacher;
    GoogleApiClient mGoogleApiClient;
    ImageView googleSign;
    private static final int RC_SIGN_IN = 9001;

    FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myteacherlogin);

        mAuth = FirebaseAuth.getInstance();
        email = (MyEditText) findViewById(R.id.emailInputteacher);
        password = (MyEditText) findViewById(R.id.passwordinputteacher);
        remember = (CheckBox) findViewById(R.id.stremember);
        googleSign = (ImageView) findViewById(R.id.googleiconT);
        googleSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(Teacher.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                            }
                        });
                signIn();
                startActivity(new Intent(Teacher.this, Home.class));
            }
        });
        fonts1 = Typeface.createFromAsset(Teacher.this.getAssets(),
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
                    user = mAuth.getCurrentUser();
                    SharedPreferencesUtils.setTypeOfCurrentUser(Teacher.this, Constants.T_TEACHER);
                    Toast.makeText(Teacher.this, "signed in ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Teacher.this, "fill your email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        MyTextView register = (MyTextView) findViewById(R.id.orrigestteacher);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Teacher.this, TeacherRegister.class));
            }
        });

        //// TODO: 30/06/2017 adding google sing in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("998044084954-k0i8s3vo7dsf5c3cuefdmqrn09bcavcb.apps.googleusercontent.com")
                .requestProfile()
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
                            Toast.makeText(Teacher.this, "Logined success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Teacher.this,Home.class));

                        } else {
                            // [START_EXCLUDE]
                            if (!task.isSuccessful()) {
                                Toast.makeText(Teacher.this, "check your email and password", Toast.LENGTH_SHORT).show();
                            }
//                        hideProgressDialog();
//                        animationView.pauseAnimation();

                        }
                    }
                });



    }

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
//        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Teacher.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
}
