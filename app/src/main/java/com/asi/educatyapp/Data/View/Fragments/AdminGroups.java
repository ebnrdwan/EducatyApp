package com.asi.educatyapp.Data.View.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asi.educatyapp.Data.View.Activities.AppController;
import com.asi.educatyapp.Data.View.Utils.Constants;
import com.asi.educatyapp.R;
import com.asi.educatyapp.Data.View.Activities.theGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.asi.educatyapp.Data.View.Adapters.groupsAdpter;
import com.asi.educatyapp.Data.Data.Models.groupsModel;
import com.asi.educatyapp.Data.Utility.itemclickforRecycler;
import com.asi.educatyapp.Data.Data.Local.SQLiteHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminGroups extends Fragment {
    ArrayList<groupsModel> GroupModels = new ArrayList<>();
    private View view;

    RecyclerView rvGroups;
    public AdminGroups() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_groups, container, false);

         rvGroups= (RecyclerView) view.findViewById(R.id.rvGroups);

        itemclickforRecycler.addTo(rvGroups).setOnItemClickListener(new itemclickforRecycler.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                startActivity(new Intent(getActivity(), theGroup.class));
            }
        });
        if(GroupModels.isEmpty())
        {
            showData();
        }else {
            groupsAdpter homeAdpter = new groupsAdpter(getActivity(), GroupModels);
            rvGroups.setLayoutManager(new GridLayoutManager(getActivity(),2));
            rvGroups.setAdapter(homeAdpter);
        }

        return view;
    }
    public void showData()
    {

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ..");
        progressDialog.show();

        final StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL+"getGroups.php", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject ob= new JSONObject(response);
                    JSONArray array=ob.getJSONArray("Groups");
                    int count=0;
                    while (count<array.length())
                    {
                        JSONObject object=new JSONObject( array.getString(count));

                        String id=object.getString("id");
                        String name=object.getString("name");
                        String schoolid=object.getString("schoolid");
                        String path = object.getString("path");


                        GroupModels.add(new groupsModel(id,schoolid,name,Constants.BASEURL+path));
                        count++;
                    }

                    rvGroups.setNestedScrollingEnabled(false);

                    groupsAdpter homeAdpter = new groupsAdpter(getActivity(), GroupModels);
                    rvGroups.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    rvGroups.setAdapter(homeAdpter);
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

                //params.put("schoolid",new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
                params.put("schoolid",new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq,"tag");
    }
}
