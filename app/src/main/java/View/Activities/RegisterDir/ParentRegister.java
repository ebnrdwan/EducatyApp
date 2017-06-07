package com.asi.educatyapp.RegisterDir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import View.Activities.AppController;
import com.asi.educatyapp.Constants;
import View.Activities.Home;
import com.asi.educatyapp.R;
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

import static com.asi.educatyapp.R.id.num;

public class ParentRegister extends AppCompatActivity implements IPickResult {


    EditText fname,lname,username,studentcode,number,pass;
    ImageView profilePic;
    private String base64_encoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);
        fname= (EditText) findViewById(R.id.fname);
        lname= (EditText) findViewById(R.id.lname);
        username= (EditText) findViewById(R.id.username);
        studentcode= (EditText) findViewById(R.id.studentCode);
        number= (EditText) findViewById(num);
        pass= (EditText) findViewById(R.id.password);
        profilePic= (ImageView) findViewById(R.id.profilePic);
    }

    public void MoveToHome(View view) {
       parentRegister();
    }

    public void parentRegister()
    {

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL+"ParentRegister.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //here is the response of server

                Log.e("ERROR==>",response);
                progressDialog.dismiss();
                try {
                    JSONObject ob =new JSONObject(response);

                    String isLogin= ob.getString("isExist");

                    if(isLogin.equals("1"))
                    {


                        Toast.makeText(ParentRegister.this,"User Exist",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ParentRegister.this,Home.class));
                    }else if(isLogin.equals("0"))
                    {
                        Toast.makeText(ParentRegister.this,"New Account Added",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //  Constant.showAlertDialog("Oops!",message,R.drawable.info,Login.this);
                        Toast.makeText(ParentRegister.this,"There are an error try again later",Toast.LENGTH_LONG).show();
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
                params.put("name",fname.getText().toString()+" "+lname.getText().toString());
                params.put("username",username.getText().toString());
                params.put("pass",pass.getText().toString());
                params.put("base",base64_encoded);
                params.put("num",number.getText().toString());
                params.put("code",studentcode.getText().toString());
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


        PickImageDialog.on(ParentRegister.this, setup);

    }


    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            r.getBitmap().compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            base64_encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            profilePic.setImageBitmap(r.getBitmap());



        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
        }
    }
}
