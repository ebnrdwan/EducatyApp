package com.asi.educatyapp.Data.View.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.asi.educatyapp.Data.Data.Models.CommentModel;


/**
 * Created by Abdulrhman on 22/02/2017.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<CommentModel> arrayList;
    Context context;


    public CommentAdapter(Context context, ArrayList<CommentModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
        return new GenericViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GenericViewHolder) {
            final GenericViewHolder genericViewHolder = (GenericViewHolder) holder;

            genericViewHolder.name.setText(arrayList.get(position).getName());
            genericViewHolder.comment.setText(arrayList.get(position).getComment());
            genericViewHolder.time.setText(arrayList.get(position).getTime());
            Glide.with(context).load(arrayList.get(position).getImage()).into(genericViewHolder.picture);


        }
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class GenericViewHolder extends RecyclerView.ViewHolder  {

        TextView name;
        TextClock time;
        TextView comment;
        ImageButton post;
        ImageView picture;


        public GenericViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameComment);
            time = (TextClock) itemView.findViewById(R.id.timeComment);
            picture = (ImageView) itemView.findViewById(R.id.imagecomment);
            post = (ImageButton) itemView.findViewById(R.id.postComment);
            comment= (TextView) itemView.findViewById(R.id.comment);

        }



    }


}

