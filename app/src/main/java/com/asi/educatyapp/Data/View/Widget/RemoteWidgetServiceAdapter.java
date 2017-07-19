package com.asi.educatyapp.Data.View.Widget;


import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Abdulrhman on 18/07/2017.
 */

public class RemoteWidgetServiceAdapter extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int id = intent.getIntExtra("id",0);
        return new WidgetRemoteViewFactory(this,id);
    }
}
