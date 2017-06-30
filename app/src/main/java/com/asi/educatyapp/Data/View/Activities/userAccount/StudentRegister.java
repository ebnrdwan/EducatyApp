package com.asi.educatyapp.Data.View.Activities.userAccount;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

    private static final String TAG = "createSTAG";
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

    // Firebase Variables
    DatabaseReference studentDatabaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference profilePhotoReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        //firebase Instances
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseStorage = FirebaseStorage.getInstance();

        // Firebase Reference
        studentDatabaseReference = firebaseDatabase.getReference("students");
        profilePhotoReference = firebaseStorage.getReference().child("Profile_photo");


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
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
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
                if (downloadPhoto == null) {
                    downloadPhoto = Uri.parse("https://firebasestorage.googleapis.com/v0/b/educaty-9304b.appspot.com/o/profile_photo%2F31610-NYB3MB.jpg?alt=media&token=92d86e46-d9de-4eec-8f22-9d73f3f297db");
                }
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(username)) {
                    Toast.makeText(StudentRegister.this, "all filds must be filled ", Toast.LENGTH_SHORT).show();
                } else {
                    createAccount(email, password);

                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .setPhotoUri(downloadPhoto).build();
                    user = firebaseAuth.getCurrentUser();
                    user.updateProfile(profileChangeRequest);
                    user.sendEmailVerification();


                    //todo save student info to database
                    studentDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(username)){
                                usernameEditText.setError("choose another username");
                                Toast.makeText(StudentRegister.this, "choose another username", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                key = user.getUid();
                                StudentModel model = new StudentModel(email,password,name,school,username,downloadPhoto.toString());
                                studentDatabaseReference.child(username).setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(StudentRegister.this, "saved student", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(StudentRegister.this, Home.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(StudentRegister.this, "faild regist student" + e, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) { }
                    });
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
                            Toast.makeText(StudentRegister.this,"check your email for verification ",Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(StudentRegister.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                        }
                    }
                });
        // [END create_user_with_email]
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER ) {
            final Uri uriImage = data.getData();
            profilePhotoReference = profilePhotoReference.child(uriImage.getLastPathSegment());
            Glide.with(StudentRegister.this)
                    .load(uriImage)
                    .into(profilePic);

            profilePhotoReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(StudentRegister.this, "sucess uploading", Toast.LENGTH_SHORT).show();
                    downloadPhoto = taskSnapshot.getDownloadUrl();
                    StudentModel model = new StudentModel(key, name, password, school, key);
//                    studentDatabaseReference.push().setValue(model);

                }
            });
        }
        else {
            Toast.makeText(StudentRegister.this, "error photo picker", Toast.LENGTH_SHORT).show();
        }

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
                            Toast.makeText(StudentRegister.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(StudentRegister.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

}
