package com.asi.educatyapp.Data.View.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asi.educatyapp.Data.View.Activities.AppController;
import com.asi.educatyapp.Data.View.Utils.Constants;
import com.asi.educatyapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.asi.educatyapp.Data.View.Adapters.activateAdapter;
import com.asi.educatyapp.Data.Data.Models.TeacherModel;
import com.asi.educatyapp.Data.Data.Local.SQLiteHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class Admin_inactive_2 extends Fragment {

    RecyclerView myrecylcer;
    Toast mToast;
    ArrayList<TeacherModel> activateTeacherList;
    private activateAdapter adapter;

    public Admin_inactive_2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_admin_inactive_2, container, false);
        activateTeacherList =new ArrayList<>();
        myrecylcer = (RecyclerView) rootView.findViewById(R.id.rvinactive);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        myrecylcer.setLayoutManager(layoutManager);
        myrecylcer.setAdapter(adapter);

        if (activateTeacherList.isEmpty())
        {
            showData();
        }
        else {
            adapter = new activateAdapter(getActivity(),activateTeacherList);
            myrecylcer.setAdapter(adapter);
        }
        return rootView;
    }

    public void showData()
    {

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ..");
        progressDialog.show();

        final StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASEURL+"getActiveTeachers.php", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject ob= new JSONObject(response);
                    JSONArray array=ob.getJSONArray("teachers");
                    int count=0;
                    while (count<array.length())
                    {
                        JSONObject object=new JSONObject( array.getString(count));

                        String id=object.getString("id");
                        String name=object.getString("name");
                        String field=object.getString("field");
                        String title = object.getString("title");
                        String schoolid = object.getString("schoolid");
                        String path = object.getString("path");
                        String num=object.getString("num");

                        activateTeacherList.add(new TeacherModel(id,name,title,schoolid,field ,num,Constants.BASEURL+path));
                        count++;
                    }

                    adapter = new activateAdapter(getActivity(),activateTeacherList);
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
                params.put("active","0");
                params.put("schoolid",new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq,"tag");
    }
}
