package com.asi.educatyapp.Data.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.asi.educatyapp.R;

/**
 * Created by Ahmad AbuKashef on 3/4/2017.
 */
public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), getResources().getString(R.string.lottoFont));
            setTypeface(tf);
        }
    }

}