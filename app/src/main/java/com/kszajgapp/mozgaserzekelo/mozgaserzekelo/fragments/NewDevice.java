package com.kszajgapp.mozgaserzekelo.mozgaserzekelo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kszajgapp.mozgaserzekelo.mozgaserzekelo.Useful;
import com.kszajgapp.mozgaserzekelo.mozgaserzekelo.UserProfile;

import com.kszajgapp.mozgaserzekelo.mozgaserzekelo.R;

public class NewDevice extends Fragment {
    private static final String TAG = "NewDevice";

    private Button btn_Add;
    private Button btn_Cancel;
    private EditText et_DeviceName;
    private EditText et_DeviceValue;


    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        return inflater.inflate(R.layout.fragment_new_device, container, false);
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstance){
        Button btn_Add = (Button) view.findViewById(R.id.btn_Add);
        final Button btn_Cancel = (Button) view.findViewById(R.id.btn_Cancel);

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, Integer.toString(btn_Cancel.getId()));
                Useful.SetFragment(R.id.btn_Cancel, getActivity());
            }
        });
    }

}
