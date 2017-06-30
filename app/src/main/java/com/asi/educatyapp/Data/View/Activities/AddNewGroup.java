package com.asi.educatyapp.Data.View.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddNewGroup extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 2;
    EditText Gname;
    ImageView Gpic;
    private String base64_encoded;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Uri downloadPhoto;
    private String name, email, key, password, school, groupname;

    // Firebase Variables
    DatabaseReference groupsDatabaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference groupPhotoReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);
        Gname = (EditText) findViewById(R.id.etGroupName);
        Gpic = (ImageView) findViewById(R.id.ivGroup);
        groupname = Gname.getText().toString();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
//        user = firebaseAuth.getCurrentUser();

        
        groupsDatabaseReference = firebaseDatabase.getReference("Groups");
        groupPhotoReference = firebaseStorage.getReference().child("group_photo");

        Gpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        Button addGroup = (Button) findViewById(R.id.add_group);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(groupname)){
                            Gname.setError("choose another groupname");
                            Toast.makeText(AddNewGroup.this, "choose another groupname", Toast.LENGTH_SHORT).show();
                        }
                        else {

//                    key = user.getUid();
                            int i=1;

                            key=String.valueOf(i);
                            i++;

                            if (downloadPhoto == null) {
                                downloadPhoto = Uri.parse("https://firebasestorage.googleapis.com/v0/b/educaty-9304b.appspot.com/o/profile_photo%2F31610-NYB3MB.jpg?alt=media&token=92d86e46-d9de-4eec-8f22-9d73f3f297db");
                            }
                            GroupsModel model = new GroupsModel(key,groupname,downloadPhoto.toString());
                            groupsDatabaseReference.child(groupname).setValue(model)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(AddNewGroup.this, "saved group", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(AddNewGroup.this, Home.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddNewGroup.this, "faild create group" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER) {
            final Uri uriImage = data.getData();
            groupPhotoReference = groupPhotoReference.child(uriImage.getLastPathSegment());
            Glide.with(AddNewGroup.this)
                    .load(uriImage)
                    .into(Gpic);

            groupPhotoReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddNewGroup.this, "sucess uploading", Toast.LENGTH_SHORT).show();
                    downloadPhoto = taskSnapshot.getDownloadUrl();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddNewGroup.this, "failed to upload", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(AddNewGroup.this, "error photo picker", Toast.LENGTH_SHORT).show();
        }


    }
}
