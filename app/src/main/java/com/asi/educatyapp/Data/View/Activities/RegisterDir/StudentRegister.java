package com.asi.educatyapp.Data.View.Activities.RegisterDir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asi.educatyapp.Data.Data.Models.StudentModel;
import com.asi.educatyapp.Data.View.Activities.AppController;
import com.asi.educatyapp.Data.View.Activities.Home;
import com.asi.educatyapp.Data.View.Activities.SecondScreen;
import com.asi.educatyapp.Data.View.Utils.Constants;
import com.asi.educatyapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vansuita.pickimage.EPickTypes;
import com.vansuita.pickimage.PickImageDialog;
import com.vansuita.pickimage.PickSetup;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class StudentRegister extends AppCompatActivity implements IPickResult {

    EditText fname, schoo_name, username, studentcode, pass;
    ImageView profilePic;
    private String base64_encoded;
    Button regist;
    private String name, number, email, key;
    FirebaseDatabase firebaseDatabase;


    DatabaseReference teachersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        fname = (EditText) findViewById(R.id.fname);
        schoo_name = (EditText) findViewById(R.id.school_name);
        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.pass);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString(SecondScreen.nameEx);
        email = bundle.getString(SecondScreen.emailEx);
        key = bundle.getString(SecondScreen.userKey);
        firebaseDatabase = FirebaseDatabase.getInstance();
        teachersDatabaseReference = firebaseDatabase.getReference("students");
        regist = (Button) findViewById(R.id.Reg_student);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentModel model = new StudentModel(key, name, schoo_name.getText().toString());
                teachersDatabaseReference.child(key).setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(StudentRegister.this, "regitered students", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(StudentRegister.this, Home.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StudentRegister.this, "faild regist student" + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    public void MoveToHome(View view) {
        StudentRegister();
    }

    public void StudentRegister() {

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL + "StudentRegister.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //here is the response of server

                Log.e("ERROR==>", response);
                progressDialog.dismiss();
                try {
                    JSONObject ob = new JSONObject(response);

                    String isLogin = ob.getString("isExist");

                    if (isLogin.equals("1")) {


                        Toast.makeText(StudentRegister.this, "User Exist", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(StudentRegister.this, Home.class));
                    } else if (isLogin.equals("0")) {
                        Toast.makeText(StudentRegister.this, "New Account Added", Toast.LENGTH_LONG).show();
                    } else {
                        //  Constant.showAlertDialog("Oops!",message,R.drawable.info,Login.this);
                        Toast.makeText(StudentRegister.this, "There are an error try again later", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
//                HashMap<String, String> user = db.getUserDetails();
//                String id = user.get("uid");
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", fname.getText().toString() + " " + schoo_name.getText().toString());
                params.put("username", username.getText().toString());
                params.put("pass", pass.getText().toString());
                params.put("base", base64_encoded);
                params.put("groupcode", studentcode.getText().toString());
                params.put("token", FirebaseInstanceId.getInstance().getToken());
                return params;
            }

        };
        // Adding request to request queue
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "tag");
    }

    public void addPic(View view) {
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


        PickImageDialog.on(StudentRegister.this, setup);

    }


    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            r.getBitmap().compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            base64_encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            profilePic.setImageBitmap(r.getBitmap());


        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
        }
    }
}
