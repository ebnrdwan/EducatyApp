package com.asi.educatyapp.Data.View.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asi.educatyapp.Data.Data.Models.PostModel;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.Data.View.Activities.AddHomePosts;
import com.asi.educatyapp.Data.View.Activities.AppController;
import com.asi.educatyapp.Data.View.Adapters.HomeAdpter;
import com.asi.educatyapp.Data.Utility.Constants;
import com.asi.educatyapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeF extends Fragment {
    private FloatingActionButton floatingActionButton;

    private View view;
    ArrayList<PostModel> TeacherModels = new ArrayList<>();
    RecyclerView rvHome;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


    public HomeF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(FirebaseUtil.postsObject);


        view = inflater.inflate(R.layout.fragment_home, container, false);
        rvHome = (RecyclerView) view.findViewById(R.id.rvHome);
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        showData();
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getActivity(), AddHomePosts.class));

            }
        });

        return view;
    }

    public void showData() {

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ..");
        progressDialog.show();

        final StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL + "getHomePosts.php", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.e("RESPONSE==>", response);
                try {
                    JSONObject ob = new JSONObject(response);
                    JSONArray array = ob.getJSONArray("items");
                    int count = 0;
                    while (count < array.length()) {
                        JSONObject object = new JSONObject(array.getString(count));

                        String id = object.getString("id");
                        String name = object.getString("tname");
                        String content = object.getString("content");
                        String profile = object.getString("tpic");
                        String contentpic = object.getString("path");
                        String date = object.getString("date");


                        TeacherModels.add(new PostModel(id, name, content, date, Constants.BASEURL + profile, Constants.BASEURL + contentpic));
                        count++;
                    }

                    HomeAdpter homeAdpter = new HomeAdpter(getActivity(), TeacherModels);
                    rvHome.setAdapter(homeAdpter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "tag");
    }
}
