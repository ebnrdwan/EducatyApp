package com.asi.educatyapp.Data.customfonts;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

import com.asi.educatyapp.R;

public class teacher extends AppCompatActivity {



    Typeface fonts1;
    CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myteacherlogin);


        remember = (CheckBox)findViewById(R.id.remember);

        fonts1 =  Typeface.createFromAsset(teacher.this.getAssets(),
                "fonts/Lato-Light.ttf");

        TextView t4 =(TextView)findViewById(R.id.remember);
        t4.setTypeface(fonts1);








    }
}
