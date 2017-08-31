package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.kszajgapp.mozgaserzekelo.mozgaserzekelo.fragments.Devices;
import com.kszajgapp.mozgaserzekelo.mozgaserzekelo.fragments.NewDevice;

/**
 * Created by Csiga on 2017. 08. 30..
 */

public class Useful {

    // Changes fragment when choose an option from floating menu
    public static void SetFragment(int id, FragmentActivity activity) {
        Fragment fragment = null;

        switch (id){
            case 0:
                fragment = new NewDevice();
                break;
            case R.id.btn_Cancel:
                fragment = new Devices();
                break;
            case R.id.nav_settings:
                //fragment = new UserSettings();
                break;
            case R.id.nav_logout:
                //fragment = new LogoutFragment();
        }

        if (fragment != null) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.FragmentLayout, fragment);
            ft.addToBackStack(activity.toString());
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
