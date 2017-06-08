package com.asi.educatyapp.Data.View.Activities;

import android.app.ProgressDialog;
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
import com.asi.educatyapp.Data.View.Utils.Constants;
import com.asi.educatyapp.R;
import com.vansuita.pickimage.EPickTypes;
import com.vansuita.pickimage.PickImageDialog;
import com.vansuita.pickimage.PickSetup;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.asi.educatyapp.Data.Data.Local.SQLiteHandler;

public class AddNewGroup extends AppCompatActivity  implements IPickResult {

    EditText Gname;
    ImageView Gpic;
    private String base64_encoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);
        Gname= (EditText) findViewById(R.id.etGroupName);
        Gpic= (ImageView) findViewById(R.id.ivGroup);
    }

    public void AddGroupPost(View view) {
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


        PickImageDialog.on(AddNewGroup.this, setup);

    }

    public void AddPost(View view) {
        AddPost();
    }


    public void AddPost()
    {


        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL+"AddGroup.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //here is the response of server

                progressDialog.dismiss();
                try {
                    JSONObject ob =new JSONObject(response);

                    String isLogin= ob.getString("status");


                    if(isLogin.equals("1"))
                    {


                        Toast.makeText(AddNewGroup.this,"New Group added",Toast.LENGTH_LONG).show();
                        finish();


                    }else if(isLogin.equals("0"))
                    {
                        Toast.makeText(AddNewGroup.this,"There is an error",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //  Constant.showAlertDialog("Oops!",message,R.drawable.info,Login.this);
                        Toast.makeText(AddNewGroup.this,"There are an error try again later",Toast.LENGTH_LONG).show();
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
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",Gname.getText().toString());
               // params.put("tid",new SQLiteHandler(getApplicationContext()).getUserDetails().get("uid"));
                params.put("base",base64_encoded);
                params.put("schoolid",new SQLiteHandler(getApplicationContext()).getUserDetails().get("uid"));
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
    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            r.getBitmap().compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            base64_encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Gpic.setImageBitmap(r.getBitmap());



        } else {
            Log.e("ERROR==>",r.getError().getMessage());
        }
    }
}
