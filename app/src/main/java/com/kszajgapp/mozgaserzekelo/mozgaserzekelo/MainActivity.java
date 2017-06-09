package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DatabaseReference mDatabase;
    public Sensor pir = new Sensor();

    //Get the token
    String token = FirebaseInstanceId.getInstance().getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ToggleButton tbtnPIRSensor = (ToggleButton) findViewById(R.id.tbtn_PIRSensor);
        final Switch swSubscribe = (Switch) findViewById(R.id.switch_Subscribe);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(PIRSensorListener);
        //mDatabase.addListenerForSingleValueEvent(PIRSensorListener);

        Button btnShowToken = (Button)findViewById(R.id.btn_show_token);
        btnShowToken.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Log.d(TAG, "Token: " + token);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
            }
        });

        tbtnPIRSensor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(pir.PIRSensor){
                    setPIRSensor(false);
                    Log.d(TAG, "FALSE-RA ALLITAS!");
                    tbtnPIRSensor.setChecked(false);
                }
                else {
                    setPIRSensor(true);
                    Log.d(TAG, "TRUE-RA ALLITAS!");
                    tbtnPIRSensor.setChecked(true);
                }

                Log.d(TAG, "PIRSensor value changed: " + pir.PIRSensor);
                Toast.makeText(MainActivity.this, "PIR Sensor value changed: " + pir.PIRSensor, Toast.LENGTH_SHORT).show();

            }
        });

        swSubscribe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Subscribe esemény");
                setSubscribe(token);
            }
        });
    }

    public void setPIRSensor(boolean PIRValue) {
        Log.d(TAG, "BEMENO PARAMETER ERTEKE: " + PIRValue);
        mDatabase.child("PIRSensor").setValue(PIRValue);
    }

    public void setSubscribe(String token){
        mDatabase.child("token").setValue(token);
    }

    public void setSubscribeSwitch(boolean val){
        final Switch swSubscribe = (Switch) findViewById(R.id.switch_Subscribe);
        swSubscribe.setChecked(val);
    }

    public void setButton(boolean togglevalue){
        final ToggleButton tbtnPIRSensor = (ToggleButton) findViewById(R.id.tbtn_PIRSensor);
        if(togglevalue){
            tbtnPIRSensor.setChecked(true);
        } else {
            tbtnPIRSensor.setChecked(false);
        }
    }

    ValueEventListener TokenListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            token = dataSnapshot.getValue().toString();
            if(token != ""){
                setSubscribeSwitch(true);
            }
            else{
                setSubscribeSwitch(false);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener PIRSensorListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            pir = dataSnapshot.getValue(Sensor.class);
            Log.d(TAG, "From FIREBASE PIRSensor Value: " + pir.PIRSensor);
            setButton(pir.PIRSensor);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.d(TAG, "Firebase Error: " + databaseError.getDetails());
        }
    };
}
