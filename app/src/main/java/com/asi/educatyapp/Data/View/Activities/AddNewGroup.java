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

import com.airbnb.lottie.LottieAnimationView;
import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.Data.Utility.SharedPreferencesUtils;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    Uri downloadPhoto, uriImage;
    private String name, email, key, password, school, groupname;

    // Firebase Variables
    DatabaseReference groupsDatabaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference groupPhotoReference;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);
        Gname = (EditText) findViewById(R.id.etGroupName);
        Gpic = (ImageView) findViewById(R.id.ivGroupitem);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        user = firebaseAuth.getCurrentUser();


        groupsDatabaseReference = firebaseDatabase.getReference(FirebaseUtil.groupsObject);
        groupPhotoReference = firebaseStorage.getReference().child(FirebaseUtil.groupsPhotoStorage);

        Gpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(getString(R.string.imageType));
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.imagePickerString)), RC_PHOTO_PICKER);
            }
        });

        Button addGroup = (Button) findViewById(R.id.add_group);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                if (uriImage != null) {
                    LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
                    animationView.setAnimation("anims/glowloading.json");
                    animationView.loop(true);
                    animationView.playAnimation();
                    groupname = Gname.getText().toString();
                    groupPhotoReference = groupPhotoReference.child(uriImage.getLastPathSegment());
                    Glide.with(AddNewGroup.this)
                            .load(uriImage)
                            .into(Gpic);

                    groupPhotoReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddNewGroup.this, R.string.successuploaing, Toast.LENGTH_SHORT).show();
                            downloadPhoto = taskSnapshot.getDownloadUrl();
                            key = groupsDatabaseReference.push().getKey();
                            String currentTeachherky = SharedPreferencesUtils.getCurrentTeacher(AddNewGroup.this);
                            GroupsModel model = new GroupsModel(key, currentTeachherky, groupname, downloadPhoto.toString());
                            FirebaseUtil.addingObjectFirebase(user1, AddNewGroup.this, groupsDatabaseReference, model, false, groupname, null);

                            startActivity(new Intent(AddNewGroup.this, Groups.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNewGroup.this, R.string.faileduplaod, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Gname.setError("write your content please and load an image");
                    Gpic.setImageResource(R.drawable.error);
                }


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER) {
            uriImage = data.getData();

        } else {
            Toast.makeText(AddNewGroup.this, R.string.errorphotopicker, Toast.LENGTH_SHORT).show();
        }


    }

}
