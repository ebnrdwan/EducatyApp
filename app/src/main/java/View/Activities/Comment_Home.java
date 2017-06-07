package com.asi.educatyapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Adapters.CommentAdapter;
import Data.Data.Models.CommentModel;
import helper.SQLiteHandler;

public class Comment_Home extends AppCompatActivity{


    RecyclerView myrecylcer;
    Toast mToast;
    ArrayList<CommentModel> CommentList;
    EditText commenttv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment__home);
        CommentList =new ArrayList<>();
        commenttv= (EditText) findViewById(R.id.commenttv);

        myrecylcer = (RecyclerView) findViewById(R.id.Recycler_activity_comment__home);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Comment_Home.this, LinearLayoutManager.VERTICAL,false);
        myrecylcer.setLayoutManager(layoutManager);
        showCommentData();

    }

    public void AddComment()
    {


        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL+"AddCommentToHomePsot.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //here is the response of server


                progressDialog.dismiss();
                try {
                    JSONObject ob =new JSONObject(response);

                    String isLogin= ob.getString("status");


                    if(isLogin.equals("1"))
                    {


                        Toast.makeText(Comment_Home.this,"Your Comment added",Toast.LENGTH_LONG).show();
                        CommentList.clear();
                        commenttv.setText(" ");
                        showCommentData();


                    }else if(isLogin.equals("0"))
                    {
                        Toast.makeText(Comment_Home.this,"There is an error",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //  Constant.showAlertDialog("Oops!",message,R.drawable.info,Login.this);
                        Toast.makeText(Comment_Home.this,"There are an error try again later",Toast.LENGTH_LONG).show();
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
                params.put("username",new SQLiteHandler(getApplicationContext()).getUserDetails().get("name"));
                params.put("path",Constants.BASEURL+new SQLiteHandler(getApplicationContext()).getUserDetails().get("path"));
                params.put("comment",commenttv.getText().toString());
                params.put("homePostId",getIntent().getStringExtra("id"));
                params.put("date",formattedDate);
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

    public void addComment(View view) {
        AddComment();
    }

    public void showCommentData()
    {

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(Comment_Home.this);
        progressDialog.setMessage("Please wait ..");
        progressDialog.show();

        final StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL+"getAllHomeComments.php", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject ob= new JSONObject(response);
                    JSONArray array=ob.getJSONArray("comments");
                    int count=0;
                    while (count<array.length())
                    {
                        JSONObject object=new JSONObject( array.getString(count));

                        String id=object.getString("id");
                        String comment=object.getString("comment");
                        String contentpic = object.getString("path");
                        String date = object.getString("date");
                        String username=object.getString("username");
                        String pid =object.getString("homePostId");


                        CommentList.add(new CommentModel(id,pid,username,contentpic,comment,date));
                        count++;
                    }

                    CommentAdapter adapter = new CommentAdapter(Comment_Home.this,CommentList);
                    myrecylcer.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",getIntent().getStringExtra("id"));
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq,"tag");
    }
}
