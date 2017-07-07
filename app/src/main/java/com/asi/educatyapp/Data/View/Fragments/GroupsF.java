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
import com.asi.educatyapp.Data.Utility.itemclickforRecycler;
import com.asi.educatyapp.Data.View.Activities.TheGroup;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    DatabaseReference databaseReference;
    FirebaseUser user;
    FirebaseAuth auth;
    ChildEventListener eventListener;
    ValueEventListener valueEventListener;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseRecyclerAdapter groupadapterfirebase;
    public static String GroupTag ="GTAG";

    public GroupsF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_groups, container, false);
        firebaseDatabase = firebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Groups");
        rvGroups = (RecyclerView) view.findViewById(R.id.rvGroups);
        rvGroups.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        itemclickforRecycler.addTo(rvGroups).setOnItemClickListener(new itemclickforRecycler.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
             GroupsModel model= (GroupsModel) groupadapterfirebase.getItem(position);
                Intent startGroup = new Intent(getActivity(),TheGroup.class);
                startGroup.putExtra(GroupTag,model.getName());
                getContext().startActivity(startGroup);
            }
        });

        groupadapterfirebase = new FirebaseRecyclerAdapter<GroupsModel, GroupsHolder>(GroupsModel.class,R.layout.groupsitem,GroupsHolder.class,databaseReference) {
          @Override
          protected void populateViewHolder(GroupsHolder viewHolder, GroupsModel model, int position) {
              viewHolder.setName(model.getName());
              viewHolder.setImage(getActivity(),model.getPath());
          }
      };

        rvGroups.setAdapter(groupadapterfirebase);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public ArrayList<GroupsModel> getGroups() {

        if (valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        GroupsModel model = postSnapshot.getValue(GroupsModel.class);
                        grouplist.add(model);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            databaseReference.addValueEventListener(valueEventListener);
        }
        return grouplist;
    }
    public static class GroupsHolder extends RecyclerView.ViewHolder {
        TextView info1;
        ImageView Gpic;

        public GroupsHolder(View itemView) {
            super(itemView);
            info1=(TextView)itemView.findViewById(R.id.tvGname);
            Gpic= (ImageView) itemView.findViewById(R.id.ivGroup);
        }

        public void setName(String name) {
            info1.setText(name);
        }

        public void setImage(Context context, String imagePath) {
            Glide.with(context).load(Uri.parse(imagePath)).error(R.drawable.back).into(Gpic);
        }
    }


}
