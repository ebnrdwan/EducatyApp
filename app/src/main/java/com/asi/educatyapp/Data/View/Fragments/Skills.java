package com.asi.educatyapp.Data.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asi.educatyapp.Data.Data.Models.SkillModel;
import com.asi.educatyapp.Data.View.Adapters.SkillsAdapter;
import com.asi.educatyapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Skills extends Fragment {
    ArrayList<SkillModel> skillList;
    RecyclerView myrecylcer;
    public static String ParceTag = "PARCETAG";

    public Skills() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_skills, container, false);
        skillList = new ArrayList<>();
        skillList.add(new SkillModel("ICDL Certificate", R.drawable.skills, R.drawable.addicon));
        skillList.add(new SkillModel("TOFEL Certificate", R.drawable.skills, R.drawable.addicon));
        skillList.add(new SkillModel("new Methodologies in Teaching", R.drawable.skills, R.drawable.addicon));
        skillList.add(new SkillModel("Best Mentor in Physics 2014", R.drawable.skills, R.drawable.addicon));
        skillList.add(new SkillModel("ICDL Certificate", R.drawable.skills, R.drawable.addicon));
        skillList.add(new SkillModel("TOFEL Certificate", R.drawable.skills, R.drawable.addicon));
        skillList.add(new SkillModel("new Methodologies in Teaching", R.drawable.skills, R.drawable.addicon));
        skillList.add(new SkillModel("Best Mentor in Physics 2014", R.drawable.skills, R.drawable.addicon));
        myrecylcer = (RecyclerView) rootView.findViewById(R.id.recyclerSkills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myrecylcer.setLayoutManager(layoutManager);

        SkillsAdapter adapter = new SkillsAdapter(getActivity(), skillList);
        myrecylcer.setAdapter(adapter);
        myrecylcer.setHasFixedSize(true);
        return rootView;
    }


}
