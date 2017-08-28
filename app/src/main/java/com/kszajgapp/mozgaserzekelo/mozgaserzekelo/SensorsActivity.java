package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kszajgapp.mozgaserzekelo.mozgaserzekelo.model.User;

public class SensorsActivity extends AppCompatActivity {
    private static final String TAG = "SensorsActivity";

    private ListView lvSensors;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lvSensors = (ListView) findViewById(R.id.lv_Sensors);
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mozgaserzekelo-98d28.firebaseio.com/users/user");
        mDatabase.addValueEventListener(userListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue(User.class);
            Log.d(TAG, user.user);
            Log.d(TAG, user.devices.toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}


