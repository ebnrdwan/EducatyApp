package com.asi.educatyapp.Data.View.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.Data.Data.Models.StudentModel;
import com.asi.educatyapp.R;

import java.util.ArrayList;


public class classroomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<StudentModel> arrayList;
    Context context;


    public classroomAdapter(Context context, ArrayList<StudentModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        boolean Connection = true;
        boolean badges = false;
        boolean skills = false;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new GenericViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GenericViewHolder) {
            final GenericViewHolder genericViewHolder = (GenericViewHolder) holder;
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Font-Regular.otf");
            Typeface typefacee = Typeface.createFromAsset(context.getAssets(), "fonts/Font-Bold.otf");

//            genericViewHolder.name.setText(arrayList.get(position).getName());
//            genericViewHolder.name.setTypeface(typefacee);
//            genericViewHolder.picture.setImageResource(arrayList.get(position).getImage());
//            genericViewHolder.StudentCard.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });

        }
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class GenericViewHolder extends RecyclerView.ViewHolder{
        CardView StudentCard;
        TextView name;
        ImageView picture;

        public GenericViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameStudentClasroom);
            picture = (ImageView) itemView.findViewById(R.id.imageStudentClassroom);
            StudentCard = (CardView) itemView.findViewById(R.id.student_cardView);
        }


    }

}
