package com.asi.educatyapp.Data.View.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.R;

import java.util.ArrayList;

import com.asi.educatyapp.Data.Data.Models.SkillModel;


/**
 * Created by mustafa on 6/26/2016.
 */
public class SkillsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<SkillModel> arrayList;
    Context context;


    public SkillsAdapter(Context context, ArrayList<SkillModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.skills, parent, false);
        return new GenericViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GenericViewHolder) {
            final GenericViewHolder genericViewHolder = (GenericViewHolder) holder;
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Font-Regular.otf");
            Typeface typefacee = Typeface.createFromAsset(context.getAssets(), "fonts/Font-Bold.otf");

            genericViewHolder.SkillName.setText(arrayList.get(position).getName());
            genericViewHolder.SkillName.setTypeface(typefacee);


        }
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class GenericViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView SkillName;
        ImageView picture;
        ImageButton skillsEndorse;

        public GenericViewHolder(View itemView) {
            super(itemView);
            SkillName = (TextView) itemView.findViewById(R.id.name_text_view_skills);
            picture = (ImageView) itemView.findViewById(R.id.imageskills);
            skillsEndorse = (ImageButton) itemView.findViewById(R.id.skillsButton);


        }

        @Override
        public void onClick(View view) {


        }
    }


}


