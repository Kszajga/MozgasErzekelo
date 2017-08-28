package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kszajgapp.mozgaserzekelo.mozgaserzekelo.model.Sensor;
import com.kszajgapp.mozgaserzekelo.mozgaserzekelo.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private DatabaseReference userDatabaseReference;
    public Sensor sensor = new Sensor();
    private ListView mListView;

    private Button btnRegister;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvLogin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    //Get the token
    String token = FirebaseInstanceId.getInstance().getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI
        btnRegister = (Button) findViewById(R.id.btn_RegisterUser);
        etEmail = (EditText) findViewById(R.id.et_Email);
        etPassword = (EditText) findViewById(R.id.et_Password);
        tvLogin = (TextView) findViewById(R.id.tv_Login);

        // Firebase settings
        firebaseAuth = FirebaseAuth.getInstance();
        userDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mozgaserzekelo-98d28.firebaseio.com/users");

        // If user already signed in, show profile
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        //Events and listeners
        progressDialog = new ProgressDialog(this);
        userDatabaseReference.addValueEventListener(userListener);

        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

    }


    private void registerUser(){
        final String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String key = userDatabaseReference.push().getKey();
                        userDatabaseReference.child(key).setValue(email);
                        Log.d(TAG, "User keyID: " + key);
                        progressDialog.hide();
                        if(task.isSuccessful()){
                            //regisztrálás OK

                                //alredy signed in
                                //profile activity here
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                            Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public void onClick(View view){
        if(view == btnRegister){
            registerUser();
        }

        if(view == tvLogin){
            //Login lesz
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
//            User user = dataSnapshot.getValue(User.class);
//            Log.d(TAG, user.email);
//            Log.d(TAG, user.devices.toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
