package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.asi.educatyapp.Data.Data.Models.PostModel;
import com.asi.educatyapp.Data.Utility.ActivityUtil;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddHomePosts extends AppCompatActivity implements View.OnClickListener {

    EditText content;
    private String base64_encoded;
    ImageView pic;
    private final int RC_PHOTO = 2;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    StorageReference photoReference;
    FirebaseUser user;
    ChildEventListener childEventListener;
    FirebaseAuth auth;
    Uri localImageUri;
    Uri downloadPhoto;
    PostModel model;
    String date;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_posts);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        photoReference = firebaseStorage.getReference(FirebaseUtil.postsPhoto);
        databaseReference = firebaseDatabase.getReference(FirebaseUtil.postsObject);

        DateFormat df = new SimpleDateFormat(getString(R.string.homePostDate));
        date = df.format(Calendar.getInstance().getTime());

        content = (EditText) findViewById(R.id.etPostContent);
        pic = (ImageView) findViewById(R.id.ivPost);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.pickImage(AddHomePosts.this, RC_PHOTO);
            }
        });
        Button add = (Button) findViewById(R.id.addPostHome);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etPostContent:
                ActivityUtil.pickImage(AddHomePosts.this, RC_PHOTO);

            case R.id.addPostHome:
                addPost();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO) {
            localImageUri = data.getData();
            photoReference = photoReference.child(localImageUri.getLastPathSegment());
            Glide.with(AddHomePosts.this)
                    .load(localImageUri)
                    .into(pic);


        }
    }

    private void addPost() {

        if (localImageUri != null) {
            photoReference.putFile(localImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddHomePosts.this, R.string.successuploaing, Toast.LENGTH_SHORT).show();
                    downloadPhoto = taskSnapshot.getDownloadUrl();
                    if (downloadPhoto == null) {
                        downloadPhoto = Uri.parse(FirebaseUtil.fakeImageProfile);
                    }


                    String PushKey = databaseReference.push().getKey();
                    model = new PostModel(PushKey, user.getDisplayName(), content.getText().toString(), date, user.getPhotoUrl().toString(), downloadPhoto.toString());
                    FirebaseUtil.addingObjectFirebase(user, AddHomePosts.this, databaseReference, model, true, null, PushKey);
                    startActivity(new Intent(AddHomePosts.this, Home.class));
                }
            });
        } else {
            Toast.makeText(AddHomePosts.this, R.string.printChoosePhoto, Toast.LENGTH_SHORT).show();
        }

    }
}
