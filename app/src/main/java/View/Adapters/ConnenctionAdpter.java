package Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.R;

import java.util.ArrayList;

import Data.Data.Models.homeModel;


/**
 * Created by mustafa on 6/26/2016.
 */
public class ConnenctionAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<homeModel> arrayList;
    Context context;

    public ConnenctionAdpter(Context context, ArrayList<homeModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child_container,parent,false));
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.connectionitem, parent, false);
        return new GenericViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GenericViewHolder) {
            final GenericViewHolder genericViewHolder = (GenericViewHolder) holder;
            Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Font-Regular.otf");
            Typeface typefacee=Typeface.createFromAsset(context.getAssets(),"fonts/Font-Bold.otf");

            genericViewHolder.info1.setText(arrayList.get(position).getName());
            genericViewHolder.info1.setTypeface(typefacee);


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

        TextView info1 ;
        ImageView logo;
        public GenericViewHolder (View itemView) {
            super (itemView);
            info1=(TextView)itemView.findViewById(R.id.nameTv);
            logo= (ImageView) itemView.findViewById(R.id.logoHealthy);
        }
    }

}
