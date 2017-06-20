package com.asi.educatyapp.Data.View.Activities.RegisterDir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.asi.educatyapp.Data.Data.Models.TeacherModel;
import com.asi.educatyapp.Data.View.Activities.Home;
import com.asi.educatyapp.Data.View.Activities.SecondScreen;
import com.asi.educatyapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vansuita.pickimage.EPickTypes;
import com.vansuita.pickimage.PickImageDialog;
import com.vansuita.pickimage.PickSetup;

public class TeacherRegisterTwo extends AppCompatActivity  {


    EditText fname,lname,filed,schoool,num,title;
    private ImageView profile;
    private EditText Email,pass;
    private String  name , number , email, key;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference teachersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_register_two);
        profile= (ImageView) findViewById(R.id.ProfilePic);
        filed= (EditText) findViewById(R.id.field);
        schoool= (EditText) findViewById(R.id.schoolname);
        num= (EditText) findViewById(R.id.num);
        title= (EditText) findViewById(R.id.title);
        Button DoneTeacher = (Button) findViewById(R.id.Done_Teacher);
        firebaseDatabase = FirebaseDatabase.getInstance();
        teachersDatabaseReference=  firebaseDatabase.getReference("teachers");
        Intent intent = getIntent();
       Bundle bundle= intent.getExtras();
       name= bundle.getString(SecondScreen.nameEx);
        email= bundle.getString(SecondScreen.emailEx);
//        number= bundle.getString(SecondScreen.numberEx);
        number = num.getText().toString();

        key = bundle.getString(SecondScreen.userKey);


        DoneTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeacherModel model = new TeacherModel(key,name,title.getText().toString(),number,email);
                teachersDatabaseReference.child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TeacherRegisterTwo.this,"regitered hah",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TeacherRegisterTwo.this, Home.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeacherRegisterTwo.this,"faild"+ e,Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public void Register() {

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

      {

        };
        // Adding request to request queue


    }

    public void addPicTeacher(View view) {
        PickSetup setup = new PickSetup();

        //setup.setBackgroundColor(yourColor);
        //setup.setTitle(yourTitle);
        //setup.setDimAmount(yourFloat);
        //setup.setTitleColor(yourColor);
        //setup.setFlip(true);
        //setup.setCancelText("Test");
        //setup.setImageSize(500);
        setup.setPickTypes(EPickTypes.GALERY);
        //setup.setProgressText("Loading...");
        //setup.setProgressTextColor(Color.BLUE);


        PickImageDialog.on(TeacherRegisterTwo.this, setup);

    }




}
