package com.asi.educatyapp.Data.View.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.Data.Data.Models.PostModel;
import com.asi.educatyapp.Data.Utility.FirebaseUtil;
import com.asi.educatyapp.Data.View.Activities.AddHomePosts;
import com.asi.educatyapp.Data.View.Activities.Comment_Home;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeF extends Fragment {
    private FloatingActionButton floatingActionButton;

    private View view;

    RecyclerView rvHome;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseRecyclerAdapter adapter;
   public static String keyTag="key";

    public HomeF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(FirebaseUtil.postsObject);


         adapter = new FirebaseRecyclerAdapter<PostModel, PostHolder>(PostModel.class, R.layout.mainlistitem, PostHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, final PostModel model, int position) {

                viewHolder.name.setText(model.getName());
                viewHolder.time.setText(model.getTime());
                viewHolder.postContent.setText(model.getContent());
                Glide.with(getActivity()).load(model.getProfile()).error(R.drawable.student).into(viewHolder.profile);
                Glide.with(getActivity()).load(model.getContentpic()).error(R.drawable.studentbg).into(viewHolder.postImage);
                viewHolder.viewComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        getContext().startActivity(new Intent(getActivity(), Comment_Home.class).putExtra(keyTag,model.getId()));
                    }
                });
            }
        };


        view = inflater.inflate(R.layout.fragment_home, container, false);
        rvHome = (RecyclerView) view.findViewById(R.id.rvHome);
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvHome.setAdapter(adapter);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), AddHomePosts.class));
            }
        });
        return view;
    }
    public static class PostHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView time;
        ImageView postImage;
        TextView postContent;
        ImageView viewComments;
        public PostHolder(View itemView) {
            super(itemView);
            profile = (ImageView) itemView.findViewById(R.id.homeProfile);
            name = (TextView) itemView.findViewById(R.id.homeName);
            time = (TextView) itemView.findViewById(R.id.posttime);
            postImage = (ImageView) itemView.findViewById(R.id.homePostPic);
            postContent = (TextView) itemView.findViewById(R.id.homePostContent);
            viewComments= (ImageView) itemView.findViewById(R.id.viewComments);
        }
    }
}
