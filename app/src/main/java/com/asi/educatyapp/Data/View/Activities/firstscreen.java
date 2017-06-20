package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.asi.educatyapp.Data.Data.helper.SessionManager;
import com.asi.educatyapp.Data.View.Activities.loginDir.LoginActivity;
import com.asi.educatyapp.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class firstscreen extends AppCompatActivity {

    private SessionManager session;
    FirebaseAuth firebaseAuth;
    private String mUsername;
    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private static final int RC_SIGN_IN = 1;
    private static final int RC_PHOTO_PICKER = 2;
    FirebaseAuth.AuthStateListener fAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstscreen);
        firebaseAuth = FirebaseAuth.getInstance();


        fAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    final DatabaseReference teacherDatabaseReference = firebaseDatabase.getReference().child("teachers");
//                    final DatabaseReference teacherDatabaseReference = firebaseDatabase.getReference();
                    final String id = user.getUid();

                    Query reference= firebaseDatabase.getReference().equalTo(id);
                    if (!reference.equals(null))
                        Toast.makeText(firstscreen.this,"yaaaaay "+reference.toString(),Toast.LENGTH_SHORT).show();



                    teacherDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(id)) {
                                Toast.makeText(firstscreen.this, "you are rocket", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(firstscreen.this, firstscreen.class));
                                startActivity(new Intent(firstscreen.this, Home.class));


                            } else if (!dataSnapshot.hasChild(id)) {
                                Toast.makeText(firstscreen.this, "you are logined", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(firstscreen.this, firstscreen.class));
                                startActivity(new Intent(firstscreen.this, SecondScreen.class));
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    final DatabaseReference studentDatabaseReference = firebaseDatabase.getReference().child("students");
//                    final DatabaseReference teacherDatabaseReference = firebaseDatabase.getReference();


                    teacherDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(id)) {
                                Toast.makeText(firstscreen.this, "you are rocket", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(firstscreen.this, firstscreen.class));
                                startActivity(new Intent(firstscreen.this, Home.class));


                            } else if (!dataSnapshot.hasChild(id)) {
                                Toast.makeText(firstscreen.this, "you are logined", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(firstscreen.this, firstscreen.class));
                                startActivity(new Intent(firstscreen.this, SecondScreen.class));
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
//                                    .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
//                                                   , new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()
                                            ))
                                    .setLogo(R.drawable.logo)
                                    .setTheme(R.style.LoginTheme)

                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            Toast.makeText(this, "sign in success", Toast.LENGTH_SHORT).show();

        } else if (requestCode == RESULT_CANCELED) {
            Toast.makeText(this, "sign in failed", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(fAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(fAuthStateListener);

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

        startActivity(new Intent(firstscreen.this, LoginActivity.class).putExtra("type", "login"));
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

        startActivity(new Intent(firstscreen.this, SecondScreen.class).putExtra("type", "signup"));
    }

}
