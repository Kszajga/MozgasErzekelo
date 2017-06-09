package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
    public Sensor sensor = new Sensor();

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
        mDatabase.addValueEventListener(TokenListener);
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
                if(sensor.PIRSensor){
                    setPIRSensor(false);
                    Log.d(TAG, "FALSE-RA ALLITAS!");
                    tbtnPIRSensor.setChecked(false);
                }
                else {
                    setPIRSensor(true);
                    Log.d(TAG, "TRUE-RA ALLITAS!");
                    tbtnPIRSensor.setChecked(true);
                }

                Log.d(TAG, "PIRSensor value changed: " + sensor.PIRSensor);
                Toast.makeText(MainActivity.this, "PIR Sensor value changed: " + !sensor.PIRSensor, Toast.LENGTH_SHORT).show();

            }
        });

        swSubscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setSubscribe(token);
                    Log.d(TAG, "Subscribe érték a Sensor-ban: " + sensor.subscribe);
                    //swSubscribe.setChecked(false);
                    Log.d(TAG, "Subscribe feliratkozás");
                }
                else {
                    setSubscribe("");
                    //swSubscribe.setChecked(true);
                    Log.d(TAG, "Subscribe leiratkozás");
                }
            }
        });

        /*swSubscribe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(sensor.subscribe != "" || sensor.subscribe != null){
                    setSubscribe("");
                    swSubscribe.setChecked(false);
                    Log.d(TAG, "Subscribe leiratkozás");
                }
                else {
                    setSubscribe(token);
                    swSubscribe.setChecked(true);
                    Log.d(TAG, "Subscribe feliratkozás");
                }


            }
        });*/
    }

    public void setPIRSensor(boolean PIRValue) {
        Log.d(TAG, "BEMENO PARAMETER ERTEKE: " + PIRValue);
        mDatabase.child("PIRSensor").setValue(PIRValue);
    }

    public void setSubscribe(String token){
        mDatabase.child("subscribe").setValue(token);
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
            sensor = dataSnapshot.getValue(Sensor.class);
            Log.d(TAG, "SUBSCRIBE ÉRTÉKE: " + sensor.subscribe);

            if(!sensor.subscribe.equals("") || !sensor.subscribe.equals(null)){
                Log.d(TAG, "SUBSCRIBE ÉRTÉKE: " + sensor.subscribe);
                setSubscribeSwitch(true);
            }
            else{
                setSubscribeSwitch(false);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.d(TAG, "Firebase Error: " + databaseError.getDetails());
        }
    };

    ValueEventListener PIRSensorListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            sensor = dataSnapshot.getValue(Sensor.class);
            Log.d(TAG, "From FIREBASE PIRSensor Value: " + sensor.PIRSensor);
            setButton(sensor.PIRSensor);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.d(TAG, "Firebase Error: " + databaseError.getDetails());
        }
    };
}
