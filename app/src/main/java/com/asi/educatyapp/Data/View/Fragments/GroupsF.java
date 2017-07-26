package com.asi.educatyapp.Data.View.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.Data.Utility.Constants;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.Data.Utility.SharedPreferencesUtils;
import com.asi.educatyapp.Data.Utility.itemclickforRecycler;
import com.asi.educatyapp.Data.View.Activities.TheGroup;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsF extends Fragment {
    ArrayList<GroupsModel> GroupModels = new ArrayList<>();
    private View view;
    TextView info1;
    ImageView Gpic;
    RecyclerView rvGroups;
    ArrayList<GroupsModel> grouplist;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference TeacherdatabaseReference;
    DatabaseReference StudentDatabaseReference;
    FirebaseUser user;
    FirebaseAuth auth;
    ChildEventListener eventListener;
    ValueEventListener valueEventListener;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseRecyclerAdapter groupadapterfirebase;
    public String GroupTag;
    Query query;

    public GroupsF() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_groups, container, false);
        GroupTag = getActivity().getResources().getString(R.string.gtag);
        firebaseDatabase = firebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        TeacherdatabaseReference = firebaseDatabase.getReference().child(FirebaseUtil.groupsObject);
        StudentDatabaseReference = firebaseDatabase.getReference().child(FirebaseUtil.studentObject).child(FirebaseUtil.groupsObject);

        rvGroups = (RecyclerView) view.findViewById(R.id.rvGroups);
        rvGroups.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        if (SharedPreferencesUtils.getTypeOfCurrentUser(getContext()).equals(Constants.T_STUDENT)) {
            query = TeacherdatabaseReference.child(FirebaseUtil.groupsObject)
                    .orderByChild(FirebaseUtil.studentObject).equalTo(SharedPreferencesUtils.getCurrentStudent(getActivity()));
            query = TeacherdatabaseReference;
        } else {
            query = TeacherdatabaseReference.orderByChild(getString(R.string.tid)).equalTo(SharedPreferencesUtils.getCurrentTeacher(getActivity()));


        }


        itemclickforRecycler.addTo(rvGroups).setOnItemClickListener(new itemclickforRecycler.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                GroupsModel model = (GroupsModel) groupadapterfirebase.getItem(position);
                SharedPreferencesUtils.setCurrentGroupKey(getContext(), model.getName());
                Intent startGroup = new Intent(getActivity(), TheGroup.class);
                startGroup.putExtra(GroupTag, model.getName());
                getContext().startActivity(startGroup);
            }
        });


        groupadapterfirebase = new FirebaseRecyclerAdapter<GroupsModel, GroupsHolder>(GroupsModel.class, R.layout.groupsitem, GroupsHolder.class, query) {
            @Override
            protected void populateViewHolder(GroupsHolder viewHolder, GroupsModel model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setImage(getActivity(), model.getPath());
            }
        };
        rvGroups.setAdapter(groupadapterfirebase);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static class GroupsHolder extends RecyclerView.ViewHolder {
        TextView info1;
        ImageView Gpic;

        public GroupsHolder(View itemView) {
            super(itemView);
            info1 = (TextView) itemView.findViewById(R.id.tvGname);
            Gpic = (ImageView) itemView.findViewById(R.id.ivGroupitem);
        }

        public void setName(String name) {
            info1.setText(name);
        }

        public void setImage(Context context, String imagePath) {
            Glide.with(context).load(Uri.parse(imagePath)).error(R.drawable.back).into(Gpic);
        }
    }
}
