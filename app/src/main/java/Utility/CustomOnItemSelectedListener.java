package Utility;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by ASI on 2/26/2017.
 */

public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {

        Toast.makeText(parent.getContext(),
                "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


}
