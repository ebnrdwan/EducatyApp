package com.asi.educatyapp.Data.View.Activities.userAccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.asi.educatyapp.R;

/**
 * Created by Abdulrhman on 01/07/2017.
 */

public class GetStart extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_start_layout);
        Button button = (Button) findViewById(R.id.gogo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetStart.this,LoginEdu.class));
            }
        });

    }
}
