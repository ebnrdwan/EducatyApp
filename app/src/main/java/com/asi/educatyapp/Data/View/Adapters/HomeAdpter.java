package com.asi.educatyapp.Data.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.Data.Data.Models.PostModel;
import com.asi.educatyapp.Data.View.Activities.Comment_Home;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


/**
 * Created by mustafa on 6/26/2016.
 */
public class HomeAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<PostModel> arrayList;
    Context context;

    public HomeAdpter(Context context, ArrayList<PostModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child_container,parent,false));
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainlistitem, parent, false);
        return new GenericViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GenericViewHolder) {
            final GenericViewHolder genericViewHolder = (GenericViewHolder) holder;

            genericViewHolder.info1.setText(arrayList.get(position).getName());
            genericViewHolder.content.setText(arrayList.get(position).getContent());
            genericViewHolder.time.setText(arrayList.get(position).getTime());
            Glide.with(context).load(arrayList.get(position).getProfile()).into(genericViewHolder.profile);
            Glide.with(context).load(arrayList.get(position).getContentpic()).into(genericViewHolder.postPic);
            genericViewHolder.viewComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, Comment_Home.class).putExtra("id",arrayList.get(position).getId()));
                }
            });


         }
    }


    private boolean isPositionHeader (int position) {
        return position == 0;
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    class GenericViewHolder extends RecyclerView.ViewHolder {

        TextView info1,time,content ;
        ImageView profile,postPic,viewComments;
        public GenericViewHolder (View itemView) {
            super (itemView);
            info1=(TextView)itemView.findViewById(R.id.homeName);
            profile= (ImageView) itemView.findViewById(R.id.homeProfile);
            postPic= (ImageView) itemView.findViewById(R.id.homePostPic);
            time= (TextView) itemView.findViewById(R.id.posttime);
            content= (TextView) itemView.findViewById(R.id.homePostContent);
            viewComments= (ImageView) itemView.findViewById(R.id.viewComments);
        }
    }

}
