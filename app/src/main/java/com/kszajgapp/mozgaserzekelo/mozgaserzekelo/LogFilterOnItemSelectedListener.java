package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by Csiga on 2017. 06. 13..
 */

public class LogFilterOnItemSelectedListener extends MainActivity implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "On Item Select : \n" + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
