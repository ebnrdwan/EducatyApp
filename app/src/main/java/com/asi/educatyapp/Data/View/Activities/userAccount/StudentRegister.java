package com.asi.educatyapp.Data.View.Activities.userAccount;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.asi.educatyapp.Data.Data.Models.StudentModel;
import com.asi.educatyapp.Data.Utility.Constants;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.Data.Utility.SharedPreferencesUtils;
import com.asi.educatyapp.Data.View.Activities.Home;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class StudentRegister extends AppCompatActivity {

    private final String TAG = "createSTAG";
    private static final int RC_PHOTO_PICKER = 2;

    // Edit Text Variables
    EditText fname, schoo_name, usernameEditText, studentcode, pass, emailTextview;
    ImageView profilePic;
    Button regist;

    // Student Model Parameter
    private String name, email, key, password, school, username;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Uri downloadPhoto;
    Uri imageUri;
    boolean isRegistered;
    FirebaseAuth.AuthStateListener fAuthStateListener;


    // Firebase Variables
    DatabaseReference studentDatabaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference profilePhotoReference;
    UserProfileChangeRequest profileChangeRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        //firebase Instances
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseStorage = FirebaseStorage.getInstance();

        // Firebase Reference
        studentDatabaseReference = firebaseDatabase.getReference(FirebaseUtil.studentObject);
        profilePhotoReference = firebaseStorage.getReference().child(FirebaseUtil.profilePhoto);


        emailTextview = (EditText) findViewById(R.id.semail);
        fname = (EditText) findViewById(R.id.fname);
        schoo_name = (EditText) findViewById(R.id.school_name);
        pass = (EditText) findViewById(R.id.spassword);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        usernameEditText = (EditText) findViewById(R.id.Susername);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(getString(R.string.image_type));
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.comp_action)), RC_PHOTO_PICKER);
            }
        });


        regist = (Button) findViewById(R.id.Reg_student);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = fname.getText().toString();
                email = emailTextview.getText().toString().trim();
                password = pass.getText().toString().trim();
                username = usernameEditText.getText().toString().trim();

                school = schoo_name.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(username)) {
                    Toast.makeText(StudentRegister.this, R.string.must_filled, Toast.LENGTH_SHORT).show();
                } else {
                    createAccount(email, password);


                    //// TODO: 30/06/2017 handle session
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            fAuthStateListener = new FirebaseAuth.AuthStateListener() {
                                @Override
                                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    //here logic if user is logined
                                    if (user != null) {

                                        user = firebaseAuth.getCurrentUser();


                                        // TODO: 30/06/2017 upload image
                                        if (imageUri != null) {
                                            profilePhotoReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Toast.makeText(StudentRegister.this, R.string.suc_uploa, Toast.LENGTH_SHORT).show();
                                                    downloadPhoto = taskSnapshot.getDownloadUrl();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                        }


                                        //// TODO: 01/07/2017 handle user account
                                        if (downloadPhoto == null) {
                                            downloadPhoto = Uri.parse(FirebaseUtil.fakeImageProfile);
                                        }
                                        FirebaseUtil.SetStudentsMap(name, username);
                                        profileChangeRequest = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .setPhotoUri(downloadPhoto).build();
                                        user.updateProfile(profileChangeRequest);
                                        user.sendEmailVerification();


                                        final FirebaseUser finalUser = user;
                                        key = finalUser.getUid();
                                        studentDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.hasChild(key)) {
//
                                                    Toast.makeText(StudentRegister.this, R.string.already_here, Toast.LENGTH_SHORT).show();
                                                } else {

                                                    if (isRegistered) {
                                                        //todo save Student info to database
                                                        studentDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.hasChild(key)) {

                                                                    Toast.makeText(StudentRegister.this, R.string.not_need_new, Toast.LENGTH_SHORT).show();
                                                                } else {


                                                                    StudentModel model = new StudentModel(key, email, password, name, school, username, downloadPhoto.toString());
                                                                    studentDatabaseReference.child(key).setValue(model)
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    Toast.makeText(StudentRegister.this, R.string.saved_s, Toast.LENGTH_SHORT).show();
                                                                                    SharedPreferencesUtils.setCurrentStudent(StudentRegister.this, key);
                                                                                    SharedPreferencesUtils.setTypeOfCurrentUser(StudentRegister.this, Constants.T_STUDENT);
                                                                                    startActivity(new Intent(StudentRegister.this, Home.class));
                                                                                }
                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(StudentRegister.this, getString(R.string.faild_s) + e, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                    } else {
                                                        Toast.makeText(StudentRegister.this, R.string.invalid_s, Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });


                                    } else {
                                        Toast.makeText(StudentRegister.this, R.string.not, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            firebaseAuth.addAuthStateListener(fAuthStateListener);
                        }
                    }, 3);
                }
            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(StudentRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(StudentRegister.this, R.string.check, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, getString(R.string.suc));
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            isRegistered = true;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, getString(R.string.fail), task.getException());
                            Toast.makeText(StudentRegister.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                            isRegistered = false;
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER) {
            imageUri = data.getData();
            profilePhotoReference = profilePhotoReference.child(imageUri.getLastPathSegment());
            Glide.with(StudentRegister.this)
                    .load(imageUri)
                    .into(profilePic);
        } else {
            Toast.makeText(StudentRegister.this, R.string.er_pick, Toast.LENGTH_SHORT).show();
        }

    }

}
