package com.asi.educatyapp.RegisterDir;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.asi.educatyapp.R;
import com.vansuita.pickimage.EPickTypes;
import com.vansuita.pickimage.PickImageDialog;
import com.vansuita.pickimage.PickSetup;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

public class Admin_RegisterOne extends AppCompatActivity implements IPickResult {

    ImageView profile ;
    EditText Email,pass;
    private String base64_encoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_register_one);
        Button RegistAdmin = (Button) findViewById(R.id.NextAdmin);

        profile= (ImageView) findViewById(R.id.ProfilePic);
        Email= (EditText) findViewById(R.id.emailinput);
        pass= (EditText) findViewById(R.id.passinput);
        RegistAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_RegisterOne.this,AdminRegisterTwo.class);
                intent.putExtra("base",base64_encoded);
                intent.putExtra("email",Email.getText().toString());
                intent.putExtra("pass",pass.getText().toString());
                startActivity(intent);

            }
        });
    }



    public void addPic(View view) {
        PickSetup setup = new PickSetup();

        //setup.setBackgroundColor(yourColor);
        //setup.setTitle(yourTitle);
        //setup.setDimAmount(yourFloat);
        //setup.setTitleColor(yourColor);
        //setup.setFlip(true);
        //setup.setCancelText("Test");
        //setup.setImageSize(500);
        setup.setPickTypes(EPickTypes.GALERY);
        //setup.setProgressText("Loading...");
        //setup.setProgressTextColor(Color.BLUE);


        PickImageDialog.on(Admin_RegisterOne.this, setup);

    }


    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            r.getBitmap().compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            base64_encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            profile.setImageBitmap(r.getBitmap());



        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
        }
    }

//    public void Next(View view) {
//        startActivity(new Intent(Admin_RegisterOne.this,AdminRegisterTwo.class));
//    }
//

}
