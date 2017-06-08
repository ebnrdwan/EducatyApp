package com.asi.educatyapp.Data.View.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asi.educatyapp.R;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.asi.educatyapp.Data.Data.Models.TeacherModel;
import com.asi.educatyapp.Data.View.Activities.AppController;
import com.asi.educatyapp.Data.View.Utils.Constants;


/**
 * Created by Abdulrhman on 22/02/2017.
 */
public class activateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    ArrayList<TeacherModel> arrayList;
    Context context;

    public activateAdapter(Context context, ArrayList<TeacherModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;


    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activation, parent, false);
        return new GenericViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GenericViewHolder) {
            final GenericViewHolder genericViewHolder = (GenericViewHolder) holder;
            Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Font-Regular.otf");
            Typeface typefacee=Typeface.createFromAsset(context.getAssets(),"fonts/Font-Bold.otf");

            genericViewHolder.name.setText(arrayList.get(position).getName());
            genericViewHolder.name.setTypeface(typefacee);
            genericViewHolder.title.setText(arrayList.get(position).getTitle()+" in "+arrayList.get(position).getSchool());
            genericViewHolder.title.setTypeface(typefacee);
            Glide.with(context).load(arrayList.get(position).getImagepath()).into(genericViewHolder.picture);
            genericViewHolder.activate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final ProgressDialog progressDialog;
                    progressDialog=new ProgressDialog(context);
                    progressDialog.setMessage("Please wait ..");
                    progressDialog.show();

                    final StringRequest strReq = new StringRequest(Request.Method.POST,
                            Constants.BASEURL+"activeTeachers.php", new Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject ob =new JSONObject(response);

                                String status= ob.getString("status");
                                if (status.equals("1"))
                                {
                                    Toast.makeText(context,"Teacher Acticated",Toast.LENGTH_LONG).show();
                                    arrayList.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                }else
                                {
                                    Toast.makeText(context,"There is an error try again",Toast.LENGTH_LONG).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("active","1");
                            params.put("id",arrayList.get(position).getId());
                            return params;
                        }

                    };
                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq,"tag");
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




    class GenericViewHolder extends RecyclerView.ViewHolder  {

        TextView name ;
        TextView title;
        Button activate;
        ImageView picture;
        int positionadapter = getAdapterPosition();

        public GenericViewHolder (View itemView) {
            super (itemView);
            name=(TextView)itemView.findViewById(R.id.name_text_view);
            title= (TextView) itemView.findViewById(R.id.title_text_view);
            picture= (ImageView) itemView.findViewById(R.id.imageactivation);
            activate = (Button) itemView.findViewById(R.id.activateButton);

        }



    }


}

