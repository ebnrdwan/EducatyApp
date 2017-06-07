package com.asi.educatyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CodeOfAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_of_admin);
        TextView code = (TextView) findViewById(R.id.Code);
        code.setText(getIntent().getStringExtra("code"));
        //
    }
}
