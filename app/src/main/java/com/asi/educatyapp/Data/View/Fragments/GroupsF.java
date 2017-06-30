package com.asi.educatyapp.Data.View.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.Data.Utility.itemclickforRecycler;
import com.asi.educatyapp.Data.View.Activities.theGroup;
import com.asi.educatyapp.Data.View.Adapters.GroupsAdpter;
import com.asi.educatyapp.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsF extends Fragment {
    ArrayList<GroupsModel> GroupModels = new ArrayList<>();
    private View view;

    RecyclerView rvGroups;

    public GroupsF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_groups, container, false);

        rvGroups = (RecyclerView) view.findViewById(R.id.rvGroups);
        rvGroups.setLayoutManager(new GridLayoutManager(getActivity(),2));

        itemclickforRecycler.addTo(rvGroups).setOnItemClickListener(new itemclickforRecycler.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                startActivity(new Intent(getActivity(), theGroup.class));
            }
        });
        ArrayList<GroupsModel> grouplist;
        grouplist = new ArrayList<>();
        grouplist.add(new GroupsModel("name", R.drawable.teacher));
        grouplist.add(new GroupsModel("name", R.drawable.teacher));
        grouplist.add(new GroupsModel("name", R.drawable.teacher));
        grouplist.add(new GroupsModel("name", R.drawable.teacher));
        grouplist.add(new GroupsModel("name", R.drawable.teacher));
        grouplist.add(new GroupsModel("name", R.drawable.teacher));
        grouplist.add(new GroupsModel("name", R.drawable.teacher));
        GroupsAdpter groupsAdpter = new GroupsAdpter(getActivity(), grouplist);
        rvGroups.setAdapter(groupsAdpter);
        return view;
    }


}
