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

import com.asi.educatyapp.Data.Data.Models.TeacherModel;
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

public class TeacherRegister extends AppCompatActivity {


    private static final String TAG = "createSTAG";
    private static final int RC_PHOTO_PICKER = 2;

    // Edit Text Variables
    EditText fname, tTitle, tField, usernameEditText, pass, emailTextview;
    ImageView profilePic;
    Button regist;

    // Teacher Model Parameters
    private String name, email, key, password, title, field, username;

    //Firebase Parameters
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Uri downloadPhoto;
    FirebaseStorage firebaseStorage;
    StorageReference profilePhotoReference;
    DatabaseReference teachersDatabaseReference;
    FirebaseAuth.AuthStateListener fAuthStateListener;
    UserProfileChangeRequest profileChangeRequest;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_register_two);
        //firebase Instances
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseStorage = FirebaseStorage.getInstance();

        // Firebase Reference
        teachersDatabaseReference = firebaseDatabase.getReference("teachers");
        profilePhotoReference = firebaseStorage.getReference().child("Profile_photo");


        emailTextview = (EditText) findViewById(R.id.temail);
        fname = (EditText) findViewById(R.id.tfname);
        tTitle = (EditText) findViewById(R.id.ttitile);
        tField = (EditText) findViewById(R.id.tfield);
        pass = (EditText) findViewById(R.id.tpassword);
        profilePic = (ImageView) findViewById(R.id.tprofilePic);
        usernameEditText = (EditText) findViewById(R.id.tusername);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });


        regist = (Button) findViewById(R.id.Reg_teacher);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = fname.getText().toString();
                email = emailTextview.getText().toString().trim();
                password = pass.getText().toString().trim();
                username = usernameEditText.getText().toString().trim();
                title = tTitle.getText().toString();
                field = tField.getText().toString();


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(username)) {
                    Toast.makeText(TeacherRegister.this, "all filds must be filled ", Toast.LENGTH_SHORT).show();

                } else if (isValidEmail(email.toString().trim())) {
                    Toast.makeText(TeacherRegister.this, "email not vaild", Toast.LENGTH_SHORT).show();
                } else {



                    createAccount(email, password);
                    FirebaseUtil.SetTeachersMap(name, username);

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
                                                    Toast.makeText(TeacherRegister.this, "sucess uploading", Toast.LENGTH_SHORT).show();
                                                    downloadPhoto = taskSnapshot.getDownloadUrl();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                        }

                                        if (downloadPhoto == null) {
                                            downloadPhoto = Uri.parse("https://firebasestorage.googleapis.com/v0/b/educaty-9304b.appspot.com/o/Profile_photo%2Fstudentsample.jpg?alt=media&token=2a970b70-1b7f-4b27-b4b7-9805cc8f348e");
                                        }
                                        //// TODO: 01/07/2017 handle user account
                                        profileChangeRequest = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .setPhotoUri(downloadPhoto).build();
                                        user.updateProfile(profileChangeRequest);
                                        user.sendEmailVerification();

                                        //todo save Teacher info to database

                                        final FirebaseUser finalUser = user;
                                        key = finalUser.getUid();

                                        teachersDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.hasChild(key)) {
                                                    usernameEditText.setError("this account already existed ");
                                                    Toast.makeText(TeacherRegister.this, "this account already existed", Toast.LENGTH_SHORT).show();
                                                } else {


                                                    TeacherModel model = new TeacherModel(email, password, name, title, field, username, downloadPhoto.toString());
                                                    teachersDatabaseReference.child(key).setValue(model)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    SharedPreferencesUtils.setTypeOfCurrentUser(TeacherRegister.this, Constants.T_TEACHER);
                                                                    SharedPreferencesUtils.setCurrentTeacher(TeacherRegister.this, key);
                                                                    Toast.makeText(TeacherRegister.this, "saved Teacher", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(TeacherRegister.this, Home.class));
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(TeacherRegister.this, "faild regist Teacher" + e, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });


                                    } else {
                                        Toast.makeText(TeacherRegister.this, "not logined", Toast.LENGTH_SHORT).show();

                                    }


                                }
                            };
                            firebaseAuth.addAuthStateListener(fAuthStateListener);

                        }
                    }, 10);

                }


            }

        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        firebaseAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(TeacherRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(TeacherRegister.this, "check your email for verification ", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(TeacherRegister.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                        }
                    }
                });
        // [END create_user_with_email]
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER) {
            imageUri = data.getData();
            profilePhotoReference = profilePhotoReference.child(imageUri.getLastPathSegment());
            Glide.with(TeacherRegister.this)
                    .load(imageUri)
                    .into(profilePic);

        }

    }

    private void sendEmailVerification(final FirebaseUser user) {

        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button


                        if (task.isSuccessful()) {
                            Toast.makeText(TeacherRegister.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(TeacherRegister.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public final static boolean isValidEmail(CharSequence target) {
        boolean isvaild = false;
       String email= target.toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (TextUtils.isEmpty(email)&& email.matches(emailPattern))
            isvaild=true;

        return isvaild;
//        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}








