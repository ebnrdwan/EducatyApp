package com.asi.educatyapp.Data.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asi.educatyapp.R;

public class AttendanceAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;


  public AttendanceAdapter(Context context) {
    layoutInflater = LayoutInflater.from(context);

  }

  @Override
  public int getCount() {
    return 6;
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    View view = convertView;

    if (view == null) {

        view = layoutInflater.inflate(R.layout.simple_grid_item, parent, false);


      viewHolder = new ViewHolder();
      viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
      viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }

    Context context = parent.getContext();
    switch (position) {
      case 0:
        viewHolder.textView.setText(context.getString(R.string.hodlername));
        viewHolder.imageView.setImageResource(R.drawable.mypic22);
        break;
      case 1:
        viewHolder.textView.setText(context.getString(R.string.hodlername));
        viewHolder.imageView.setImageResource(R.drawable.mypic22);
        break;
      default:
        viewHolder.textView.setText(context.getString(R.string.hodlername));
        viewHolder.imageView.setImageResource(R.drawable.mypic22);
        break;
    }

    return view;
  }

  static class ViewHolder {
    TextView textView;
    ImageView imageView;
  }
}
