package com.asi.educatyapp.Data.View.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.Data.Data.Models.GroupsModel;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class GroupsAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<GroupsModel> arrayList;
    Context context;

    public GroupsAdpter(Context context, ArrayList<GroupsModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child_container,parent,false));
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.groupsitem, parent, false);
        return new GenericViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GenericViewHolder) {
            final GenericViewHolder genericViewHolder = (GenericViewHolder) holder;
            Typeface typefacee=Typeface.createFromAsset(context.getAssets(),"fonts/Font-Bold.otf");

            genericViewHolder.info1.setText(arrayList.get(position).getName());
            Glide.with(context).load(arrayList.get(position).getPath()).error(R.drawable.back).into(genericViewHolder.Gpic);


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
        TextView info1;
        ImageView Gpic;
        //ImageView fb,logo;
        public GenericViewHolder (View itemView) {
            super (itemView);
            info1=(TextView)itemView.findViewById(R.id.tvGname);
            Gpic= (ImageView) itemView.findViewById(R.id.ivGroup);

        }
    }

}
