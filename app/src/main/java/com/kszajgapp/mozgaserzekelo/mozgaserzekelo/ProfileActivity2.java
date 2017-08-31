package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kszajgapp.mozgaserzekelo.mozgaserzekelo.model.Sensor;

import java.util.Map;

public class ProfileActivity2 extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogout;
    private Button btnMasik;
    private Button btnNewDevice;

    private static final String TAG = "MainActivity";
    private DatabaseReference mDatabase;
    public Sensor sensor = new Sensor();
    private ListView mListView;

    //Get the token
    String token = FirebaseInstanceId.getInstance().getToken();

    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Firebase settings
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        // UI
        final ToggleButton tbtnPIRSensor = (ToggleButton) findViewById(R.id.tbtn_PIRSensor);
        Switch swSubscribe = (Switch) findViewById(R.id.switch_Subscribe);
        Button btn_deletelogs = (Button) findViewById(R.id.btn_delete_logs);
        Button btnShowToken = (Button) findViewById(R.id.btn_show_token);
        btnMasik = (Button) findViewById(R.id.btn_Masik);
        Spinner spinner_filter_logs  = (Spinner) findViewById(R.id.spinner_filter_logs);
        btnLogout = (Button) findViewById(R.id.btn_Logout);
        btnNewDevice = (Button) findViewById(R.id.btn_NewDevice);

        // Events, listeners
        mDatabase.addValueEventListener(PIRSensorListener);
        mDatabase.addValueEventListener(TokenListener);
        mDatabase.addValueEventListener(LogListener);

        DatabaseReference logRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mozgaserzekelo-98d28.firebaseio.com/logs");
        mListView = (ListView) findViewById(R.id.logitems);
        //Adatlista elkészítése
        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_list_item_1,
                logRef
        ){
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                Log.d(TAG, "LISTADAPTER " + model);
                textView.setText(model);
            }
        };
        mListView.setAdapter(firebaseListAdapter);

        //Log szűréshez Spinner filter_logs_adapter és feltöltése
        ArrayAdapter<CharSequence> filter_logs_adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_logs_array, android.R.layout.simple_spinner_item);
        filter_logs_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_filter_logs.setAdapter(filter_logs_adapter);
        LogFilterOnItemSelectedListener l = new LogFilterOnItemSelectedListener();
        spinner_filter_logs.setOnItemSelectedListener(l);

        //Log törlése gomb funkció
        btn_deletelogs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "LOG TÖRLÉSE");
                mDatabase.child("logs").removeValue();
            }
        });

        btnLogout.setOnClickListener(this);
        btnMasik.setOnClickListener(this);
        btnNewDevice.setOnClickListener(this);

        btnShowToken.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG, "Token: " + token);
                Toast.makeText(ProfileActivity2.this, token, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ProfileActivity2.this, "PIR Sensor value changed: " + !sensor.PIRSensor, Toast.LENGTH_SHORT).show();

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

            if(token.equals(sensor.subscribe)){
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

    ValueEventListener LogListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            sensor = dataSnapshot.getValue(Sensor.class);
            Log.d(TAG, "LOGOLÁS FIREBASEBŐL");

            //Egyenkénti kilistázása a log bejegyzéseknek
            for (Map.Entry<String, String> entry : sensor.logs.entrySet())
            {
                //Log.d(TAG,(entry.getKey() + "/" + entry.getValue()));
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View v) {
        if(v == btnLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        if(v == btnMasik){
            finish();
            startActivity(new Intent(this, UserProfile.class));
        }

        if(v == btnNewDevice){

        }
    }
}
