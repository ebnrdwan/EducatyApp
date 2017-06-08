package com.asi.educatyapp.Data.View.Activities.RegisterDir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asi.educatyapp.Data.View.Activities.AppController;
import com.asi.educatyapp.Data.View.Activities.CodeOfAdmin;
import com.asi.educatyapp.Data.View.Utils.Constants;
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

import com.asi.educatyapp.Data.View.CustomViews.CircleImageView;


public class AdminRegisterTwo extends AppCompatActivity implements IPickResult {

    EditText num,school,curriculum,level;
    EditText Email,pass;
    private String base64_encoded;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register_two);
        Email= (EditText) findViewById(R.id.emailinput);
        pass= (EditText) findViewById(R.id.passinput);
        num= (EditText) findViewById(R.id.num);
        school= (EditText) findViewById(R.id.schoolname);
        curriculum= (EditText) findViewById(R.id.curriculum);
        level=(EditText)findViewById(R.id.level);
        profile= (CircleImageView) findViewById(R.id.ProfilePic);

        Button Done = (Button) findViewById(R.id.Done_Admin);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Register();
            }
        });
    }

    public void Register() {


        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL+"AdminRegister.php", new Response.Listener<String>() {
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


                        Toast.makeText(AdminRegisterTwo.this,"User Exist",Toast.LENGTH_LONG).show();

                    }else if(isLogin.equals("0"))
                    {
                        Toast.makeText(AdminRegisterTwo.this,"New Account Added",Toast.LENGTH_LONG).show();
                        String message=ob.getString("msg");
                        startActivity(new Intent(AdminRegisterTwo.this,CodeOfAdmin.class).putExtra("code",message));
                    }
                    else
                    {
                        //  Constant.showAlertDialog("Oops!",message,R.drawable.info,Login.this);
                        Toast.makeText(AdminRegisterTwo.this,"There are an error try again later",Toast.LENGTH_LONG).show();
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
                params.put("name",school.getText().toString());
                params.put("email",Email.getText().toString());
                params.put("pass",pass.getText().toString());
                params.put("base",base64_encoded);
                params.put("code",Constants.genrateCode());
                params.put("location","Egypt");
                params.put("num",num.getText().toString());
                params.put("curriculum",curriculum.getText().toString());
                params.put("level",level.getText().toString());
                SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
                //String token = prefs.getString("token", null);
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

    public void addPicc(View view) {
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


        PickImageDialog.on(AdminRegisterTwo.this, setup);

    }


    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            r.getBitmap().compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            base64_encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            profile.setImageBitmap(r.getBitmap());



        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
        }
    }

//    public void Next(com.asi.educatyapp.Data.View view) {
//        startActivity(new Intent(AdminRegisterTwo.this,AdminRegisterTwo.class));
//    }
//
}
