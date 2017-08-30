package com.kszajgapp.mozgaserzekelo.mozgaserzekelo.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kszajgapp.mozgaserzekelo.mozgaserzekelo.R;

/**
 * Created by Csiga on 2017. 08. 29..
 */

public class Devices extends Fragment {

    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devices, container, false);
    }
}
